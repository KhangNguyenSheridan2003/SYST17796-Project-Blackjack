/**
 * SYST 17796 Project - Blackjack
 * A card class specifically for Blackjack game.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

/**
 * Represents a playing card for Blackjack with a suit and rank.
 * Extends the abstract Card class.
 */
public class BlackjackCard extends Card {
    
    /**
     * Enum representing the four suits of a standard deck.
     */
    public enum Suit {
        HEARTS("Hearts", "H"),
        DIAMONDS("Diamonds", "D"),
        CLUBS("Clubs", "C"),
        SPADES("Spades", "S");
        
        private final String name;
        private final String symbol;
        
        Suit(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }
        
        public String getName() {
            return name;
        }
        
        public String getSymbol() {
            return symbol;
        }
    }
    
    /**
     * Enum representing the 13 ranks of a standard deck.
     */
    public enum Rank {
        ACE("A", 11),      // Ace can be 1 or 11, default to 11
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 10),
        QUEEN("Q", 10),
        KING("K", 10);
        
        private final String symbol;
        private final int value;
        
        Rank(String symbol, int value) {
            this.symbol = symbol;
            this.value = value;
        }
        
        public String getSymbol() {
            return symbol;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    // Instance variables
    private final Suit suit;
    private final Rank rank;
    
    /**
     * Constructor to create a card with specified suit and rank.
     * 
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public BlackjackCard(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    /**
     * Gets the suit of this card.
     * 
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }
    
    /**
     * Gets the rank of this card.
     * 
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }
    
    /**
     * Gets the Blackjack value of this card.
     * Face cards (J, Q, K) are worth 10.
     * Ace is worth 11 (adjustment for 1 is handled in Hand class).
     * Number cards are worth their face value.
     * 
     * @return the point value of this card
     */
    public int getValue() {
        return rank.getValue();
    }
    
    /**
     * Checks if this card is an Ace.
     * 
     * @return true if this card is an Ace
     */
    public boolean isAce() {
        return rank == Rank.ACE;
    }
    
    /**
     * Returns a string representation of this card.
     * 
     * @return a string like "A♠" or "K♥"
     */
    @Override
    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }
    
    /**
     * Returns a detailed string representation of this card.
     * 
     * @return a string like "Ace of Spades"
     */
    public String toDetailedString() {
        String rankName;
        switch (rank) {
            case ACE: rankName = "Ace"; break;
            case JACK: rankName = "Jack"; break;
            case QUEEN: rankName = "Queen"; break;
            case KING: rankName = "King"; break;
            default: rankName = rank.getSymbol(); break;
        }
        return rankName + " of " + suit.getName();
    }
}
