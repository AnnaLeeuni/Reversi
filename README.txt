Reversi Game Model - Readme

Overview:
This codebase is dedicated to the implementation of the classic board game, Reversi.
The game pits two players against each other, represented by BLACK and WHITE discs.
The goal is to capture the opponent's discs by surrounding them with one's own discs.
There are three AI mode, and a total of four strategy type, fully optimized.
There are GUI representation of the view.
Controller is fully implemented.

Updated code for assignment 7:
The GUI view was fixed to be able to resize as the board enlarges or shrinks.
(But could have minor bug, but Lerner says it was no longer part of the evaluation,
so left unfixed).
Fixed AIMiniMax, which now also catches the null pointer exception.
The strategies were optimized.
AI moves are delayed, we can see step by step of the AI moves.
IController did not extend ModelListener or PlayerActionListener, so in case there might be
controller that don't need register as these features.
Fixed GameOver in the model. in the previous case, if there is no possible move anymore,
the game is over. Therefore, in the updated version, we corrected it so that if there is no valid
move, players are to recognize that themselves。
Method "InitateHexagon" is moved from the view and into an independent class.




Assumptions:
Basic understanding of the reversi game rules.
Our Hexagon coordinate system follow the layout of this example:
https://www.redblobgames.com/grids/hexagons/#map-storage
It is a 2D Array. Use nulls at the unused spaces.
array[r][q].


1.Quick Start:

for assignment 5:
    IBoard gameBoard = new BasicBoard(); // BasicBoard implements IBoard interface
    gameBoard.placeDisc(3, 4); // Place a disc at position (3,4), 0-base system for both inputs
    TextualView view = new TextualViewBoard(gameBoard); // Create a textual view of the board
    System.out.println(view); // Display the board
    DiscType currentPlayer = gameBoard.getTurns(); // Fetch the current player's disc type
    gameBoard.skipRound(); // allow player to skip their round
    boolean gameOver? = gameBoard.gameOver(); // if player quite two times in a row, game over
    DiscType winner = gameBoard.getWinner(); // can only be called when the game is over
    // getWinner is get by comparing the total Disc on the board of the both player, the one with
    // more Disc is the winner.

    for more detailed game play test in TestBasicBoardGameExampleGamePlay.
    for more public method test in TestBasicBoardModelPublic.

for assignment 6:
    GUI view:
    simply run a main method to take a quick view of our GUI displace.
    public static void main(String [] args) {
        BasicBoard board = new BasicBoard(7);
        // or you can start by a customized pile:
        // BasicBoard boardNew = new BasicBoard(piles);
        // or a assigned player turn:
        // BasicBoard boardNew = new BasicBoard(piles turn);
        IView view = new ViewBoard(board);
        view.setVisible(true); // see in "GameBoardExampleDisplace" class
      }
    Note:
    The Tile can be select and de-select, you can select a tile and then keypress space bar,
    to indicate that you want to move to this tile, everytime you select a tile,
    it will be highlighted and indicate it's logical location as a message printout.

     Using Strategy:
     IStrategy startegy1 = new AICaptureManyPieces();
     board.placeDisc(strategy.getPosition(boardNew).getRow(),
                     strategy.getPosition(boardNew).getIndex());

     // see more in test class: "TestAIStrategy"

for assignment 7:
    In configuration, in order to start the game, player could type in string to customize game.
    "human" "human" means two human player game. (the first inputted string always assigned to BLACK.
    "human" "strategy1" means human verse AIEasy.
    And you can configure the rest by leveling up the AI.
    Or you can have AI verse AI:
    where strategy1 means AIEasy, strategy2 means AIMedian, strategy3 means AIHard.

    Main game initiation:
        To run the game, instantiate BoardWithNotifier, HumanPlayer/MachinePlayer, and ViewBoard,
        and then create a BoardController with these instances.
        The game starts by calling model.startGame().









2.0 Key components:

    2.1 create a new game in model:
        by calling the constructor, which allowed for:
        Standard Initialization: Default board setup with standard dimensions. (11x11)
        Custom Initialization: Ability to initialize a board with custom dimensions.
        Custom pile initialization: Pass in a rigged version pile to represents
        the state of the board at any time. (usually used to test a certain condition,
        will not be open to player to use in future controller implementation.)
        // some restriction with custom board in BasicBoard: (see more in <ABoard> and <BasicBoard>)
        // 1. height and width has to be larger than 3,
        // o x
        //x _ o
        // o x
        because this is not a valid board to be played.
        //    and height and width has to be the same to constitute a regular hexagon. (in Basic)
        // 2. Pile height/width cannot be even. (in all condition, defined in the abstract class)

        // there is a special condition if in future variation that board is not regular.
        ///// 3. The height should always satisfy the condition "max height=2×width−1"./////


    2.2 Game Logic:
    The game logic includes functions like:

        placeDisc(int x, int y) - To place a disc on the board, 0-base index.

                        _ _ _
                       _ o x _
                      _ x _ o _
                       _ o x _
                        _ _ _
        for example: placeDisc(0, 1)

                        _ x _
                       _ o x _
                      _ x _ o _
                       _ o x _
                        _ _ _

        skipRound() - if the current turn is Black, the current turn will be White instead.
                      If players skips twice in a row, the game is over.
                      (however, the same player can skip twice in a row and will not end game.)
        getPile: Returns the current state of the board. (do not return the reference, deep copy)
        Game Over State: Checks if the game is over.
                         Throws an exception if an operation is attempted after the game has ended.


    2.3 Error Handling：

        1. When a player tries to place a disc in an invalid location,
            a location that do not flip any opponent's disc.
        2. If a player tries to play/skip after the game is over.
        3. If the coordinates provided are negative or exceed the board's limits.
            ( or the coordinate let to a null value on the board)
        4. Trying to getWinner when the game is not over yet will throw exception
            ( if the game is over, but the score of the two player are the same, return null)

    2.4 Coordinate System
        The coordinate system uses row and column.
        if you call getDisc(0,1), you are getting the Disc at first row,
        and the second disc in that row. There are all 0-base. (The same logic with placeDisc(0,1)
        This system has worked well with our implementation,
        since our board is a list of list of(2D array) DiscType.
        It is a 2D Array. Use nulls at the unused spaces.
        array[r][q]. (r/x = row, q/y = column), sometimes in the coe we refer it to x and y.

                        _ x _
                       _ o x _
                      _ x _ o _
                       _ o x _
                        _ _ _
        for a board like above, the list 2D array representation would be like:
        {   {null, null, EMPTY, BLACK, EMPTY }
            {null, EMPTY, WHITE, BLACK, EMPTY}
            {EMPTY, BLACK, EMPTY, WHITE, EMPTY}
            {EMPTY, BLACK, EMPTY, WHITE, EMPTY}
            {EMPTY, WHITE, BLACK，EMPTY， null}
            {EMPTY, EMPTY, EMPTY, null, null}   }

            ** remember, whenever using the coordinate system, for example, getDisc or placeDisc, **
            ** you do not need to consider the null value as having a place in the array, **
            ** those function has been adjusted to work just fine **


    2.5 AI Strategy (for assignment 6)
        AICaptureManyPieces:
        AICaptureManyPieces is an AI strategy implemented for the game of Reversi (Othello).
        This strategy focuses on maximizing the number of opponent pieces captured in a single turn.
        It's part of a larger project that implements different strategies for playing Reversi.

        AIGetCorners:
        Corners are pivotal in Reversi as once captured, they cannot be flipped by the opponent.
        This strategy scans the board for available corner moves and
        prioritizes them over other options. If a corner move is available, it is chosen;
        otherwise, an exception is thrown indicating no corner moves are available.
        Note:
        This strategy extends AIAvoidNextToCorner, inheriting its capabilities and
        ensuring corner moves are prioritized when available.

        AIAvoidNextToCorner:
        a strategy for the Reversi game that focuses on avoiding moves adjacent
        to the corners of the board. In Reversi, playing next to a corner can often put a player at a
        disadvantage, as it gives the opponent an opportunity to capture a corner.
        Note:
        If all available moves are next to corners, throw exception.

        AIMinimax：
        This strategy aims to minimize the maximum advantage the opponent can gain in
        their next move. It selects a move by evaluating the opponent's best possible response
        and chooses the one that leads to the least advantageous position for the opponent.
        Note:
        It assumes an opponent's strategy, when opponent's strategy throws an exception, that means:
        our pick for the location ts very good that whatever strategy opponent is using,
        we made them powerless, therefore, when find an exception thrown by opponent,
        play at that location.

        CompositeStrategy:
        This strategy allows combining multiple strategies, using a primary strategy first and
        resorting to a secondary strategy if the primary one is not applicable or an exception.
        It implements IStrategy and is in the controller package.

        different level:
        AIEasyStrategy, AIMedianStrategy, AIHardStrategy

    2.6 ReadOnly interface and class (for assignment 6):
        The Readonly Reversi Model is ideal for AI implementations, game state analyses,
        and scenario simulations where altering the board state is not desired or necessary.
        It provides a reliable and consistent view of the game, crucial for strategy development
        and decision-making processes in the game of Reversi.

        Break down:
        ReadonlyReversiModel is the root interface with all the basic getter.
        IBoard extend ReadonlyReversiModel, with mutable features like skip and place,
        and make a copy of itself (a mutable board model)

        Mock class:
        We also have a MockReadonlyReversiModel, which extends BasicBoard,
        where all the mutable method does not work, and provided with the ability to
        customize the legal ourselves. // see more to the javadoc of the class itself.

    2.7 (for assignment7:) Controller implementation overview:
        The BoardController class serves as the controller in the Model-View-Controller (MVC) .
        It mediates between the model (BoardWithNotifier), the view (ViewBoard), and the player
        (IPlayer - either HumanPlayer or MachinePlayer). It implements ModelStatusListener and
        PlayerActionListener to handle game state changes and player actions.

            2.7 (1) Controller's Key Features:
            Listens to the model for any changes in the game state and responds accordingly.
            Processes player actions and updates the model.
            Updates the view based on changes in the model.
            Ensures that actions are taken only when it's the respective player's turn.

    2.8 (for assignment 7) BoardWithNotifier
        An extension of the BasicBoard class that includes functionality to notify listeners
        about significant events in the game such as game start, disc placement, round skipping,
        and game completion.

        Key Features of BoardWithNotifier:
        Notifies registered listeners about changes in the game state.
        Extends BasicBoard functionalities with additional notification mechanisms.

3.0 Key Subcomponents:
    1. Within DiscType, there are three possible states a board cell can have:
        BLACK, WHITE, and EMPTY.
    2. BoardLocation has two main attributes: numberRow (row number on the board)
        and index (index within the row).
    3. IBoard provides methods such as placeDisc, gameOver, getPile, getWidth, getHeight, getDisc,
        getWinner, and getTurns to interact with and manipulate the game board.
    4. TextualViewBoard Class: An implementation of the TextualView interface that
        provides a string representation of the game board.

    (for the updated version in assignment 7:)
    5. HumanPlayer
       Represents a human player in the game. It handles the interactions between the user
       and the controller, translating user actions into game actions.

    6. MachinePlayer
       This class represents an AI player in the game, using a specified strategy to make moves.
       Notifies the controller of its actions based on the strategy.

    7. PlayerActionListener
       An interface defining the actions that a player can perform.
       It's implemented by both human and AI players to handle their respective actions.

    8. ModelStatusListener
       An interface used for notifying controllers about changes in the game model.
       This includes notifications for game start, end, disc placements, and round skips.

    9. ViewBoard
       ViewBoard is a JFrame-based GUI class that renders the game board.

       Key Features:
       Displays the game board with hexagonal tiles (SingleHexagon).
       Updates the board based on the game state.
       Provides functionalities for user interactions like mouse clicks and key presses.
       Dynamically updates the title to reflect the current state of the game.

