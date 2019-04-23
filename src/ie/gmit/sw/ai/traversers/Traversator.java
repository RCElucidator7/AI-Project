package ie.gmit.sw.ai.traversers;

import java.util.LinkedList;

import ie.gmit.sw.ai.node.*;
public interface Traversator {
	public void traverse(Node[][] maze, Node start);
	 LinkedList<Node> getStack();
}
