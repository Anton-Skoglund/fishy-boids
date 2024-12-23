package org.example.fishyboids;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.fishyboids.Point;
import org.example.fishyboids.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProceduralBody  {
    private Point head;

    private List<Circle> nodes;
    private List<Point> points;
    private List<Line> lines;


    public ProceduralBody(Point head) {

        this.head = head;

        nodes = new ArrayList<>();
        lines = new ArrayList<>();
        points = new ArrayList<>();

        int amount = 25;

        for(int i = 0; i < amount; i++){
            Random random = new Random();
            int step = 255 / amount;


            Circle newCircle = new Circle(50, 50, (1 - Math.sin(i / 20.0)) * 10 + 5, Color.rgb(0, 0 , (amount - i)*step));

            Line newLine = new Line(0, 0, 0, 0);
            Point newPoint = new Point(0,0);
            nodes.add(newCircle);
            lines.add(newLine);
            points.add(newPoint);
        }
    }

    public void move() {
        nodes.getFirst().setCenterX(head.x - nodes.getFirst().getRadius() / 2);
        nodes.getFirst().setCenterY(head.y - nodes.getFirst().getRadius() / 2);



        for(int i = nodes.toArray().length - 1; i > 0; i--){
            Point p1 = new Point(nodes.get(i-1).getCenterX(), nodes.get(i-1).getCenterY());
            Point p2 = new Point(nodes.get(i).getCenterX(), nodes.get(i).getCenterY());

            Vector vector = new Vector(p1, p2);
            // double factor = 50.0 / vector.getLength();
            double factor = (nodes.get(i-1).getRadius() / 4) / Math.max(vector.getLength(), 1);

            vector.scale(factor);

            lines.get(i-1).setStartX(nodes.get(i-1).getCenterX());
            lines.get(i-1).setStartY(nodes.get(i-1).getCenterY());
            lines.get(i-1).setEndX(nodes.get(i-1).getCenterX() + vector.get(0));
            lines.get(i-1).setEndY(nodes.get(i-1).getCenterY() + vector.get(1));


            nodes.get(i).setCenterX(nodes.get(i-1).getCenterX() + vector.get(0));
            nodes.get(i).setCenterY(nodes.get(i-1).getCenterY() + vector.get(1));
        }
    }


    public List<Circle> getNodes() {
        return nodes;
    }
}
