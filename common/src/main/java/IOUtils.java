import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public final class IOUtils {

    private IOUtils() {
    }

    public static List<String> readLines(URL resource) throws IOException {
        try (Stream<String> lines = Files.lines(getFilePath(resource))) {
            return lines.map(String::trim).toList();
        }
    }

    private static Path getFilePath(URL resource) throws IOException {
        if (resource == null) {
            throw new IOException("Missing file: " + resource);
        }
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
