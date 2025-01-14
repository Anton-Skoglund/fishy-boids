package org.example.fishyboids;

import javafx.scene.Node;
import org.example.fishyboids.Body.Body;
import org.example.fishyboids.Boid.Boid;

import java.util.List;

public class Fish implements Drawable{
    private Boid head;
    private Body body;

    public Fish(Boid head, Body body){
        this.head = head;
        this.body = body;
    }

    public void update(){
        head.updatePosition();
        body.update();
    }


    public Boid getHead() {
        return head;
    }



    @Override
    public List<? extends Node> getNodes() {
        return body.getNodes();
    }
}

