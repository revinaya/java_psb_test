import org.testng.Assert;
import org.testng.annotations.Test;
//import org.testng.annotations.*;


public class PointTests {
    @Test
    public void testDistance1() {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(10, 5);
        Assert.assertEquals(p1.distance(p2), 5.0);
    }
    @Test
    public void testDistance2() {
        Point p1 = new Point(-8, 0);
        Point p2 = new Point(4, -2);
        Assert.assertEquals(p1.distance(p2), 12.165525060596439);
    }


    @Test
    public void testDistance3() {
        Point p1 = new Point(7, 0);
        Point p2 = new Point(0, 0);
        Assert.assertEquals(p1.distance(p2), 7.0);
    }

    @Test
    public void testDistance4() {
        Point p1 = new Point(7, 0);
        Point p2 = new Point(0, 0);
        Assert.assertEquals(MyFirstProgram.distance(p1, p2), 7.0);
    }
}
