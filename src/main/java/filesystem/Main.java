package filesystem;

public class Main {

    public static void main(String[] args) {
        DirectoryNode root = buildSampleTree();
        printTotalSize(root);
        printSearchResults(root);
        printStatistics(root);
    }

    private static DirectoryNode buildSampleTree() {
        DirectoryNode root = new DirectoryNode("project");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode archive = new DirectoryNode("archive");

        root.add(new FileNode("readme.txt", 100));
        root.add(new FileNode("logo.png", 3000));
        root.add(docs);
        root.add(new DirectoryNode("empty"));

        docs.add(new FileNode("report.txt", 2000));
        docs.add(new FileNode("notes.txt", 50));
        docs.add(archive);

        archive.add(new FileNode("old_report.txt", 500));
        archive.add(new FileNode("backup.zip", 700));
        return root;
    }

    private static void printTotalSize(DirectoryNode root) {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        System.out.println("Total size: " + visitor.calculateTotalSize(root));
    }

    private static void printSearchResults(DirectoryNode root) {
        SearchVisitor visitor = new SearchVisitor(".txt", 100);
        System.out.println("Search '.txt', minSize 100:");
        for (String path : visitor.search(root)) {
            System.out.println("  " + path);
        }
    }

    private static void printStatistics(DirectoryNode root) {
        StatisticsVisitor visitor = new StatisticsVisitor();
        visitor.collect(root);
        System.out.println("Files: " + visitor.getFileCount());
        System.out.println("Directories: " + visitor.getDirectoryCount());
        System.out.println("Max depth: " + visitor.getMaxDepth());
    }
}
