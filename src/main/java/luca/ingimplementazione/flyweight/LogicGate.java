package luca.ingimplementazione.flyweight;

import javafx.scene.canvas.GraphicsContext;

//interfaccia flyweight
public interface LogicGate {
    public void draw(GraphicsContext gc, double x, double y, double zoomLevel);
}
