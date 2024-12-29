package org.example.fishyboids.Body;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.example.fishyboids.Util.Point;
import org.example.fishyboids.Util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RectangleBody implements Body {
    private Point head;
    private int amount;
    private Function<Double, Double> bodyShape;
    private Function<Integer, Color> colorFunction;

    private List<Rectangle> nodes;
    private List<Point> points;
    private List<Line> lines;


    public RectangleBody(Point head, int amount, Function<Double, Double> bodyShape, Function<Integer, Color> colorFunction) {
        this.head = head;
        this.amount = amount;
        this.bodyShape = bodyShape;
        this.colorFunction = colorFunction;

        nodes = new ArrayList<>();
        lines = new ArrayList<>();
        points = new ArrayList<>();


        initBody();
    }

    public RectangleBody(Point head, int amount) {
        this(head, amount, k -> Math.abs(Math.cos((k / (25 + 10.0)) * Math.PI)) * 10 + 5, i -> Color.rgb(0, 0 ,  Math.max(i * (255 / amount), (amount - i) * (255 / amount))));
    }

    private void initBody() {
        for(int i = 0; i < amount; i++){

            Rectangle neeRectangle = new Rectangle(bodyShape.apply((double) i), bodyShape.apply((double) i), colorFunction.apply(i));

            Line newLine = new Line(0, 0, 0, 0);
            Point newPoint = new Point(0,0);
            nodes.add(neeRectangle);
            lines.add(newLine);
            points.add(newPoint);
        }
    }

    public void update() {
        Rectangle first = nodes.getFirst();

        first.setX(head.x - (first.getWidth() / 2));
        first.setY(head.y - (first.getHeight() / 2));


        for(int i = nodes.toArray().length - 1; i > 0; i--){
            Point p1 = new Point(nodes.get(i-1).getX(), nodes.get(i-1).getY());
            Point p2 = new Point(nodes.get(i).getX(), nodes.get(i).getY());

            Vector vector = new Vector(p1, p2);

            // double factor = 50.0 / vector.getLength();
            double factor = (nodes.get(i-1).getWidth() / 4) / Math.max(vector.getLength(), 1);

            vector.scale(factor);

            lines.get(i-1).setStartX(nodes.get(i-1).getX());
            lines.get(i-1).setStartY(nodes.get(i-1).getY());
            lines.get(i-1).setEndX(nodes.get(i-1).getX() + vector.get(0));
            lines.get(i-1).setEndY(nodes.get(i-1).getY() + vector.get(1));


            nodes.get(i).setX(nodes.get(i-1).getX() + vector.get(0));
            nodes.get(i).setY(nodes.get(i-1).getY() + vector.get(1));
        }
    }

    @Override
    public List<? extends Node> getNodes() {
        return nodes;
    }

}
