public class Honors extends Tiles{
    public final static int EAST_HONOR   = 1;
    public final static int SOUTH_HONOR  = 2;
    public final static int WEST_HONOR   = 3;
    public final static int NORTH_HONOR  = 4;
    public final static int RED_HONOR    = 5;
    public final static int WHITE_HONOR  = 6;
    public final static int GREEN_HONOR  = 7;

    public Honors(int honorType){
        super(Tiles.HONORS, honorType);
    }

    /* JAVA DOC DESC 
    * @return         -  */
    public String DisplayTileNum(){
        return "|HONORS ";
    }

    /* JAVA DOC DESC 
    * @return         -  */
    public String DisplayTileSuit(){
        switch(GetNum()){
            case EAST_HONOR:
                return "| EAST  ";
            case SOUTH_HONOR:
                return "| SOUTH ";
            case WEST_HONOR:
                return "| WEST  ";
            case NORTH_HONOR:
                return "| NORTH ";
            case RED_HONOR:
                return "|  RED  ";
            case WHITE_HONOR:
                return "| WHITE ";
            case GREEN_HONOR:
                return "| GREEN ";
            default:
                return null;
        }
    }
}