import org.example.fishyboids.Main;
import org.example.fishyboids.Util.Point;
import org.example.fishyboids.Util.Ray;
import org.example.fishyboids.Util.Vector;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayTest {
    @Test
    public void init(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(100, 0);

        genericRayTest(p1, p2, 10);
    }

    @Test
    public void decimalStep(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(100, 0);

        genericRayTest(p1, p2, 3.4847126);
    }

    @Test
    public void zero(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);

        genericRayTest(p1, p2, 10);
    }

    @Test
    public void diagonal(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(100, 100);


        genericRayTest(p1, p2, 10);
    }

    @Test
    public void everyDegreeTest(){
        for(int angle = 0; angle < 360; angle++){
            Point p1 = new Point(0, 0);
            Point p2 = new Point(100 * Math.cos(Math.toRadians(angle)), 100 * Math.sin(Math.toRadians(angle)));
            if(!genericRayTest(p1, p2, 10)){
                assert(false);
            }
        }

        assert(true);
    }

    private boolean genericRayTest(Point p1, Point p2, double stepSize){
        Ray ray = new Ray(p1, p2, stepSize);

        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);


        List<Point> points = new ArrayList<>();
        for(int i = 0; i < ray.getRayPoints().size(); i++){
            Point newPoint = new Point(p1.x + i * stepSize * Math.cos(angle), p1.y + i * stepSize * Math.sin(angle));
            System.out.println(newPoint + ", " + p2 + ", " + (Math.abs(p2.x) < Math.abs(newPoint.x) || (Math.abs(p2.y) < Math.abs(newPoint.y))));
            if (Math.abs(p2.x) < Math.abs(newPoint.x) || (Math.abs(p2.y) < Math.abs(newPoint.y))) {
                points.add(p2);
            }else {
                points.add(newPoint);
            }
        }

        System.out.println(p1 + ", " + p2 + ", " + stepSize);
        System.out.println(Math.toDegrees(angle) + ", " + Math.cos(angle) + ", " + Math.sin(angle));
        System.out.println("ray, " + ray);
        System.out.println("test, " + points);

        for(int i = 0; i < ray.getRayPoints().size(); i++){
            if(!points.get(i).equals(ray.getRayPoints().get(i))){
                System.out.println("FAIL\n");
                return false;
            }
        }


        return true;



    }
}
