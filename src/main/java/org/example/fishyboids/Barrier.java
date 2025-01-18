package org.example.fishyboids;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.example.fishyboids.Util.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Barrier<T extends Shape> implements Drawable {
    //TODO
    // - set color not here but in shape you pass in
    private T shape;


    //TODO
    // - bad name
    public Barrier(T shape) {
        this.shape = shape;
    }


    public Node getShape(){
        return shape;
    }

    public boolean inside(Point point){
        return shape.contains(point.x, point.y);
    }

    @Override
    public List<? extends Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(shape);
        return nodes;
    }
}
