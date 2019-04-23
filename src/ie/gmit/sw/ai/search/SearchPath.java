package ie.gmit.sw.ai.search;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.node.Node;
import ie.gmit.sw.ai.traversers.AStarTraversator;
import ie.gmit.sw.ai.traversers.BasicHillClimbingTraversator;
import ie.gmit.sw.ai.traversers.BeamTraversator;
//import ie.gmit.sw.ai.heuristicsearch.maze.IndicateArea;
//import ie.gmit.sw.ai.heuristicsearch.maze.SearchingAreaIndicator;
import ie.gmit.sw.ai.traversers.BestFirstTraversator;
import ie.gmit.sw.ai.traversers.BruteForceTraversator;
import ie.gmit.sw.ai.traversers.DepthLimitedDFSTraversator;
import ie.gmit.sw.ai.traversers.IDAStarTraversator;
import ie.gmit.sw.ai.traversers.IDDFSTraversator;
import ie.gmit.sw.ai.traversers.RandomWalk;
import ie.gmit.sw.ai.traversers.RecursiveDFSTraversator;
import ie.gmit.sw.ai.traversers.SteepestAscentHillClimbingTraversator;
import ie.gmit.sw.ai.traversers.Traversator;

import java.util.LinkedList;
import java.util.Random;

public class SearchPath {

    private Maze maze;

    public SearchPath(Maze maze){
        this.maze = maze;
    }

    //Finds a path to the Character
    public LinkedList<Node> search(Node initialNode, Node goalNode){
        //Set up search area
        SearchArea area = new Area(getAreaHeight(initialNode, goalNode), getAreaWidth(initialNode, goalNode),
                getLeftTopCorner(initialNode, goalNode), this.maze, goalNode, initialNode);

        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 1) + 1) + 1;
        
        //Traverse through the area
        Traversator t;
        
        //Generates a random number between 1 and 5 to determine which search algorithm to use
        if(randomNum == 1) {
        	//This examines the neighboring nodes one by one and selects the first neighboring node which optimizes the current cost as next node.
        	t = new BasicHillClimbingTraversator(goalNode);
        }
        else if(randomNum == 2) {
        	/*
        	 * Each step it picks the node according to a value-‘f’ which is a parameter equal to the sum of two other parameters – ‘g’ and ‘h’. 
        	 * At each step it picks the node/cell having the lowest ‘f’, and process that node/cell.
        	 */
        	t = new AStarTraversator(goalNode);
        }
        else if(randomNum == 3) {
        	/*
        	 * Similar to A* but uses iterative deepening to search down each layer. Incrementing the depth and rerunning after every search.
        	 */
        	t = new IDAStarTraversator(goalNode);
        }
        else if(randomNum == 4) {
        	//This examines all the neighboring nodes and then selects the node closest to the solution state as next node.
        	t = new SteepestAscentHillClimbingTraversator(goalNode);
        }
        else {
        	//Traverses through all the nodes in the tree using a queue with all the nodes its going to visit.
        	t = new BestFirstTraversator(goalNode);
        }

        t.traverse(area.getMaze(), initialNode);

        return t.getStack();
    }

    private int getAreaHeight(Node initialNode, Node goalNode){
        int height;
        if(initialNode.getRow() > goalNode.getRow())
            height = initialNode.getRow() - goalNode.getRow() + 1;
        else height = goalNode.getRow() - initialNode.getRow() + 1;
        return height;
    }

    private int getAreaWidth(Node initialNode, Node goalNode){
        int width;
        if(initialNode.getCol() > goalNode.getCol())
            width = initialNode.getCol() - goalNode.getCol() + 1;
        else width = goalNode.getCol() - initialNode.getCol() + 1;
        return width;
    }

    private Node getLeftTopCorner(Node initialNode, Node goalNode){
        int row, col;
        if(initialNode.getRow() < goalNode.getRow())
            row  = initialNode.getRow();
        else row = goalNode.getRow();
        if(initialNode.getCol() < goalNode.getCol())
            col = initialNode.getCol();
        else col = goalNode.getCol();

        return new Node(row, col);
    }
}
