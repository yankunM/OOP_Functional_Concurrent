package geometry;

import java.util.*;

public class SquareSymmetries implements Symmetries<Square> {

    @Override
    public boolean areSymmetric(Square s1, Square s2) {
        // translate to same center
        Point center = s1.center();
        double diffX = s2.center().x - center.x;
        double diffY = s2.center().y - center.y;
        Square second = new Square(new Point(s2.a.name, s2.a.x - diffX, s2.a.y - diffY),
                new Point(s2.b.name, s2.b.x - diffX, s2.b.y - diffY),
        new Point(s2.c.name, s2.c.x - diffX, s2.c.y - diffY),
        new Point(s2.d.name, s2.d.x - diffX, s2.d.y - diffY));

        // check in list
        for(Square s: symmetriesOf(s1)){
            if(second.a.equals(s.a) && second.b.equals(s.b) && second.c.equals(s.c) && second.d.equals(s.d)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Square> symmetriesOf(Square square) {
        List<Square> list = new ArrayList<>();
        // identity
        list.add(square);
        // rotation by 90 degrees counterclockwise
        list.add(square.rotateBy(90));
        // rotation by 180 degrees counterclockwise
        list.add(square.rotateBy(180));
        // rotation by 270 degrees counterclockwise
        list.add(square.rotateBy(270));
        //vertical reflection
        Square vReflected = new Square(new Point(square.d.name, square.a.x, square.a.y), new Point(square.c.name, square.b.x, square.b.y), new Point(square.b.name, square.c.x, square.c.y), new Point(square.a.name, square.d.x, square.d.y));
        list.add(vReflected);
        //Horizontal Reflection
        Square hReflected = new Square(new Point(square.b.name, square.a.x, square.a.y), new Point(square.a.name, square.b.x, square.b.y), new Point(square.d.name, square.c.x, square.c.y), new Point(square.c.name, square.d.x, square.d.y));;
        list.add(hReflected);
        //Diagonal Reflection
        Square dReflected = new Square(new Point(square.a.name, square.a.x, square.a.y), new Point(square.d.name, square.b.x, square.b.y), new Point(square.c.name, square.c.x, square.c.y), new Point(square.b.name, square.d.x, square.d.y));;
        list.add(dReflected);
        //Counter Diagonal Reflection
        Square cdReflected = new Square(new Point(square.c.name, square.a.x, square.a.y), new Point(square.b.name, square.b.x, square.b.y), new Point(square.a.name, square.c.x, square.c.y), new Point(square.d.name, square.d.x, square.d.y));;
        list.add(cdReflected);
        return list;
    }


}
