# Biriba 3

2D game based on the Greek card game Biriba

### Rules:

Played with two to four players, using two decks of standard playing cards.
The objective of the game is to score points by forming sequences of cards.
A sequence consists of three or more cards of the same suit in consecutive order.
At the beginning of the game, each player is dealt 11 cards
and two stacks of 11 cards called biribakia are placed on the side face down.
The remaining cards are placed in a draw pile.
The top card from the draw pile is turned face-up to start the discard pile.

At the start of each round the player can either draw the top card from the draw pile
or pick up the entire discard pile. If the player chooses to pick up the discard pile,
they must use the one of the cards to form a sequence or extend an existing one.
If a player has no cards left in their hand, and his team has not taken a biribaki then
he takes one and the game continues. Otherwise, the game ends. The game also ends if only 2
cards are left in the draw pile.
At the end of the game teams score points for the sequences they have formed. A new game will be
played until a team accumulates 2000 points.

### Architecture

The MVC pattern is used to separate the game logic from the user interface.
The model contains the game state and logic, while the view is responsible for
rendering the game graphics. The controller handles user inout and updates the game state.

* #### Model:
  The model consist of classes that represent the game such as:
    * The Board class, contains the deck, the discards pile and the biribakia.
    * The Player class, contains the hand of cards
    * The Team class, contains the players, the score and the triti
    * The Triti class, contains the logic for creating and extending a sequence of cards

* #### View:
  The view consist of classes that render the game graphics and listen for user input such as:
    * The CardActor, renders the card on screen
    * The DeckActor, displays the deck and fires a pickFromDeckEvent if clicked
    * The PlayerHandActor, displays all the information about the player including his hand

* #### Controller:
  The controller handles every event in the game. A class exists for every possible
  action in the game, and it is created either after a user interaction or from another event.
  They implement the GameEvent Interface that has the methods execute and undo.
  The controller's handleAction method takes a GameEvent as input, checks if the 
  action is allowed to happen and calls its execute method. Also, it stores the event in a queue so that
  it can use the last event's undo method if the player presses the undo button.