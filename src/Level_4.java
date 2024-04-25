import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Level_4 {

    private static final Set<Pos> positions = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("src/input.txt"));
        Files.deleteIfExists(Path.of("src/output.txt"));
        Files.createFile(Path.of("src/output.txt"));

        solve(input);
    }

    private static void solve(List<String> input) {
        for (int i = 1; i < input.size(); ) {
            positions.clear();

            int dimX = Integer.parseInt(input.get(i).split(" ")[0]);
            System.out.println("dimX = " + dimX);
            int dimY = Integer.parseInt(input.get(i).split(" ")[1]);
            System.out.println("dimY = " + dimY);


            LinkedList<String> lines = new LinkedList<>();
            for (int j = i + 1; j < i + 1 + dimY; j++) {
                lines.add(input.get(j));
            }

            char[][] lawn = new char[dimY][dimX];
            for (int j = 0; j < dimY; j++) {
                for (int k = 0; k < dimX; k++) {
                    try {
                        lawn[j][k] = lines.get(j).charAt(k);
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                }
            }

            for (int j = 0; j < dimY; j++) {
                for (int k = 0; k < dimX; k++) {
                    Set<Pos> solution = recurse(j, k, dimX, dimY, lawn);
                }
            }

            i += dimY + 1;
        }
    }

    private static Set<Pos> recurse(int x, int y, int dimX, int dimY, char[][] lawn) {
        boolean emptyFieldLeft = false;

        Pos currPos = new Pos(x, y);

        if (!positions.add(currPos)) {
            System.out.println("Doppelte pos");
            try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                out.write("INVALID" + System.lineSeparator());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        for (int i = 0; i < dimY; i++) {
            for (int j = 0; j < dimX; j++) {
                if (!positions.contains(new Pos(i, j))) {
                    emptyFieldLeft = true;
                    break;
                }
            }
        }

        if (!emptyFieldLeft) {
            return positions;
        }

        // if (lawn[x + 1][y]) {

        // }

        return null;
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
