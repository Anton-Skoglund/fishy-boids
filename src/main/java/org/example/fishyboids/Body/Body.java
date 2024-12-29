package org.example.fishyboids.Body;

import javafx.scene.Node;

import java.util.List;

//TODO
// - forced the update function, but think it is best
public interface Body {
    List<? extends Node> getNodes();
    void update();
}
