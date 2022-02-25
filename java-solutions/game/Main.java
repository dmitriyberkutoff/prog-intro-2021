package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            new Tournament(new Player[]{new HumanPlayer(new Scanner(System.in)), new RandomPlayer(11, 18), new SequentialPlayer(11, 18)}, "tournament.txt").play(new HexBoard(11, 4));
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Can not write in file" + e.getMessage());
        }
        System.out.println();
    }
}
