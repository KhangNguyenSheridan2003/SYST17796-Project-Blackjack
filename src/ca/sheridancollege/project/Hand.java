/**
 * SYST 17796 Project - Blackjack
 * A hand class representing the cards a player holds.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

/**
 * Represents a player's hand in Blackjack.
 * Extends GroupOfCards to manage a collection of cards.
 */
public class Hand extends GroupOfCards {
    
    /**
     * Constructor to create an empty hand.
     */
    public Hand() {
        super(21); // Maximum meaningful size in Blackjack
        setCards(new ArrayList<>());
    }
    
    /**
     * Clears all cards from this hand.
     */
    public void clear() {
        this.getCards().clear();
        this.setSize(0);
    }
    
    /**
     * Calculates the total value of this hand.
     * Handles Aces intelligently: counts as 11 unless that would bust,
     * then counts as 1.
     * 
     * @return the total value of the hand
     */
    public int getValue() {
        int value = 0;
        int aceCount = 0;
        
        for (Card card : this.getCards()) {
            BlackjackCard bjCard = (BlackjackCard) card;
            value += bjCard.getValue();
            if (bjCard.isAce()) {
                aceCount++;
            }
        }
        
        // If we have Aces and we're over 21, convert Aces from 11 to 1
        while (value > 21 && aceCount > 0) {
            value -= 10; // Convert one Ace from 11 to 1
            aceCount--;
        }
        
        return value;
    }
    
    /**
     * Checks if this hand is busted (over 21).
     * 
     * @return true if hand value exceeds 21
     */
    public boolean isBusted() {
        return this.getValue() > 21;
    }
    
    /**
     * Checks if this hand is a Blackjack (exactly 21 with 2 cards).
     * 
     * @return true if this is a natural Blackjack
     */
    public boolean isBlackjack() {
        return this.getSize() == 2 && getValue() == 21;
    }
    
    /**
     * Gets the number of cards in this hand.
     * 
     * @return the number of cards
     */
    public int getCardCount() {
        return this.getSize();
    }
    
    /**
     * Returns a string representation of the hand showing all cards.
     * 
     * @return string showing all cards and total value
     */
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[Empty Hand]";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getCards().size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append("[").append(getCards().get(i).toString()).append("]");
        }
        sb.append(" (Value: ").append(getValue()).append(")");
        return sb.toString();
    }
    
    /**
     * Returns a string representation with one card hidden (for dealer).
     * 
     * @return string showing first card and hidden card
     */
    public String toStringHidden() {
        if (getCards().size() < 2) {
            return toString();
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getCards().get(0).toString()).append("] [??]");
        sb.append(" (Value: ??)");
        return sb.toString();
    }
}
