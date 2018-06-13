/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u08009cwk;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author p0073862
 */
public class GUI extends Application {

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 800;
    private static final double NODE_WIDTH = 80;
    private static final double NODE_HEIGHT = 50;
    private static final double ARROW_LENGTH = 20;
    private static final double ARROW_ANGLE = Math.PI / 6;

    private enum Mode {
        NORMAL, SELECT_NEXT_NODE, RUN
    };
    Mode mode = Mode.NORMAL;
    boolean targetBranch;
    private final Canvas displayArea = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private final ToggleGroup nodeButtonGroup = new ToggleGroup();
    private final Button runButton = new Button("Run");
    private final Button stopButton = new Button("Stop");
    private final Button confirmButton = new Button("Confirm");
    private final Button yesButton = new Button("Yes");
    private final Button noButton = new Button("No");
    private final TextField nameField = new TextField();

    boolean startSet = false;
    SNode selectedNode = null;
    private final FlowChart flowChart = new FlowChart();

    @Override
    public void start(Stage primaryStage) {

        displayArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() == MouseButton.PRIMARY) {
                    SNode targetNode = findNode(me.getSceneX(), me.getSceneY());
                    if (targetNode != null) {
                        if (mode == Mode.NORMAL) {
                            performNodeClickDialog(me, targetNode);
                        } else if (mode == Mode.SELECT_NEXT_NODE) {
                            makeConnection(selectedNode, targetNode, targetBranch);
                            mode = Mode.NORMAL;
                            selectedNode = null;
                        }
                    } else if (mode == Mode.NORMAL) {
                        performAddNodeDialog(me);
                    }
                    draw();
                }
            }
        }
        );

        VBox root = new VBox();

        root.getChildren()
                .add(displayArea);

        primaryStage.setTitle(
                "Flow Chart");
        HBox hbox = new HBox();

        runButton.setDisable(true);
        stopButton.setDisable(true);
        confirmButton.setDisable(true);
        yesButton.setDisable(true);
        noButton.setDisable(true);
        nameField.setEditable(false);
        //Set width of name field. There must be a better way of doing this!
        double nameWidth = CANVAS_WIDTH / 2;
        nameField.setPrefWidth(nameWidth);

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flowChart.advance(false);
                selectedNode = flowChart.getCurrentNode();
                advanceUI();
            }
        });

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flowChart.advance(true);
                selectedNode = flowChart.getCurrentNode();
                advanceUI();
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flowChart.advance(false);
                selectedNode = flowChart.getCurrentNode();
                advanceUI();
            }
        });

        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setModeRun();
            }

        });

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopRun();
            }

        });
        hbox.getChildren().addAll(runButton, stopButton, nameField,
                confirmButton, yesButton, noButton);

        root.getChildren()
                .add(hbox);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.setResizable(
                false);
        primaryStage.show();

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void setModeRun() {
        mode = Mode.RUN;
        runButton.setDisable(true);
        stopButton.setDisable(false);
        flowChart.start();
        selectedNode = flowChart.getCurrentNode();
        advanceUI();
    }

    private void stopRun() {
        mode = Mode.NORMAL;
        runButton.setDisable(false);
        stopButton.setDisable(true);
        selectedNode = null;
        advanceUI();
    }

    private void makeConnection(SNode startNode, SNode targetNode, boolean choice) {
        if (startNode instanceof NodeAction) {
            ((NodeAction) startNode).setNext(targetNode);
        } else if (startNode instanceof NodeDecision) {
            if (choice) {
                ((NodeDecision) startNode).setNext(targetNode, true);
            } else {
                ((NodeDecision) startNode).setNext(targetNode, false);
            }
        }
    }

    private double getSceneX(SNode node) {
        double result = 0;
        if (node != null) {
            result = node.getDisplayX() * displayArea.getWidth();
        }
        return result;
    }

    private double getSceneY(SNode node) {
        double result = 0;
        if (node != null) {
            result = node.getDisplayY() * displayArea.getHeight();
        }
        return result;
    }

    private double getScreenX(SNode node) {
        double result = 0;
        if (node != null) {
            result = displayArea.getLayoutX() + node.getDisplayX() * displayArea.getWidth();
        }
        return result;
    }

    private double getScreenY(SNode node) {
        double result = 0;
        if (node != null) {
            result = displayArea.getLayoutY() + node.getDisplayY() * displayArea.getHeight();
        }
        return result;
    }

    private void performAddNodeDialog(MouseEvent me) {
        Stage popupStage = new Stage();
        popupStage.setX(me.getScreenX());
        popupStage.setY(me.getScreenY());
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add Node");
        VBox root = new VBox();
        HBox hbox = new HBox();
        Button addActionButton = new Button("Add Action");
        Button addDecisionButton = new Button("Add Decision");
        Button cancelButton = new Button("Cancel");
        hbox.getChildren().add(addActionButton);
        hbox.getChildren().add(addDecisionButton);
        hbox.getChildren().add(cancelButton);
        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter Name");
        double displayX = me.getSceneX() / displayArea.getWidth();
        double displayY = me.getSceneY() / displayArea.getHeight();
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });
        addActionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NodeAction node = flowChart.addAction(textArea.getText(),
                        displayX, displayY);
                if (flowChart.getNbrNodes() == 1) {
                    flowChart.setStartNode(node);
                    runButton.setDisable(false);
                }
                popupStage.close();
            }

        });

        addDecisionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NodeDecision node = flowChart.addDecision(textArea.getText(),
                        displayX, displayY);
                if (flowChart.getNbrNodes() == 1) {
                    flowChart.setStartNode(node);
                    runButton.setDisable(false);
                }
                popupStage.close();
            }
        });

        root.getChildren().add(hbox);

        root.getChildren().add(textArea);
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void performNodeClickDialog(MouseEvent me, SNode node) {
        Stage popupStage = new Stage();
        popupStage.setX(me.getScreenX());
        popupStage.setY(me.getScreenY());
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Node Options");
        VBox root = new VBox();
        if (node instanceof NodeAction) {
            Button addConnectionButton = new Button("Add Connection");
            addConnectionButton.setTextAlignment(TextAlignment.LEFT);
            addConnectionButton.setMaxWidth(Double.MAX_VALUE);
            addConnectionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mode = Mode.SELECT_NEXT_NODE;
                    selectedNode = node;
                    popupStage.close();
                }
            });
            root.getChildren().add(addConnectionButton);
        } else if (node instanceof NodeDecision) {
            Button addYesConnectionButton = new Button("Add Connection on Yes");
            addYesConnectionButton.setMaxWidth(Double.MAX_VALUE);
            addYesConnectionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mode = Mode.SELECT_NEXT_NODE;
                    selectedNode = node;
                    targetBranch = true;
                    popupStage.close();
                }
            });
            root.getChildren().add(addYesConnectionButton);
            Button addNoConnectionButton = new Button("Add Connection on No");
            addNoConnectionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mode = Mode.SELECT_NEXT_NODE;
                    selectedNode = node;
                    targetBranch = false;
                    popupStage.close();
                }
            });
            addYesConnectionButton.setMaxWidth(Double.MAX_VALUE);
            root.getChildren().add(addNoConnectionButton);
        }
        Button setStartButton = new Button("Set as Start Node");
        setStartButton.setMaxWidth(Double.MAX_VALUE);
        setStartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flowChart.setStartNode(node);
                popupStage.close();
            }
        });
        root.getChildren().add(setStartButton);
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        root.getChildren().add(cancelButton);
        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void drawLineBetween(GraphicsContext gc, SNode startNode, SNode endNode, String text) {
        double displayAreaWidth = displayArea.getWidth();
        double displayAreaHeight = displayArea.getHeight();
        double xStart = startNode.getDisplayX() * displayAreaWidth;
        double yStart = startNode.getDisplayY() * displayAreaHeight;
        double xEnd = endNode.getDisplayX() * displayAreaWidth;
        double yEnd = endNode.getDisplayY() * displayAreaHeight;
        gc.strokeLine(xStart, yStart, xEnd, yEnd);

        double xTwoThirds = (xStart + 2 * xEnd) / 3;
        double yTwoThirds = (yStart + 2 * yEnd) / 3;

        double theta = Math.atan2(yStart - yEnd, xStart - xEnd);
        double arrowX = xTwoThirds + ARROW_LENGTH * Math.cos(theta + ARROW_ANGLE);
        double arrowY = yTwoThirds + ARROW_LENGTH * Math.sin(theta + ARROW_ANGLE);
        gc.strokeLine(xTwoThirds, yTwoThirds, arrowX, arrowY);
        arrowX = xTwoThirds + ARROW_LENGTH * Math.cos(theta - ARROW_ANGLE);
        arrowY = yTwoThirds + ARROW_LENGTH * Math.sin(theta - ARROW_ANGLE);
        gc.strokeLine(xTwoThirds, yTwoThirds, arrowX, arrowY);

        if (text != null) {
            double xOneThird = (2 * xStart + xEnd) / 3;
            double yOneThird = (2 * yStart + yEnd) / 3;
            gc.strokeText(text, xOneThird, yOneThird);
        }

    }

    private void draw() {
        GraphicsContext gc = displayArea.getGraphicsContext2D();;
        double displayAreaWidth = displayArea.getWidth();
        double displayAreaHeight = displayArea.getHeight();
        gc.clearRect(0, 0, displayAreaWidth,
                displayAreaHeight);
        int size = flowChart == null ? 0 : flowChart.getNbrNodes();
        for (int i = 0; i < size; i++) {
            SNode node = flowChart.getNode(i);
            SNode nextNode;
            if (node instanceof NodeAction) {
                nextNode = ((NodeAction) node).getNext(true);
                if (nextNode != null) {
                    drawLineBetween(gc, node, nextNode, null);
                }
            } else if (node instanceof NodeDecision) {
                nextNode = ((NodeDecision) node).getNext(true);
                if (nextNode != null) {
                    drawLineBetween(gc, node, nextNode, "Yes");
                }
                nextNode = ((NodeDecision) node).getNext(false);
                if (nextNode != null) {
                    drawLineBetween(gc, node, nextNode, "No");
                }
            }
        }
        for (int i = 0; i < size; i++) {
            SNode node = flowChart.getNode(i);
            double centreX = displayAreaWidth * node.getDisplayX();
            double centreY = displayAreaHeight * node.getDisplayY();
            gc.setFill(node == selectedNode ? Color.RED : Color.WHITE);
            if (node instanceof NodeAction) {
                gc.fillRect(centreX - NODE_WIDTH / 2, centreY - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
                gc.strokeRect(centreX - NODE_WIDTH / 2, centreY - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
            } else if (node instanceof NodeDecision) {
                double[] xPoints = {centreX - NODE_WIDTH / 2, centreX, centreX + NODE_WIDTH / 2, centreX};
                double[] yPoints = {centreY, centreY - NODE_HEIGHT / 2, centreY, centreY + NODE_HEIGHT / 2};

                gc.fillPolygon(xPoints, yPoints, xPoints.length);
                gc.strokePolygon(xPoints, yPoints, xPoints.length);
            }
            gc.strokeText(node.getTitle(), centreX - NODE_WIDTH / 2, centreY);
        }

    }

    private SNode findNode(double sceneX, double sceneY) {
        SNode resultNode = null;
        int nbrNodes = flowChart.getNbrNodes();
        for (int i = 0; resultNode == null && i < nbrNodes; i++) {
            SNode node = flowChart.getNode(i);
            double sceneXCen = node.getDisplayX() * displayArea.getWidth();
            double sceneYCen = node.getDisplayY() * displayArea.getHeight();
            if (Math.abs(sceneX - sceneXCen) <= NODE_WIDTH / 2
                    && Math.abs(sceneY - sceneYCen) <= NODE_HEIGHT / 2) {
                resultNode = node;

            }

        }
        return resultNode;
    }

    private void advanceUI() {
        if (selectedNode != null) {
            nameField.setText(selectedNode.getTitle());
            if (selectedNode instanceof NodeAction) {
                confirmButton.setDisable(false);
                yesButton.setDisable(true);
                noButton.setDisable(true);
            } else if (selectedNode instanceof NodeDecision) {
                confirmButton.setDisable(true);
                yesButton.setDisable(false);
                noButton.setDisable(false);
            }
        } else {
            nameField.setText("");
            confirmButton.setDisable(true);
            yesButton.setDisable(true);
            noButton.setDisable(true);
        }
        draw();
    }

}
