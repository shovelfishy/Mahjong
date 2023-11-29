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

    private Tiles[][] add(Tiles[][] arr, Tiles[] tiles){
        Tiles[][] temp = new Tiles[arr.length+1][];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = tiles;
        return temp;
    }

    private Tiles[] add(Tiles[] arr, Tiles newTile){
        Tiles[] temp = new Tiles[arr.length+1];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = newTile;
        return temp;
    }

    private Tiles[] remove(Tiles[] arr, int pos){
        // Swap the tile in their concealed hand
        // The concealedTiles array does not have the same indices as the hand array
        Tiles[] temp = new Tiles[arr.length - 1];
        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if(i != pos){
                temp[counter] = arr[i];
                counter++;
            }
        }
        return temp;
    }

    private Tiles[] remove(Tiles[] arr, Tiles[] tilesToRemove){
        Tiles[] temp = new Tiles[arr.length-tilesToRemove.length];
        arr = copyTiles(arr);
        for (int i = 0; i < tilesToRemove.length; i++) {
            Tiles currElement = tilesToRemove[i]; 
            for (int j = 0; j < arr.length; j++) {
                if(arr[j] != null && arr[j].GetSuit() == currElement.GetSuit() && arr[j].GetNum() == currElement.GetNum()){
                    arr[j] = null;
                    break;
                }
            }
        }
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != null){
                temp[counter] = arr[i];
                counter++;
            }
        }
        return temp;
    }


    /* JAVA DOC DESC 
    * @param PARAM NAME      - 
    * @return         -  */
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

    public Player(int wind, Tiles[] hand){
        this.wind = wind;
        this.concealedTiles = hand;
        this.exposedMelds = new Tiles[0][];
        this.isPlaying = true;
    }

    public void ArrangeHand(int pos1, int pos2){
        Tiles temp = concealedTiles[pos1];

        if(pos1 > pos2){
            for(int i = pos1; i > pos2; i--){
                concealedTiles[i] = concealedTiles[i-1];
            }
        } else{
            for(int i = pos1; i < pos2; i++){
                concealedTiles[i] = concealedTiles[i+1];
            }
        }
        concealedTiles[pos2] = temp;
    }

    public void AutoSortHand(){
        Tiles[] temp = new Tiles[0];
        int[] suits = {Tiles.BAMBOO, Tiles.CHARACTER, Tiles.DOT, Tiles.HONORS};
        for(int i = 0; i < suits.length; i++){
            Tiles[] singleSuitTiles = new Tiles[0];
            for (int j = 0; j < concealedTiles.length; j++) {
                if(concealedTiles[j].GetSuit() == suits[i]){
                    singleSuitTiles = add(singleSuitTiles, concealedTiles[j]);
                }
            }
            Sort(singleSuitTiles);
            for (int j = 0; j < singleSuitTiles.length; j++) {
                temp = add(temp, singleSuitTiles[j]);
            }
        }
        concealedTiles = temp;
    }


    public void AddTile(Tiles newTile){
        concealedTiles = add(concealedTiles, newTile);
    }

    public Tiles DiscardTile(int pos){
        Tiles oldTile = concealedTiles[pos];
        concealedTiles = remove(concealedTiles, pos);

        return oldTile;
    }
    
    public int GetWind() {
        return wind;
    }
    public Tiles[][] GetExposedMelds() {
        return exposedMelds;
    }
    public Tiles[] GetConcealedTiles() {
        return concealedTiles;
    }
    public boolean GetIsPlaying() {
        return isPlaying;
    }

    public void Disqualify(){
        exposedMelds = new Tiles[0][0];
        concealedTiles = new Tiles[0];
        isPlaying = false;
    }

    public void Sort(Tiles[] arr){
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

    public Tiles[] PossibleChowTiles(Tiles tile){
        Tiles[] sameSuit = {};
        for (int i = 0; i < concealedTiles.length; i++) {
            if(concealedTiles[i].GetSuit() == tile.GetSuit()){
                sameSuit = add(sameSuit, concealedTiles[i]);
            }
        }

        Tiles[] possibleTiles = DiffBy2(sameSuit, tile.GetNum());
        Tiles[] temp = new Tiles[0];
        // Remove duplicate Tiles in possibleTiles
        for (int i = 0; i < possibleTiles.length-1; i++) {
            if(!possibleTiles[i].Equals(possibleTiles[i+1])){
                temp = add(temp, possibleTiles[i]);
            }
        }
        temp = add(temp, possibleTiles[possibleTiles.length-1]); // Add the last Tile in possibleTiles to temp array
        return temp;
    }

    public Tiles[] PossiblePongTiles(Tiles tile){
        Tiles[] temp = {};
        for (int i = 0; i < concealedTiles.length; i++) {
            if(concealedTiles[i].Equals(tile)){
                temp = add(temp, concealedTiles[i]);
            }
        }
        return temp;
    }


    public Tiles[] DiffBy2(Tiles[] arr, int num){
        Tiles[] diffBy2 = new Tiles[0];
        for(int i=0; i<arr.length;i++){
            int difference = Math.abs(num - arr[i].GetNum());
            if(difference == 1 || difference == 2){
                diffBy2 = add(diffBy2, arr[i]);
           }
        }
        if(diffBy2.length < 2){
            return new Tiles[0];
        }
        Sort(diffBy2);
        return diffBy2;
    }

    private Tiles[] copyTiles(Tiles[] list){
        Tiles[] temp = new Tiles[list.length];
        for(int i = 0; i < list.length; i++){
            temp[i] = list[i];
        }
        return temp;
    }
   
    public Tiles[][] CheckConsecutive(Tiles[] arr, Tiles tileToSearch){
        int num = tileToSearch.GetNum();
        Tiles[] diffBy2Arr = DiffBy2(arr, num);
        Tiles[][] consequetiveSets = new Tiles[0][3]; 
        for(int i = 0; i < diffBy2Arr.length-1; i++){
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

    public int CheckMeld(Tiles tile){
        int identicalTiles = 0;
        
        Tiles[] sameSuit = {};
        for(int i = 0; i < concealedTiles.length; i++){
            if(concealedTiles[i].GetSuit() == tile.GetSuit()){
                if(concealedTiles[i].GetNum() == tile.GetNum()){
                    identicalTiles++;
                }
                sameSuit = add(sameSuit, concealedTiles[i]);   
            }
        }

        Tiles[][] chowNumCombinations = CheckConsecutive(sameSuit, tile);
        if(identicalTiles >= 2 && chowNumCombinations.length > 0){
            return Tiles.PONG_CHOW_MELD;
        } else if(identicalTiles >= 2){
            return Tiles.PONG_MELD;
        } else if(chowNumCombinations.length > 0){
            return Tiles.CHOW_MELD;
        }

        return Tiles.NO_MELD;
    }

    public void ExposeMeld(Tiles tile1, int pos1, int pos2){
        Tiles[] meld = {tile1, concealedTiles[pos1], concealedTiles[pos2]};

        // Ensures items are removed backwards from the array
        // Prevents error when removing an index affects the length of the array 
        int smallerIndex = Math.min(pos1, pos2);
        int biggestIndex = Math.max(pos1, pos2);
        concealedTiles = remove(concealedTiles, biggestIndex);
        concealedTiles = remove(concealedTiles, smallerIndex);
        Sort(meld);
        exposedMelds = add(exposedMelds, meld);
    }

    public Tiles[][] CheckMeld(Tiles[] tilesArr, Tiles tile, int meldType){
        if(meldType == Tiles.PONG_MELD){
            Tiles[] temp = {};
            for(int i = 0; i < tilesArr.length; i++) {
               if(tilesArr[i].Equals(tile) && temp.length < 3){
                    temp = add(temp, tilesArr[i]);
               }
            }
            if(temp.length == 3){
                return new Tiles[][]{temp};
            }
        } else if(meldType == Tiles.CHOW_MELD){
            Tiles[] sameSuit = {};
            for(int i = 0; i < tilesArr.length; i++){
                if(tilesArr[i].GetSuit() == tile.GetSuit()){
                    sameSuit = add(sameSuit, tilesArr[i]);   
                }
            }
            Tiles[][] chowMelds = CheckConsecutive(sameSuit, tile);
            if(chowMelds.length!=0){
                return chowMelds;
            }
        }
        return null;
    }

    public boolean CheckWinningHand(){
        for (int i = 0; i < concealedTiles.length; i++) {
            Tiles[] eye = CheckEye(concealedTiles, concealedTiles[i]);
            if(eye!=null){
                Tiles[] restOfTiles = remove(concealedTiles, eye);
                boolean IsAllMelds = IsHandAllMelds(restOfTiles, Tiles.CHOW_MELD, 0);
                if(IsAllMelds){
                    return true;
                }
            }
        }
        return false;
    }

    public Tiles[] CheckEye(Tiles[] tilesArr, Tiles tile){
            Tiles[] temp = {};
            for(int i = 0; i < tilesArr.length; i++) {
               if(tilesArr[i].Equals(tile) && temp.length < 2){
                    temp = add(temp, tilesArr[i]);
               }
            }
            if(temp.length == 2){
                return temp;
            }
            return null;
    }

    public boolean IsHandAllMelds(Tiles[] arr, int meld, int start){
        if(start >= arr.length){
            return arr.length == 0;
        } else if(meld == Tiles.CHOW_MELD){
            Tiles[][] chowMelds = CheckMeld(arr, arr[start], Tiles.CHOW_MELD);
            for (int i = 0; chowMelds!=null && i < chowMelds.length; i++) {
                if(IsHandAllMelds(remove(arr, chowMelds[i]), Tiles.CHOW_MELD, 0)){
                    return true;
                }
                if(IsHandAllMelds(remove(arr, chowMelds[i]), Tiles.PONG_MELD, 0)){
                    return true;
                }
            }
            if(IsHandAllMelds(arr, Tiles.CHOW_MELD, start+1)){
                return true;
            }
            if(IsHandAllMelds(arr, Tiles.PONG_MELD, start)){
                return true;
            }
        } else if(meld == Tiles.PONG_MELD){
            Tiles[][] pongMelds = CheckMeld(arr, arr[start], Tiles.PONG_MELD);
            if(pongMelds != null){
                return IsHandAllMelds(remove(arr, pongMelds[0]), Tiles.PONG_MELD, start);
            }
        }
        return false;
    }
}