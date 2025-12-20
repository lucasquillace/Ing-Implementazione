package luca.ingimplementazione.flyweight;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

//interfaccia flyweight
public interface LogicGate {
    public void draw(Pane pane, double x, double y);
}
