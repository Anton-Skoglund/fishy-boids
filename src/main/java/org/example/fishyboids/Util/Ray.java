package org.example.fishyboids.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ray {
    private Point start;
    private Point end;
    private List<Point> points;

    public Ray(Point start, Point end, double stepSize){
        this.start = start;
        this.end  = end;

        points = new ArrayList<>();


        Vector line = new Vector(this.start, this.end);
        double length = line.getLength();
        line.scale(1/ line.getLength());

        line.scale(stepSize);
        Vector stepLine = new Vector(line.getArray());

        points.add(start);

        while (line.getLength() < length){
            points.add(new Point(start.x + line.getArray()[0], start.y + line.getArray()[1]));
            line = line.add(stepLine);
        }

        points.add(end);
    }

    public List<Point> getRayPoints(){
        return points;
    }

    public Point getStartPoint(){
        return start;
    }

    public Point getEndPoint(){
        return end;
    }



    @Override
    public String toString(){
        return points.toString();
    }
}
