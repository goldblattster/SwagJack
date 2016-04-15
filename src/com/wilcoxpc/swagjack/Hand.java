package com.wilcoxpc.swagjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used to represent player and computer hands.
 */
public class Hand {
    private ArrayList<Card> cards;

    /**
     * Initialize a Hand with no cards.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Initialize a Hand with some cards.
     *
     * @param cards Cards that the Hand is to be initialized with.
     */
    public Hand(Card[] cards) {
        this.cards = new ArrayList<>();
        Collections.addAll(this.cards, cards); // add cards from parameter to class var
    }

    /**
     * Add a new card to the hand
     *
     * @param card Card to add to the hand
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * Get the cards that are in this hand
     *
     * @return Cards that are in the hand
     */
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    /**
     * Get the possible sums of the cards in the hand. For instance, a hand
     * with an 4 of hearts and an ace of spades would have possible sums of
     * 5 and 15.
     *
     * @param hand Hand to get the possible sums from
     * @return The possible sums of hand
     */
    public static ArrayList<Integer> getPossibleSums(Hand hand) {
        ArrayList<Integer> sums = new ArrayList<>();

        int currentSum = 0;
        int sumAsOne = 0;
        int sumAsEleven = 0;

        boolean firstAce = true; // there can only be one ace counted as 11, or it would go over 21

        for(Card card: hand.getCards()) {
            if (card.getRank() == Rank.ACE) {
                if (firstAce) {
                    firstAce = false;

                    sumAsEleven += 11; // treat this ace as if its value were 11
                } else {
                    sumAsEleven += 1; // if there was already a card counted as 11 this one needs to be treated as 1
                }

                sumAsOne++; // card could be counted as one if it is counted as eleven
            } else {
                sumAsOne += card.getRank().getValues()[0]; // if the card is not an ace, add it to all the sums
                sumAsEleven += card.getRank().getValues()[0];
                currentSum += card.getRank().getValues()[0];
            }
        }

        if (!firstAce) { // if there was an ace, then use sumAsEleven and sumAsOne
            sums.add(sumAsEleven);
            sums.add(sumAsOne);
        } else { // otherwise use the simple sum
            sums.add(currentSum);
        }

        // Eliminate all sums over 21, they don't count

        for (int i = 0; i < sums.size(); i++) {
            if (sums.get(i) > 21) {
                sums.remove(i);
            }
        }

        return sums;

    }

    /**
     * Validate the hands of the player and computer to verify the status of the game
     *
     * @param computerHand Hand of the computer
     * @param playerHand Hand of the player
     * @return The current status of the game
     */
    public static GameResult validate(Hand computerHand, Hand playerHand) {
        // calculate possible sums of the player and computer
        ArrayList<Integer> playerPossibleSums = Hand.getPossibleSums(playerHand);
        ArrayList<Integer> computerPossibleSums = Hand.getPossibleSums(computerHand);

        // if the player has no possible sums, he has lost
        if (playerPossibleSums.size() == 0) {
            System.out.println("You busted with the following cards: " + String.join(", ", playerHand.getCards().toString()));
            System.out.println();
            return GameResult.PLAYER_LOSS;
        }

        // if the computer has no possible sums, he has lost
        if (computerPossibleSums.size() == 0) {
            System.out.println("Computer busted with the following cards: " + String.join(", ", computerHand.getCards().toString()));
            System.out.println("Your hand: " + String.join(", ", playerHand.getCards().toString()));
            System.out.println();
            return GameResult.PLAYER_WIN;
        }

        // get the max sum of the player and computer
        int playerMax = Collections.max(playerPossibleSums);
        int computerMax = Collections.max(computerPossibleSums);

        // if the player has no possible sums, he has lost
        if (playerMax == 21) {
            System.out.println("You reached 21 with the following cards: " + String.join(", ", playerHand.getCards().toString()));
            System.out.println();
            return GameResult.PLAYER_WIN;
        }

        // if the computer has no possible sums, he has lost
        if (computerMax == 21) {
            System.out.println("Computer reached 21 with the following cards: " + String.join(", ", computerHand.getCards().toString()));
            System.out.println("Your hand: " + String.join(", ", playerHand.getCards().toString()));
            System.out.println();
            return GameResult.PLAYER_LOSS;
        }

        // continue playing if there is no game-ending status
        return GameResult.CONTINUE;

    }
}
