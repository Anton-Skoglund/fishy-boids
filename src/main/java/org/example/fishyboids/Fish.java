package org.example.fishyboids;

import org.example.fishyboids.Body.Body;
import org.example.fishyboids.Boid.Boid;

public class Fish {
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


    public Body getBody() {
        return body;
    }
}

