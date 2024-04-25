import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class Level_2 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("src/input.txt"));
        Files.deleteIfExists(Path.of("src/output.txt"));
        Files.createFile(Path.of("src/output.txt"));

        solve(input);
    }

    private static void solve(List<String> input) {
        input.stream().skip(1).forEach(line -> {
//            int w = line.length() - line.replaceAll("W", "").length();
//            int a = line.length() - line.replaceAll("A", "").length();
//            int s = line.length() - line.replaceAll("S", "").length();
//            int d = line.length() - line.replaceAll("D", "").length();
            char[] direction = line.toCharArray();
            int currentX = 0;
            int currentY = 0;
            int highestX = 0;
            int lowestX = 0;
            int highestY = 0;
            int lowestY = 0;

            for (char c : direction) {
                System.out.println("c = " + c);
                switch (c) {
                    case 'A':
                        currentX--;

                        if (currentX < lowestX) {
                            lowestX = currentX;
                        }

                        break;
                    case 'S':
                        currentY--;

                        if (currentY < lowestY) {
                            lowestY = currentY;
                        }

                        break;
                    case 'D':
                        currentX++;

                        if (currentX > highestX) {
                            highestX = currentX;
                        }

                        break;
                    case 'W':
                        currentY++;

                        if (currentY > highestY) {
                            highestY = currentY;
                        }

                        break;
                }

                System.out.println("currentPos: = " + currentX + ", " + currentY);

            }

            System.out.println("Math.abs(lowestX) = " + lowestX);
            System.out.println("Math.abs(lowestY) = " + lowestY);
            System.out.println("Math.abs(highestX) = " + highestX);
            System.out.println("Math.abs(highestY) = " + highestY);
            System.out.println("------------------");

            int x_dim = Math.abs(lowestX) + Math.abs(highestX);
            int y_dim = Math.abs(lowestY) + Math.abs(highestY);

            try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                out.write((x_dim + 1) + " " + (y_dim + 1) + System.lineSeparator());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
