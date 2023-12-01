public class Honors extends Tiles{
    
    //GLOBAL CONSTANTS
    public final static int EAST_HONOR   = 10;
    public final static int SOUTH_HONOR  = 20;
    public final static int WEST_HONOR   = 30;
    public final static int NORTH_HONOR  = 40;
    public final static int RED_HONOR    = 50;
    public final static int WHITE_HONOR  = 60;
    public final static int GREEN_HONOR  = 70;

    //=================== CONSTRUCTOR =====================//
    // DEVELOPED BY: DANIEL
    /* Constructs a new Honor tile */
    public Honors(int honorType){
        super(Tiles.HONORS, honorType);
    }

    // DEVELOPED BY: DANIEL
    /* Displayer override method to display the suit 
    * @return         -  the string of the tile's suit*/
    public String DisplayTileSuit(){
        return "|HONORS ";
    }

    // DEVELOPED BY: DANIEL
    /* Displayer override method to display the numerical of the tile
    * @return         -  the string of the numerical of tile*/
    public String DisplayTileNum(){
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
    // DEVELOPED BY: DANIEL
    /* Displayer override method to display the numerical of the tile
    * @param          - The type of meld
    * @return         - The appropriate points, depending if it is a pong or not*/
    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 120;
            case Tiles.NO_MELD:
                return 30;
        }
        return 0;
    }   
}