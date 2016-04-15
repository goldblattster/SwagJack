package com.wilcoxpc.swagjack;

/**
 * Class used to represent Cards which can be used in Hands or Decks.
 */
public class Card {
    private Suit suit;
    private Rank rank;

    /**
     * Initialize a new Card.
     *
     * @param suit The suit of this card
     * @param rank The rank of this card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Get the suit of this card
     *
     * @return The suit of this card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Get the rank of this card
     *
     * @return The rank of this card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Get a String representation of this card
     *
     * @return A string representation of this card
     */
    @Override
    public String toString() {
        return suit.toString() + rank.toString();
    }
}
