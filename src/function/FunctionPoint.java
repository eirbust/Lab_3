package function;

public class FunctionPoint {
    private double coordX, coordY;

    public FunctionPoint(double x, double y){
        coordX = x;
        coordY = y;
    }

    public FunctionPoint(FunctionPoint point){
        coordX = point.coordX;
        coordY = point.coordY;
    }

    public FunctionPoint(){
        coordX = coordY = 0;
    }

    public double getX(){
        return coordX;
    }

    public double getY(){
        return coordY;
    }

    public void setX(double x){
        this.coordX = x;
    }

    public void setY(double y){
        this.coordY = y;
    }
}
