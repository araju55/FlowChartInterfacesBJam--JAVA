/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u08009cwk;

/**
 * Class whose objects represent nodes in a flow chart
 * @author p0073862
 */
public abstract class SNode implements Node {
    
    private String nodeTitle;
    private double displayX;
    private double displayY;
    
    public SNode(String nodeTitle, double displayX, double displayY) {
        this.nodeTitle = nodeTitle;
        this.displayX = displayX;
        this.displayY = displayY;
    }

    /**
     * Get the title of the node
     *
     * @return title of the node
     */
    
    public String getTitle() {
        return nodeTitle;
    }
    
    
    public void setTitle(String titleNode) {
        nodeTitle = titleNode;
    }
    /**
     * Return a value indicating the horizontal position of the node when
     * displayed in a GUI. Should be in range 0 (extreme left of display area)
     * to 1 (extreme right).
     *
     * @return horizontal position
     */
    
    public double getDisplayX() {
        return displayX;
    }
    
    
    public void setDisplayX(double displayx) {
        displayX = displayx;
    }

    /**
     * Return a value indicating the vertical position of the node when
     * displayed in a GUI. Should be in range 0 (top of display area) to 1
     * (bottom).
     *
     * @return vertical position
     */
    
    public double getDisplayY() {
        return displayY;
    }
    
    
    public void setDisplayY(double displayy) {
        displayY = displayy;
    }
    }