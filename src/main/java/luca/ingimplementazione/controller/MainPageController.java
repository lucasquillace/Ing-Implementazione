package luca.ingimplementazione.controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import luca.ingimplementazione.flyweight.LogicGate;
import luca.ingimplementazione.flyweight.LogicGateFactory;

import java.net.URL;

public class MainPageController {


    @FXML
    private ImageView menuButton;

    @FXML
    private VBox lateralPanel;

    @FXML
    private AnchorPane overlay;

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

    //per gestire quando l'utente vuole spostare un qualcosa
    Line selectedLine = null;
    ImageView selectedGateImageView = null;

    //per capire quando bisogna aggiungere spazio
    ImageView newImage = null;

    //per capire quando aprire e chiudere il menù laterale
    private boolean isMenuOpen = false;

    private static final int HEIGHT = 100;
    private static final int WIDTH = 160;

    //stile css
    private static final String NORMAL_GATE=" -fx-background-color: transparent; -fx-background-radius: 5px;";
    private static final String HOVER_GATE = "-fx-background-color: rgba(33, 150, 243, 0.2);" +
            "    -fx-background-radius: 5px;" +
            "    -fx-border-color: #2196f3;" +
            "    -fx-border-radius: 5px;" +
            "    -fx-border-width: 1px;";

    private static final String TITLE_LABEL = """
            -fx-font-family: "Segoe UI", sans-serif;
                -fx-font-size: 24px;
                -fx-font-weight: bold;
                -fx-text-fill: #ffffff;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 0, 1);
           """;
    private static final String SUBTITLE_LABEL = """
            -fx-font-family: "Segoe UI", sans-serif;
                -fx-font-size: 16px;
                -fx-font-weight: normal;
                -fx-text-fill: #aaaaaa;
                -fx-padding: 0 0 10 0;
            """;
    private static final String BODY_LABEL = """
            -fx-font-family: "Segoe UI", sans-serif;
                -fx-font-size: 14px;
                -fx-text-fill: #e0e0e0; 
                -fx-line-spacing: 4px; 
            """;

    @FXML
    public void initialize() {

        overlay.setVisible(false);
        overlay.setOnMouseClicked(event -> toggleMenu());
        //altrimenti si rompe per la scrollbar
        mainScrollPane.setFocusTraversable(true);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        //per bindare le immagini al proprio pane
        this.bindImages();

        //per il drag and drop
        this.dragAndDropSource(notPane, notGateImageView, "NOT");
        this.dragAndDropSource(andPane, andGateImageView, "AND");
        this.dragAndDropSource(nandPane, nandGateImageView, "NAND");
        this.dragAndDropSource(norPane, norGateImageView, "NOR");
        this.dragAndDropSource(orPane, orGateImageView, "OR");
        this.dragAndDropSource(xorPane, xorGateImageView, "XOR");

        this.dragAndDropDestination();

        //setup per le linee
        mainPane.setOnMousePressed(event -> {
            System.out.println("Evento catturato" + event.getButton() + " " + event.getTarget());

            mainScrollPane.setPannable(true);
            //se si sta cliccando una linea
            if (event.getTarget() instanceof Line && selectedLine == null) {
                selectedLine = (Line) event.getTarget();
                selectedLine.setStroke(Color.RED);
                if (selectedGateImageView != null) {
                    selectedGateImageView.setEffect(null);
                    selectedGateImageView = null;
                }
                return;
            }

            //se si sta cliccando un'immagine. Non funziona a volte
            if (event.getTarget() instanceof ImageView && selectedGateImageView == null) {
                selectedGateImageView = (ImageView) event.getTarget();

                //per colorare la porta
                ColorInput red = new ColorInput(
                        0, 0,
                        selectedGateImageView.getImage().getWidth(),
                        selectedGateImageView.getImage().getHeight(),
                        Color.RED
                );

                Blend blend = new Blend(BlendMode.SRC_ATOP);
                blend.setTopInput(red);
                selectedGateImageView.setEffect(blend);

                if (selectedLine != null) {
                    selectedLine.setStroke(Color.BLACK);
                    selectedLine = null;
                }
                return;
            }


            //se premo con il tasto destro cancello la linea
            if (event.getTarget() instanceof Line && selectedLine != null && event.getButton() == MouseButton.SECONDARY) {

                //se clicco 2 volte sulla stessa linea la cancello
                if (event.getTarget().equals(selectedLine)) {
                    mainScrollPane.setPannable(false);
                    mainPane.getChildren().remove(selectedLine);
                    selectedLine.setStroke(Color.BLACK);
                    selectedLine = null;
                }

            }

            //se premo con il tasto destro, cancello un'immagine
            if (event.getTarget() instanceof ImageView && selectedGateImageView != null && event.getButton() == MouseButton.SECONDARY) {
                if (event.getTarget().equals(selectedGateImageView)) {
                    mainScrollPane.setPannable(false);
                    mainPane.getChildren().remove(selectedGateImageView);
                    selectedGateImageView.setEffect(null);
                    selectedGateImageView = null;

                }
            }

            //se clicco con il mouse sinistro, creo una nuova linea
            if (event.getButton() == MouseButton.PRIMARY && event.getTarget() instanceof Pane) {
                Line line = new Line(event.getX(), event.getY(), event.getX() + 50, event.getY());
                line.setStrokeWidth(3);

                line.setOnMouseDragged(e -> {
                    if (selectedLine == e.getTarget()) {
                        mainScrollPane.setPannable(false);
                        line.setEndX(e.getX());
                        line.setEndY(e.getY());
                    }
                });

                mainPane.getChildren().add(line);
            }
        });

        mainPane.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY && selectedLine != null) {
                mainScrollPane.setPannable(false);
                selectedLine.setEndX(event.getX());
                selectedLine.setEndY(event.getY());
            }
            if (event.getButton() == MouseButton.PRIMARY && selectedGateImageView != null) {
                mainScrollPane.setPannable(false);
                selectedGateImageView.setX(event.getX() - selectedGateImageView.getFitWidth() / 2);
                selectedGateImageView.setY(event.getY() - selectedGateImageView.getFitHeight() / 2);
            }

        });

        mainPane.setOnMouseReleased(event -> {
            this.expandPaneIfNeeded(event.getX(), event.getY());
            if (event.getButton() == MouseButton.PRIMARY && selectedLine != null) {
                selectedLine.setStroke(Color.BLACK);
                selectedLine = null;
            }

            if (event.getButton() == MouseButton.PRIMARY && selectedGateImageView != null) {
                selectedGateImageView.setEffect(null);
                selectedGateImageView = null;
            }
        });

        //per applicare lo stile
        this.applyStyle();
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

    private void dragAndDropDestination() {
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
                this.expandPaneIfNeeded(event.getX(), event.getY());
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }

    //crea il gate di tipo type in posizione x y
    private void createNewGate(String type, double x, double y) {
        LogicGate logicGate = LogicGateFactory.getGate(type);
        logicGate.draw(mainPane, x, y);

    }

    //ingrandisce il pannello opportunamente
    private void expandPaneIfNeeded(double eventX, double eventY) {
        if ((eventX + 200 >= mainPane.getWidth() || eventY + 200 >= mainPane.getHeight())) {
            double width = mainPane.getWidth();
            double height = mainPane.getHeight();

            mainPane.setMinWidth(width + 500);
            mainPane.setMinHeight(height + 500);
            mainPane.setPrefWidth(width + 500);
            mainPane.setPrefHeight(height + 500);
        }
    }


    //aprire e chiudere il menù
    public void toggleMenu() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(lateralPanel);

        if (isMenuOpen) {
            transition.setToX(-250);
            transition.setOnFinished(e -> overlay.setVisible(false));
            isMenuOpen = false;
        } else {
            overlay.setVisible(true);
            transition.setToX(0);
            isMenuOpen = true;
        }

        transition.play();
    }

    private void applyStyle(){
        lateralPanel.getStylesheets().add(getClass().getResource("/luca/ingimplementazione/styles.css").toExternalForm());

        applyHoverStyle(notPane);
        applyHoverStyle(andPane);
        applyHoverStyle(orPane);
        applyHoverStyle(nandPane);
        applyHoverStyle(norPane);
        applyHoverStyle(xorPane);

    }

    private void applyHoverStyle(Pane pane){
        pane.setCursor(Cursor.OPEN_HAND);
        pane.setOnMouseEntered(e ->{
            pane.setStyle(HOVER_GATE);
        });

        pane.setOnMouseExited(e -> {
            pane.setStyle(NORMAL_GATE);
        });
    }
}