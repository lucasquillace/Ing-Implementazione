package luca.ingimplementazione.flyweight;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ConcreteLogicGate implements LogicGate {

    //tipo dell'oggetto flyweight e immagine
    private String type;
    private Image image;

    private final static float WIDTH = 160;
    private final static float HEIGHT = 100;


    public ConcreteLogicGate(String type, String imagePath) {
        this.type = type;
        try{
            this.image = new Image(getClass().getResourceAsStream(imagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Pane pane, double x, double y) {

        //non carico tutta l'immagine, solo il suo contenitore
        ImageView imageView = new ImageView(this.image);

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        imageView.setX(x - WIDTH / 2);
        imageView.setY(y - HEIGHT / 2);
        imageView.setPreserveRatio(true);

        pane.getChildren().add(imageView);
    }

    public Image getImage() {
        return image;
    }
}
