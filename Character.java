import java.util.Arrays;

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
}