import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DirectorySizeCalculator {
    private final Map<Directory, Long> directorySizeCache = new HashMap<>();

    public DirectorySizeCalculator(Directory rootDirectory) {
        calculateDirectorySize(rootDirectory);
    }

    private long calculateDirectorySize(Directory directory) {
        return directory.getChildren().stream().mapToLong(this::getNodeSize).sum();
    }

    private long getNodeSize(FilesystemNode filesystemNode) {
        if (filesystemNode.isDirectory()) {
            return getDirectorySize((Directory) filesystemNode);
        } else {
            return ((File) filesystemNode).getSize();
        }
    }

    public Set<Directory> getAllDirectories() {
        return Collections.unmodifiableSet(directorySizeCache.keySet());
    }

    public long getDirectorySize(Directory directory) {
        Long cachedResult = directorySizeCache.get(directory);
        if (cachedResult != null) {
            return cachedResult;
        }
        long result = calculateDirectorySize(directory);
        directorySizeCache.put(directory, result);
        return result;
    }
}
