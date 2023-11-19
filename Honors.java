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

    /* JAVA DOC DESC 
    * @return         - The tile's honor type */
    public int GetHonorType() {
        return honorType;
    }
    
    /* JAVA DOC DESC 
    * @return         -  */
    public String DisplayTileNum(){
        return "|HONORS ";
    }

    /* JAVA DOC DESC 
    * @return         -  */
    public String DisplayTileSuit(){
        String suit = "";
        switch(honorType){
            case EAST_HONOR:
                suit = "| EAST  ";
                break;
            case SOUTH_HONOR:
                suit = "| SOUTH ";
                break;
            case WEST_HONOR:
                suit = "| WEST  ";
                break;
            case NORTH_HONOR:
                suit = "| NORTH ";
                break;
            case RED_HONOR:
                suit = "|  RED  ";
                break;
            case WHITE_HONOR:
                suit = "| WHITE ";
                break;
            case GREEN_HONOR:
                suit = "| GREEN ";
                break;
        }
        return suit;
    }
}