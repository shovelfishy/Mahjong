public class Player {

    // GLOBAL CONSTANTS: PLAYER SEAT WINDS
    public final static int EAST_WIND = 100;
    public final static int SOUTH_WIND = 101;
    public final static int WEST_WIND = 102;
    public final static int NORTH_WIND = 103;

    // Private variables
    private int wind;
    private Tiles[][] exposedMelds;
    private Tiles[] concealedTiles;
    private boolean isPlaying;


    //=================== CONSTRUCTOR =====================//
    // DEVELOPED BY: EDWARD
    /* Consturct new Player object
    * @param wind      - Player's seat wind
    * @param hand      - Player's starting hand */
    public Player(int wind, Tiles[] hand){
        this.wind = wind;
        this.concealedTiles = hand;
        this.exposedMelds = new Tiles[0][]; // Player starts with no exposed melds
        this.isPlaying = true;
    }


    //================== PRIVATE METHODS ====================//

    // DEVELOPED BY: EDWARD
    /* Adds an array of tiles to a 2D array of tiles
    * @param arr       - the 2d array of tiles to add to
    * @param tiles     - The Tiles array to add  
    * @return          - A new array with the Tiles array added */
    private Tiles[][] add(Tiles[][] arr, Tiles[] tiles){
        Tiles[][] temp = new Tiles[arr.length+1][];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = tiles;
        return temp;
    }

    // DEVELOPED BY: EDWARD
    /* Adds a tile to aN array of tiles
    * @param arr       - the array of tiles to add to
    * @param tiles     - The tile to add  
    * @return          - A new array with the tile added */
    private Tiles[] add(Tiles[] arr, Tiles newTile){
        Tiles[] temp = new Tiles[arr.length+1];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = newTile;
        return temp;
    }

    // DEVELOPED BY: DANIEL
    /* Remove a tile at an index from an array of tiles
    * @param arr       - the array of tiles to remove from
    * @param pos       - The index to remove tile from
    * @return          - A new array with the Tile removed */
    private Tiles[] remove(Tiles[] arr, int pos){
        Tiles[] temp = new Tiles[arr.length - 1];
        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if(i != pos){ // If index is not the target index then add tile to temp array
                temp[counter] = arr[i];
                counter++;
            }
        }
        return temp;
    }

    // DEVELOPED BY: EDWARD
    /* Removes the first occurance of each element in one array from another array
    * @param arr               - Array of Tiles to remove tiles from 
    * @param tileToRemove      - Array of Tiles to be removed
    * @return                  - A new array with the Tiles removed*/
    private Tiles[] remove(Tiles[] arr, Tiles[] tilesToRemove){

        // Make a copy of the parameter arr to avoid mutating the orignal array 
        Tiles[] temp = new Tiles[arr.length];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        arr = temp;
        
        // Iterate through arr and sets the first occurence of any element 
        // from tilesToRemove to null
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if(counter == tilesToRemove.length) break;
            // Check that tile has not already been removed and whether
            // it is a tile that should be removed
            if(arr[i] != null && arr[i].Equals(tilesToRemove[counter])){ 
                arr[i] = null;
                counter++;
            }
        }

        // Removes all the null elements in arr
        temp = new Tiles[arr.length-tilesToRemove.length];
        counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != null){ // If element is not a null element then add it to the temp array
                temp[counter] = arr[i];
                counter++;
            }
        }
        return temp;
    }

    // DEVELOPED BY: EDWARD
    /* Sort an array of tiles in ascending order of their numerical value
     * @param arr               - Array of Tiles to sort */
    private void sort(Tiles[] arr){
        for(int i = 1; i < arr.length; i++){
            Tiles currElement = arr[i];
            int currElementNum = arr[i].GetNum();
            int j = i-1;
            while(j >= 0 && currElementNum < arr[j].GetNum()){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = currElement;
        }
    }

    // DEVELOPED BY: EDWARD
    /* Finds all tiles in an array whose values differ by one or two from the target number
    * @param arr               - Array of Tiles to check through 
    * @param num               - The target number as a baseline
    * @return                  - A sorted array with Tiles whose values differ by one or two */
    private Tiles[] diffBy2(Tiles[] arr, int num){
        Tiles[] diffBy2 = new Tiles[0];
        for(int i=0; i<arr.length;i++){ // Loop through all Tiles in arr
            int difference = Math.abs(num - arr[i].GetNum()); // Check difference between tile number and target num
            if(difference == 1 || difference == 2){ // Add tile to array if difference is 1 or 2
                diffBy2 = add(diffBy2, arr[i]);
           }
        }
        if(diffBy2.length < 2){ // Check whether new array length at least 2
            return new Tiles[0];
        }
        sort(diffBy2); // Sort array
        return diffBy2;
    }

    // DEVELOPED BY: EDWARD
    /* Finds all combinations of 3 consecutive tiles in an array based on a target tile
    * @param arr               - Array of Tiles to check through 
    * @param tilesToSearch               - The target tile as a baseline
    * @return                  - A 2D array of all combination of 3 consecutive Tiles */
    private Tiles[][] threeConsecutiveTiles(Tiles[] arr, Tiles tileToSearch){
        int num = tileToSearch.GetNum(); 
        Tiles[] diffBy2Arr = diffBy2(arr, num);
        Tiles[][] consequetiveSets = new Tiles[0][3]; 

        // Iterate through diffBy2Arr and check all combinations
        for(int i = 0; i < diffBy2Arr.length-1; i++){

            // Check all the different combinations of 3 consecutive tiles
            // Ie. Target tile number: 3 --> {1,2,3}, {2,3,4}, {3,4,5}
            if(diffBy2Arr[i+1].GetNum()-diffBy2Arr[i].GetNum() == 1){
                if(diffBy2Arr[i+1].GetNum() < num){
                    consequetiveSets = add(consequetiveSets, new Tiles[]{diffBy2Arr[i], diffBy2Arr[i+1], tileToSearch});
                } else if(diffBy2Arr[i].GetNum() > num){
                    consequetiveSets = add(consequetiveSets, new Tiles[]{tileToSearch, diffBy2Arr[i], diffBy2Arr[i+1]});
                }
            }
            if(diffBy2Arr[i+1].GetNum()-diffBy2Arr[i].GetNum() == 2){
                consequetiveSets = add(consequetiveSets, new Tiles[]{diffBy2Arr[i], tileToSearch, diffBy2Arr[i+1]});
            }
        }
        return consequetiveSets;
    }

    // DEVELOPED BY: EDWARD
    /* Check if an array of tiles contains a pair
    * @param tilesArr               - Array of Tiles to check through 
    * @param tile               - The target tile to use for checking
    * @return                  - A Tiles array of a pair of tiles */
    private Tiles[] checkEye(Tiles[] tilesArr, Tiles tile){
            Tiles[] temp = {};
            for(int i = 0; i < tilesArr.length; i++) { // Add identical tiles to temp array
               if(tilesArr[i].Equals(tile) && temp.length < 2){
                    temp = add(temp, tilesArr[i]);
               }
            }
            if(temp.length == 2){ // Return array if there are 2 tiles
                return temp;
            }
            return null;
    }


    // DEVELOPED BY: EDWARD
    /* Check if an array contains only melds (recursion)
    * @param arr               - Array of Tiles to check through 
    * @param meldType               - meldType to check for
    * @param index               - Index of tile to check meld for
    * @return                  - True if the array is all melds, False otherwise */
    private boolean isHandAllMelds(Tiles[] arr, int meldType, int index){
        if(index >= arr.length){ // Base case
            return arr.length == 0;
        } else if(meldType == Tiles.CHOW_MELD){
            Tiles[][] chowMelds = CheckMeld(arr, arr[index], Tiles.CHOW_MELD);
            for (int i = 0; chowMelds!=null && i < chowMelds.length; i++) {
                if(isHandAllMelds(remove(arr, chowMelds[i]), Tiles.CHOW_MELD, 0)){
                    return true;
                }
                if(isHandAllMelds(remove(arr, chowMelds[i]), Tiles.PONG_MELD, 0)){
                    return true;
                }
            }
            if(isHandAllMelds(arr, Tiles.CHOW_MELD, index+1)){
                return true;
            }
            if(isHandAllMelds(arr, Tiles.PONG_MELD, index)){
                return true;
            }
        } else if(meldType == Tiles.PONG_MELD){
            Tiles[][] pongMelds = CheckMeld(arr, arr[index], Tiles.PONG_MELD);
            if(pongMelds != null){
                return isHandAllMelds(remove(arr, pongMelds[0]), Tiles.PONG_MELD, index);
            }
        }
        return false;
    }



    //================== PUBLIC METHODS ====================//
    // DEVELOPED BY: DANIEL
    /* Displays the player wind 
    * @return         - returns the string of the players wind  */
    public String DisplayPlayer(){
        switch(wind){
            case Player.EAST_WIND:
                return "EAST PLAYER";
            case Player.SOUTH_WIND:
                return "SOUTH PLAYER";
            case Player.WEST_WIND:
                return "WEST PLAYER";
            case Player.NORTH_WIND:
                return "NORTH PLAYER";
            default:
                return null;
        }
    }

    // DEVELOPED BY: EDWARD
    /* The player can arrange their tiles by moving tiles.
    * @param pos1     - the position of the tile that is wanted to swap
    * @param pos2     - The position of the tile to move first tile to  */
    public void ArrangeHand(int pos1, int pos2){
        Tiles temp = concealedTiles[pos1];

        // Shift tiles left or right in the array depending on where the initial location was relative to the target location
        if(pos1 > pos2){
            for(int i = pos1; i > pos2; i--){
                concealedTiles[i] = concealedTiles[i-1]; // Shift tiles right
            }
        } else{
            for(int i = pos1; i < pos2; i++){
                concealedTiles[i] = concealedTiles[i+1]; // Shift tiles left
            }
        }
        concealedTiles[pos2] = temp;
    }

    // DEVELOPED BY: EDWARD
    /* Austomaically sort player's hand based on suit and asecending numerical value */
    public void AutoSortHand(){
        Tiles[] temp = new Tiles[0];
        int[] suits = {Tiles.BAMBOO, Tiles.CHARACTER, Tiles.DOT, Tiles.HONORS};

        // Iterate through all suits
        for(int i = 0; i < suits.length; i++){
            // Seperate all of one suit into an array
            Tiles[] singleSuitTiles = new Tiles[0];
            for (int j = 0; j < concealedTiles.length; j++) {
                if(concealedTiles[j].GetSuit() == suits[i]){
                    singleSuitTiles = add(singleSuitTiles, concealedTiles[j]);
                }
            }
            sort(singleSuitTiles); // Sort all tiles of that suit
            for (int j = 0; j < singleSuitTiles.length; j++) { // Add the sorted tiles into temp array
                temp = add(temp, singleSuitTiles[j]); 
            }
        }
        concealedTiles = temp; // update hand
    }

    // DEVELOPED BY: DANIEL
    /* Adds a tile to the players hand
    * @param newTile        - the tile object that is added to the players hand */
    public void AddTile(Tiles newTile){
        concealedTiles = add(concealedTiles, newTile);
    }

    // DEVELOPED BY: DANIEL
    /* Discards a chosen tile from the players hand
    * @param pos      - the position of the tile that is to be discarded
    * @returns        - the tile object that is discarded */
    public Tiles DiscardTile(int pos){
        Tiles oldTile = concealedTiles[pos];
        concealedTiles = remove(concealedTiles, pos);

        return oldTile;
    }

    // DEVELOPED BY: DANIEL
    /* Deletes the disqualified player's melds and tiles. Then removes the player from the game. */
    public void Disqualify(){
        exposedMelds = new Tiles[0][0];
        concealedTiles = new Tiles[0];
        isPlaying = false;
    }

    // DEVELOPED BY: DANIEL
    /* Checks for all tiles that can be used for chow meld by finding the tiles with the same suit and consequtive numerical
    * @param  tile     - the tile to make a chow meld with
    * @return          - The tiles array with the tiles that can have a chance to form a complete chow meld */
    public Tiles[] PossibleChowTiles(Tiles tile){
        // Isolate all tiles in player's hand that have identical suit has target tile
        Tiles[] sameSuit = {};
        for (int i = 0; i < concealedTiles.length; i++) {
            if(concealedTiles[i].GetSuit() == tile.GetSuit()){ // Check for same suit
                sameSuit = add(sameSuit, concealedTiles[i]);
            }
        }

        Tiles[] possibleTiles = diffBy2(sameSuit, tile.GetNum()); // Get all the tiles from player's hand that could participate in a Chow meld
        Tiles[] temp = new Tiles[0];
        // Remove duplicate Tiles in possibleTiles
        for (int i = 0; i < possibleTiles.length-1; i++) {
            if(!possibleTiles[i].Equals(possibleTiles[i+1])){ // Check whether the current element is the same as the next one in the array
                temp = add(temp, possibleTiles[i]); // Add tile to array only if it's not a duplicate of next Tile
            }
        }
        temp = add(temp, possibleTiles[possibleTiles.length-1]); // Add the last Tile in possibleTiles to temp array
        return temp;
    }

    // DEVELOPED BY: DANIEL
    /* Finds the tiles with the exact same numerical and suit
    * @param pos1     - the tile object to check if it can complete a pong meld
    * @return         - the possible tiles that can possibly complete a pong meld  */
    public Tiles[] PossiblePongTiles(Tiles tile){
        Tiles[] temp = {};
        for (int i = 0; i < concealedTiles.length; i++) { // Get all tiles in player's hand identical to target tile
            if(concealedTiles[i].Equals(tile)){
                temp = add(temp, concealedTiles[i]); // Add to temp array
            }
        }
        return temp;
    }
   
    // DEVELOPED BY: EDWARD
    /* Checks which meld a tile can complete
    * @param tile     - The tile object to check
    * @return         - PONG_CHOW_MELD if it completes either a pong or a chow meld
    *                 - PONG_MELD if the tile completes a pong meld
    *                 - CHOW_MELD if it completes a Chow meld
    *                 - NO_MELD if it does not complete any meld */
    public int CheckMeld(Tiles tile){
        int identicalTiles = 0;
        
        // Seperate player's hand into tiles with same suit as target tile
        Tiles[] sameSuit = {};
        for(int i = 0; i < concealedTiles.length; i++){
            if(concealedTiles[i].GetSuit() == tile.GetSuit()){
                if(concealedTiles[i].GetNum() == tile.GetNum()){
                    identicalTiles++;
                }
                sameSuit = add(sameSuit, concealedTiles[i]);   
            }
        }

        Tiles[][] chowNumCombinations = threeConsecutiveTiles(sameSuit, tile); // Get all combinations of consecutive tiles
        if(identicalTiles >= 2 && chowNumCombinations.length > 0){ // If at least 2 identical tiles and 1 consecutive set of 3 tiles exist
            return Tiles.PONG_CHOW_MELD;
        } else if(identicalTiles >= 2){ // At least 2 identical tiles
            return Tiles.PONG_MELD;
        } else if(chowNumCombinations.length > 0){ // At least 1 consecutive set of 3 tiles
            return Tiles.CHOW_MELD;
        }

        return Tiles.NO_MELD;
    }

    // DEVELOPED BY: EDWARD
    /* Checks wether the tile will complete 
    * @param tilesArr   - An array of tiles to check through
    * @param tile       - the tile object to check
    * @param meldType   - type of meld 
    * @returns          - returns a 2d array of all the possible combinations of a certain meld type */
    public Tiles[][] CheckMeld(Tiles[] tilesArr, Tiles tile, int meldType){
        if(meldType == Tiles.PONG_MELD){ // Pong meld
            Tiles[] temp = {};
            for(int i = 0; i < tilesArr.length; i++) { // Get identical tiles from array
               if(tilesArr[i].Equals(tile) && temp.length < 3){
                    temp = add(temp, tilesArr[i]);
               }
            }
            if(temp.length == 3){ // Return only if there are 3 tiles to make a meld with
                return new Tiles[][]{temp};
            }
        } else if(meldType == Tiles.CHOW_MELD){ // Chow meld
            // Seperate tiles into individual suits
            Tiles[] sameSuit = {};
            for(int i = 0; i < tilesArr.length; i++){
                if(tilesArr[i].GetSuit() == tile.GetSuit()){
                    sameSuit = add(sameSuit, tilesArr[i]);   
                }
            }
            Tiles[][] chowMelds = threeConsecutiveTiles(sameSuit, tile); // Find all possibile chow melds to make
            if(chowMelds.length!=0){
                return chowMelds;
            }
        }
        return null;
    }

    // DEVELOPED BY: EDWARD
    /* Displays the completed meld and removes the tiles from the players concealed hand.
    * @param tile     - the tile that completes the meld 
    * @param pos1     - the pos of 1st tile that the player wants to expose that completes the meld
    * @param pos2     - the pos of 2nd tile that the player wants to expose that completes the meld  */
    public void ExposeMeld(Tiles tile, int pos1, int pos2){
        Tiles[] meld = {tile, concealedTiles[pos1], concealedTiles[pos2]};

        // Ensures items are removed backwards from the array
        // Prevents error when removing an index affects the length of the array 
        int smallerIndex = Math.min(pos1, pos2);
        int biggestIndex = Math.max(pos1, pos2);
        concealedTiles = remove(concealedTiles, biggestIndex);
        concealedTiles = remove(concealedTiles, smallerIndex);
        sort(meld);
        exposedMelds = add(exposedMelds, meld);
    }

    // DEVELOPED BY: EDWARD
    /* Checks if the player has a winning hand
    * @return    - true if the player has a possible winning hand
    *              false otherwise */
    public boolean CheckWinningHand(){
        for (int i = 0; i < concealedTiles.length; i++) { // Checks all combinations of tiles
            Tiles[] eye = checkEye(concealedTiles, concealedTiles[i]); // Find an eye 
            if(eye!=null){ // If there is an eye
                Tiles[] restOfTiles = remove(concealedTiles, eye); // Get an array with all tiles but the eye tiles
                boolean IsAllMelds = isHandAllMelds(restOfTiles, Tiles.CHOW_MELD, 0); // Check if all tiles are melds
                if(IsAllMelds){
                    return true;
                }
            }
        }
        return false;
    }

    // DEVELOPED BY: DANIEL
    /* Gets the player's wind
    * @returns      - wind int*/
    public int GetWind() {
        return wind;
    }
    // DEVELOPED BY: DANIEL
    /* Gets the exposed and completed melds
    * @returns      - 2d array of the completed melds*/
    public Tiles[][] GetExposedMelds() {
        return exposedMelds;
    }
    // DEVELOPED BY: DANIEL
    /* Gets the player's concealed tiles
    * @returns      - array of concealed tiles*/
    public Tiles[] GetConcealedTiles() {
        return concealedTiles;
    }
    // DEVELOPED BY: DANIEL
    /* Gets if the player is still playing
    * @returns      - true if the player is still currently in the game
    *               - false otherwise 
    */
    public boolean GetIsPlaying() {
        return isPlaying;
    }
}






