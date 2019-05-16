import java.util.*;

public class Node {

    private String name;
    private List<String> neighbors;
    private HashSet<String> glanceNeighbors;

    /**
     * Creates a Node object to store an actor's info
     * neighbors stores the names of the actor's neighbors
     * glanceNeighbors stores the names of the neighbors in a hashset so it's easier to check if a name exists
     * instead of iterating through a whole list
     *
     * @param name the name of the actor
     */
    public Node(String name) {
        this.name = name;
        this.neighbors = new LinkedList<>();
        this.glanceNeighbors = new HashSet<>();
    }

    /**
     * @return the actor's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the list of neighbors' name
     */
    public List<String> getNeighbors() {
        return this.neighbors;
    }

    /**
     * Uses glanceNeighbors to check if the name actually exists instead of having to iterate through the list
     * to check if it exists as a way to quicken the process.
     * Adds a neighbor to the Node's list of neighbors
     *
     * @param neighbor The name of the neighbor to add
     */
    public void addNeighbor(String neighbor) {
        //Checks if the name already exists using glanceNeighbors and also checks to make sure
        //that the actor isn't added to their own list of neighbors
        if (!this.name.equals(neighbor) && !glanceNeighbors.contains(neighbor)) {
            this.glanceNeighbors.add(neighbor);
            this.neighbors.add(neighbor);
        }
    }
}
