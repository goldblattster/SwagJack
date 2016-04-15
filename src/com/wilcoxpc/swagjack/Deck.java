package com.wilcoxpc.swagjack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class used to represent decks as part of the game.
 */
public class Deck {
    private Card[] cards;
    int nextIndex;

    /**
     * Initialize a new deck. It creates an ordered 52 card deck.
     */
    public Deck() {
        this.cards = new Card[52];
        this.nextIndex = 0;

        for(int i = 0; i < Suit.values().length; i++) { // iterate over all possible suits
            for(int j = 0; j < Rank.values().length; j++) { // iterate over all possible values
                // add card to cards array with array and suit of the current iteration
                this.cards[this.nextIndex] = new Card(Suit.values()[i], Rank.values()[j]);
                ++this.nextIndex;
            }
        }

    }

    /**
     * Get all cards in this deck represented by Card array.
     *
     * @return The cards in this deck.
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * Shuffle the cards in this deck. It is necessary to call this method for the deck to be randomized.
     */
    public void shuffle() {
        Random random = ThreadLocalRandom.current();
        for (int i = this.cards.length - 1; i > 0; i--) { // iterate over cards in deck
            int index = random.nextInt(i + 1); // find random index

            // swap a card from random index with card on iteration
            Card a = this.cards[index];
            this.cards[index] = this.cards[i];
            this.cards[i] = a;
        }
    }

    /**
     * Draw a card from the top of the deck.
     *
     * @return A card from the top of the deck
     */
    public Card draw() {
        --nextIndex; // move cursor down
        return this.cards[nextIndex];
    }

    /**
     * Get a String representation of this deck
     *
     * @return A string representation of this deck
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(Card card : this.cards) {
            builder.append(card.toString()); // build string representation from each card
        }

        return builder.toString();
    }
}
