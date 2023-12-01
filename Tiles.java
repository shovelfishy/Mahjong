public abstract class Tiles {

    // GLOBAL CONSTANTS: MELD TYPES
    public static final int NO_MELD = -200;
    public static final int PONG_MELD = 200;
    public static final int CHOW_MELD = 201;
    public static final int PONG_CHOW_MELD = 202;

    // GLOBAL CONSTANTS: TILE TYPES
    public static final int BAMBOO = 400;
    public static final int DOT = 401;
    public static final int CHARACTER = 402;
    public static final int HONORS = 403;

    // Private variables
    private int suit;
    private int numerical;

    //=================== CONSTRUCTOR =====================//
    // DEVELOPPED BY: DANIEL
    //Constructor that takes in the suit and the numerical, then sets the tile with that suit and numerical
    public Tiles(int suit, int numerical){
        this.suit = suit;
        this.numerical = numerical;
    }
    //================== PUBLIC METHODS ====================//

    // DEVELOPED BY: DANIEL
    /* Gets the tile's suit
    * @return         - An integer that represents the tile's suit*/
    public int GetSuit() {
        return suit;
    }
    
    // DEVELOPED BY: DANIEL
    /* Gets the tile's numerical
    * @return         - An integer that represents the tile's individual number*/
    public int GetNum() {
        return numerical;
    }

    // DEVELOPED BY: DANIEL
    /* Checks if the tile's numerical and suit are equal
    * @Param tile     - A tile object
    * @return         - True if the tile's numerical and suit are equal
                      - False otherwise */
    public boolean Equals(Tiles tile){
        if(tile.GetNum() == numerical && tile.GetSuit() == suit){
            return true;
        }
        return false;
    }

    // DEVELOPED BY: DANIEL
    /* Displayer abstract methods to display the tile number
     * @return    - String of the numerical of the tile */ 
    abstract public String DisplayTileNum();

    // DEVELOPED BY: DANIEL
    /* Displayer abstract methods to display the suit of the tile
     * @return    - String of the suit of the tile */ 
    abstract public String DisplayTileSuit();

    // DEVELOPED BY: DANIEL
    /* Calculates the points of each type of meld, as each meld has different point values
     * @Param meldType   - the type of meld
     * @return           - appropriate points */ 
    abstract public int CalcPoints(int meldType);
}