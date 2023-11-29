public class Character extends Tiles{

    public Character(int numerical){
        super(Tiles.CHARACTER, numerical);
    }

    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }
    
    public String DisplayTileSuit(){
        return "| CHAR  ";
    }

    public int CalcPoints(int meldType){
        switch(meldType){
            case Tiles.PONG_MELD:
                return 115;
            case Tiles.CHOW_MELD:
                return 95;
            case Tiles.NO_MELD:
                return 15;
        }
        return 0;
    }   
}