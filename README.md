# Visitor Pattern - Advanced File System

Java 17, Maven, JUnit 5.

## Commands

Run the tests:

    mvn test

Run the demo application:

    mvn compile exec:java

## Project structure

- `Element`, `Visitor` - the two contracts of the pattern.
- `Node` - common name and parent of all elements.
- `FileNode`, `DirectoryNode` - concrete elements.
- `TotalSizeVisitor`, `SearchVisitor`, `StatisticsVisitor` - independent operations.
- `Main` - deterministic demonstration.

## Design decisions

- Traversal owner: the visitors. `accept()` only performs dispatch, and
  `visit(DirectoryNode)` calls `child.accept(this)`. Each node is visited exactly
  once, because the tree has no cycles and no node has two parents.
- Child order: `DirectoryNode` keeps children in a `TreeMap`, so traversal is deterministic.
- Paths: relative to the visited root, separator `/`, sorted lexicographically.
  The root name is not part of a path.
- Depth: the root has depth 0, every element is one level deeper than its directory.
  Files are included in the maximum depth.
- State: every visitor is reusable. The entry methods `calculateTotalSize()`,
  `search()` and `collect()` reset the state before each run.
- Tree integrity: `FileNode` rejects negative sizes; `DirectoryNode.add()` rejects
  null, cycles, a second parent and duplicate sibling names with `IllegalArgumentException`.
  `getChildren()` returns an unmodifiable copy.

## Extension boundary

`StatisticsVisitor` was added without changing `FileNode`, `DirectoryNode` or `accept()`.
A new operation means a new `Visitor` class only. A new element type would require a new
`visit()` method in `Visitor` and changes in all existing visitors.
