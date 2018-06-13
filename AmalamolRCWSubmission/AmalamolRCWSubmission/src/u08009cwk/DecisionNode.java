/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u08009cwk;

/**
 *
 * @author Joseph
 */
public interface DecisionNode {

    SNode getNext(boolean choice);

    /**
     * Set one of the nodes that follows this one in the flow chart. That is to
     * say the node to which we advance when this node when an answer to the
     * decision is recorded
     *
     * @param node node to which we advance
     * @param choice true if we are setting the node that we advance to when
     * "Yes" is the answer to the decision, and false if it is the node we
     * advance to when the answer is "No".
     */
    void setNext(SNode node, boolean choice);
    
}
