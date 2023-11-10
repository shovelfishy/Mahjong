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

    public Player(int wind, Tiles[] hand){
        this.wind = wind;
        this.hand = hand;
    }

    private Tiles[] copyTiles(Tiles[] list){
        Tiles[] temp = new Tiles[list.length];
        for(int i = 0; i < list.length; i++){
            temp[i] = list[i];
        }
        return temp;
    }

    // public void SwapTiles(int pos, Tiles newTile){
    //     Tiles[][] temp = new Tiles[completedMelds.length][];

    // }

    public int CheckMelds(Tiles searchTile){
        Tiles[] identicalTiles = {searchTile};
        Tiles[] conseqTiles = {searchTile};
        int meldType = 0;
        for(int i = 0; i < hand.length; i++){
            if(hand[i].GetSuit() == searchTile.GetSuit()){
                int s
                if(hand[i].GetNum == searchTile.GetNum() - 1 )
                if(hand[i].GetNum() == searchTile.GetNum()){

                }
            }
        }

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