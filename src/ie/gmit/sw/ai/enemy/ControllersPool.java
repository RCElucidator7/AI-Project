package ie.gmit.sw.ai.enemy;

/**
 * ControllersPool - Thread pool for the character, maze and spiders
 */

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.Character;
import ie.gmit.sw.ai.fuzzy.*;
import ie.gmit.sw.ai.nn.NNFacade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllersPool {

    // Properties
    private Maze maze;
    private Character character;
    private SpiderProduction spiders;
    
    // Thread Pool
    private ExecutorService executor = Executors.newCachedThreadPool();

    // Constructor
    // Initializes the maze and gets collection of spiders
    public ControllersPool(Maze maze, Character character){
        this.maze = maze;
        this.spiders = maze.getSpiders();
        this.character = character;
    }

    // Runs the Thread Pool
    public void runController(){
        for(int i = 0; i < this.spiders.getSpidersNumber(); i++){
            executor.submit(new EnemyController(this.spiders.getSpiderByIndex(i), this.maze, new NNFacade(), new FuzzyLogic(), this.character));
        }
    }

}
