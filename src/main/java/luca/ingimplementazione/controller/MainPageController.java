package luca.ingimplementazione.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import luca.ingimplementazione.flyweight.LogicGate;
import luca.ingimplementazione.flyweight.LogicGateFactory;

import java.util.ArrayList;

public class MainPageController {

    @FXML
    private Pane mainPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    void onAddAnd(MouseEvent event) {
        addGate("AND");
    }

    @FXML
    void onAddNand(MouseEvent event) {
        addGate("NAND");
    }

    @FXML
    void onAddNor(MouseEvent event) {
        addGate("NOR");
    }

    @FXML
    void onAddNot(MouseEvent event) {
        addGate("NOT");
    }

    @FXML
    void onAddOr(MouseEvent event) {
        addGate("OR");
    }

    @FXML
    void onAddXor(MouseEvent event) {
        addGate("XOR");
    }

    //per il pan
    private double lastX, lastY;

    //per memorizzare ogni linea
    ArrayList<Line> lines = new ArrayList<>();

    Line selectedLine = new Line();

    @FXML
    public void initialize() {

        //altrimenti si rompe per la scrollbar
        mainScrollPane.setFitToHeight(true);
        mainScrollPane.setFitToWidth(true);

        mainPane.setOnMousePressed(event -> {
            System.out.println("Evento catturato" + event.getButton() + " " + event.getTarget());

            //se si sta cliccando una linea
            if (event.getTarget() instanceof Line && selectedLine == null) {
                selectedLine = (Line) event.getTarget();
                return;
            }

            //se premo con il tasto destro cancello la linea
            if (event.getTarget() instanceof Line && selectedLine != null && event.getButton() == MouseButton.SECONDARY) {
                mainPane.getChildren().remove(selectedLine);
                selectedLine = null;
            }

            //se clicco con il mouse sinistro, creo una nuova linea
            if (event.getButton() == MouseButton.PRIMARY) {
                selectedLine = null;
                Line line = new Line(event.getX() , event.getY() , event.getX()+50 , event.getY()-20);
                line.setStrokeWidth(3);

                line.setOnMouseDragged(e-> {
                    if (selectedLine == e.getTarget()) {
                        line.setEndX(e.getX());
                        line.setEndY(e.getY());
                    }
                });

                mainPane.getChildren().add(line);
            }
        });

        mainPane.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY && selectedLine != null) {
                selectedLine.setEndX(event.getX());
                selectedLine.setEndY(event.getY());
            }

        });

        mainPane.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY && selectedLine != null) {
                selectedLine = null;
            }
        });
    }

    private void addGate(String type){
        LogicGate logicGate = LogicGateFactory.getGate(type);

    }

}
