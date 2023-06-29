package com.akgames.biriba3.controller;

/**
 * State of players turn.
 * <ul>
 *
 *
 * <br>
 * PICK     - Either pick a card from the deck or pick all the discarded cards.
 * <br>
 * TRITI    - (Only when picking discarded). Must use one of the cards in a triti.
 * <br>
 * DISCARD  - Must discard a card and allowed to create trites.
 * <br>
 * BIRIBAKI_PLAY - Can take biribaki. Has 1 or x card, picks 1, uses both in a triti takes biribaki,
 * is allowed to create triti and must discard.
 * <br>
 * BIRIBAKI_END -  has 1 or x card, picks 1, uses all but one in triti discards card, Takes biribaki,
 * not allowed to use triti.
 * <br>
 * END      - Can end turn
 * </ul>
 */
public class Turn {
	private static TurnPhases current;
	private static Turn instance = null;
	
	private Turn() {
		current = TurnPhases.PICK;
	}
	
	public static Turn getNewInstance() {
		instance = new Turn();
		return instance;
	}
	
	public static void nextPhase() {
		switch(current) {
			case PICK:
				current = TurnPhases.DISCARD;
				break;
			case DISCARD:
				current = TurnPhases.END;
				break;
			case END:
				current = TurnPhases.PICK;
				break;
		}
	}
	
	public static TurnPhases CurrentPhase() {
		return current;
	}
	
	public static void setCurrentPhaseTo(TurnPhases phase) {
		Turn.current = phase;
	}
	
	public enum TurnPhases {PICK, TRITI, DISCARD, BIRIBAKI_PLAY, BIRIBAKI_END, END}
}
