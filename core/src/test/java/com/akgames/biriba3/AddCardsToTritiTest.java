package com.akgames.biriba3;

import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCardsToTritiTest {
    GameLogic gameLogic = GameLogic.getInstance();
    GameOptions gameOptions = gameLogic.gameOptions;
    boolean startGame = gameOptions.createPlayers();

    Card diamondEight = new Card(7);
    Card diamondSeven = new Card(6);
    Card diamondSix = new Card(5);
    Card diamondFive = new Card(4);
    Card diamondFour = new Card(3);
    Card diamondThree = new Card(2);
    Card diamondTwo = new Card(1);
    Card diamondTwo2 = new Card(1);
    Card diamondAce = new Card(0);
    Card clubTwo = new Card(14);
    Card diamondTen = new Card(9);
    Card clubTen = new Card(22);
    Card clubJack = new Card(23);
    Card clubQueen = new Card(24);
    Card clubKing = new Card(25);
    Card clubAce = new Card(13);


    Card clubFour = new Card(16);

    Card joker = new Card(-1);
    Triti triti = null;

    @Test
    void addSingleValidCardBellow() {

        //     Valid Cases    //
        // ------------------//
        // regular
        // 7-6-5    + 4
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(7, 6, 5, 4), triti.getCardsAsValues());

        // 7-6-5    + J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(7, 6, 5, 4), triti.getCardsAsValues());

        // 7-6-5    + 2
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        triti.addCard(diamondTwo);
        Assertions.assertEquals(Arrays.asList(7, 6, 5, 4), triti.getCardsAsValues());

        // 7-J-5    + 4
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, joker, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(7, 6, 5, 4),
                triti.getCardsAsValues());

        // 7-6-J    + 4
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, joker)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(7, 6, 5, 4),
                triti.getCardsAsValues());

        // jokers

        // 7d-6d-5d + 2c
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(clubTwo);
        Assertions.assertEquals(Arrays.asList(7, 6, 5, 4),
                triti.getCardsAsValues());

        // 5d-4d-3d + 2c
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, diamondFour, diamondThree)));
        Assertions.assertNotNull(triti);
        Card clubTwo = new Card(14);
        triti.addCard(clubTwo);
        Assertions.assertEquals(Arrays.asList(5, 4, 3, 2),
                triti.getCardsAsValues());

    }

    @Test
    void testAce() {
        // ace
        // 4-3-2    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1), triti.getCardsAsValues());

        // 4-3-2    + J     => 4-3-2-J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1), triti.getCardsAsValues());

        // 4-3-2    + 2
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        triti.addCard(diamondTwo2);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1), triti.getCardsAsValues());

        // 5-J-3-2    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, joker, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(5, 4, 3, 2, 1),
                triti.getCardsAsValues());

        // 3-2-J    + A     >> Move joker up
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1),
                triti.getCardsAsValues());

        // 4-J-2    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, joker, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1),
                triti.getCardsAsValues());

        // 4-3-J    + A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, joker)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondAce);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1),
                triti.getCardsAsValues());

    }

    @Test
    void addSingleInvalidCards() {
        //     Invalid Cases    //
        // ---------------------//
        // same suit, wrong rank
        // 7-6-5    + 3
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondThree);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, diamondFive),
                triti.getCards());
        // 7-6-5    + 10
        triti.addCard(diamondTen);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, diamondFive),
                triti.getCards());


        // wrong suit
        // 7d-6d-5d + 4c
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(clubFour);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondSix, diamondFive), triti.getCards());

        // Joker
        // 3-2-2    + J
        diamondTwo = new Card(1);
        diamondTwo2 = new Card(1);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondThree, diamondTwo2, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(3, 2, 1)), triti.getCardsAsValues());

        // 5-2-3-2  + J
        diamondTwo = new Card(1);
        diamondTwo2 = new Card(1);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, diamondTwo2, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(diamondFive, diamondTwo2, diamondThree, diamondTwo), triti.getCards());

        // 4-2-2    + J
        diamondTwo = new Card(1);
        diamondTwo2 = new Card(1);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondTwo2, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(diamondFour, diamondTwo2, diamondTwo), triti.getCards());

        // 6-J-4    + J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSix, joker, diamondFour)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(diamondSix, joker, diamondFour), triti.getCards());

    }

    @Test
    void ReplaceJoker() {
        // 6-J-4   + 5      => 6-5-4-J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSix, joker, diamondFour)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFive);
        Assertions.assertEquals(Arrays.asList(6, 5, 4, 3), triti.getCardsAsValues());

        // 6-5-J   + 4      => 6-5-4-J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSix, diamondFive, joker)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(6, 5, 4, 3), triti.getCardsAsValues());

        // J-2-A    + 3     => J-3-2-A
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondTwo, diamondAce)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondThree);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1), triti.getCardsAsValues());

        // A-J-Q    + K     => A-K-Q-J
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(clubAce, joker, clubQueen)));
        Assertions.assertNotNull(triti);
        triti.addCard(clubKing);
        Assertions.assertEquals(Arrays.asList(14, 13, 12, 11), triti.getCardsAsValues());

        // 5-2-3   + 4     => 5-4-3-2
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, diamondTwo, diamondThree)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondFour);
        Assertions.assertEquals(Arrays.asList(5, 4, 3, 2), triti.getCardsAsValues());
        // 5-4-3-2   + J    => 5-4-3-2-J
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(5, 4, 3, 2, 1), triti.getCardsAsValues());

        // 3-J-A    + 2     => J-3-2-A
        diamondTwo = new Card(1);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondThree, joker, diamondAce)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondTwo);
        Assertions.assertEquals(Arrays.asList(4, 3, 2, 1), triti.getCardsAsValues());

        // 3d-2c-Ad    +2d    => 2c-3d-2d-Ad
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondThree, clubTwo, diamondAce)));
        Assertions.assertNotNull(triti);
        triti.addCard(diamondTwo);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(4, 3, 2, 1)), triti.getCardsAsValues());
    }

    @Test
    void CompleteTriti() {
        ArrayList<Card> cards = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            cards.add(new Card(i));
        }
        Triti triti = Triti.createTriti(cards);
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(cards, triti.getCards());
    }

    @Test
    void TwoAsAJoker() {
        // 7-2-5    + J
        diamondTwo2 = new Card(1);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondTwo2, diamondFive)));
        Assertions.assertNotNull(triti);
        triti.addCard(joker);
        Assertions.assertEquals(Arrays.asList(diamondSeven, diamondTwo2, diamondFive), triti.getCards());

        // 3-2-2    + 2     => 3-2-2
        diamondTwo2 = new Card(1);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo2, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(clubTwo);
        Assertions.assertEquals(Arrays.asList(3, 2, 1), triti.getCardsAsValues());

        // 4-2-2    + 2
        diamondTwo = new Card(1);
        diamondTwo2 = new Card(1);
        clubTwo = new Card(14);
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondTwo2, diamondTwo)));
        Assertions.assertNotNull(triti);
        triti.addCard(clubTwo);
        Assertions.assertEquals(Arrays.asList(diamondFour, diamondTwo2, diamondTwo), triti.getCards());
    }

    @Test
    void duplicates() {
        // 4-3-2    + 3
        Triti triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)));
        Assertions.assertNotNull(triti);
        Card diamondThree2 = new Card(2);
        triti.addCard(diamondThree2);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondFour, diamondThree, diamondTwo)), triti.getCards());

        // Q-J-10   + Q
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(clubQueen, clubJack, clubTen)));
        Assertions.assertNotNull(triti);
        Card clubQueen2 = new Card(24);
        triti.addCard(clubQueen2);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(clubQueen, clubJack, clubTen)), triti.getCards());
    }

}
