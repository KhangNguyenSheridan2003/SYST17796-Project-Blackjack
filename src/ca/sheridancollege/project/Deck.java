/**
 * SYST 17796 Project - Blackjack
 * A deck class representing a standard 52-card deck.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

/**
 * Represents a standard 52-card deck for Blackjack.
 * Extends GroupOfCards and adds functionality to reset and draw cards.
 */
public class Deck extends GroupOfCards {
    
    /**
     * Constructor to create a new shuffled deck of 52 cards.
     */
    public Deck() {
        super(52);
        reset();
    }
    
    /**
     * Resets the deck to a full 52-card deck and shuffles it.
     * Creates all combinations of suits and ranks.
     */
    public void reset() {
        ArrayList<Card> cards = new ArrayList<>();
        
        // Create all 52 cards (4 suits × 13 ranks)
        for (BlackjackCard.Suit suit : BlackjackCard.Suit.values()) {
            for (BlackjackCard.Rank rank : BlackjackCard.Rank.values()) {
                this.addCard(new BlackjackCard(suit, rank));
            }
        }
        this.shuffle();
    }
    
         /**
     * Draws (removes and returns) the top card from the deck.
     * 
     * @return the top card, or null if deck is empty
     */
    public Card draw() {
        if (this.isEmpty()) {
            System.out.println("Error: Deck is empty");
            return null;
        }
        this.increaseSize();
        return this.getCards().remove(0);
    }
    
    /**
     * Returns a string representation of the deck.
     * 
     * @return string showing number of cards remaining
     */
    @Override
    public String toString() {
        return "Deck: " + this.getSize() + " cards remaining";
    }
}
