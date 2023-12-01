public class Character extends Tiles{

    //=================== CONSTRUCTOR =====================//
    // DEVELOPED BY: DANIEL
    /* Constructs a new Character tile */
    public Character(int numerical){
        super(Tiles.CHARACTER, numerical);
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
        return "| CHAR  ";
    }
    // DEVELOPED BY: DANIEL
    /* Displayer override method to display the numerical of the tile
    * @param          - The type of meld
    * @return         - The appropriate points, depending if it is a pong, or chow or no meld*/
    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 115;
            case Tiles.CHOW_MELD:
                return 95;
            case Tiles.NO_MELD:
                return 15;
        }
        return 0;
    }
}