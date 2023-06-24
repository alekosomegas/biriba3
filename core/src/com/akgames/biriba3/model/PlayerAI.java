package com.akgames.biriba3.model;


import com.akgames.biriba3.actions.*;
import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.Turn;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.akgames.biriba3.controller.Turn.TurnPhases.DISCARD;

//TODO: chose Actions at random
public class PlayerAI extends  Player{
    List<Card> validCombination;

    public PlayerAI(String name, int teamNumber) {
        super(name, teamNumber);
        this.validCombination = new ArrayList<>();
    }

    @Override
    public void act() {
        Random rand = new Random();
        // either 0 or 1
        int randomNum = 0;
        int randNumCard = rand.nextInt(getCardCount());


        // 1. Do discards hold a triti?
        // 2. Do 2 cards from the discards + 1 from hand form a triti?
        // 3. Does 1 card from discards + 2 from hand form a triti?
        // 4. Does one card from discards be added to existing triti?
        // 5. A card from discards plus 1 from hand can be added to existing triti


        // TODO: refactor name
        // picked from discards


        // TODO: this only checks one card against existing, need to check only discards and any combination of 2s against hand

        // if it can't find triti then pick from deck
        if (!findTriti()) randomNum = 1;

        if (randomNum == 0) {
            GameLogic.getInstance().handleAction(new PickDiscards());
            GameLogic.getInstance().handleAction(new CreateTritiAction(validCombination));
        } else {
            GameLogic.getInstance().handleAction(new PickFromDeck());
        }


        Turn.setCurrentPhaseTo(DISCARD);
        GameLogic.getInstance().handleAction(new ThrowCardToDiscards(getHand().get(randNumCard).turn()));
        GameLogic.getInstance().handleAction(new EndTurn());
    }

    private boolean findTriti() {
        List<Card> discards = new ArrayList<>(GameLogic.getInstance().getBoard().getDiscardPile());
        this.groups = groupHandBySuit();

        // 4. Does one card from discards be added to existing triti?
        // TODO: not working
        for (Card card : discards) {
            List<Triti> trites = GameLogic.getInstance().getBoard().getTrites(getTeamNumber());
            for (Triti triti : trites) {
                if (triti.validateAddCard(card)) {
                    triti.addCard(card);
                    return true;
                }
            }
        }

        // 1. Do discards hold a triti?
        if (discards.size() > 2) {
            if (findValidTritiIn(discards)) {
                return true;
            };
        }

        // 2. Do 2 cards from the discards + 1 from hand form a triti?
        if (discards.size() > 1) {
            if (findValidTriti(discards)) {
                return true;
            }
        }

        // 3. Does 1 card from discards + 2 from hand form a triti?
        for (Card card : discards) {
            if (findValidTriti(card)) {
                return true;
            }
        }
        // tried everything
        return false;
    }

    private boolean findValidTritiIn(List<Card> discards) {
        List<List<Card>> combinationsFromDiscards = generateCombinations3s(discards);

        for (List<Card> combination : combinationsFromDiscards) {
            if(Triti.validate(combination)) {
                validCombination = combination;
                return true;
            }
        }
        return false;
    }
    
    private boolean findValidTriti(List<Card> discards) {
        List<List<Card>> combinationsFromDiscards = generateCombinationsPairs(discards);

        // TODO: remove jokers add them later( check normals, check jokers, check 2 not optimal but ok)
        for (Card cardHand : getHand()) {
            for(List<Card> combination : combinationsFromDiscards) {
                combination.add(cardHand);
                if(Triti.validate(combination)) {
                    validCombination = combination;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findValidTriti(Card card) {
        Gdx.app.log(this.getClass().getName(), "Looking for a valid triti using card : " +card);
        List<List<Card>> groups = this.groups;

        List<List<Card>> combinations;
        int i = 0;
        // check suits groups
        for (List<Card> group : groups) {
            if (i > 3) break;
            combinations = generateCombinationsPairs(group);
            for (List<Card> combination : combinations) {
                combination.add(card);
                if(Triti.validate(combination)) {
                    validCombination = combination;
                    return true;
                }
            }
            i++;
        }
//
//        // try jokers, if no jokers try 2s
//        if (jokers.size() > 0) {
//            Card joker = jokers.get(0);
//            List<Card> combination = Arrays.asList(joker, card);
//            for (Card handCard : getHand()) {
//                Gdx.app.log("Fails", joker.toString());
//                combination.add(handCard);
//                if(Triti.validate(combination)) {
//                    validCombination = combination;
//                    return true;
//                }
//            }
//        }
//        if (twos.size() > 0) {
//            Card joker = twos.get(0);
//            List<Card> combination = Arrays.asList(joker, card);
//            for (Card handCard : getHand()) {
//                Gdx.app.log("Fails 2", joker.toString());
//                combination.add(handCard);
//                if(Triti.validate(combination)) {
//                    validCombination = combination;
//                    return true;
//                }
//            }
//        }

        return false;
    }

    private List<List<Card>> generateCombinations3s(List<Card> group) {
        List<List<Card>> combinations = new ArrayList<>();

        for (int i = 0; i < group.size() - 2; i++) {
            for (int j = i + 1; j < group.size() -1; j++) {
                for (int k = 0; k < group.size(); k++) {
                    List<Card> combination = new ArrayList<>();
                    combination.add(group.get(i));
                    combination.add(group.get(j));
                    combination.add(group.get(k));
                    combinations.add(combination);

                }
            }
        }
        return combinations;
    }
    
    private List<List<Card>> generateCombinationsPairs(List<Card> group) {
        List<List<Card>> combinations = new ArrayList<>();

        for (int i = 0; i < group.size() - 1; i++) {
            for (int j = i + 1; j < group.size(); j++) {
                List<Card> combination = new ArrayList<>();
                combination.add(group.get(i));
                combination.add(group.get(j));
                combinations.add(combination);
            }
        }
        return combinations;
    }



    @Override
    public void addToHand(Card card) {
        card.setShowFace(false);
        getHand().add(card);
    }

    @Override
    public void addToHand(List<Card> cards) {
        for (Card card : cards)
            {addToHand(card);
        }
    }
}
