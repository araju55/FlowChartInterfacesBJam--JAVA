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
public class Phase1Test {
    
    public static void main (String[] args) {
        test1();
    }
    
    private static void test1() {
        System.out.println("TEST 1: Creation of an ActionNode");
        NodeAction actionNode = new NodeAction("Test Action", 0.5, 0.6);
        System.out.println("Title of action node");
        System.out.println("Expected: Test Action");
        System.out.println("Actual:" + actionNode.getTitle());
        System.out.println("Display co-ordinates of action node");
        System.out.println("Expected: 0.5, 0.6");
        System.out.println("Actual:" + actionNode.getDisplayX() + "," + actionNode.getDisplayY());
        
    }
    
}
