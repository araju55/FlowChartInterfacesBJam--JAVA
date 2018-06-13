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
public interface Node {

    /**
     * Return a value indicating the horizontal position of the node when
     * displayed in a GUI. Should be in range 0 (extreme left of display area)
     * to 1 (extreme right).
     *
     * @return horizontal position
     */
    double getDisplayX();

    /**
     * Return a value indicating the vertical position of the node when
     * displayed in a GUI. Should be in range 0 (top of display area) to 1
     * (bottom).
     *
     * @return vertical position
     */
    double getDisplayY();

    /**
     * Return a node that follows this one in the flow chart. If this node is an
     * ActionNode the method returns its 'next node', and the choice parameter is
     * ignored. If this node is a DecisionNode, then the method returns either
     * the next node on the "Yes" branch of the decision (if choice is true) or
     * on the "No" branch (if choice is false).
     *
     * @param choice If this node is a DecisionNode then the choice parameter
     * specifies whether the "Yes" branch or the "No" branch is to be returned.
     * It is ignored if the current node is an ActionNode.
     * @return the next node along the specified branch.
     */
    SNode getNext(boolean choice);

    /**
     * Get the title of the node
     *
     * @return title of the node
     */
    String getTitle();

    void setDisplayX(double displayx);

    void setDisplayY(double displayy);

    void setTitle(String titleNode);
    
}
