package function;

public class ArrayTabulatedFunction implements TabulatedFunction{
    private FunctionPoint[] points;
    private int size;

    public ArrayTabulatedFunction(FunctionPoint[] inputPoints) throws IllegalArgumentException{
        if(inputPoints.length < 2){
            throw new IllegalArgumentException("Invalid parameters");
        }

        points = new FunctionPoint[inputPoints.length + 5];
        points[0] = new FunctionPoint(inputPoints[0]);
        for(int i = 1; i < inputPoints.length; i++){
            if(inputPoints[i].getX() <= inputPoints[i - 1].getX()){
                throw new IllegalArgumentException("Invalid parameters");
            }
            points[i] = new FunctionPoint(inputPoints[i]);
        }
        size = inputPoints.length;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if(leftX >= rightX || pointsCount < 2){
            throw new IllegalArgumentException("Invalid parameters");
        }

        size = pointsCount;
        points = new FunctionPoint[size + 5];

        double step = (rightX - leftX) / (size - 1);

        for (int i = 0; i < size; i++) {
            double x = leftX + step * i;
            points[i] = new FunctionPoint(x, 0);
        }
    }


    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException{
        if(leftX >= rightX || values.length < 2){
            throw new IllegalArgumentException("Invalid parameters");
        }

        size = values.length;
        points = new FunctionPoint[size + 5];

        double step = (rightX - leftX) / (size - 1);

        for (int i = 0; i < size; i++) {
            double x = leftX + step * i;
            points[i] = new FunctionPoint(x, values[i]);
        }
    }

    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    public double getRightDomainBorder(){
        return points[size - 1].getX();
    }

    public double getFunctionValue(double x){
        if(x < points[0].getX() || x > points[size - 1].getX()){
            return Double.NaN;
        }
        int leftX = 0;
        int rightX = size - 1;

        while (leftX < rightX){
            if(x >= points[leftX].getX() && x <= points[leftX + 1].getX()){
                break;
            }
            leftX++;
        }

        double x0 = points[leftX].getX();
        double x1 = points[leftX + 1].getX();
        double y0 = points[leftX].getY();
        double y1 = points[leftX + 1].getY();

        return y0 + ((y1 - y0) * (x - x0) / (x1 - x0));
    }

    public int getPointsCount(){
        return size;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        return points[index];
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        if(points[index - 1].getX() > point.getX() || point.getX() > points[index + 1].getX()){
            throw new InappropriateFunctionPointException("Inappropriate point");
        }

        points[index] = point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        return points[index].getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        if(points[index - 1].getX() > x || x > points[index + 1].getX()){
            throw new InappropriateFunctionPointException("Inappropriate point");
        }

        points[index].setX(x);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        return points[index].getY();
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }
        if(points[index-1].getY() <= y && y <= points[index+1].getY()) {
            points[index].setY(y);
        }
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalArgumentException{
        if(index < 0 || index >= size - 1){
            throw new FunctionPointIndexOutOfBoundsException("Index is out of bounds");
        }

        if(size<3){
            throw new IllegalArgumentException("Can't delete point");
        }

        System.arraycopy(points, index+1, points, index, size-index-1 );
        size --;
    }

    public void addPoint (FunctionPoint point) throws InappropriateFunctionPointException{
        int index;

        if(point.getX() <= points[size - 1].getX()){
            for(index = 0; point.getX() > points[index].getX(); ++index) {
                if(point.getX() == points[index].getX()){
                    throw new InappropriateFunctionPointException("Inappropriate point");
                }
            }
        }else index = size;

        if(size != points.length){
            System.arraycopy(points, index, points, index+1, size-index);
            points[index]=point;
        }else{
            FunctionPoint[] NewPoints = new FunctionPoint[size + 5];
            System.arraycopy(points, 0, NewPoints, 0, index);
            NewPoints[index] = point;
            System.arraycopy(points, index, NewPoints, index + 1, size -
                    index);
            points = NewPoints;
        }
        ++size;
    }
}
