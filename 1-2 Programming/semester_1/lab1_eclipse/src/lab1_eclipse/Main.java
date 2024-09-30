package lab1_eclipse;
import java.lang.Math;
import java.util.Random;


public class Main{
    public static void main(String[] args){
        short[] k = new short[11];
        float[] x = new float[15];
        double[][] d = new double[11][15];
        float x1;
        Random r = new Random();
        for(int i = 0; i<11; i++){
            k[i]=(short)(3+2*i);
        }
        for(int i = 0; i < 15; i++){
            x[i] = -5F + r.nextFloat()*(3F - (-5F));
        }
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 15; j++){
                x1 = x[j];
                if(k[i] == 11)
                    d[i][j] = Math.pow(Math.pow((25+x1)/x1, x1), 2*Math.asin((x1-1)/8));
                else if(k[i] == 3 || k[i] == 7 || k[i] == 9|| k[i] == 13 || k[i] == 23)
                    d[i][j] = Math.asin(1/Math.pow(Math.E, (1/(3/5+Math.pow(Math.abs(x1),0.5)))));
                else d[i][j] = 0.25*Math.pow((Math.log(Math.pow(Math.tan(x1),2))*Math.pow((Math.PI + Math.pow(Math.sin(x1)/(Math.asin((x1-1)/8)-0.5),3)), 3)), 3);
                System.out.print(String.format("%.3f", d[i][j]) + " ");
            }
            System.out.println("\n");
        }

    }
}
