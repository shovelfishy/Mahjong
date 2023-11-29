public class Dot extends Tiles{

    public Dot(int numerical){
        super(Tiles.DOT, numerical);
    }
    
    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }
    public String DisplayTileSuit(){
        return "|  DOT  ";
    }

    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 105;
            case Tiles.CHOW_MELD:
                return 90;
            case Tiles.NO_MELD:
                return 25;
        }
        return 0;
    }   
}