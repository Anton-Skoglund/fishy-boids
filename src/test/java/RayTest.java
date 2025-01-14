import org.example.fishyboids.Util.Point;
import org.example.fishyboids.Util.Ray;
import org.example.fishyboids.Util.Vector;
import org.junit.jupiter.api.Test;


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

        System.out.println(new Ray(p1, p2, 10));

        genericRayTest(p1, p2, 10);

    }

    private void genericRayTest(Point p1, Point p2, double stepSize){
        Ray ray = new Ray(p1, p2, stepSize);

        double angle = Math.atan((p2.x - p1.x) / (p2.y - p1.y));
        System.out.println(angle);


        for(int i = 0; i < new Vector(p1, p2).getLength() / stepSize; i++){
            assertEquals(new Point(stepSize * i, 0), ray.getRayPoints().get(i));
        }
    }
}
