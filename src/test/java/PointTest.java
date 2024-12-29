import org.example.fishyboids.Util.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {
    @Test
    public void init(){
        Point point = new Point(10.0, 10.0);
        assertEquals(10.0, point.x);
        assertEquals(10.0, point.y);

    }

    @Test
    public void set(){
        Point point = new Point(10.0, 10.0);
        point.x = 20.0;
        point.y = 20.0;

        assertEquals(20.0, point.x);
        assertEquals(20.0, point.y);
    }

    @Test
    public void get(){
        Point point = new Point(10.0, 10.0);
        assertEquals(10.0, point.x);
        assertEquals(10.0, point.y);
        assertEquals(10.0, point.x);
        assertEquals(10.0, point.y);
    }
}
