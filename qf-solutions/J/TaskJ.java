import java.util.Scanner;

public class TaskJ {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;
        int n = in.nextInt();
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            line = in.next();
            for (int j = 0; j < n; j++) {
                graph[i][j] = line.charAt(j) - '0';
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (graph[i][j] == 1) {
                    for (int k = j+1; k < n; k++) {
                        graph[i][k] = ((graph[i][k] - graph[j][k])%10+10)%10;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(graph[i][j]);
            }
            System.out.println();
        }
        in.close();
    }
}