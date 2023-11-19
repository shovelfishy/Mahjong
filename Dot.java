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
}