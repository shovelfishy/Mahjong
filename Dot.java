public class Dot extends Tiles{

    //=================== CONSTRUCTOR =====================//
    // DEVELOPED BY: DANIEL
    /* Constructs a new Dot tile */
    public Dot(int numerical){
        super(Tiles.DOT, numerical);
    }
    // DEVELOPED BY: DANIEL
    /* Displayer override method to display the numerical of the tile
    * @return         -  the string of the numerical of tile*/
    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }

     // DEVELOPED BY: DANIEL
    /* Displayer override method to display the suit 
    * @return         -  the string of the tile's suit*/
    public String DisplayTileSuit(){
        return "|  DOT  ";
    }

    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 105;
            case Tiles.CHOW_MELD:
                return 90;
            case Tiles.NO_MELD:
                return 25;
        }
        return 0;
    }   
}