import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class IOUtils {

    private IOUtils() {
    }

    public static List<String> readLines(URL resource) throws IOException {
        return readLines(resource, Function.identity());
    }

    public static List<String> readTrimmedLines(URL resource) throws IOException {
        return readLines(resource, String::trim);
    }

    private static List<String> readLines(URL resource, Function<String, String> lineTransformer) throws IOException {
        try (Stream<String> lines = Files.lines(getFilePath(resource))) {
            return lines.map(lineTransformer).toList();
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
