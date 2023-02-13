public class MyFirstProgram {

    public static void main(String[] args) {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(4, 4);
        System.out.println("Расстояние между двумя точками = " + distance(p1, p2));

    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}