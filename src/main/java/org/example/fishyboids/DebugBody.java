package org.example.fishyboids;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class DebugBody implements Body{
    private DebugBoid head;

    private List<Circle> bodies;
    private List<Line> lines;


    private Circle vissionCircle;

    public DebugBody(DebugBoid head) {
        this.head = head;

        bodies = new ArrayList<>();
        lines = new ArrayList<>();

        initBody();
    }


    private void initBody() {
        Circle newCircle = new Circle(0, 0, 10, Color.WHITE);
        Circle newVissionCircle = new Circle(0, 0, head.getVisionRadius(), Color.WHITE);
        newVissionCircle.setFill(null);
        newVissionCircle.setStroke(Color.rgb(255, 255, 255, 0.2));

        bodies.add(newCircle);
        this.vissionCircle = newVissionCircle;

    }

    public void update() {
        bodies.getFirst().setCenterX(head.getCenter().x - bodies.getFirst().getRadius() / 2);
        bodies.getFirst().setCenterY(head.getCenter().y - bodies.getFirst().getRadius() / 2);

        vissionCircle.setCenterX(head.getCenter().x - bodies.getFirst().getRadius() / 2);
        vissionCircle.setCenterY(head.getCenter().y - bodies.getFirst().getRadius() / 2);


        lines.clear();


        for(Boid otherBoid : head.getNeighbors()){
            double distance = new Vector(head.getCenter(), otherBoid.getCenter()).getLength();
            if(distance <= head.getVisionRadius()){
                // Ugly
                Line newLine = new Line(head.getCenter().x - bodies.getFirst().getRadius() / 2, head.getCenter().y- bodies.getFirst().getRadius() / 2, otherBoid.getCenter().x - bodies.getFirst().getRadius() / 2, otherBoid.getCenter().y- bodies.getFirst().getRadius() / 2);
                newLine.setStroke(Color.WHITE);
                lines.add(newLine);
            }
        }

        record Triple<A, B, C>(A first, B second, C third) {}

        List<Triple<Double, Vector, Color>> vectorsToLines = new ArrayList<>() {{
            add(new Triple<>(100.0, head.getDirectionVector(), Color.RED));
            add(new Triple<>(1 / head.alignmentWeight, head.getAligmentVector(), Color.BLUE));
            add(new Triple<>(1 / head.cohesionWeight, head.getCohesionVector(), Color.GREEN));
            add(new Triple<>(1 / head.separationWeight, head.getSeparationVector(), Color.PINK));
        }};

        for(Triple<Double, Vector, Color> triple : vectorsToLines){
            double scalar = triple.first;
            Vector vector = triple.second;
            Color color = triple.third;

            Line directionLine = new Line(bodies.getFirst().getCenterX(), bodies.getFirst().getCenterY(), bodies.getFirst().getCenterX() + vector.get(0) * scalar, bodies.getFirst().getCenterY() + vector.get(1) * scalar);
            directionLine.setStroke(color);
            lines.add(directionLine);
        }

    }

    // BAD, but for testing
    @Override
    public List<? extends Node> getNodes() {
        List<Node> combined = new ArrayList<>();
        combined.addAll(lines);  // Add all line nodes
        combined.addAll(bodies);  // Add all circle nodes

        combined.add(vissionCircle);
        return combined;
    }


}
