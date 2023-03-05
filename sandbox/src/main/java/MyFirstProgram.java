public class MyFirstProgram {

    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        System.out.println("Расстояние между двумя точками 1 способ = " + distance(p1, p2));
        System.out.println("Расстояние между двумя точками 2 способ = " + p1.distance(p2));
    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}