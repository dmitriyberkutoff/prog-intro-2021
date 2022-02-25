import java.util.Scanner;

public class TaskB {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int ans = -710*25000;
        for (int i = 0; i < n; i++) {
            System.out.println(ans);
            ans += 710;
        }
        in.close();
    }
}