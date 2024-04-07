import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

// Graph edge class
class Edge {
    Node startingPointNode, endingPointNode;
    int weight;

    Edge(Node startingPointNode, Node endingPointNode, int weight) {
        this.startingPointNode = startingPointNode;
        this.endingPointNode = endingPointNode;
        this.weight = weight;
    }
}

// Graph node class
class Node {
    // id is the unique identifier for each node (can be an int or a String)
    Object id;
    int distance;
    Node shortestPathParent;
    ArrayList<Edge> edges;

    Node(Object id) {
        this.id = id;
        this.distance = -1;
        this.shortestPathParent = null;
        this.edges = new ArrayList<Edge>();
    }

    // add an outgoing edge from current node
    void addEdge(Node endingPointNode, int weight) {
        Edge edge = new Edge(this, endingPointNode, weight);
        this.edges.add(edge);
    }
}

// Graph class
class Graph {
    static final int INF = 1000000;
    int numberOfNodes;
    ArrayList<Node> nodes;

    Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        this.nodes = new ArrayList<Node>();
        for (int id = 0; id < this.numberOfNodes; id++) {
            this.nodes.add(new Node(id)); // Create nodes with integer IDs by default
        }
    }

    Node getNode(Object nodeID) {
        if (nodeID instanceof Integer) {
            int intID = (int) nodeID;
            return this.nodes.get(intID);
        } else if (nodeID instanceof String) {
            String strID = (String) nodeID;
            for (Node node : this.nodes) {
                if (node.id.equals(strID)) {
                    return node;
                }
            }
        }
        return null;
    }

    void addEdge(Object startingPointNodeID, Object endingPointNodeID, int weight) {
        Node startingNode = getNode(startingPointNodeID);
        Node endingNode = getNode(endingPointNodeID);
        startingNode.addEdge(endingNode, weight);
    }

    void dijkstraShortestPath(Object sourceNodeID) {
        Comparator<Node> customComparator = new Comparator<Node>() {
            @Override
            public int compare(Node firstNode, Node secondNode) {
                return firstNode.distance - secondNode.distance;
            }
        };
        PriorityQueue<Node> pq = new PriorityQueue<Node>(customComparator);

        Node source = this.getNode(sourceNodeID);

        for (Node node : this.nodes) {
            if (node.id.equals(sourceNodeID)) {
                node.distance = 0;
                node.shortestPathParent = node;
            } else {
                node.distance = INF;
                node.shortestPathParent = null;
            }
            pq.add(node);
        }

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            for (Edge edge : u.edges) {
                Node v = edge.endingPointNode;
                int alt = u.distance + edge.weight;
                if (alt < v.distance) {
                    v.distance = alt;
                    v.shortestPathParent = u;
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }
    }

    ArrayList<Node> getShortestPathFromSource(Node u) {
        ArrayList<Node> path = new ArrayList<Node>();
        while (u != null && u != u.shortestPathParent) {
            u = u.shortestPathParent;
            path.add(u);
        }
        Collections.reverse(path);
        return path;
    }

    void printDistanceAndShortestPathFromSource(Object nodeID) {
        Node u = this.getNode(nodeID);
        if (u.distance == INF) {
            System.out.println("There is no path from source to " + u.id + ".");
            return;
        }
        System.out.print("The distance of the shortest path from source to " + u.id + " is " + u.distance
                + ", and the shortest path is ");
        ArrayList<Node> path = this.getShortestPathFromSource(u);
        for (int i = 0; i < path.size(); i++) {
            if (i != 0) {
                System.out.print(" -> ");
            }
            System.out.print(path.get(i).id);
        }
        System.out.println(".");
    }
}

public class ProgrammingAssignment4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the number of nodes in the graph:");
        int n = scanner.nextInt();
        System.out.println();

        Graph graph = new Graph(n);

        System.out.println("Please enter the number of edges in the graph:");
        int m = scanner.nextInt();
        System.out.println();

        for (int i = 1; i <= m; i++) {
            System.out.println("Please enter the starting point node id/name, ending point node id/name, and the weight of the edge[" + i + "]:");
            Object startingPointNodeID = scanner.next();
            Object endingPointNodeID = scanner.next();
            int weight = scanner.nextInt();
            System.out.println();

            graph.addEdge(startingPointNodeID, endingPointNodeID, weight);
        }

        System.out.println("Please enter the source node id/name for Dijkstra's algorithm:");
        Object sourceNodeID = scanner.next();
        System.out.println();

        graph.dijkstraShortestPath(sourceNodeID);

        for (int id = 0; id < n; id++) {
            graph.printDistanceAndShortestPathFromSource(id);
        }

        scanner.close();
    }
}

