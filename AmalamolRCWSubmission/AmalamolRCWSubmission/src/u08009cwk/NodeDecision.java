/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u08009cwk;

/**
 * Class whose objects represent decision nodes in flow
 * charts
 * @author p0073862
 */
public class NodeDecision extends SNode implements DecisionNode {
    
    private SNode node;
    private SNode nextNode;
    
    public NodeDecision(String nodeTitle, double displayX, double displayY) {
        super(nodeTitle, displayX, displayY);
    }

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
    @Override
    public void setNext(SNode node, boolean choice) {
        
        if(choice) {
            this.nextNode = node;
        }
        
        else {
            this.node = node;
        }
        
    }

    @Override
    public SNode getNext(boolean choice) {
        if(choice) {
            return nextNode;
        }
        
        else {
            return node;
        }  
    }
}
