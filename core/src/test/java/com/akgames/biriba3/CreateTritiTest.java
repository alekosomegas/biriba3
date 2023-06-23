package com.akgames.biriba3;


import com.akgames.biriba3.controller.GameLogic;
import com.akgames.biriba3.controller.GameOptions;
import com.akgames.biriba3.model.Card;
import com.akgames.biriba3.model.Triti;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateTritiTest {
    GameLogic gameLogic = GameLogic.getInstance();
    GameOptions gameOptions = gameLogic.gameOptions;
    boolean startGame = gameOptions.createPlayers();

    Card joker = new Card(-1);
    Card diamondThree = new Card(2);
    Card diamondFour = new Card(3);
    Card diamondFive = new Card(4);
    Card diamondSix = new Card(5);
    Card diamondSeven = new Card(6);
    Card diamondNine = new Card(8);
    Card diamondTen = new Card(9);
    Card clubAce = new Card(13);
    Card clubKing = new Card(25);
    Card clubQueen = new Card(24);

    Card clubFour = new Card(16);
    Card diamondTwo = new Card(1);
    Card diamondAce = new Card(0);
    Card diamondTwo2 = new Card(1);
    Card clubTwo = new Card(14);
    Card heartTwo = new Card(27);

    @Test
    void InvalidTriti() {
        // less than three cards
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSix, diamondFive))));
        // 2 jokers
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(joker, joker, diamondSix))));
        // Three 2's
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondTwo2, clubTwo))));

    }

    @Test
    void NoJokersTriti() {
        // Same suit, sequential
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSix, diamondFive, diamondSeven))));
        // Same suit, not sequential
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, diamondSeven, diamondNine))));
        // Sequential, different suit
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(clubFour, diamondFive, diamondSix))));
        // Not sequential, different suit
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, diamondSeven, clubAce))));
    }

    @Test
    void OneJokerTriti() {
        // 1 joker, one gap, same suit
        Triti triti1 = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondFive, diamondSeven)));
        Assertions.assertNotNull(triti1);

        // 1 joker, sequential, same suit
        Triti triti2 = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondSix, diamondFive)));
        Triti triti3 = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondSix, diamondFive, diamondSeven)));
        Assertions.assertNotNull(triti2);
        Assertions.assertNotNull(triti3);

        // 1 joker, 2 gaps, same suit
        Triti triti4 = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondFive, diamondSeven, diamondNine)));
        Assertions.assertNull(triti4);
        // 1 joker, 2 gaps, same suit
        Triti triti4a = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondFive, diamondSeven, diamondTen)));
        Assertions.assertNull(triti4a);

        // 1 joker, 1 gap , different suit
        Triti triti5 = Triti.createTriti(new ArrayList<>(Arrays.asList(joker, clubFour, diamondFive, diamondSix)));
        Assertions.assertNull(triti5);

        // Joker in the Middle
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondSeven, joker, diamondFive)), triti1.getCards());
//         Joker at the Bottom
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondSix, diamondFive, joker)), triti2.getCards());
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondSeven,  diamondSix, diamondFive, joker)), triti3.getCards());
    }

    @Test
    void MultipleTwosInListNoJoker() {
        // NO JOKER //
        //----------//

        //  Normal 2 (e.g. 2-3-4)
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, diamondFour))));
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(clubTwo, diamondThree, diamondFour))));
        // 2 as a Joker (e.g. 3-2-5)
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, diamondFive))));
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondFive, diamondTwo, diamondThree)),
                Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, diamondFive))).getCards());
        // 2 as Joker at bottom (e.g. 7-6-2)
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, clubTwo)),
                Triti.createTriti(new ArrayList<>(Arrays.asList(diamondSeven, diamondSix, clubTwo))).getCards());

        // Two 2s in a non-joker triti (e.g. 2-2-3-4-5)
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondTwo2, diamondThree))));
        // Two 2s in a non-joker triti and a gap (e.g. 2-3-2-5)
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, diamondTwo2, diamondFive))));

        // reset value
        diamondTwo = new Card(1);
        // Two 2s in a non-joker triti and 2 gaps (e.g. 2-3-2-5-7)
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, diamondTwo2, diamondFive, diamondSeven))));
        diamondTwo = new Card(1);
        diamondTwo2 = new Card(1);
        // Two 2s in a non-joker triti and big gap (e.g. 2-3-2-7)
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, diamondTwo2, diamondSeven))));
        diamondTwo = new Card(1);
        diamondTwo2 = new Card(1);
        clubTwo = new Card(14);
        // Two 2s in a non-joker same suit triti and one 2 is different suit(joker)  (e.g. 2d-3d-2c-5d)
        Triti triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, diamondThree, clubTwo, diamondFive)));
        Assertions.assertNotNull(triti);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondFive, clubTwo, diamondThree, diamondTwo)), triti.getCards());
        clubTwo = new Card(14);
        // Two 2s in a non-joker same suit triti and both 2s have different suit  (e.g. 2h-3d-2c-5d)
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(heartTwo, diamondThree, clubTwo, diamondFive))));


    }

    @Test
    void MultipleTwosInListWithJoker() {
        // WITH JOKER //
        //------------//

        // Normal 2 and a joker (e.g. 2-J-4)
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, joker, diamondFour))));
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondFour, joker, diamondTwo)),
                Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, joker, diamondFour))).getCards());
        // Normal 2 and a joker (e.g. 2-3-J)
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, joker, diamondThree))));
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondThree, diamondTwo, joker)),
                Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo, joker, diamondThree))).getCards());
        // Two 2s and a joker(same as 2 jokers)
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondTwo,diamondTwo2, joker))));
        // A 2 and a joker (same as 2 jokers) (e.g. 5-2-7-J-9)
        Assertions.assertNull(Triti.createTriti(new ArrayList<>(Arrays.asList(diamondFive, diamondTwo, diamondSeven, joker, diamondNine))));
    }

    @Test
    void TritiWithAce() {
        // Ace on top (e.g. A-K-Q)
        Triti triti = Triti.createTriti(new ArrayList<>(Arrays.asList(clubAce, clubKing, clubQueen)));
        Assertions.assertNotNull(triti);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(clubAce, clubKing, clubQueen)), triti.getCards());

        // Ace on bottom (e.g 3-2-A)
        triti = Triti.createTriti(new ArrayList<>(Arrays.asList(diamondAce, diamondTwo, diamondThree)));
        Assertions.assertNotNull(triti);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(diamondThree, diamondTwo, diamondAce)), triti.getCards());

        // J-3-2-A
        Assertions.assertNotNull(Triti.createTriti(new ArrayList<>(Arrays.asList(joker, diamondThree, diamondTwo, diamondAce))));
        // 2-3-2-A
        Assertions.assertNotNull(new ArrayList<>(Arrays.asList(clubTwo, diamondThree, diamondTwo, diamondAce)));
    }


}
