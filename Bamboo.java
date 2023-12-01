public class Bamboo extends Tiles{
 
    //=================== CONSTRUCTOR =====================//
    // DEVELOPED BY: DANIEL
    /* Constructs a new Bamboo tile */
    public Bamboo(int numerical){
        super(Tiles.BAMBOO, numerical);
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
        return "|BAMBOO ";
    }
    // DEVELOPED BY: DANIEL
    /* Displayer override method to display the numerical of the tile
    * @param          - The type of meld
    * @return         - The appropriate points, depending if it is a pong or chow or no meld*/
    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 110;
            case Tiles.CHOW_MELD:
                return 105;
            case Tiles.NO_MELD:
                return 20;
        }
        return 0;
    }
}