import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Directory implements FilesystemNode {
    private final Directory parent;
    private final String name;
    private final Map<String, FilesystemNode> children;

    public Directory(Directory parent, String name) {
        this.parent = parent;
        this.name = name;
        this.children = new HashMap<>();
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addChild(FilesystemNode child) {
        children.put(child.getName(), child);
    }

    public Optional<FilesystemNode> getChild(String name) {
        return Optional.ofNullable(children.get(name));
    }

    public List<FilesystemNode> getChildren() {
        return new ArrayList<>(children.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Directory directory)) {
            return false;
        }
        return Objects.equals(parent, directory.parent) && name.equals(directory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, name);
    }
}
