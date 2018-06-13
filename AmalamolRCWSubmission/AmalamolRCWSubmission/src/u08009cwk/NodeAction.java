/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u08009cwk;


/**
 * Class whose objects represent action nodes in flow charts
 * @author p0073862
 */
public class NodeAction extends SNode implements ActionNode  {
    
    private SNode nextNode;
    
    public NodeAction(String nodeTitle, double displayX, double displayY) {
        super(nodeTitle, displayX, displayY);
    }
    /**
     * Set the node that follows this one in the flow chart. That is to say
     * the node to which we advance when this node is completed
     * @param nexNode
     */
    @Override
    public void setNext(SNode nodeNext) {
        this.nextNode = nodeNext;
    }

    @Override
    public SNode getNext(boolean choice) {
        return nextNode;
    }
}
