package com.akgames.biriba3;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCardsToTriti {
    GameLogic gameLogic = GameLogic.getInstance();
    GameOptions gameOptions = gameLogic.gameOptions;
    boolean startGame = gameOptions.createPlayers();

    Card diamondSeven = new Card(6);
    Card diamondSix = new Card(5);
    Card diamondFive = new Card(4);
    Card diamondFour = new Card(3);
    Card diamondThree = new Card(2);
    Card diamondTwo = new Card(1);
    Card diamondTwo2 = new Card(1);
    Card diamondAce = new Card(0);



    Card joker = new Card(-1);
    Triti triti = null;

    @Test
    void addSingleSameSuitCardBellow() {

        //     Valid Cases    //
        // ------------------//
        // regular
        // 7-6-5    + 4
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, diamondFive, diamondFour),
                triti.getCards());

        // 7-J-5    + 4
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, joker, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(diamondSeven, joker, diamondFive, diamondFour),
                triti.getCards());

        // 7-6-J    + 4
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, joker)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, joker, diamondFour),
                triti.getCards());

        // ace
        // 4-3-2    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(diamondFour, diamondThree, diamondTwo, diamondAce),
                triti.getCards());

        // 5-J-3-2    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, joker, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(diamondFive, joker, diamondThree, diamondTwo, diamondAce),
                triti.getCards());

        // 3-2-J    + A     >> Move joker up
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(joker, diamondThree, diamondTwo, diamondAce),
                triti.getCards());

        // 4-J-2    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, joker, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(diamondFour, joker, diamondTwo, diamondAce),
                triti.getCards());

        // 4-3-J    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, joker)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(diamondFour, diamondThree, joker, diamondAce),
                triti.getCards());

        // jokers
        // 7-6-5    + J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, diamondFive, joker),
                triti.getCards());

        // 7-6-5    + 2
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondTwo);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, diamondFive, diamondTwo),
                triti.getCards());

        // 4-3-2    + J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(diamondFour, diamondThree, diamondTwo, joker),
                triti.getCards());

        // 4-3-2    + 2
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondTwo2);
        Assertions.assertEquals(Arrays.asList(diamondFour, diamondThree, diamondTwo, diamondTwo2),
                triti.getCards());

        //     InValid Cases    //
        // ---------------------//
        // 2-3-2    + J
        // 4-2-2    + J

        // 2-3-2    + 2
        // 4-2-2    + 2
    }
}
