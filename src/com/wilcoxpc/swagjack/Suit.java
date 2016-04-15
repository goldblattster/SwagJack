package com.wilcoxpc.swagjack;

/**
 * Class used to represent Suit which can be used to describe suit in cards.
 */
public enum Suit {
    // initialize suits with array of possible values used for generating hand sums
    SPADE("♠"), CLUB("♣"), DIAMOND("♦"), HEART("♥");

    private String readableName;

    /**
     * Initialize a new suit enum.
     *
     * @param readableName Readable name of suit used when printing game status.
     */
    Suit(String readableName) {
        this.readableName = readableName;
    }

    /**
     * Get a String representation of this suit
     *
     * @return A string representation of this suit
     */
    @Override
    public String toString() {
        return this.readableName;
    }
}
