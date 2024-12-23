package org.example.fishyboids;

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

