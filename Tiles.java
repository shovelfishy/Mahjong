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

    public Tiles(int suit){
        this.suit = suit;
    }

    public Tiles(int suit, int numerical){
        this.suit = suit;
        this.numerical = numerical;
    }

    public int GetSuit() {
        return suit;
    }

    public int GetNum() {
        return numerical;
    }

    public boolean Equals(Tiles tile){
        if(tile.GetNum() == numerical && tile.GetSuit() == suit){
            return true;
        }
        return false;
    }

    abstract public String DisplayTileNum();
    abstract public String DisplayTileSuit();
    abstract public int CalcPoints(int meldType);
}