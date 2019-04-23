package ie.gmit.sw.ai;

/**
 * Moveable - checks if the movement is possible
 */
import ie.gmit.sw.ai.enemy.Spider;

public interface Movable {
    void move(int row, int col, Spider s);
}
