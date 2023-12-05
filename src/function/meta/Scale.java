package function.meta;

import function.Function;

public class Scale implements Function {
    private Function func;
    private double scaleX;
    private double scaleY;

    public Scale(Function func, double scaleX, double scaleY){
        this.func = func;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder() * scaleX;
    }

    public double getRightDomainBorder() {
        return func.getRightDomainBorder() * scaleX;
    }

    public double getFunctionValue(double x) {
        return func.getFunctionValue(x * scaleX) * scaleY;
    }
}
