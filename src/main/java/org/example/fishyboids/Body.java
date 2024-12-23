package org.example.fishyboids;

import javafx.scene.Node;

import java.util.List;

//TODO
// - forced the update function, but think it is best
public interface Body<T extends Node> {
    List<T> getNodes();
    void update();

}
