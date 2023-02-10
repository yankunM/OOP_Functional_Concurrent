package arithmetic;

import core.Group;

enum PlusOrMinusOne{
    PLUS_ONE(1),
    MINUS_ONE(-1);

    private int value;
    private PlusOrMinusOne(int val){
        value = val;
    }
    private int getValue(){
        return value;
    }
    @Override
    public String toString(){
        return Integer.toString(this.value);
    }
}

public class FiniteGroupOfOrderTwo implements Group<PlusOrMinusOne> {
    @Override
    public PlusOrMinusOne binaryOperation(PlusOrMinusOne one, PlusOrMinusOne other) {
        switch(one){
            case PLUS_ONE:
                switch(other){
                    case PLUS_ONE:
                        return PlusOrMinusOne.PLUS_ONE;
                    case MINUS_ONE:
                        return PlusOrMinusOne.MINUS_ONE;
                }
            case MINUS_ONE:
                switch(other){
                    case PLUS_ONE:
                        return PlusOrMinusOne.MINUS_ONE;
                    case MINUS_ONE:
                        return PlusOrMinusOne.PLUS_ONE;
                }
        }
        return null;
    }

    @Override
    public PlusOrMinusOne identity() {
        return PlusOrMinusOne.PLUS_ONE;
    }

    @Override
    public PlusOrMinusOne inverseOf(PlusOrMinusOne plusOrMinusOne) {
        switch(plusOrMinusOne){
            case PLUS_ONE:
                return PlusOrMinusOne.MINUS_ONE;
            case MINUS_ONE:
                return PlusOrMinusOne.PLUS_ONE;
        }
        return null;
    }
}
