package luca.ingimplementazione.flyweight;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ConcreteLogicGate implements LogicGate {

    //tipo dell'oggetto flyweight e immagine
    private String type;
    private Image image;

    private final static float WIDTH = 80;
    private final static float HEIGHT = 60;


    public ConcreteLogicGate(String type, String imagePath) {
        this.type = type;
        try{
            this.image = new Image(getClass().getResourceAsStream(imagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y, double zoomLevel) {
        double width = zoomLevel * WIDTH;
        double height = zoomLevel * HEIGHT;

        gc.drawImage(image, x, y, width, height);
    }
}
