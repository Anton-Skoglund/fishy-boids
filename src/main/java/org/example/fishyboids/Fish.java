package org.example.fishyboids;

public class Fish {
    private Boid head;
    private ProceduralBody body;

    public Fish(Boid head, ProceduralBody Body){
        this.head = head;
        this.body = body;
        body = new ProceduralBody(head.getCenter(), 25, i -> (1 - Math.sin(i / 20.0)) * 10 + 5);
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

