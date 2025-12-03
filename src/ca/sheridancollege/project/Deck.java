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
                cards.add(new BlackjackCard(suit, rank));
            }
        }
        
        setCards(cards);
        shuffle();
    }
    
    /**
     * Draws (removes and returns) the top card from the deck.
     * 
     * @return the top card, or null if deck is empty
     */
    public Card draw() {
        if (isEmpty()) {
            return null;
        }
        return getCards().remove(0);
    }
    
    /**
     * Checks if the deck is empty.
     * 
     * @return true if no cards remain in the deck
     */
    public boolean isEmpty() {
        return getCards() == null || getCards().isEmpty();
    }
    
    /**
     * Gets the number of cards remaining in the deck.
     * 
     * @return the number of remaining cards
     */
    public int cardsRemaining() {
        return getCards() == null ? 0 : getCards().size();
    }
    
    /**
     * Returns a string representation of the deck.
     * 
     * @return string showing number of cards remaining
     */
    @Override
    public String toString() {
        return "Deck: " + cardsRemaining() + " cards remaining";
    }
}
