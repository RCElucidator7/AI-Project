# Artifical Inteligence Project

Developed by: Ryan Conway <br/>
Lecturer: John Healy

### Project Outline
You  are  required  to  create an AI controlledmaze game containingthe set of features listed below using asuite of stubs 
provided on Moodle. The objective of the game should be to escape from amaze and either avoid or fight off the enemy 
characters that move around in the game environment.

### How to play
The objective of the game is to navigate your character through a maze, encountering many creatures along the way (Spoiler - They're 
all spiders) and reaching the end goal to espace the maze.
<br/>
To Control the character, the user uses the following controls:

    Right Arrow/A - Move the player left
    Left Arrow/D - Move the player right
    Up Arrow/W - Move the player up
    Down Arrow/S - Move the player Down
  
Other Controls include
  
    E - Display Current Health and current weapon in hand
    Z - Toggle the zoom of the maze
  
#### Maze Contents
The maze contains Spiders that roam throughout the maze using a neural network and search algorithms to determine their movement.
Each coloured spider has different stats and different search algorithms.

The player starts off the maze with a fist as their weapon, so if the user is forced into battle they have some chance to win, with the cost of losing 
a good chunck of health. Although we can't have our character explore the maze empty handed. Scattered throughout the maze are weapons 
the player can pick up, these include:

    Sword : Deals 15 points of damage
    Bomb : Deals 25 points of damage
    Hydrogen Bomb : Deals 50 points of damage
  
Also throughout the maze, are help points. These help points Display to the user where the end goal is located. (I initally wanted some 
form of waypoint system that would guide the user to the goal node using one of the search algorithms, due to time constraints I was unable 
to do this).

### Features
  * The end goal is randomly placed within the maze on generation. I went for this option as if I placed the end goal at the furthest possible 
  position from the player the game would go on longer than needed. (The maze is fairly big)
  * Spiders are threaded so each one moves independantly.
  * Spiders are controlled by both a neural network and search algorithms. The NN makes decisions based on what it should do when encountering the 
  player. The Search algorithm determines where the spiders move throughout the maze.
  * Player has an array of weapons that they pick up throughout the maze. Once combat is initated the weapon is disposed of.
  * Help points display the co-ordinates of the goal.

### Thread Pool
For the threaded characters I used an executor service, which handled the Spiders in the maze allowing each one to move independly.

### AI features

#### Fuzzy Logic

I Decided to go with a single use for the fuzzy logic, that being the combat.
Combat is initated when the spider attacks the player who is in a neighbouring cell. If the spider decides to attack the fuzzy logic is triggered.

The fuzzy logic determines the results of the battle by using the following inputs:

    The players health
    The spiders health
    The Weapon the player has currently
  
The output of this is used to determine how much damage the player takes after killing the spider. 

#### Neural Network
The neural network used in this project is implemented in the spiders that roam through the maze. They make decisions based on the spiders stats,
Health, Rage, Strength and Defence. These parameters are passed into an already trained Neural Network.(The data and expected results can 
be seen in a .txt file contained in "resources/NN". After inputting these parameters, three outputs are returned which are then indexed into a 
single result. There are 3 decisions the spider can make based on the outputs:

    Walk Away - The spider moves into a free cell
    Attack - The spider attacks the player and the fuzzy combat is triggered
    Run away - The spider moves into a free cell 3 blocks away
  

#### Heuristic Search Algorithms
The search algorithms are implemented into the spiders to determine where they move in the maze. In this project I've implemented 5 
different Search algorithms:

    - Basic Hill Climbing - This examines the neighboring nodes one by one and selects the first neighboring 
    node which optimizes the current cost as next node. 
    
    - Steepest Accent Hill Climbing - This examines all the neighboring nodes and then selects the node closest to the solution state as next node.
    
    - A* - Each step it picks the node according to a value-‘f’ which is a parameter equal to the sum of two other parameters – ‘g’ and ‘h’.
    At each step it picks the node/cell having the lowest ‘f’, and process that node/cell. 
    
    - IDA* - Similar to A* but uses iterative deepening to search down each layer. Incrementing the depth and rerunning after every search.
    
    - BFS - Best First Search - Traverses through all the nodes in the tree using a queue with all the nodes its going to visit.
    
These are implemented to each spider by generating a random number between 1-5, with the result determining which search algorithm to use.
I thought of giving each spider their own unique search algorithm but I was unable to implement this in time. Due to this I didn't want 
to implement just one Search, so I added this instead.





