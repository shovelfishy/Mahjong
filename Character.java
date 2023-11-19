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

        Tiles[][] chowNumCombinations = CheckConsecutive(sameSuit, new Character(GetNum()));
        if(identicalTiles >= 2 && chowNumCombinations.length > 0){
            return PONG_CHOW_MELD;
        } else if(identicalTiles >= 2){
            return Tiles.PONG_MELD;
        } else if(chowNumCombinations.length > 0){
            return Tiles.CHOW_MELD;
        }

        return Tiles.NO_MELD;
    }

    public Tiles[][] CheckMeld(Tiles[] playerConcealedHand, int meldType){
        Tiles[] temp = {};
        if(meldType == Tiles.PONG_MELD){
            for(int i = 0; i < playerConcealedHand.length; i++) {
               if(playerConcealedHand[i].GetNum() == GetNum() && playerConcealedHand[i].GetSuit() == GetSuit() && temp.length < 3){
                    temp = Add(temp, playerConcealedHand[i]);
               }
            }
            if(temp.length == 3){
                return new Tiles[][]{temp};
            } else return null;
        } else if(meldType == Tiles.CHOW_MELD){
            Tiles[] sameSuit = {};
            for(int i = 0; i < playerConcealedHand.length; i++){
                if(playerConcealedHand[i] instanceof Character){
                    sameSuit = Add(sameSuit, playerConcealedHand[i]);   
                }
            }
            return CheckConsecutive(sameSuit, new Character(GetNum()));
        } else return null;
    }

    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }
    public String DisplayTileSuit(){
        return "| CHAR  ";
    }
}