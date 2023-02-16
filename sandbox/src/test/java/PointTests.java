import org.testng.Assert;
import org.testng.annotations.Test;
//import org.testng.annotations.*;


public class PointTests {
    @Test
    public void testDistance() {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(10, 5);
        Assert.assertEquals(Point.distance(p1, p2), 5.0);
    }

}
