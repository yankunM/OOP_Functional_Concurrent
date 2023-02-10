package geometry;

import java.util.*;

public class RadialGraph extends Shape {

    public Point center;
    public List<Point> neighbors;

    public RadialGraph(Point center, List<Point> neighbors) {
       this.center = center;
       // check if all given neighbors have the same length
        double distance = distanceBtwPoints(center, neighbors.get(0));
        for(Point p: neighbors){
            if(distanceBtwPoints(center, p) != distance) throw new IllegalArgumentException();
        }
       this.neighbors = sortPoints(center, neighbors);
    }

    private static List<Point> sortPoints(Point center, List<Point> neighbors){
        List<Point> sortedList = new ArrayList<>();
        double[] angles = new double[neighbors.size()];
        Map<Double, Point> map = new HashMap<>();
        for(Point p: neighbors){
            map.put(angleOfLine(center, p), p);
        }
        for(int i=0; i< neighbors.size(); i++){
            angles[i] = angleOfLine(center, neighbors.get(i));
        }
        Arrays.sort(angles);
        for(double d: angles){
            sortedList.add(map.get(d));
        }
        return sortedList;
    }
    // checked
    private static double angleOfLine(Point one, Point other)
    {
        double angle = Math.atan2(other.y - one.y,
                other.x - one.x);
        if(angle < 0){
            angle += 2*Math.PI;
        }
        return angle;
    }
    //method to calculate distance between two Points - checked
    private static double distanceBtwPoints(Point one, Point two){
        double x1 = one.x;
        double y1 = one.y;
        double x2 = two.x;
        double y2 = two.y;
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    //checked
    public RadialGraph(Point center) {
        this.center = center;
        this.neighbors = null;
    }

    @Override
    public RadialGraph rotateBy(int degrees) {
        List<Point> rotatedPoints = new ArrayList<>();
        for(Point p: neighbors){
//            System.out.println("right now "+p.x);
//            System.out.println("right now "+p.y);
            // convert to standard coordinate system
            double newX = p.x - this.center.x;
            double newY = p.y - this.center.y;
//            System.out.println("standard "+newX);
//            System.out.println("standard "+newY);
            // apply rotation matrix
            double tempX = newX;
            double tempY = newY;
            newX = tempX*Math.cos(Math.toRadians(degrees)) - tempY*Math.sin(Math.toRadians(degrees));
            newY = tempX*Math.sin(Math.toRadians(degrees)) + tempY*Math.cos(Math.toRadians(degrees));
//            System.out.println("rotated "+newX);
//            System.out.println("rotated "+newY);
            // convert back to original coordinate system
            double x = Math.round((newX + this.center.x)*100000.0)/100000.0;
            double y = Math.round((newY + this.center.y)*100000.0)/100000.0;
            rotatedPoints.add(new Point(p.name, x, y));
        }
//        for(Point p: rotatedPoints){
//            System.out.println("Point "+p.x);
//            System.out.println("Point "+p.y);
//            System.out.println("Center "+center.x);
//            System.out.println("Center "+center.y);
//            System.out.println(distanceBtwPoints(center, p));
//        }

        return new RadialGraph(center, rotatedPoints);
    }

    //checked
    @Override
    public RadialGraph translateBy(double x, double y) {
        List<Point> translatedPoints = new ArrayList<>();
        Point newCenter = new Point(center.name, center.x+x, center.y+y);
        for(Point p: neighbors){
            Point newPoint = new Point(p.name, p.x+x, p.y+y);
            translatedPoints.add(newPoint);
        }
        return new RadialGraph(newCenter, translatedPoints);
    }

    //checked
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[" + center.toString());
        if (this.neighbors != null) {
            for (Point p : neighbors) {
                str.append("; ").append(p.toString());
            }
        }
        str.append("]");
        return str.toString();
    }
    //checked
    @Override
    public Point center() {
        return this.center;
    }

    /* Driver method given to you as an outline for testing your code. You can modify this as you want, but please keep
     * in mind that the lines already provided here as expected to work exactly as they are (some lines have additional
     * explanation of what is expected) */
    public static void main(String... args) {
        Point center = new Point("center", 0, 0);
        Point east = new Point("east", 1, 0);
        Point west = new Point("west", -1, 0);
        Point north = new Point("north", 0, 1);
        Point south = new Point("south", 0, -1);
        Point toofarsouth = new Point("south", 0, -2);
//        System.out.println(sortPoints(center, Arrays.asList(north, south, east, west)));

//        Point center = new Point("center", 1, 1);
//        Point east = new Point("east", 2, 1);
//        Point west = new Point("west", 0, 1);
//        Point north = new Point("north", 1, 2);
//        Point south = new Point("south", 1, 0);
//        Point toofarsouth = new Point("south", 1, -1);


        // A single node is a valid radial graph.
//        RadialGraph lonely = new RadialGraph(center);

        // Must print: [(center, 0.0, 0.0)]
//        System.out.println(lonely);

        // This line must throw IllegalArgumentException, since the edges will not be of the same length
//      // RadialGraph nope = new RadialGraph(center, Arrays.asList(north, toofarsouth, east, west));
//


        Shape g = new RadialGraph(center, Arrays.asList(north, south, east, west));
////
////        // Must follow the documentation in the Shape abstract class, and print the following string:
////        // [(center, 0.0, 0.0); (east, 1.0, 0.0); (north, 0.0, 1.0); (west, -1.0, 0.0); (south, 0.0, -1.0)]
////        System.out.println(g.center());
////
////        // After this counterclockwise rotation by 90 degrees, "north" must be at (-1, 0), and similarly for all the
////        // other radial points. The center, however, must remain exactly where it was.
        System.out.println(g);
        g = g.rotateBy(75);
        System.out.println(g);
//
//        // you should similarly add tests for the translateBy(x, y) method
//        g = g.translateBy(1,1);
//        System.out.println(g);
    }
}
