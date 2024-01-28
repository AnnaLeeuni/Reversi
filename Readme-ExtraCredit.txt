Changes:
Since there is no interface for JPanel, we manually added the paintComponent method to our
panel's interface to utilize JPanel's methods without casting.
Additionally, we elevated the public methods from our previous hexagonal panel to its interface,
allowing for the implementation of different panel types. We have moved the getCorners methods
from our strategy to the readonlyModel. In the future, to add more game modes,
one would only need to implement these getCorners methods, rather than modifying our strategies.

How to Use:
The first argument defines the game mode: use "hexagonal" for hexagonal gameplay
and "square" for a square board.

The second argument determines if you want hints in the game.
Both square and hexagonal modes can use hints: "hint" for a view with hint features,
and "noHint" for a view without hint features.

The third and fourth arguments are for specifying the player type:

"human" for a human player,
"strategy1" for an easy AI mode,
"strategy2" for a medium AI mode,
"strategy3" for a hard AI mode.
For example, to set up a hexagonal board with hints and two AI players on hard mode,
input: "hexagonal hint strategy3 strategy3".