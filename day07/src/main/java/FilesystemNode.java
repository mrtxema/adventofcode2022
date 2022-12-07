public interface FilesystemNode {

    Directory getParent();

    String getName();

    boolean isDirectory();
}
