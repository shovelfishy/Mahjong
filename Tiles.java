import java.util.Arrays;

public abstract class Tiles {
    public static final int NO_MELD = -200;
    public static final int PONG_MELD = 200;
    public static final int CHOW_MELD = 201;
    public static final int EYE_PAIR = 202;
    public static final int PONG_CHOW_MELD = 203;

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

    public int GetNum() {
        return numerical;
    }

    public Player GetOwner() {
        return owner;
    }

    public void SetOwner(Player owner) {
        this.owner = owner;
    }
    
    public abstract int CheckMeld(Tiles[] playerConcealedHand);
    public abstract Tiles[][] CheckMeld(Tiles[] playerConcealedHand, int meldType);

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

    public Tiles[] Add(Tiles[] arr, Tiles tile){
        Tiles[] temp = new Tiles[arr.length+1];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = tile;
        return temp;
    }

    public Tiles[][] Add(Tiles[][] arr, Tiles[] num){
        Tiles[][] temp = new Tiles[arr.length+1][];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        temp[arr.length] = num;
        return temp;
    }


    // MAY NOT NEED
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
   
    public Tiles[][] CheckConsecutive(Tiles[] arr, Tiles tileToSearch){
        int num = tileToSearch.GetNum();
        Tiles[] diffBy2Arr = DiffBy2(arr, num);
        Tiles[][] consequetiveSets = new Tiles[0][3]; 
        for(int i = 0; i < diffBy2Arr.length-1; i++){
            if(diffBy2Arr[i+1].GetNum()-diffBy2Arr[i].GetNum() == 1){
                if(diffBy2Arr[i+1].GetNum() < num){
                    consequetiveSets = Add(consequetiveSets, new Tiles[]{diffBy2Arr[i], diffBy2Arr[i+1], tileToSearch});
                } else if(diffBy2Arr[i].GetNum() > num){
                    consequetiveSets = Add(consequetiveSets, new Tiles[]{tileToSearch, diffBy2Arr[i], diffBy2Arr[i+1]});
                }
            }
            if(diffBy2Arr[i+1].GetNum()-diffBy2Arr[i].GetNum() == 2){
                consequetiveSets = Add(consequetiveSets, new Tiles[]{diffBy2Arr[i], tileToSearch, diffBy2Arr[i+1]});
            }
        }
        return consequetiveSets;
    }

    abstract public String DisplayTileNum();
    abstract public String DisplayTileSuit();
}