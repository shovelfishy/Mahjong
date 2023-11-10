public abstract class Tiles {
    public static final int PONG_MELD = 200;
    public static final int CHOW_MELD = 201;
    public static final int EYE_PAIR = 202;

    public static final int BAMBOO = 400;
    public static final int DOT = 401;
    public static final int CHARACTER = 402;
    public static final int HONORS = 403;

    private int suit;
    private int numerical;
    private Player owner;

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

    public int GetNumerical() {
        return numerical;
    }

    public Player GetOwner() {
        return owner;
    }

    public void SetOwner(Player owner) {
        this.owner = owner;
    }
    
}