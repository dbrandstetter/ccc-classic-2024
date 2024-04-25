import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Level_3 {

    private static final Set<Pos> positions = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("src/input.txt"));
        Files.deleteIfExists(Path.of("src/output.txt"));
        Files.createFile(Path.of("src/output.txt"));

        solve(input);
    }

    private static void solve(List<String> input) {
        for (int i = 1; i < input.size(); ) {
            int dimX = Integer.parseInt(input.get(i).split(" ")[0]);
            System.out.println("dimX = " + dimX);
            int dimY = Integer.parseInt(input.get(i).split(" ")[1]);
            System.out.println("dimY = " + dimY);

            positions.clear();

            char[] direction = input.get(i + dimY + 1).toCharArray();

            int currentX = 0;
            int currentY = 0;
            int highestX = 0;
            int lowestX = 0;
            int highestY = 0;
            int lowestY = 0;

            for (char c : direction) {
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
            }

            int x_dim = Math.abs(lowestX) + Math.abs(highestX);
            int y_dim = Math.abs(lowestY) + Math.abs(highestY);

            int startX = Math.abs(lowestX);
            int staticfinal_startX = startX;
            System.out.println("startX = " + startX);
            int startY = Math.abs(lowestY);
            int staticfinal_startY = startY;
            System.out.println("startY = " + startY);

            LinkedList<String> lines = new LinkedList<>();
            for (int j = i + 1; j < i + 1 + dimY; j++) {
                lines.add(input.get(j));
            }

            i += dimY + 2;

            char[][] lawn = new char[dimY][dimX];
            if (dimX == 20) {
                System.out.println("lawn[0][17] = " + lawn[0][17]);
            }
            for (int j = 0; j < dimY; j++) {
                for (int k = 0; k < dimX; k++) {
                    //                    System.out.println("lines.get(j) = " + lines.get(j));
                    //                    System.out.println("k = " + k);
                    try {
                        lawn[j][k] = lines.get(j).charAt(k);
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                }
            }

            for (char[] row : lawn) {
                for (char c : row) {
                    System.out.print(c + " ");
                }
                System.out.println();
            }

            boolean exit = false;
            // Rand überprüfen
            if (startX < 0 || startX > dimX - 1 || startY < 0 || startY > dimY - 1) {
                System.out.println("Über dem Rand");
                try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                    out.write("INVALID" + System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                exit = true;
            } else if (lawn[dimY - startY - 1][startX] == 'X') {
                System.out.println("BAUM BEI:");
                System.out.println(startX);
                System.out.println(startY);
                System.out.println("Baum überfahren");
                try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                    out.write("INVALID" + System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                exit = true;
            } else {
                lawn[dimY - startY - 1][startX] = '1';

                for (char c : direction) {
                    switch (c) {
                        case 'A':
                            startX--;

                            break;
                        case 'S':
                            startY--;

                            break;
                        case 'D':
                            startX++;

                            break;
                        case 'W':
                            startY++;

                            break;
                    }

                    // Rand überprüfen
                    if (startX < 0 || startX > dimX - 1 || startY < 0 || startY > dimY - 1) {
                        System.out.println("Über dem Rand");
                        try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                            out.write("INVALID" + System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        exit = true;
                    }

                    if (exit) {
                        break;
                    }

                    // Baum überprüfen
                    if (lawn[dimY - startY - 1][startX] == 'X') {
                        System.out.println("BAUM BEI:");
                        System.out.println(startX);
                        System.out.println(startY);
                        System.out.println("Baum überfahren");
                        try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                            out.write("INVALID" + System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        exit = true;
                        break;
                    }

                    if (exit) {
                        break;
                    }

                    lawn[dimY - startY - 1][startX] = '1';

                    // Doppelte pos überprüfen
                    Pos currPos = new Pos(startX, startY);
                    //                System.out.println("currPos = " + currPos);

                    if (!positions.add(currPos)) {
                        System.out.println("Doppelte pos");
                        try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                            out.write("INVALID" + System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        exit = true;
                        break;
                    }

                    if (exit) {
                        break;
                    }
                }
            }


            // Nicht gefahrene Felder prüfen
            boolean fieldNotMowed = false;
            if (!exit) {
                for (char[] row : lawn) {
                    for (char cha : row) {
                        if (cha == '.') {
                            System.out.println("Feld nicht gemäht");
                            try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                                out.write("INVALID" + System.lineSeparator());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            fieldNotMowed = true;
                            break;
                        }
                    }
                    if (fieldNotMowed) {
                        break;
                    }
                }

                if (!exit && !fieldNotMowed) {
                    try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                        out.write("VALID" + System.lineSeparator());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }

    static class Pos {
        private final int dimX;
        private final int dimY;

        public Pos(int dimX, int dimY) {
            this.dimX = dimX;
            this.dimY = dimY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Pos pos = (Pos) o;
            return dimX == pos.dimX && dimY == pos.dimY;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dimX, dimY);
        }

        @Override
        public String toString() {
            return "Pos{" + "dimX=" + dimX + ", dimY=" + dimY + '}';
        }
    }
}
