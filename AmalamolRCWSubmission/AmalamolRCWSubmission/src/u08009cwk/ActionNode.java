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
public interface ActionNode {

    SNode getNext(boolean choice);

    /**
     * Set the node that follows this one in the flow chart. That is to say
     * the node to which we advance when this node is completed
     * @param nexNode
     */
    void setNext(SNode nodeNext);
    
}
