public class SuitTiles extends Tiles{
    
    public SuitTiles(int suit, int numerical){
        super(suit, numerical);
    }

    public String DisplayTileNum(){
        return "|   "+GetNum()+"   ";
    }

    public String DisplayTileSuit(){
        switch(GetSuit()) {
            case Tiles.BAMBOO:
                return "|BAMBOO ";
            case Tiles.CHARACTER:
                return "| CHAR  ";
            case Tiles.DOT:
                return "|  DOT  ";
            default:
                return null;
        }
    }
}
