import java.util.*;

public class KevinBacon {

    public static HashMap<String, Node> movieCast = new HashMap<>();
    public static GraphAdjList actorsGraph = new GraphAdjList();
    public ReadFile reader;
    public String fileLoc;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        KevinBacon kevin = new KevinBacon();

        //Read the file
        List<String> spliced = kevin.reader.readFile("./tmdb_5000_credits.csv");
        for (String s : spliced) {
            kevin.findNames(s);
        }

        String input1 = null;
        String input2 = null;
        //Loop until the user types "EXIT" - not cap-sensitive
        do {
            System.out.println("Enter two names one at a time. Enter \"EXIT\" to quit.");
            System.out.println("Enter the first name");
            input1 = scan.nextLine().toUpperCase().trim();
            if (input1.equals("EXIT")) break;
            System.out.println("Enter the second name");
            input2 = scan.nextLine().toUpperCase().trim();
            if (input2.equals("EXIT")) break;
            kevin.printList(kevin.findShortestPath(input1, input2));
        } while (!input1.equals("EXIT") && !input2.equals("EXIT"));
        System.out.println("Closing");
    }

    /**
     * Constructor to initialize the reader
     */
    public KevinBacon() {
        this.reader = new ReadFile();
    }

    /**
     * Uses the reader to read the given file and find the actors' names
     *
     * @param fullString the input string to sort through
     */
    public void findNames(String fullString) {
        try {
            String name = "";
            //Repeatedly finds names and trims off the already scanned bits of the string
            while ((fullString.charAt(0)) != ']') {
                name += fullString.substring(fullString.indexOf("\"name\"\": \"\"") + 11, fullString.indexOf("\"\", \"\"order"));
                fullString = fullString.substring(fullString.indexOf('}') + 1);
                Node node = new Node(name.toUpperCase());
                movieCast.put(node.getName(), node);
                name = "";
            }
        } catch (IndexOutOfBoundsException exception) {

        }
        //Takes the temp hashmap and adds the actors to the permanent list and adds other actors as neighbors
        addActorAndNeighbors();
    }

    /**
     * Adds the actors from the temp hashmap to the permanent one and adds the other temp actors
     * as neighbors to each other
     */
    public void addActorAndNeighbors() {
        for (Map.Entry<String, Node> entry : movieCast.entrySet()) {
            //If that actor isn't already in the main list then add them
            if (!actorsGraph.containsNode(entry.getKey())) {
                actorsGraph.addNode(entry.getValue());
            }
            //Add neighbors to the actors
            for (Map.Entry<String, Node> neighbor : movieCast.entrySet()) {
                actorsGraph.getNode(entry.getKey()).addNeighbor(neighbor.getKey());
            }
        }
        //Clears the temp list for the next time
        movieCast.clear();
    }

    /**
     * Uses a modified Breadth First Search to sort through the graph from the starting point until it finds
     * the ending point or until there are no more points to visit. Uses a hashmap to relate each visited point
     * to the parent that led there to be able to retrace steps at the end to find the actual path taken.
     *
     * @param src the point to start at
     * @param tar the point to look for
     * @return the path taken from src to tar as a list
     */
    public List<Node> findShortestPath(String src, String tar) {
        //Checks to see if the points actually exist in the list
        if (actorsGraph.containsNode(src) && actorsGraph.containsNode(tar)) {
            //Turn the input names into nodes to look for
            Node start = actorsGraph.getNode(src);
            Node end = actorsGraph.getNode(tar);

            //Initialize the lists needed
            //The path from start to finish
            LinkedList<Node> shortestPath = new LinkedList<>();
            //The queue of all the upcoming vertexes to visit
            LinkedList<Node> queue = new LinkedList<>();
            //Tracks the parents of each vertex to find the path
            HashMap<Node, Node> parents = new HashMap<>();
            //The current node
            Node node = null;

            //Start off the queue with the start value
            queue.add(start);
            parents.put(start, null);

            //If the queue is empty then there are no more points to visit and the two points are not connected
            while (!queue.isEmpty()) {
                node = queue.remove(0);
                //Ends the loop if the end is found
                if (node.equals(end)) {
                    break;
                }
                //Puts all the neighbors on the queue and records their parents in the hashMap
                for (String neighbor : node.getNeighbors()) {
                    if (!parents.containsKey(actorsGraph.getNode(neighbor))) {
                        queue.add(actorsGraph.getNode(neighbor));
                        parents.put(actorsGraph.getNode(neighbor), node);
                    }
                }
            }
            //Means that the points are not connected if the end is not in the parents hashmap
            if (!parents.containsKey(end)) {
                return null;
            }
            node = end;
            //Loops from end to start using the parents hashmap to find the path back to the start
            while (node != null) {
                shortestPath.add(0, node);
                node = parents.get(node);
            }
            return shortestPath;

        } //Else one or both of the actors don't exist in the list
        else {
            System.out.println("One or both of the actors aren't on the list");
            return null;
        }
    }

    /**
     * Prints the list returned in findShortestPath by iterating through the points and adding them to
     * a string to print out at the end
     *
     * @param list the list to print out
     */
    public void printList(List<Node> list) {
        String s = "";
        int count = 0;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                s += list.get(i).getName();
                if (!(i == list.size() - 1)) {
                    s += " -> ";
                }
                if (count == 4) {
                    s += "\n";
                    count = 0;
                }
                count++;
            }
        } //If the list is null it means that the points weren't connected
        else {
            s = "The nodes are not connected";
        }
        System.out.println(s);
    }
}
