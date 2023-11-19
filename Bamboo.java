public class Bamboo extends Tiles{
 
    public Bamboo(int numerical){
        super(Tiles.BAMBOO, numerical);
    }
    
    public int CheckMeld(Tiles[] playerConcealedHand){
        int identicalTiles = 0;
        
        Tiles[] sameSuit = {};
        for(int i = 0; i < playerConcealedHand.length; i++){
            if(playerConcealedHand[i] instanceof Bamboo){
                if(playerConcealedHand[i].GetNum() == GetNum()){
                    identicalTiles++;
                }
                sameSuit = Add(sameSuit, playerConcealedHand[i]);   
            }
        }

        Tiles[][] chowNumCombinations = CheckConsecutive(sameSuit, new Bamboo(GetNum()));

        if(identicalTiles >= 2 && chowNumCombinations.length > 0){
            return PONG_CHOW_MELD;
        } else if(identicalTiles >= 2){
            return Tiles.PONG_MELD;
        } else if(chowNumCombinations.length > 0){
            return Tiles.CHOW_MELD;
        }

        return Tiles.NO_MELD;
    }

    public Tiles[] CheckMeld(Tiles[] playerConcealedHand, int meldType){
        return new Tiles[4];
    }

    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }
    public String DisplayTileSuit(){
        return "|BAMBOO ";
    }

}