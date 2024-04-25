import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Level_1 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("src/input.txt"));
        Files.deleteIfExists(Path.of("src/output.txt"));
        Files.createFile(Path.of("src/output.txt"));

        solve(input);
    }

    private static void solve(List<String> input) {
        input.stream().skip(1).forEach(line -> {
            int w = line.length() - line.replaceAll("W", "").length();
            int a = line.length() - line.replaceAll("A", "").length();
            int s = line.length() - line.replaceAll("S", "").length();
            int d = line.length() - line.replaceAll("D", "").length();


            try (BufferedWriter out = Files.newBufferedWriter(Path.of("src/output.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                out.write(w + " " + d + " " + s + " " + a + System.lineSeparator());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
