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

}