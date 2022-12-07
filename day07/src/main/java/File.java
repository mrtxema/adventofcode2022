public class File implements FilesystemNode {
    private final Directory parent;
    private final String name;
    private final long size;

    public File(Directory parent, String name, long size) {
        this.parent = parent;
        this.name = name;
        this.size = size;
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
        return false;
    }

    public long getSize() {
        return size;
    }
}
