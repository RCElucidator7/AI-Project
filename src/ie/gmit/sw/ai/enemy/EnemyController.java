package ie.gmit.sw.ai.enemy;

/**
 * EnemyController - Controls the actions each spider makes
 */

import ie.gmit.sw.ai.node.*;
import ie.gmit.sw.ai.search.SearchPath;
import ie.gmit.sw.ai.*;
//import ie.gmit.sw.ai.fuzzylogic.FuzzyLogic;
//import ie.gmit.sw.ai.heuristicsearch.PathSearcher;
//import ie.gmit.sw.ai.nn.NNFacade;
import ie.gmit.sw.ai.Character;
import ie.gmit.sw.ai.fuzzy.FuzzyLogic;
import ie.gmit.sw.ai.nn.NNFacade;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EnemyController implements Movable, Runnable {

    // Instances
    private Spider spider;
    private Maze maze;
    private NNFacade gr;
    private FuzzyLogic fl;
    private Character character;

    // Constructor with initialization
    public EnemyController(Spider spider, Maze maze, NNFacade gr, FuzzyLogic fl, Character character){
        this.maze = maze;
        this.spider = spider;
        this.gr = gr;
        this.fl = fl;
        this.character = character;
    }

    //Run method implemented from Runnable
    @Override
    public void run(){
        moveSpider();
    }

    /*
    * move() method implemented from Movable
    * Checks the current location of the spider
    * Determines what action to take based on the results from the NN
    */
    @Override
    public void move(int row, int col, Spider spider) {

        //Cell where the spider is
        int[] current = {spider.getCurrentRow(), spider.getCurrentCol()};

        if(isValidMove(row, col)){ // if move is valid
            doStep(current, row, col, spider);
        }
        //If the spider interacts with the character
        else if(row <= this.maze.size() - 1 && col <= this.maze.size() -1
                && this.maze.get(row, col) == '5'){
            try {

                //Retrieve the output from the Neural Network
                int nnOutput = gr.action(spider.getLife(), spider.getRage(), spider.getPower(), spider.getDefence());
                switch(nnOutput){
                    case 1: //Walk away - move to a valid cell
                        if(isValidMove(spider.getCurrentRow(), (spider.getCurrentCol() + 1))){
                            doStep(current, spider.getCurrentRow(), (spider.getCurrentCol() + 1), spider);
                        }
                        else if(isValidMove(spider.getCurrentRow(), (spider.getCurrentCol() - 1))){
                            doStep(current, spider.getCurrentRow(), (spider.getCurrentCol() - 1), spider);
                        }
                        else if(isValidMove((spider.getCurrentRow() + 1), spider.getCurrentCol())){
                            doStep(current, (spider.getCurrentRow() + 1), spider.getCurrentCol(), spider);
                        }
                        else if(isValidMove((spider.getCurrentRow() - 1), spider.getCurrentCol())){
                            doStep(current, (spider.getCurrentRow() - 1), spider.getCurrentCol(), spider);
                        }
                        else{
                            attack(spider);
                        }
                        break;
                    case 2: //Attack - start combat
                        // attack the character
                        attack(spider);
                        break;
                    case 3: //Run away - Move away three cells
                        if(isValidMove(spider.getCurrentRow(), (spider.getCurrentCol() + 3))){
                            doStep(current, spider.getCurrentRow(), (spider.getCurrentCol() + 3), spider);
                        }
                        else if(isValidMove(spider.getCurrentRow(), (spider.getCurrentCol() - 3))){
                            doStep(current, spider.getCurrentRow(), (spider.getCurrentCol() - 3), spider);
                        }
                        else if(isValidMove((spider.getCurrentRow() + 3), spider.getCurrentCol())){
                            doStep(current, (spider.getCurrentRow() + 3), spider.getCurrentCol(), spider);
                        }
                        else if(isValidMove((spider.getCurrentRow() - 3), spider.getCurrentCol())){
                            doStep(current, (spider.getCurrentRow() - 3), spider.getCurrentCol(), spider);
                        }
                        else{
                            attack(spider);
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Determines where the spider should move
    public void moveSpider(){
        int x1, x2, y1, y2;
        //Run until death of spider
        while(spider.getHealth() > 0){

            x1 = this.spider.getCurrentRow();
            y1 = this.spider.getCurrentCol();
            x2 = this.character.getCurrentRow();
            y2 = this.character.getCurrentCol();

            try {
                if(isWithinRange(x1, y1, x2, y2, this.spider.getRange())){
                    Node spiderNode = new Node(x1, y1);
                    Node characterNode = new Node(x2, y2);
                    SearchPath sp = new SearchPath(this.maze);
                    spider.setPathFinderer(sp.search(spiderNode, characterNode));
                    if(spider.getPathFinderer().pathSize() > 0){
                        spider.getPathFinderer().takeLastNodeOf();
                        int row = spider.getPathFinderer().getLastNode().getRow();
                        int col = spider.getPathFinderer().getLastNode().getCol();
                        move(row, col, spider);
                    }
                    else{
                        int direction = (int) (4 * Math.random() + 1); // generates number 1 - 4 inclusive
                        switch (direction) {
                            case 1:
                                move(spider.getCurrentRow() - 1, spider.getCurrentCol(), spider); // Move Up
                                break;
                            case 2:
                                move(spider.getCurrentRow(), spider.getCurrentCol() + 1, spider); // Move Right
                                break;
                            case 3:
                                move(spider.getCurrentRow() + 1, spider.getCurrentCol(), spider); // Move Down
                                break;
                            case 4:
                                move(spider.getCurrentRow(), spider.getCurrentCol() - 1, spider); // Move Left
                                break;
                        }
                    }
                }
                else {
                    int direction = (int) (4 * Math.random() + 1); // generates number 1 - 4 inclusive
                    switch (direction) {
                        case 1:
                            move(spider.getCurrentRow() - 1, spider.getCurrentCol(), spider); // Move Up
                            break;
                        case 2:
                            move(spider.getCurrentRow(), spider.getCurrentCol() + 1, spider); // Move Right
                            break;
                        case 3:
                            move(spider.getCurrentRow() + 1, spider.getCurrentCol(), spider); // Move Down
                            break;
                        case 4:
                            move(spider.getCurrentRow(), spider.getCurrentCol() - 1, spider); // Move Left
                            break;
                    }
                }
                Thread.sleep(1000); // Sleep for 1 sec.
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Checks to see if its a valid moves
    public boolean isValidMove(int row, int col){
        if(row <= this.maze.size() - 1 && col <= this.maze.size() -1
                && this.maze.get(row, col) == ' ') return true;
        return false;
    }

    //Move the spider into a new available cell
    public void doStep(int[] current, int destinationRow, int destinationCol, Spider spider){
    	//Replaces the current cell with a space
    	this.maze.set(current[0], current[1], '\u0020'); 

        //Set the new position of the spider
        spider.setCurrentRow(destinationRow);
        spider.setCurrentCol(destinationCol);
        this.maze.set(destinationRow, destinationCol, spider.getSpiderType());
    }

    public void attack(Spider spider){
    	//Fuzzy Logic to determine the results of the battle
        this.character.setDamageTaken(this.fl.combat(this.character, spider, this.character.useWeapon()));

        spider.setHealth(spider.getHealth() - 50);
        spider.setLife();
        //If the Spider died remove it
        if(spider.getHealth() <= 0){
            this.maze.set(spider.getCurrentRow(), spider.getCurrentCol(), ' ');
            this.maze.getSpiders().removeSpider(spider);
            spider = null;
            System.out.println("There are " + this.maze.getSpiders().getSpidersNumber() + " left in the maze");
        }
    }

    //Checks if the spider is within range of the character
    private boolean isWithinRange(int x1, int y1, int x2, int y2, int range){
        if((((x1 - x2) <= range) && ((x1 - x2) >= (-1 * range))) &&
                (((y1 - y2) <= range) && ((y1 - y2) >= (-1 * range)))){
            return true;
        }
        return false;
    }
}
