package function.meta;

import function.Function;

public class Shift implements Function {
    private Function func;
    private double shiftX;
    private double shiftY;

    public Shift(Function func, double shiftX, double shiftY){
        this.func = func;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder() + shiftX;
    }

    public double getRightDomainBorder() {
        return func.getRightDomainBorder() + shiftX;
    }

    public double getFunctionValue(double x) {
        return func.getFunctionValue(x - shiftX) + shiftY;
    }
}
