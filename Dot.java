public class Dot extends Tiles{

    public Dot(int numerical){
        super(Tiles.DOT, numerical);
    }
    

    public int CheckMeld(Tiles[] playerConcealedHand){
        int identicalTiles = 0;
        
        Tiles[] sameSuit = {};
        for(int i = 0; i < playerConcealedHand.length; i++){
            if(playerConcealedHand[i] instanceof Dot){
                if(playerConcealedHand[i].GetNum() == GetNum()){
                    identicalTiles++;
                }
                sameSuit = Add(sameSuit, playerConcealedHand[i]);  
            }
        }

        int[][] chowNumCombinations = CheckConsecutive(sameSuit, GetNum());

        if(identicalTiles >= 2){
            return PONG_CHOW_MELD;
        } else if(identicalTiles >= 2){
            return Tiles.PONG_MELD;
        } else if(chowNumCombinations.length > 0){
            return Tiles.CHOW_MELD;
        }

        return -1;
    }

    public Tiles[] CheckMeld(Tiles[] playerConcealedHand, int meldType){
        return new Tiles[4];
    }
}