public class Player {

    // GLOBAL CONSTANTS: PLAYER SEAT WINDS
    public static final int EAST_WIND = 100;
    public static final int NORTH_WIND = 101;
    public static final int WEST_WIND = 102;
    public static final int SOUTH_WIND = 103;

    // Private variables
    private int wind;
    private Tiles[][] exposedMelds;
    private Tiles[] concealedTiles;
    private Tiles[] hand;

    //TESTING
    public void SetHand(Tiles[] hand){
        this.hand = hand;
    }

    public Player(int wind, Tiles[] hand){
        this.wind = wind;
        this.hand = hand;
        this.concealedTiles = hand;
    }

    private Tiles[] copyTiles(Tiles[] list){
        Tiles[] temp = new Tiles[list.length];
        for(int i = 0; i < list.length; i++){
            temp[i] = list[i];
        }
        return temp;
    }

    

    public boolean CheckWinnningHand(){

    }

    public void ArrangeHand(int pos1, int pos2){
        Tiles temp = hand[pos1];
        hand[pos1] = hand[pos2];
        hand[pos2] = temp;
    }

    public void SwapTiles(int pos, Tiles newTile){
        Tiles oldTile = hand[pos];
        hand[pos] = newTile;

        // Swap the tile in their concealed hand
        // The concealedTiles array does not have the same indices as the hand array
        Tiles[] temp = new Tiles[concealedTiles.length - 1];
        int counter = 0;
        for(int i = 0; i < concealedTiles.length; i++){
            if(concealedTiles[i] != oldTile){
                temp[counter] = concealedTiles[i];
                counter++;
            }
        }
    }
    
    public int GetWind() {
        return wind;
    }
    public Tiles[][] GetExposedMelds() {
        return exposedMelds;
    }
    public Tiles[] GetHand() {
        return hand;
    }
}