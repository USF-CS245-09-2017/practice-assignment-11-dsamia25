import java.util.HashMap;

public class GraphAdjList {

    private HashMap<String, Node> list;

    /**
     * Constructor to create a graph.
     * Uses an adjacency list stored on a hashmap instead of an array to get better runtime
     */
    public GraphAdjList() {
        this.list = new HashMap<>();
    }

    /**
     * Adds a node to the graph
     *
     * @param node the node to add
     */
    public void addNode(Node node) {
        this.list.put(node.getName(), node);
    }

    /**
     * Checks the list to see if the actor of that name exists
     *
     * @param name the name of the actor to check for
     * @return true if the graph contains the node and false otherwise
     */
    public boolean containsNode(String name) {
        return this.list.containsKey(name.toUpperCase());
    }

    /**
     * Checks the list to see if the node exists in it.
     * Is used less often than the String version since the keys to most of my hashmaps are Strings.
     *
     * @param node the node to check for
     * @return true if the node is in the list and false otherwise
     */
    public boolean containsNode(Node node) {
        return this.list.containsValue(node);
    }

    /**
     * Finds a particular node that is designated by the String name
     *
     * @param name the key of the node to find
     * @return The actor who's name is name
     */
    public Node getNode(String name) {
        return this.list.get(name);
    }

    /**
     * @return the main hashmap of the graph
     */
    public HashMap<String, Node> getList() {
        return list;
    }

    /**
     * @return the amount of items in the graph
     */
    public int size() {
        return this.list.size();
    }
}
