public class Honors extends Tiles{
    public final static int EAST_HONOR   = 1;
    public final static int SOUTH_HONOR  = 2;
    public final static int WEST_HONOR   = 3;
    public final static int NORTH_HONOR  = 4;
    public final static int RED_HONOR    = 5;
    public final static int WHITE_HONOR  = 6;
    public final static int GREEN_HONOR  = 7;

    private int honorType;

    public Honors(int honorType){
        super(Tiles.HONORS);    
        this.honorType = honorType;
    }

    public int GetHonorType() {
        return honorType;
    }
    
}