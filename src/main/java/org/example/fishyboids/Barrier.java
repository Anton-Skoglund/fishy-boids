package org.example.fishyboids;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Barrier {
    private double x;
    private double y;
    private double width;
    private double height;

    //TODO
    // - limiting
    private Rectangle shape;

    //TODO
    // - bad name
    public Barrier(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        shape = new Rectangle(x, y, width, height);
    }

    public Node getShape(){
        return shape;
    }

}
