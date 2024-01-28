README - Adapting to Provider's View Implementation
Overview
This document outlines the key changes made to our codebase to integrate and adapt the
view implementation provided by another group. These changes were necessary to ensure
compatibility and functionality with the provided code.

How to play:
Just like assignment 7, in configuration, you need to enter two string to represent two player
such as:
"human" "human"
"human" "strategy1" (can be strategy1->AIEasy; strategy2->AIMedian; strategy3->AIHard)
and with the adaptation of the provider's strategy: "providerStrategy"
try "human" "providerStrategy"
or "providerStrategy" "strategy1/2/3"

Modifications：

1. Interface for Board with Notifier:
    Introduced an interface for BoardWithNotifier to standardize the way notifications are
    handled across different board implementations.

2. Public Methods in HexPanel Interface:
    Elevated public methods from HexPanel to its corresponding interface.
    This change increases the flexibility in how the panel can be implemented and interacted with.

3. Conversion Method in Disc Enum:
    Added a method in the DiscType enum to facilitate the conversion of our disc types to
    the provider's disc types. This ensures compatibility in representing game pieces.

4. Enum with Interface Implementation:
    Modified the enum DiscType to implement an interface, allowing for future extensions and
    maintaining consistency across different disc representations.

5. Coordinate System Conversion Challenges:
    Addressed difficulties in converting between our coordinate system and the provider's.
    This involved mapping our grid-based coordinates to their hexagonal grid representation.

6. Methods Added to IGuiView:
    Enhanced the IGuiView interface by adding necessary methods, improving its capacity
    to interact with different view implementations.

7. ViewBoard Adaptation:
    Updated ViewBoard to take in an adapted model instead of a regular model.
    This change ensure that the view works seamlessly with the provider's model structure.

8. Adaptors:
    a) We made a view adaptor by extending their gameFrame and implement our view interface,
    therefore, our controller can work with both our view and provider's view.
    b) We also made a model adaptor that implements our model interface and their model interface,
    and extends our basicBoard to save some space for implementation. In their method like
    moveDisc(), we simply just called our "placeDisc()".
    c) We also have an adaptor class for strategy call providerStrategy. It extends their strategy
    and implements our strategy interface.
