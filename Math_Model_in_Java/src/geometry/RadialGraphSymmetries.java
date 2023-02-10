package geometry;
import java.util.*;

public class RadialGraphSymmetries implements Symmetries<RadialGraph> {
    @Override
    public boolean areSymmetric(RadialGraph s1, RadialGraph s2) {
        if(s1.neighbors.size() != s2.neighbors.size()){
            return false;
        }
        // translate to same center
        Point fix = s1.center;
        double diffX = s2.center.x - fix.x;
        double diffY = s2.center.y - fix.y;
        List<Point> fixed = new ArrayList<>();
        for(Point p: s2.neighbors){
            Point x = new Point(p.name, p.x-diffX, p.y-diffY);
            fixed.add(x);
        }
        RadialGraph r = new RadialGraph(fix, fixed);

        int count = 0;
        //check
        for(RadialGraph x: symmetriesOf(s1)){
            for(int i=0; i<r.neighbors.size();i++){
                if(r.neighbors.get(i).equals(x.neighbors.get(i))){
                    count++;
                }
            }
        }
        return count == r.neighbors.size();
    }

    // given: radial graph
    // return: all it's symmetries
    @Override
    public List<RadialGraph> symmetriesOf(RadialGraph radialGraph) {
        List<RadialGraph> l = new ArrayList<>();
        int size = radialGraph.neighbors.size();
        if(size == 0){
            l.add(radialGraph);
            return l;
        } else if(size == 1){
            l.add(radialGraph);
            l.add(radialGraph.rotateBy(180));
            return l;
        } else{
            int ideal = 360/size;
//            System.out.println(ideal);
            for(int i=1; i<size; i++){
                if((int) angleBetween2Lines(radialGraph.center, radialGraph.neighbors.get(i), radialGraph.neighbors.get(i-1)) != ideal){
                    l.add(radialGraph);
                    return l;
                }
            }
            // this is an ideal graph
            for(int i=0; i<360; i+=ideal){
                l.add(radialGraph.rotateBy(i));
            }
        }
        return l;
    }

    private static double angleBtwLine(Point one, Point other)
    {
        double angle = Math.atan2(other.y - one.y,
                other.x - one.x);
        if(angle < 0){
            angle += 2*Math.PI;
        }
        return angle;
    }

    // Calculates the angle formed between two lines
    private static double angleBetween2Lines(Point center, Point one, Point other)
    {
        double angle1 = angleBtwLine(center, one);
        double angle2 = angleBtwLine(center,other);
        return Math.toDegrees(Math.abs(angle1 - angle2));

    }

    public static void main(String[] args) {
        Point center = new Point("center", 0, 0);
        Point point1 = new Point("center", -1, 1);
        Point point2 = new Point("center", 1, 0);
        System.out.println(angleBetween2Lines(center, point2, point1));
    }
}
