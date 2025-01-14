package org.example.fishyboids;

import javafx.scene.Node;

import java.util.List;

public interface Drawable {
    List<? extends Node> getNodes();
}
