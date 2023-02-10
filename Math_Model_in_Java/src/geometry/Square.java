package geometry;

import java.util.*;

public class Square extends Shape {

    public Point a; // right upper
    public Point b; // left upper
    public Point c; // left lower
    public Point d; // right lower


    // Rotation Invariant
    @Override
    public Point center() {
        return new Point("center", (a.x+c.x)/2, (a.y+c.y)/2);
    }

    @Override
    public Square rotateBy(int degrees) {
        Point center = center();
        // convert to standard coordinate system
        Point newA = new Point(a.name, a.x - center.x, a.y-center.y);
        Point newB = new Point(b.name, b.x - center.x, b.y-center.y);
        Point newC = new Point(c.name, c.x - center.x, c.y-center.y);
        Point newD = new Point(d.name, d.x - center.x, d.y-center.y);

        // Apply Rotation Matrix to Each point
        Point rotA = new Point(a.name,  newA.x*Math.cos(Math.toRadians(degrees)) - newA.y*Math.sin(Math.toRadians(degrees)), newA.x*Math.sin(Math.toRadians(degrees)) + newA.y*Math.cos(Math.toRadians(degrees)));
        Point rotB = new Point(b.name,  newB.x*Math.cos(Math.toRadians(degrees)) - newB.y*Math.sin(Math.toRadians(degrees)), newB.x*Math.sin(Math.toRadians(degrees)) + newB.y*Math.cos(Math.toRadians(degrees)));
        Point rotC = new Point(c.name,  newC.x*Math.cos(Math.toRadians(degrees)) - newC.y*Math.sin(Math.toRadians(degrees)), newC.x*Math.sin(Math.toRadians(degrees)) + newC.y*Math.cos(Math.toRadians(degrees)));
        Point rotD = new Point(d.name,  newD.x*Math.cos(Math.toRadians(degrees)) - newD.y*Math.sin(Math.toRadians(degrees)), newD.x*Math.sin(Math.toRadians(degrees)) + newD.y*Math.cos(Math.toRadians(degrees)));

        // convert back (ADD ROUNDING)
        double newX = Math.round((rotA.x + center.x)*100000.0)/100000.0;
        double newY = Math.round((rotA.y + center.y)*100000.0)/100000.0;
        Point a = new Point(rotA.name, newX, newY);
        newX = Math.round((rotB.x + center.x)*100000.0)/100000.0;
        newY = Math.round((rotB.y + center.y)*100000.0)/100000.0;
        Point b = new Point(rotB.name, newX, newY);
        newX = Math.round((rotC.x + center.x)*100000.0)/100000.0;
        newY = Math.round((rotC.y + center.y)*100000.0)/100000.0;
        Point c = new Point(rotC.name, newX, newY);
        newX = Math.round((rotD.x + center.x)*100000.0)/100000.0;
        newY = Math.round((rotD.y + center.y)*100000.0)/100000.0;
        Point d = new Point(rotD.name, newX, newY);

        // divide into cases
        if(degrees >= 270){
            return new Square(b,c,d,a);
        } else if(degrees >= 180){
            return new Square(c,d,a,b);
        } else if(degrees >= 90){
            return new Square(d,a,b,c);
        } else {
            return new Square(a,b,c,d);
        }
    }

    @Override
    public Square translateBy(double x, double y) {
        Point newA = new Point(a.name, a.x+x, a.y+y);
        Point newB = new Point(b.name, b.x+x, b.y+y);
        Point newC = new Point(c.name, c.x+x, c.y+y);
        Point newD = new Point(d.name, d.x+x, d.y+y);
        return new Square(newA, newB, newC, newD);
    }

    @Override
    public String toString() {
        return "[" +a.toString()+"; "+b.toString()+"; "+c.toString()+"; "+d.toString()+"]";
    }

    public Square(Point a, Point b, Point c, Point d) {
        // check order of points
        List<Point> correct = new ArrayList<>();
        correct.add(d);
        correct.add(a);
        correct.add(b);
        List<Point> neighbors = new ArrayList<>();
        neighbors = sortPoints(c, correct);
        if(!equalInOrder(correct,neighbors)) throw new IllegalArgumentException();
        // check all four sides have equal length
        double distance = Math.round(distanceBtwPoints(a,b));
        if(Math.round(distanceBtwPoints(b,c)) != distance) throw new IllegalArgumentException();
        if(Math.round(distanceBtwPoints(c,d)) != distance) throw new IllegalArgumentException();
        if(Math.round(distanceBtwPoints(d,a)) != distance) throw new IllegalArgumentException();
        // ALL VALID
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }


    private static boolean equalInOrder(List<Point> one, List<Point> other){
        if(one.size() != other.size()){
            return false;
        }
        for(int i=0; i<one.size(); i++){
            if(!one.equals(other)){
                return false;
            }
        }
        return true;
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

    public static void main(String[] args) {
        // test creation of squares
        Point A = new Point("a", 1, 1);
        Point B = new Point("b", 0, 1);
        Point C = new Point("c", 0, 0);
        Point D = new Point("d", 1, 0);
        Square square = new Square(A,B,C,D);
        System.out.println(square);
        System.out.println(square.center());

        // test on rotated Square
        square = square.rotateBy(75);
        System.out.println(square);

        // printing a translated square
        System.out.println("Translated: ");
        square = square.translateBy(-2.5,-2.5);
        System.out.println(square);
        System.out.println(square.center());


//        List<Point> list1 = new ArrayList<>();
//        list1.add(A);
//        list1.add(B);
//
//        List<Point> list2 = new ArrayList<>();
//        list2.add(B);
//        list2.add(A);


//        System.out.println(equalInOrder(list1, list2));

    }




}
