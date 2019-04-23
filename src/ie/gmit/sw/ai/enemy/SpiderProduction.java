package ie.gmit.sw.ai.enemy;

/**
 * SpiderProduction - Generates a List of Spiders
 */
import java.util.ArrayList;
import java.util.List;

public class SpiderProduction {
    
    //List of spiders
    private List<Spider> spiders = new ArrayList<Spider>();

    public int getSpidersNumber(){
        return spiders.size();
    }
    
    //Add the spider to the list
    public void addSpider(Spider spider){
        spiders.add(spider);
    }

    //Remove the spider from the list
    public void removeSpider(Spider spider){
        spiders.remove(spider);
    }

    //Returns by index
    public Spider getSpiderByIndex(int i) {
        return spiders.get(i);
    }
}
