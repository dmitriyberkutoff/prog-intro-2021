package game;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Tournament {
    private final Map<String, Integer> pointCount;
    private final Player[] players;
    private final BufferedWriter writer;


    public Tournament(Player[] players, String name) throws IOException {
        this.players = players;
        this.pointCount = new HashMap<>();
        for (int i = 1; i < players.length + 1; i++) {
            this.pointCount.put("Player " + i, 0);
        }

        var f = new File("game/").toPath().resolve(name).toFile();
        System.out.println(f.getAbsolutePath());
        writer = new BufferedWriter(new OutputStreamWriter(new
                FileOutputStream(f), StandardCharsets.UTF_8));
        writer.write("Tournament for " + players.length + " players" + '\n');
    }

    public void play(AbstractBoard board) throws IOException {
        int p1, p2;
        for (int i = 0; i < players.length-1; i++) {
            for (int j = i+1; j < players.length; j++) {
                int cntWins = 0;
                for (int t = 0; t < 2; t++) {
                    if (t == 0) {
                        p1 = i + 1;
                        p2 = j + 1;
                    } else {
                        p1 = j + 1;
                        p2 = i + 1;
                    }
                    int result = new TwoPlayerGame(players[p1-1], players[p2-1], true).play(board);

                    System.out.println("Position:");
                    System.out.println(board.toString());
                    board.clear();

                    if (result == 1) {
                        if (t == 0) {
                            cntWins++;
                        }
                        System.out.println("Player " + p1 + " won round versus Player " + p2);
                        pointCount.replace("Player " + p1, pointCount.get("Player " + p1) + 3);
                    } else if (result == 2) {
                        if (t == 1) {
                            cntWins++;
                        }
                        System.out.println("Player " + p2 + " won round versus Player " + p1);
                        pointCount.replace("Player " + p2, pointCount.get("Player " + p2) + 3);
                    } else if (result == 0) {
                        System.out.println("Draw between Player " + p1 + " and Player " + p2);
                        pointCount.replace("Player " + p1, pointCount.get("Player " + p1) + 1);
                        pointCount.replace("Player " + p2, pointCount.get("Player " + p2) + 1);
                    }
                    try {
                        if (t == 1) {
                            writer.write("Player " + p2 + " vs Player " + p1 + ": " + cntWins + " " + (2 - cntWins) + '\n');
                        }
                    } catch (IOException e) {
                        System.out.println("Can not write in file" + e.getMessage());
                        writer.close();
                    }
                }
            }
        }
        try {
            for (int i = 1; i < pointCount.size()+1; i++) {
                writer.write("Player " + i + " has " + pointCount.get("Player " + i) + " points" + '\n');
            }
        } catch (IOException e) {
            System.out.println("Can not write in file" + e.getMessage());
            writer.close();
        }
        writer.close();
    }
}