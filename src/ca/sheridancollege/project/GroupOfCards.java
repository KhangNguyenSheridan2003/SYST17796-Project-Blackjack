/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 * 
 * @author dancye
 * @author Paul Bonenfant Jan 2020
 * @author Solo Creator Team - November 2025
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A concrete class that represents any grouping of cards for a Game. HINT, you might want to subclass this more than
 * once. The group of cards has a maximum size attribute which is flexible for reuse.
 */
public class GroupOfCards {

    //The group of cards, stored in an ArrayList
    private ArrayList<Card> cards;
    private final int maxSize;
    private int size;//the size of the grouping

    public GroupOfCards(int size) {
        this.maxSize = size;
        this.cards = new ArrayList<>();
    }

    /**
     * A method that will get the group of cards as an ArrayList
     *
     * @return the group of cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    /**
     * Sets the cards in this group.
     * 
     * @param cards the ArrayList of cards to set
     */
    public void setCards(ArrayList<Card> cards) {
        if (cards.size() > this.maxSize)
            System.out.println("Error: Size of input is larger than max size of group of card");
        else {
            this.cards = cards;
            this.size = cards.size();
        }
    }
    
     /**
     * Add a new card to this group.
     * 
     * @param card the ArrayList of cards to set
     */
    public void addCard(Card card){
        if (this.cards.size() == this.maxSize)
            System.out.println("Error: Cannot add more cards, group of card size is maxed out");
        else{
            this.cards.add(card);
            this.size++;
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Checks if the deck is empty.
     * 
     * @return true if no cards remain in the deck
     */
    public boolean isEmpty() {
        return getCards() == null;
    }
    
     /**
     * Get current size of the group of card.
     * 
     * @return number of card in the group of card
     */
    public int getSize(){
        return this.size;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public void increaseSize(){
        this.size++;
    }
    
    public int getMaxSize(){
        return this.maxSize;
    }
}//end class
