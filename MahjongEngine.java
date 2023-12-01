public class MahjongEngine {

    // GLOBAL CONSTANTS: ERROR CODES
    public final static int NO_COMPLETE_MELD = -500;
    public final static int INCORRECT_POSITION = -501;
    public final static int UNSUCCESSFUL = -502;
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

    // DEVELOPED BY: EDWARD
    /* Constructor to generates a new game with randomized player hands */
    public MahjongEngine(){

        // Creating the wall with the appropriate amount of tiles
        wall = new Tiles[136];
        int insertIndex = 0; // Index counter of where the next tile should be inserted
        for(int i = 0; i<4; i++){ // Iterates 4 times because there are 4 sets of each tile in each suit
            for(int j = 0; j<9; j++){ // Iterates 9 times because Characcter, Dot & Bamboo suits have tiles from 1 to 9
                // Inserting each tile into the wall
                wall[insertIndex] = new Character(j+1);
                wall[insertIndex+1] = new Dot(j+1);;
                wall[insertIndex+2] = new Bamboo(j+1);
                insertIndex += 3;
            }

            // Inserting individual Honor tiles into the wall
            wall[insertIndex] = new Honors(Honors.EAST_HONOR);
            wall[insertIndex+1] = new Honors(Honors.SOUTH_HONOR);
            wall[insertIndex+2] = new Honors(Honors.WEST_HONOR);
            wall[insertIndex+3] = new Honors(Honors.NORTH_HONOR);
            wall[insertIndex+4] = new Honors(Honors.RED_HONOR);
            wall[insertIndex+5] = new Honors(Honors.WHITE_HONOR);
            wall[insertIndex+6] = new Honors(Honors.GREEN_HONOR);
            insertIndex += 7;
        }

        // Dealing 13 tiles to each player
        player1 = new Player(Player.EAST_WIND, dealTiles(13));
        player2 = new Player(Player.SOUTH_WIND, dealTiles(13));
        player3 = new Player(Player.WEST_WIND, dealTiles(13));
        player4 = new Player(Player.NORTH_WIND, dealTiles(13));
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
        Tiles[] newArr = new Tiles[arr.length-1]; // Make an array with 1 element less
        // Loops through arr and copies Tiles to newArr unless loop counter equals index
        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if(i != index){ // Checks if the index of the tile is the one that needs to remove
                newArr[counter] = arr[i]; // If not the tile to remove, put into the new array
                counter++;
            }
        }
        return newArr; 
    }

    // DEVELOPED BY: DANIEL
    /* Creates an array with a specified amount of spaces filled with Tiles dealt from the wall
    * @param numTiles      - The number of Tiles to be dealt
    * @return              - An array with the Tiles dealt from the wall*/
    private Tiles[] dealTiles(int numTiles){
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
    /* Find the possible tiles in a player's hand that can be used to make a Pong or Chow meld
    * @param player        - The player whose hand to check
    * @param meldType      - The type of meld to check
    * @return              - An array of Tiles that exist in the player's hand that can make the specified meld*/
    private Tiles[] meldOptions(Player player, int meldType){
        if(meldType == Tiles.PONG_MELD){
            return player.PossiblePongTiles(liveDiscard);
        } else if(meldType == Tiles.CHOW_MELD){
            return player.PossibleChowTiles(liveDiscard);
        }
        return null;
    }
    
    //================== PUBLIC METHODS ====================//

    // DEVELOPED BY: DANIEL
    /* Returns the players in an array who have not been disqualified
     * @return              - The Players array that are still playing (not eliminated)*/
    public Player[] CurrentlyPlaying(){
        int numPlaying = 0; // Counter for the number of players that are playing 
        for (int i = 0; i < players.length; i++) { //Loop that goes through all 4 players
            if(players[i].GetIsPlaying()){ // Checks if player still playing
                numPlaying++;
            }
        }

        // Make an array with the players that are still playing
        Player[] temp = new Player[numPlaying]; 
        int counter = 0;
        // Iterate through all 4 players and add them to new array if they are still playing
        for (int i = 0; i < players.length; i++) { 
            if(players[i].GetIsPlaying()){
                temp[counter] = players[i];
                counter++;
            }
        }
        
        return temp;
    }

    // DEVELOPED BY: DANIEL
    /* Draws a random tile from the wall
     * @return              - A random Tile object from the wall*/
    public void DrawTile(){
        int index = random(0, wall.length-1); // Take random index in wall array
        currPlayer.AddTile(wall[index]); // Add the Tile at the random index to the current player
        wall = remove(wall, index); // Remove Tile from the wall so it is unable to be taken from the wall again
    }

    // DEVELOPED BY: EDWARD
    /* Discard a tile from the players hand
     * @param player        - The player to discard a tile from
     * @param pos           - the position of the tile in the players hand
     * @return              - Returns 1 once discarding is done*/
    public int DiscardTile(Player player, int pos){
        if(pos >= 1 && pos <= currPlayer.GetConcealedTiles().length){ // Check if position is valid
            liveDiscard = player.DiscardTile(pos-1); // Remove tile from player and set this as the new liveDiscard
            return SUCCESSFUL;
        }
        return UNSUCCESSFUL;
    }

    // DEVELOPED BY: DANIEL
    /* Checks if the wall still has tiles or not, or all but one player are disqualified
     * @return              - EMPTY_WALL if the wall has no more tiles,
     *                        NO_PLAYERS if there are no more players,
     *                        otherwise -1 */
    public int IsGameOver(){
        if(wall.length == 0){ // Check wall empty
            return EMPTY_WALL;
        } else if(CurrentlyPlaying().length <= 1){ // Check amount of playing players
            return NO_PLAYERS;
        }
        return -1;
    }

    // DEVELOPED BY: EDWARD
    /* Player calls Mahjong and checks whether they have a winning hand. If mahjong is called and player does not have a winning hand, they will be disqualified. Otherwise, the player will win.
     * @param player        - The player that is calling Mahjong
     * @param lastTile      - The final tile added to form the player's winning hand. If specified, then player is taking a discarded tile. If lastTile is set as null, that means the final tile was a tile drawn from the wall so it's already in their hand
     * @return              - True if the players hand is a winning hand
     *                        False otherwise */
    public boolean Mahjong(Player player, Tiles lastTile){
        if(lastTile!=null){ // Add lastTile to player's hand if it not already drawn from wall
            player.AddTile(lastTile);
        }
        boolean isWin = player.CheckWinningHand(); // Check if player has winning hand
        if(isWin){
            if(lastTile!=null){
                liveDiscard = null; // Set liveDiscard to null if the discard tile was taken to win
            }
            return true;
        } else{ // Incorrect call --> Player eliminated
            int playerIndex = FindPlayerIndex(player); // Finds the players index in the player array
            players[playerIndex].Disqualify(); // Disqualify player
            return false;
        }
    }

    // DEVELOPED BY: EDWARD
    /* Finds the player index in the "players" array
     * @param player        - The player to find
     * @return              - the index of the player in the "players" array */
    public int FindPlayerIndex(Player player){
        // Find index of current player in players array (see MahjongEngine) to use in calculation 
        int playerIndex = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i] == player) playerIndex = i;
        }
        return playerIndex;
    }

    // DEVELOPED BY: EDWARD
    /* Finds the index of the player inside a specified player array 
     * @param player        - The player to find 
     * @param playerArr     - The array of players
     * @return              - the index of the player in the specified player array*/
    public int FindPlayerIndex(Player player, Player[] playerArr){
        // Find index of current player in players array (see MahjongEngine) to use in calculation 
        int playerIndex = 0;
        for(int i = 0; i < playerArr.length; i++){
            if(playerArr[i] == player) playerIndex = i;
        }
        return playerIndex;
    }

    // DEVELOPED BY: DANIEL
    /* Finds the index of the player inside a specified player array 
     * @param player        - The player to find 
     * @param playerArr     - The array of players
     * @return              - the index of the player in the specified player array */
    public void SwitchPlayer(){
        int currPlayerIndex = FindPlayerIndex(currPlayer); // Find the index of current lpayer
        
        // Keep shifting to next player in "players" array until a player who is not disqualified is found
        int newPlayerIndex = currPlayerIndex;
        do {
            newPlayerIndex = (newPlayerIndex+1)%4; // Get the next index in array
        } while(!players[newPlayerIndex].GetIsPlaying());
        currPlayer = players[newPlayerIndex]; // Set currPlayer
    }

    // DEVELOPED BY: EDWARD
    /* Set the value of the currPlayer
     * @param val - The player to set */
    public void SetCurrPlayer(Player player){
        currPlayer = player;
    }

    // DEVELOPED BY: EDWARD
    /* Returns an array of players that includes all currently playing players except one specified player 
     * @param player        - Player to exclude 
     * @return              - Array of playing players excluding the one player */
    public Player[] OtherPlayers(Player player){
        Player[] currentlyPlaying = CurrentlyPlaying();
        
        if(player.GetIsPlaying()){ // If specified player has not been elimintated
            // Loop through currentlyPlaying array and remove the specified player
            Player[] temp = new Player[currentlyPlaying.length-1];
            int counter = 0;
            for (int i = 0; i < currentlyPlaying.length; i++) {
                if(currentlyPlaying[i].GetWind() != player.GetWind()){
                    temp[counter] = currentlyPlaying[i];
                    counter++;
                }
            }
            return temp;
        } else{ // If specified player has been elimintated
            return currentlyPlaying;
        }
    }
    
    // DEVELOPED BY: DANIEL
    /* Gets the tile in the discard pile
    * @return              - Current discard tile object*/
    public Tiles GetLiveDiscard() {
        return liveDiscard;
    }

    // DEVELOPED BY: DANIEL
    /* Gets the player that is currently playing in their turn
     * @return              - The current player object */
    public Player GetCurrPlayer() {
        return currPlayer;
    }

    // DEVELOPED BY: DANIEL
    /* Gets the array of all 4 players, eliminated or not
     * @return              - The "players" array */
    public Player[] GetPlayers() {
        return players;
    }

    // DEVELOPED BY: EDWARD
    /* Checks if the tiles in the players hand are able to form a pong or chow meld
     * @param player              - The player trying to meld their tiles
     * @param playerOfDiscard     - The player that discarded the tile that will be used to meld
     * @param meldType            - Type of meld (see Tiles for values)
     * @return                    - NO_COMPLETE_MELD if the meld is not possible with player's tiles 
     *                              INCORRECT_POSITION if the player wants make a chow meld but is not to the right of the player that discarded the tile 
     *                              SUCCESSFUL if player's tiles are able to meld */
    public int Meldable(Player player, Player playerOfDiscard, int meldType){
        if(meldType == Tiles.PONG_MELD){ // Pong meld
            if(player.CheckMeld(liveDiscard) != Tiles.PONG_MELD && player.CheckMeld(liveDiscard) != Tiles.PONG_CHOW_MELD){ // Check if player CANNOT make the pong meld
                return NO_COMPLETE_MELD;
            }
        } else if(meldType == Tiles.CHOW_MELD){ // Chow meld
            // Get player and discarder index in current player array
            int discarderIndex = FindPlayerIndex(playerOfDiscard, CurrentlyPlaying());
            int playerIndex = FindPlayerIndex(player, CurrentlyPlaying());
            if(Math.floorMod(playerIndex-1, CurrentlyPlaying().length) != discarderIndex){ // Check whether discarder is not directly before player in play order
                return INCORRECT_POSITION;
            } else if(player.CheckMeld(liveDiscard) != Tiles.CHOW_MELD && player.CheckMeld(liveDiscard) != Tiles.PONG_CHOW_MELD){ // Check if player CANNOT make the chow meld
                return NO_COMPLETE_MELD;
            }
        }
        return SUCCESSFUL;
    }


    // DEVELOPED BY: EDWARD
    /* Checks if the tiles the player selects can form a Chow meld, and exposes the chow meld if successful
     * @param player              - The player that is calling Chow
     * @param pos1                - The position of the first tile in player's hand to use in the Chow meld
     * @param pos2                - The position of the second tile in player's hand to use in the Chow meld
     * @return                    - TRUE if the player can call chow 
     *                              FALSE otherwise  */
    public boolean Chow(Player player, int pos1, int pos2){
        Tiles[] meldOptions = meldOptions(player, Tiles.CHOW_MELD); // All tiles a player has that can make a Chow meld with the liveDiscard tile
        // Store the tiles at the specified positions
        Tiles tiles1 = player.GetConcealedTiles()[pos1-1];
        Tiles tiles2 = player.GetConcealedTiles()[pos2-1];

        // Iterate through meldOptions to test whether tiles1 and tiles2 are consecutive tiles in meldOptions
        for (int i = 0; i < meldOptions.length-1; i++) {

            // If tiles1 and tiles2 are adjacent (consecutive) in meldOptions
            if((meldOptions[i].Equals(tiles1) && meldOptions[i+1].Equals(tiles2)) || (meldOptions[i].Equals(tiles2) && meldOptions[i+1].Equals(tiles1))){
                player.ExposeMeld(liveDiscard, pos1-1, pos2-1); // Remove liveDiscard
                liveDiscard = null; // Remove liveDiscard
                return true;
            }
        }
        return false;
    }

    // DEVELOPED BY: DANIEL
    /* Checks if the tiles the player selects can form a Pong meld, and exposes the pong meld if successful
     * @param player              - The player that is calling Pong
     * @param pos1                - The position of the first tile in player's hand to use in the Pong meld
     * @param pos2                - The position of the second tile in player's hand to use in the Pong meld
     * @return                    - TRUE if the player can successfully call Pong 
     *                              FALSE otherwise  */
    public boolean Pong(Player player, int pos1, int pos2){
        Tiles[] meldOptions = meldOptions(player, Tiles.PONG_MELD); // All tiles a player has that can make a Pong meld with the liveDiscard tile
        // Store the tiles at the specified positions
        Tiles tiles1 = player.GetConcealedTiles()[pos1-1];
        Tiles tiles2 = player.GetConcealedTiles()[pos2-1];
        if(tiles1.Equals(meldOptions[0]) && tiles2.Equals(meldOptions[1])){ // If chosen tiles are in meldOptions (valid)
            player.ExposeMeld(liveDiscard, pos1-1, pos2-1); // Expose meld
            liveDiscard = null; // Remove liveDiscard
            return true;
        }
        return false;
    }
}