package com.wilcoxpc.swagjack;

/**
 * Class used to represent Rank which can be used to describe suit in cards.
 */
public enum Rank {
    // initialize ranks with array of possible values used for generating hand sums
    TWO("2", new int[]{2}), THREE("3", new int[]{3}), FOUR("4", new int[]{4}), FIVE("5", new int[]{5}),
    SIX("6", new int[]{6}), SEVEN("7", new int[]{7}), EIGHT("8", new int[]{8}), NINE("9", new int[]{9}),
    TEN("10", new int[]{10}), JACK("J", new int[]{10}), QUEEN("Q", new int[]{10}), KING("K", new int[]{10}),
    ACE("A", new int[]{1, 11});

    private String readableName;
    private int[] values;

    /**
     * Initialize a new rank enum.
     *
     * @param readableName Readable name of card used when printing game status.
     * @param values Values that can be used to calculate hand sums.
     */
    Rank(String readableName, int[] values) {
        this.readableName = readableName;
        this.values = values;
    }

    /**
     * Get the possible values of this rank. For instance, the possible values
     * for ace are 1 and 11.
     *
     * @return The possible values of this rank
     */
    public int[] getValues() {
        return this.values;
    }

    /**
     * Get a String representation of this rank
     *
     * @return A string representation of this rank
     */
    @Override
    public String toString() {
        return this.readableName;
    }
}
