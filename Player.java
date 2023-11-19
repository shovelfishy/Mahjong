public class Player {

    // GLOBAL CONSTANTS: PLAYER SEAT WINDS
    public static final int EAST_WIND = 100;
    public static final int SOUTH_WIND = 101;
    public static final int WEST_WIND = 102;
    public static final int NORTH_WIND = 103;

    // Private variables
    private int wind;
    private Tiles[][] exposedMelds;
    private Tiles[] concealedTiles;
    private Tiles[] hand;

    //TESTING
    public void SetHand(Tiles[] hand){
        this.hand = hand;
        this.concealedTiles = hand;
    }
    public void SetExposedMelds(Tiles[][] hand){
        this.exposedMelds = hand;
    }


    

    public Player(int wind, Tiles[] hand){
        this.wind = wind;
        this.hand = hand;
        this.concealedTiles = hand;
        this.exposedMelds = new Tiles[0][];
    }

    // public boolean CheckWinningHand(){
    //     int completedMelds = concealedTiles.length;
    // }

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

    public Tiles[] Add(Tiles[] arr, Tiles newTile){
        Tiles[] temp = new Tiles[arr.length+1];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = newTile;
        return temp;
    }

    public Tiles[] Remove(Tiles[] arr, int pos){
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



    public Tiles[] Remove(Tiles[] arr, Tiles tile){
        // Swap the tile in their concealed hand
        // The concealedTiles array does not have the same indices as the hand array
        Tiles[] temp = new Tiles[arr.length - 1];
        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] != tile){
                temp[counter] = arr[i];
                counter++;
            }
        }
        return temp;
    }

    public void AddTile(Tiles newTile){
        hand = Add(hand, newTile);
        concealedTiles = Add(concealedTiles, newTile);
    }

    public Tiles DiscardTile(int pos){
        Tiles oldTile = concealedTiles[pos];
        concealedTiles = Remove(concealedTiles, pos);
        hand = Remove(hand, pos);

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
    public Tiles[] GetHand() {
        return hand;
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


    public int[][] Add(int[][] arr, int[] num){
        int[][] temp = new int[arr.length+1][];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = num;
        return temp;
    }

    public Tiles[] DiffBy2(Tiles[] arr, int num){
        Tiles[] diffBy2 = new Tiles[0];
        for(int i=0; i<arr.length;i++){
            int difference = Math.abs(num - arr[i].GetNum());
            if(difference == 1 || difference == 2){
                diffBy2 = Add(diffBy2, arr[i]);
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

   
    public int[][] CheckConsecutive(Tiles[] arr, int num){
        Tiles[] diffBy2Arr = DiffBy2(arr, num);
        int[][] consequetiveSets = new int[0][3]; 
        for(int i = 0; i < diffBy2Arr.length-1; i++){
            if(diffBy2Arr[i+1].GetNum()-diffBy2Arr[i].GetNum() == 1){
                if(diffBy2Arr[i+1].GetNum() < num){
                    consequetiveSets = Add(consequetiveSets, new int[]{num-2, num-1, num});
                } else if(diffBy2Arr[i+1].GetNum() > num){
                    consequetiveSets = Add(consequetiveSets, new int[]{num, num+1, num+2});
                }
            }
            if(diffBy2Arr[i+1].GetNum()-diffBy2Arr[i].GetNum() == 2){
                consequetiveSets = Add(consequetiveSets, new int[]{num-1, num, num+1});
            }
        }
        return consequetiveSets;
    }

    public boolean CheckWinningHand(Tiles[] arr, int meld){
        for (Tiles tiles : arr) {
            System.out.println(tiles.GetNum());
        }
        System.out.println();

        if(arr.length == 0){
            return true;
        } else if (meld == Tiles.CHOW_MELD){
            Tiles tile = arr[0];
            Tiles[][] chowMelds = tile.CheckMeld(arr, Tiles.CHOW_MELD);
            for (int i = 0; chowMelds!=null && i < chowMelds.length; i++) {
                System.out.println(i+"E"+chowMelds.length);
                if(CheckWinningHand(Remove(arr, chowMelds[i]), Tiles.PONG_MELD)){
                    return true;
                }
            }
        } else {
            Tiles tile = arr[0];
            Tiles[][] pongMelds = tile.CheckMeld(arr, Tiles.PONG_MELD);
            if(pongMelds!=null){
                return CheckWinningHand(Remove(arr, pongMelds[0]), Tiles.PONG_MELD);
            }
            return false;
        }
        return false;
    }

    public Tiles[] Remove(Tiles[] arr, Tiles[] tilesToRemove){
        arr = copyTiles(arr);
        Tiles[] temp = new Tiles[arr.length-tilesToRemove.length];
        int counter = 0;
        for (int i = 0; i < tilesToRemove.length; i++) {
            Tiles currElement = tilesToRemove[i]; 
            for (int j = 0; j < arr.length; j++) {
                if(arr[j] != null && arr[j].GetSuit() == currElement.GetSuit() && arr[j].GetNum() == currElement.GetNum()){
                    arr[j] = null;
                    break;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != null){
                temp[counter] = arr[i];
                counter++;
            }
        }
        return temp;
    }
}