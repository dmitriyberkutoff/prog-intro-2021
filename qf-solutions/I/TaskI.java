import java.util.Scanner;
import java.lang.Math;

public class TaskI {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;
        int n = in.nextInt();
        int xi, yi, hi;
        int xLeft = Integer.MAX_VALUE;
        int xRight = Integer.MIN_VALUE;
        int yLeft = Integer.MAX_VALUE;
        int yRight = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            line = in.nextLine();
            xi = in.nextInt();
            yi = in.nextInt();
            hi = in.nextInt();
            if ((xi - hi) < xLeft) {
                xLeft = xi - hi;
            }
            if ((xi + hi) > xRight) {
                xRight = xi + hi;
            }
            if ((yi - hi) < yLeft) {
                yLeft = yi - hi;
            }
            if ((yi + hi) > yRight) {
                yRight = yi + hi;
            }
        }
        System.out.print(Integer.toString((xLeft+xRight)/2) + " ");
        System.out.print(Integer.toString((yLeft+yRight)/2) + " ");
        System.out.println((Math.max((xRight-xLeft), (yRight-yLeft)) + 1)/2);
        in.close();
    }
}