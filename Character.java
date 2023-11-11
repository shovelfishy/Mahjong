import java.util.Arrays;

public class Character extends Tiles{

    public Character(int numerical){
        super(Tiles.CHARACTER, numerical);
    }

    public int CheckMeld(Tiles[] playerConcealedHand){
        int identicalTiles = 0;
        
        Tiles[] sameSuit = {};
        for(int i = 0; i < playerConcealedHand.length; i++){
            if(playerConcealedHand[i] instanceof Character){
                if(playerConcealedHand[i].GetNum() == GetNum()){
                    identicalTiles++;
                }
                sameSuit = Add(sameSuit, playerConcealedHand[i]);   
            }
        }

        int[][] chowNumCombinations = CheckConsecutive(sameSuit, GetNum());
        if(identicalTiles >= 2 && chowNumCombinations.length > 0){
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