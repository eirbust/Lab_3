package function;

public class LinkedListTabulatedFunction implements TabulatedFunction{

    private static class FunctionNode{
        FunctionPoint data;
        FunctionNode next;
        FunctionNode prev;

        FunctionNode(){
            this.data = null;
            this.next = null;
            this.prev = null;
        }

        FunctionNode(FunctionPoint data, FunctionNode next, FunctionNode prev){
            this.data = new FunctionPoint(data);
            this.next = next;
            this.prev = prev;
        }

        FunctionNode(FunctionNode node){
            this.data = new FunctionPoint(node.data);
            this.next = node.next;
            this.prev = node.prev;
        }
    }

    private FunctionNode Head;
    private int CountOfNodes;
    private FunctionNode tmpNode;

    private FunctionNode getNodeByIndex(int index){
        FunctionNode tmp = Head.next;
        for(int i=0; i<index; i++){
            tmp=tmp.next;
        }
        return tmp;
    }

    private FunctionNode addNodeToTail(){
        FunctionNode newNode = new FunctionNode(new FunctionPoint(), Head.next, Head.prev);
        Head.next.prev = newNode;
        Head.prev.next = newNode;
        Head.prev = newNode;
        CountOfNodes++;
        tmpNode = newNode;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index){
        FunctionNode indexNode = getNodeByIndex(index);
        FunctionNode newNode = new FunctionNode(new FunctionPoint(), indexNode.next, indexNode.prev);
        indexNode.prev.next = newNode;
        indexNode.prev = newNode;
        CountOfNodes++;
        tmpNode = newNode;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index){
        FunctionNode deleteNode = getNodeByIndex(index);
        if(index == 0) Head.next = deleteNode.next;
        else if(index == CountOfNodes -1) Head.prev = deleteNode.prev;
        deleteNode.prev.next = deleteNode.next;
        deleteNode.next.prev = deleteNode.prev;
        CountOfNodes--;
        return deleteNode;
    }

    private void TwoFirstElements(FunctionPoint first, FunctionPoint second){
        Head = new FunctionNode();
        CountOfNodes = 2;
        FunctionNode firstNode = new FunctionNode(first, null, null);
        FunctionNode secondNode = new FunctionNode(second, firstNode, firstNode);
        Head.next = firstNode;
        Head.prev = secondNode;
        firstNode.next = secondNode;
        firstNode.prev = secondNode;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException{
        if(points.length < 2){
            throw new IllegalArgumentException("Invalid parameters");
        }
        TwoFirstElements(new FunctionPoint(points[0]), new FunctionPoint(points[1]));
        for(int i = 2; i < points.length; i++){
            if(points[i].getX() <= points[i-1].getX()){
                throw new IllegalArgumentException("Invalid parameters");
            }
            addNodeToTail();
            Head.prev.data.setX(points[i].getX());
            Head.prev.data.setY(points[i].getY());
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if (leftX >= rightX || pointsCount < 2){
            throw new IllegalArgumentException("Illegal argument");
        }

        double step = (rightX-leftX)/(pointsCount - 1);
        TwoFirstElements(new FunctionPoint(leftX, 0), new FunctionPoint(leftX + step, 0));
        leftX += 2 * step;

        for (int i = 2; i < pointsCount; i++){
            addNodeToTail();
            Head.prev.data.setX(leftX);
            leftX += step;
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException{
        if (leftX >= rightX || values.length<2){
            throw new IllegalArgumentException("Illegal argument");
        }

        double step = (rightX-leftX)/(values.length - 1);
        TwoFirstElements(new FunctionPoint(leftX, values[0]), new FunctionPoint(leftX + step, values[1]));
        leftX += 2 * step;

        for (int i = 2; i<values.length; i++){
            addNodeToTail();
            Head.prev.data.setX(leftX);
            Head.prev.data.setY(values[i]);
            leftX += step;
        }
    }

    public double getLeftDomainBorder(){
        return Head.next.data.getX();
    }

    public double getRightDomainBorder(){
        return Head.prev.data.getX();
    }

    public double getFunctionValue(double x){
        if(x >= getLeftDomainBorder() && x <= getRightDomainBorder()){

            double x1 = getLeftDomainBorder();
            double x2 = getRightDomainBorder();
            double y1 = Head.next.data.getY();
            double y2 = Head.prev.data.getY();

            return (((x - x1) * (y2 - y1)) / (x2 - x1)) + y1;
        }
        else{
            return Double.NaN;
        }
    }

    public int getPointsCount() {
        return CountOfNodes;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if(index<0 || index>=CountOfNodes){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        FunctionNode tmp = getNodeByIndex(index);
        return new FunctionPoint(tmp.data.getX(), tmp.data.getY());
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= CountOfNodes){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        FunctionNode tmp = getNodeByIndex(index);

        if(tmp.prev.data.getX() > point.getX() || point.getX() > tmp.next.data.getX()){
            throw new InappropriateFunctionPointException("Inappropriate point");
        }

        tmp.data = point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if(index < 0 || index >= CountOfNodes){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }
        return getNodeByIndex(index).data.getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index >= CountOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        FunctionNode tmp = getNodeByIndex(index);
        if(tmp.prev.data.getX() <= x && x <= tmp.next.data.getX()){
            tmp.data.setX(x);
        }
        else {
            throw new InappropriateFunctionPointException("Inappropriate point");
        }
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if(index<0 || index>=CountOfNodes){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }
        return getNodeByIndex(index).data.getY();
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index >= CountOfNodes){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }
        getNodeByIndex(index).data.setY(y);
    }

    public void deletePoint(int index) {
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode tmp = Head.next;
        int index = 0;

        while(point.getX() > tmp.data.getX() && tmp != Head.prev) {
            tmp = tmp.next;
            index++;
        }
        if(point.getX() == tmp.data.getX()){
            throw new InappropriateFunctionPointException("Inappropriate point");
        }

        if(tmp == Head.prev){
            if(point.getX() > Head.prev.data.getX())
                addNodeToTail();
            else
                addNodeByIndex(CountOfNodes-1);
        }
        else {
            addNodeByIndex(index);
        }
        tmpNode.data.setX(point.getX());
        tmpNode.data.setY(point.getY());
    }
}
