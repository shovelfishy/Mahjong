import java.util.Arrays;

public class MahjongEngine {

    // GLOBAL CONSTANTS: ERROR CODES
    public final static int NO_COMPLETE_MELD = -500;
    public final static int INCORRECT_POSITION = -501;
    public final static int SUCCESSFUL = 502;
    public final static int EMPTY_WALL = 700;
    public final static int NO_PLAYERS = 701;

    // Private variables
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player[] players;
    private Tiles[] wall;
    private Tiles liveDiscard;
    private Player currPlayer;
    

    //=================== CONSTRUCTOR =====================//
    public MahjongEngine(){
        wall = new Tiles[136];

        int insertIndex = 0;
        for(int i = 0; i<4; i++){
            for(int j = 0; j<9; j++){
                Character charTile = new Character(j+1);
                Dot dotTile = new Dot(j+1);
                Bamboo bambooTile = new Bamboo(j+1);

                wall[insertIndex] = charTile;
                wall[insertIndex+1] = dotTile;
                wall[insertIndex+2] = bambooTile;
                insertIndex += 3;
            }

            Honors eastHonor = new Honors(Honors.EAST_HONOR);
            Honors southHonor = new Honors(Honors.SOUTH_HONOR);
            Honors westHonor = new Honors(Honors.WEST_HONOR);
            Honors northHonor = new Honors(Honors.NORTH_HONOR);
            Honors redHonor = new Honors(Honors.RED_HONOR);
            Honors whiteHonor = new Honors(Honors.WHITE_HONOR);
            Honors greenHonor = new Honors(Honors.GREEN_HONOR);
            wall[insertIndex] = eastHonor;
            wall[insertIndex+1] = southHonor;
            wall[insertIndex+2] = westHonor;
            wall[insertIndex+3] = northHonor;
            wall[insertIndex+4] = redHonor;
            wall[insertIndex+5] = whiteHonor;
            wall[insertIndex+6] = greenHonor;
            insertIndex += 7;
        }

        player1 = new Player(Player.EAST_WIND, DealTiles(13));
        player2 = new Player(Player.SOUTH_WIND, DealTiles(13));
        player3 = new Player(Player.WEST_WIND, DealTiles(13));
        player4 = new Player(Player.NORTH_WIND, DealTiles(13));
        currPlayer = player1;
        players = new Player[]{player1, player2, player3, player4};
    }


    //================== PRIVATE METHODS ====================//

    // DEVELOPED BY: DANIEL
    /* Generates a random number between a range
    * @param min      - Lower bound of the random number
    * @param max      - Upper bound of the random number
    * @return         - Random number between the min and max number*/
    private int random(int min, int max){
        return (int)Math.round(Math.random()*(max-min)+min);
    } 

    // DEVELOPED BY: DANIEL
    /* Removes the Tile stored at an index in a Tiles array
    * @param arr        - The Tiles array from which to remove the Tile from
    * @param index      - Index of Tile to be removed
    * @return           - New array with the Tile at specified index removed*/
    private Tiles[] remove(Tiles[] arr, int index){
        Tiles[] newArr = new Tiles[arr.length-1];

        // Loops through arr and copies Tiles to newArr unless loop counter equals index
        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if(i != index){
                newArr[counter] = arr[i];
                counter++;
            }
        }
        return newArr; 
    }

    // DEVELOPED BY: DANIEL
    /* Creates an array with a specified amount of spaces filled with Tiles dealt from the wall
    * @param numTiles      - The number of Tiles to be dealt
    * @return              - An array with the Tiles dealt from the wall*/
    private Tiles[] DealTiles(int numTiles){
        Tiles[] hand = new Tiles[numTiles];

        // Add a random tile from the wall each iteration
        for(int i=0; i < numTiles; i++){
            int index = random(0, wall.length-1); // Get a random index between 0 and the last index of wall array
            hand[i] = wall[index]; // Add Tile from wall to the hand
            wall = remove(wall, index); // Remove Tile from the wall
        }
        return hand;
    }

    // DEVELOPED BY: EDWARD
    /* Creates an array with a specified amount of spaces filled with Tiles dealt from the wall
    * @param numTiles      - The number of Tiles to be dealt
    * @return              - An array with the Tiles dealt from the wall*/
    private Tiles[] meldOptions(Player player, int meldType){
        if(meldType == Tiles.PONG_MELD){
            return player.PossiblePongTiles(liveDiscard);
        } else if(meldType == Tiles.CHOW_MELD){
            return player.PossibleChowTiles(liveDiscard);
        }
        return null;
    }
    
    //================== PUBLIC METHODS ====================//

    public Player[] CurrentlyPlaying(){
        int numPlaying = 0;
        for (int i = 0; i < players.length; i++) {
            if(players[i].GetIsPlaying()){
                numPlaying++;
            }
        }

        Player[] temp = new Player[numPlaying];
        int counter = 0;
        for (int i = 0; i < players.length; i++) {
            if(players[i].GetIsPlaying()){
                temp[counter] = players[i];
                counter++;
            }
        }
        
        return temp;
    }

    public Tiles DrawTile(){
        int index = random(0, wall.length-1);
        Tiles tile = wall[index];
        wall = remove(wall, index);
        currPlayer.AddTile(tile);
        return tile;
    }

    public int DiscardTile(Player player, int pos){
        liveDiscard = player.DiscardTile(pos-1);
        return 1;
    }

    

    public int IsGameOver(){
        if(wall.length == 0){
            return EMPTY_WALL;
        } else if(CurrentlyPlaying().length <= 1){
            return NO_PLAYERS;
        }
        return -1;
    }

    public boolean Mahjong(Player player, Tiles lastTile){
        if(lastTile!=null){
            player.AddTile(lastTile);
        }
        boolean isWin = player.CheckWinningHand();
        if(isWin){
            return true;
        } else{
            int playerIndex = FindPlayerIndex(player);
            players[playerIndex].Disqualify();
            return false;
        }
        
    }

    public int FindPlayerIndex(Player player){
        // Find index of current player in players array (see MahjongEngine) to use in calculation 
        int playerIndex = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i] == player) playerIndex = i;
        }
        return playerIndex;
    }

    public int FindPlayerIndex(Player player, Player[] playerArr){
        // Find index of current player in players array (see MahjongEngine) to use in calculation 
        int playerIndex = 0;
        for(int i = 0; i < playerArr.length; i++){
            if(playerArr[i] == player) playerIndex = i;
        }
        return playerIndex;
    }

    public void SwitchPlayer(){
        int currPlayerIndex = FindPlayerIndex(currPlayer);
        int newPlayerIndex = currPlayerIndex;
        do {
            newPlayerIndex = (newPlayerIndex+1)%4;
        } while(!players[newPlayerIndex].GetIsPlaying());
        currPlayer = players[newPlayerIndex];
    }

    public void SwitchPlayer(Player player){
        currPlayer = player;
    }

    public Player[] OtherPlayers(Player player){
        Player[] currentlyPlaying = CurrentlyPlaying();
        
        if(player.GetIsPlaying()){
            Player[] temp = new Player[currentlyPlaying.length-1];
            int counter = 0;
            for (int i = 0; i < currentlyPlaying.length; i++) {
                if(currentlyPlaying[i].GetWind() != player.GetWind()){
                    temp[counter] = currentlyPlaying[i];
                    counter++;
                }
            }
            return temp;
        } else{
            return currentlyPlaying;
        }
    }

    public Tiles GetLiveDiscard() {
        return liveDiscard;
    }

    public Player GetCurrPlayer() {
        return currPlayer;
    }

    public Player[] GetPlayers() {
        return players;
    }

    public int Meldable(Player player, Player playerOfDiscard, int meldType){
        if(meldType == Tiles.PONG_MELD){
            if(player.CheckMeld(liveDiscard) != Tiles.PONG_MELD && player.CheckMeld(liveDiscard) != Tiles.PONG_CHOW_MELD){
                return NO_COMPLETE_MELD;
            }
        } else if(meldType == Tiles.CHOW_MELD){
            int discarderIndex = FindPlayerIndex(playerOfDiscard, CurrentlyPlaying());
            int playerIndex = FindPlayerIndex(player, CurrentlyPlaying());
            if(Math.floorMod(playerIndex-1, CurrentlyPlaying().length) != discarderIndex){
                return INCORRECT_POSITION;
            } else if(player.CheckMeld(liveDiscard) != Tiles.CHOW_MELD && player.CheckMeld(liveDiscard) != Tiles.PONG_CHOW_MELD){
                return NO_COMPLETE_MELD;
            }
        }
        return SUCCESSFUL;
    }

    public boolean Chow(Player player, int pos1, int pos2){
        Tiles[] meldOptions = meldOptions(player, Tiles.CHOW_MELD);
        Tiles tiles1 = player.GetConcealedTiles()[pos1-1];
        Tiles tiles2 = player.GetConcealedTiles()[pos2-1];
        for (int i = 0; i < meldOptions.length-1; i++) {
            if((meldOptions[i].Equals(tiles1) && meldOptions[i+1].Equals(tiles2)) || (meldOptions[i].Equals(tiles2) && meldOptions[i+1].Equals(tiles1))){
                player.ExposeMeld(liveDiscard, pos1-1, pos2-1);
                liveDiscard = null;
                return true;
            }
        }
        return false;
    }

    public boolean Pong(Player player, int pos1, int pos2){
        Tiles[] meldOptions = meldOptions(player, Tiles.PONG_MELD);
        Tiles tiles1 = player.GetConcealedTiles()[pos1-1];
        Tiles tiles2 = player.GetConcealedTiles()[pos2-1];
        if(tiles1.Equals(meldOptions[0]) && tiles2.Equals(meldOptions[1])){
            player.ExposeMeld(liveDiscard, pos1-1, pos2-1);
            liveDiscard = null;
            return true;
        }
        return false;
    }
}