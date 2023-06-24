package com.akgames.biriba3.controller;

import com.badlogic.gdx.Gdx;

/**
 * PICK     - Either pick a card from the deck or pick all the discarded cards.
 * TRITI    - (Only when picking discarded) Must use one of the cards in a triti.
 * DISCARD  - Must discard a card and allowed to create trites.
 * END      - Can end turn
 * BIRIBAKI - Can take biribaki
 *
 *  case 1: has 1 or x card, picks 1, uses both in a triti
 *          takes biribaki, is allowed to create triti and must discard
 *  case 2: has 1 or x card, picks 1, uses all but one in triti
 *          discards card, Takes biribaki, not allowed to use triti
 *          ends turn
 */
public class Turn {
    public enum TurnPhases { PICK, TRITI, DISCARD, BIRIBAKI_PLAY, BIRIBAKI_END, END};
    private static TurnPhases current;
    private static Turn instance = null;

    private Turn() {
        current = TurnPhases.PICK;
    }

    public static Turn getInstance() {
        if (instance == null) {
            instance = new Turn();
        }
        current = TurnPhases.PICK;
        return instance;
    }

    public static void nextPhase() {
        switch (current) {
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
}
