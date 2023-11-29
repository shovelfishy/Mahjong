public class Bamboo extends Tiles{
 
    public Bamboo(int numerical){
        super(Tiles.BAMBOO, numerical);
    }
    
    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }
    public String DisplayTileSuit(){
        return "|BAMBOO ";
    }

    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 110;
            case Tiles.CHOW_MELD:
                return 105;
            case Tiles.NO_MELD:
                return 20;
        }
        return 0;
    }   
}