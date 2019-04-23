package ie.gmit.sw.ai.node;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Node {
	    public enum Direction {North, South, East, West};
	    private Node parent;
	    private Color color = Color.BLACK;
	    private Direction[] paths = null;
	    public boolean visited =  false;
	    private boolean walkable = false;
	    public boolean goal;
	    public boolean initial;
	    private int row = -1;
	    private int col = -1;
	    private int mazeRow = 0;
	    private int mazeCol = 0;
	    private int distance;
	    private char nodeType;

	    public Node(int row, int col) {
	        this.row = row;
	        this.col = col;
	    }

	    public Node(int row, int col, int mazeRow, int mazeCol){
	        this.row = row;
	        this.col = col;
	        this.mazeRow = mazeRow;
	        this.mazeCol = mazeCol;
	    }

	    public int getMazeRow() {
	        return mazeRow;
	    }

	    public void setMazeRow(int mazeRow) {
	        this.mazeRow = mazeRow;
	    }

	    public int getMazeCol() {
	        return mazeCol;
	    }

	    public void setMazeCol(int mazeCol) {
	        this.mazeCol = mazeCol;
	    }

	    public int getRow() {
	        return row;
	    }

	    public int getCol() {
	        return col;
	    }

	    public char getNodeType() {
	        return nodeType;
	    }

	    public void setNodeType(char nodeType) {
	        this.nodeType = nodeType;
	    }

	    public boolean isWalkable() {
	        return walkable;
	    }

	    public void setWalkable(boolean walkable) {
	        this.walkable = walkable;
	    }

	    public Node getParent() {
	        return parent;
	    }

	    public void setParent(Node parent) {
	        this.parent = parent;
	    }

	    public boolean hasDirection(Direction direction){
	        for (int i = 0; i < paths.length; i++) {
	            if (paths[i] == direction) return true;
	        }
	        return false;
	    }

	    public Node[] children(Node[][] maze){

	        List<Node> children = new ArrayList<>();
	        
	        if (row > 0 && maze[row - 1][col].hasDirection(Direction.South) && walkable) children.add(maze[row - 1][col]); //Add North
	        if (row < maze.length - 1 && maze[row + 1][col].hasDirection(Direction.North)  && walkable) children.add(maze[row + 1][col]); //Add South
	        if (col > 0 && maze[row][col - 1].hasDirection(Direction.East)  && walkable) children.add(maze[row][col - 1]); //Add West
	        if (col < maze[row].length - 1 && maze[row][col + 1].hasDirection(Direction.West)  && walkable) children.add(maze[row][col + 1]); //Add East
	        

	        return children.toArray(new Node[children.size()]);
	    }

	    public Node[] adjacentNodes(Node[][] maze){
	        java.util.List<Node> adjacents = new java.util.ArrayList<Node>();

	        if (row > 0) adjacents.add(maze[row - 1][col]); //Add North
	        if (row < maze.length - 1) adjacents.add(maze[row + 1][col]); //Add South
	        if (col > 0) adjacents.add(maze[row][col - 1]); //Add West
	        if (col < maze[row].length - 1) adjacents.add(maze[row][col + 1]); //Add East

	        return (Node[]) adjacents.toArray(new Node[adjacents.size()]);
	    }

	    public Direction[] getPaths() {
	        return paths;
	    }

	    public void addPath(Direction direction) {
	        int index = 0;
	        if (paths == null){
	            paths = new Direction[index + 1];
	        }else{
	            index = paths.length;
	            Direction[] temp = new Direction[index + 1];
	            for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
	            paths = temp;
	        }

	        paths[index] = direction;
	    }

	    public boolean isVisited() {
	        return visited;
	    }

	    public void setVisited(boolean visited) {
	        this.visited = visited;
	    }

	    public boolean isGoalNode() {
	        return goal;
	    }

	    public boolean isInitial() {
	        return initial;
	    }

	    public void setInitial(boolean initial) {
	        this.initial = initial;
	    }

	    public void setGoalNode(boolean goal) {
	        this.goal = goal;
	    }

	    public int getHeuristic(Node goal){
	        double x1 = this.col;
	        double y1 = this.row;
	        double x2 = goal.getCol();
	        double y2 = goal.getRow();
	        return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	    }

	    public int getPathCost() {
	        return distance;
	    }

	    public void setPathCost(int distance) {
	        this.distance = distance;
	    }

	    public String toString() {
	        return "[" + row + "/" + col + "]";
	    }

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}
	}
