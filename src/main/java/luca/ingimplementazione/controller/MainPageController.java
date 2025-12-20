package luca.ingimplementazione.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import luca.ingimplementazione.flyweight.LogicGate;
import luca.ingimplementazione.flyweight.LogicGateFactory;

import java.util.ArrayList;

public class MainPageController {

    @FXML
    private ImageView andGateImageView;

    @FXML
    private AnchorPane andPane;

    @FXML
    private Pane mainPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private ImageView nandGateImageView;

    @FXML
    private AnchorPane nandPane;

    @FXML
    private ImageView norGateImageView;

    @FXML
    private AnchorPane norPane;

    @FXML
    private ImageView notGateImageView;

    @FXML
    private AnchorPane notPane;

    @FXML
    private ImageView orGateImageView;

    @FXML
    private AnchorPane orPane;

    @FXML
    private ImageView xorGateImageView;

    @FXML
    private AnchorPane xorPane;

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

    Line selectedLine = new Line();

    private static final int HEIGHT = 100;
    private static final int WIDTH = 160;

    @FXML
    public void initialize() {

        //altrimenti si rompe per la scrollbar
        mainScrollPane.setFitToHeight(true);
        mainScrollPane.setFitToWidth(true);

        //per bindare le immagini al proprio pane
        this.bindImages();
        this.dragAndDropSource(notPane, notGateImageView, "NOT");
        this.dragAndDropDestination();

        //setup per le linee
        mainPane.setOnMousePressed(event -> {
            System.out.println("Evento catturato" + event.getButton() + " " + event.getTarget());

            //se si sta cliccando una linea
            if (event.getTarget() instanceof Line && selectedLine == null) {
                selectedLine = (Line) event.getTarget();
                selectedLine.setStroke(Color.RED);
                return;
            }

            //se premo con il tasto destro cancello la linea
            if (event.getTarget() instanceof Line && selectedLine != null && event.getButton() == MouseButton.SECONDARY) {

                //se clicco 2 volte sulla stessa linea la cancello
                if (event.getTarget().equals(selectedLine)) {
                    mainPane.getChildren().remove(selectedLine);
                    selectedLine.setStroke(Color.BLACK);
                    selectedLine = null;
                }

            }

            //se clicco con il mouse sinistro, creo una nuova linea
            if (event.getButton() == MouseButton.PRIMARY) {
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
                selectedLine.setStroke(Color.BLACK);
                selectedLine = null;
            }
        });
    }

    //aggiunge il gate alla tela
    private void addGate(String type){
        //LogicGate logicGate = LogicGateFactory.getGate(type);

    }

    private void bindImages() {
        Platform.runLater(() -> {

            andGateImageView.fitWidthProperty().bind(andPane.widthProperty());
            andGateImageView.fitHeightProperty().bind(andPane.heightProperty());

            nandGateImageView.fitWidthProperty().bind(nandPane.widthProperty());
            nandGateImageView.fitHeightProperty().bind(nandPane.heightProperty());

            norGateImageView.fitWidthProperty().bind(norPane.widthProperty());
            norGateImageView.fitHeightProperty().bind(norPane.heightProperty());

            notGateImageView.fitWidthProperty().bind(notPane.widthProperty());
            notGateImageView.fitHeightProperty().bind(notPane.heightProperty());

            orGateImageView.fitWidthProperty().bind(orPane.widthProperty());
            orGateImageView.fitHeightProperty().bind(orPane.heightProperty());

            xorGateImageView.fitWidthProperty().bind(xorPane.widthProperty());
            xorGateImageView.fitHeightProperty().bind(xorPane.heightProperty());
        });

    }

    //per permettere il drag and drop
    private void dragAndDropSource(Pane pane, ImageView imageView, String type) {

        pane.setOnDragDetected(event -> {

            //avvia il drag and drop passando la stringa con il tipo
            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.COPY);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(type);
            dragboard.setContent(clipboardContent);

            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);

            WritableImage writableImage = pane.snapshot(snapshotParameters, null);

            dragboard.setDragView(writableImage);

            dragboard.setDragViewOffsetX(pane.getWidth() / 2);
            dragboard.setDragViewOffsetY(pane.getHeight() / 2);
        });
    }

    private void dragAndDropDestination(){
        mainPane.setOnDragOver(event -> {
            if (event.getGestureSource() != mainPane && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        mainPane.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String gateType = dragboard.getString();
                this.createNewGate(gateType, event.getX(), event.getY());
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }

    //crea il gate di tipo type in posizione x y
    private void createNewGate(String type, double x, double y) {
        LogicGate logicGate = LogicGateFactory.getGate(type);
        logicGate.draw(x,y);

    }
}
