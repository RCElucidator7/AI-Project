package ie.gmit.sw.ai.search;

import ie.gmit.sw.ai.node.*;

public interface SearchArea {
    Node getGoalNode();
    Node[][] getMaze();
}
