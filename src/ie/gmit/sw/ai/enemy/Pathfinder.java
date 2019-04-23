package ie.gmit.sw.ai.enemy;

/**
 * Pathfinder - determine a path to take
 */

import ie.gmit.sw.ai.node.*;
import java.util.LinkedList;

public class Pathfinder {

    private LinkedList<Node> path = new LinkedList< Node>();

    public void insertNode(Node node) {
        path.addLast(node);
    }

    public int pathSize() {
        return path.size();
    }

    public void clearPath() {
        path.clear();
    }

    public Node takeLastNodeOf() {
        return path.pollLast();
    }

    public Node getLastNode(){
        return path.getLast();
    }
}
