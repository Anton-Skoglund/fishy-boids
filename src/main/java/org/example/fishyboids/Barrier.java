package org.example.fishyboids;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.fishyboids.Util.Point;

import java.util.ArrayList;
import java.util.List;

public class Barrier implements Drawable {
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
        shape.setFill(Color.rgb(255, 255, 255));
    }

    public Node getShape(){
        return shape;
    }

    public boolean inside(Point point){
        return x < point.x && point.x < x + width && y < point.y && point.y < y + height;
    }

    @Override
    public List<? extends Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(shape);
        return nodes;
    }
}
