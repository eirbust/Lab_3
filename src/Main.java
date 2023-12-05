import function.*;

public class Main {
    public static void main(String[] args){
        double[] values = {1.0, 2.0, 3.0, 4.0};

        TabulatedFunction fun = new LinkedListTabulatedFunction(0.0, 9.0, values);

        for(int i = 0; i < fun.getPointsCount(); i++){
            System.out.println(fun.getPointX(i) + "-" + fun.getPointY(i));
        }

        try {
            fun.getPointX(2);
        }catch (FunctionPointIndexOutOfBoundsException x){
            System.out.println(x.getMessage());
        }
    }
}