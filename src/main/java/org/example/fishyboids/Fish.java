package org.example.fishyboids;

public class Fish {
    private Boid head;
    private ProceduralBody body;

    public Fish(Boid head, ProceduralBody body){
        this.head = head;
        this.body = body;
    }

    public void update(){
        head.updatePosition();
        body.move();
    }


    public Boid getHead() {
        return head;
    }


    public ProceduralBody getBody() {
        return body;
    }



}

