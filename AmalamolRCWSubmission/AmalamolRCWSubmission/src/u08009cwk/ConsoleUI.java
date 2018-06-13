/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u08009cwk;

import java.util.Scanner;

/**
 *
 * @author p0073862
 */
public class ConsoleUI {

    private static FlowChart flowChart = new FlowChart();
    private static boolean running = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String option = "M";
        while (!option.startsWith("Q")) {

            if (option.startsWith("M")) {
                showMenu();
            } //Options available in set up mode
            if (!running) {
                if (option.startsWith("R")) {
                    flowChart.start();
                    running = true;
                } else if (option.startsWith("L")) {
                    listNodes();
                } else if (option.startsWith("A")) {
                    addNode(true);
                } else if (option.startsWith("D")) {
                    addNode(false);
                } else if (option.startsWith("C") && flowChart.getNbrNodes() >= 2) {
                    addConnection();
                } else if (option.startsWith("S")) {
                    SNode node = getNode("Enter index of start node");
                    flowChart.setStartNode(node);
                }
            } else { //Otions available in run mode
                SNode currentNode = flowChart.getCurrentNode();
                if (option.startsWith("C")
                        && currentNode instanceof NodeAction) {
                    flowChart.advance(true);
                } else if (option.startsWith("Y")
                        && currentNode instanceof NodeDecision) {
                    flowChart.advance(true);
                } else if (option.startsWith("N")
                        && currentNode instanceof NodeDecision) {
                    flowChart.advance(false);
                } else if (option.startsWith("S")) {
                    running = false;
                }
            }

            if (running) {
                SNode currentNode = flowChart.getCurrentNode();
                if (currentNode != null) {
                    System.out.println("Current Node is " + currentNode.getTitle());
                }
            }
            System.out.print("Enter first character of your choice (type M to see menu)> ");
            option = scanner.nextLine();
        }
    }

    private static void showMenu() {
        if (!running) {
            showSetupMenu();
        } else {
            showRunMenu();
        }
        System.out.println("Q - Quit Program");
    }

    private static void showSetupMenu() {
        System.out.println("Options are");
        System.out.println("L - List Nodes");
        System.out.println("A - Add Action");
        System.out.println("D - Add Decision");
        if (flowChart.getNbrNodes() >= 2) {
            System.out.println("C - Connect two nodes");
            System.out.println("S - Set Start Node");
        }
        System.out.println("R - Run");
    }

    private static void showRunMenu() {
        SNode currentNode = flowChart.getCurrentNode();
        System.out.println("Options are");
        System.out.println("S - Stop");
        if (currentNode instanceof NodeAction) {
            System.out.println("C- Confirm Action");
        } else if (currentNode instanceof NodeDecision) {
            System.out.println("Y - Answer Yes to Decision");
            System.out.println("N - Answer No to Decision");
        }
    }

    private static void addNode(boolean isAction) {
        System.out.print("Title of " + (isAction ? "Action>" : "Decision>"));
        String title = scanner.nextLine();
        System.out.print("X display coordinate (must be in range 0-1)>");
        double displayX = scanner.nextDouble();
        System.out.print("Y display coordinate (must be in range 0-1)>");
        double displayY = scanner.nextDouble();
        //consume line break
        scanner.nextLine();
        SNode addedNode;
        if (isAction) {
            addedNode = flowChart.addAction(title, displayX, displayY);
        } else {
            addedNode = flowChart.addDecision(title, displayX, displayY);
        }
        if (flowChart.getNbrNodes() == 1) {
            flowChart.setStartNode(addedNode);
        }
    }

    private static void listNodes() {
        int nbrNodes = flowChart.getNbrNodes();
        for (int i = 0; i < nbrNodes; i++) {
            SNode node = flowChart.getNode(i);
            System.out.println(Integer.toString(i) + ":" + node.getTitle());
        }
    }

    private static void addConnection() {
        SNode startNode = getNode("Enter index of start node ");
        SNode targetNode = getNode("Enter index of target node ");

        //NOTE TO STUDENTS - IT IS GENERALLY BEST TO AVOID USING THE
        //instanceof OPERATOR, UNLESS YOU REALLY HAVE TO!
        if (startNode instanceof NodeDecision) {
            System.out.print("Type Y to add a connection when decision is Yes, or N for No>");
            String contype = scanner.nextLine();
            ((NodeDecision) startNode).setNext(targetNode, contype.startsWith("Y"));
        } else if (startNode instanceof NodeAction) {
            ((NodeAction) startNode).setNext(targetNode);
        }

    }
    
    private static SNode getNode(String prompt) {
        int nbrNodes = flowChart.getNbrNodes();
        int nodeIdx;
        do {
            System.out.print(prompt + " (range is 0-" + (nbrNodes - 1) + ")");
            nodeIdx = scanner.nextInt();
        } while (nodeIdx < 0 || nodeIdx >= nbrNodes);
        //consume next line character
        scanner.nextLine();
        return flowChart.getNode(nodeIdx);
    }
}
