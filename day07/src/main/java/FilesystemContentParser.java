import java.util.List;

public class FilesystemContentParser {
    private static final String ROOT_DIRECTORY_NAME = "/";
    private static final String COMMAND_PREFIX = "$ ";
    private static final String CHANGE_DIRECTORY_COMMAND_PREFIX = "cd ";
    private static final String PARENT_DIRECTORY_FLAG = "..";
    private static final String LIST_COMMAND = "ls";
    private static final String DIRECTORY_FLAG = "dir";

    public Directory parse(List<String> lines) {
        Directory rootDirectory = new Directory(null, ROOT_DIRECTORY_NAME);
        ParseContext context = new ParseContext(lines, 0, rootDirectory);
        while (!context.isFinished()) {
            context = parseCommandOutput(context);
        }
        return rootDirectory;
    }

    private ParseContext parseCommandOutput(ParseContext context) {
        String line = context.getCurrentLine();
        if (isNotCommand(line)) {
            throw new IllegalArgumentException("Invalid command line: " + line);
        }
        String command = line.substring(COMMAND_PREFIX.length()).trim();
        if (command.startsWith(CHANGE_DIRECTORY_COMMAND_PREFIX)) {
            String newDirectoryName = command.substring(CHANGE_DIRECTORY_COMMAND_PREFIX.length()).trim();
            Directory newDirectory = processChangeDirectoryCommand(context.currentDirectory(), newDirectoryName);
            return new ParseContext(context.lines(), context.index() + 1, newDirectory);
        }
        if (command.equals(LIST_COMMAND)) {
            return processListCommand(context);
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }

    private Directory processChangeDirectoryCommand(Directory current, String newDirectoryName) {
        if (newDirectoryName.equals(ROOT_DIRECTORY_NAME)) {
            Directory result = current;
            while (result.getParent() != null) {
                result = result.getParent();
            }
            return result;
        }
        if (newDirectoryName.equals(PARENT_DIRECTORY_FLAG)) {
            return current.getParent();
        }
        return current.getChild(newDirectoryName)
                .filter(FilesystemNode::isDirectory)
                .map(Directory.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("Missing directory: " + newDirectoryName));
    }

    private ParseContext processListCommand(ParseContext context) {
        Directory currentDirectory = context.currentDirectory();
        int index = context.index() + 1;
        String line;
        do {
            line = context.lines().get(index);
            if (isNotCommand(line)) {
                String[] itemInfo = line.split(" ", 2);
                if (itemInfo[0].equals(DIRECTORY_FLAG)) {
                    currentDirectory.addChild(new Directory(currentDirectory, itemInfo[1]));
                } else {
                    currentDirectory.addChild(new File(currentDirectory, itemInfo[1], Long.parseLong(itemInfo[0])));
                }
                index++;
            }
        } while (index < context.lines().size() && isNotCommand(line));
        return new ParseContext(context.lines(), index, currentDirectory);
    }

    private boolean isNotCommand(String line) {
        return !line.startsWith(COMMAND_PREFIX);
    }

    private record ParseContext(List<String> lines, int index, Directory currentDirectory) {

        boolean isFinished() {
            return index >= lines.size();
        }

        String getCurrentLine() {
            return lines.get(index);
        }
    }
}
