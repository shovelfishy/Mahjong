public class MahjongEngine {

    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Tiles[] wall;
    Tiles liveDiscard;
    Player currPlayer;
    
    private int random(int min, int max){
        return (int)Math.round(Math.random()*(max-min)+min);
    } 

    private Tiles[] remove(Tiles[] arr, int index){
        Tiles[] newArr = new Tiles[arr.length-1];

        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if(i != index){
                newArr[counter] = arr[i];
                counter++;
            }
        }
        return newArr; 
    }

    private Tiles[] DealTiles(int numTiles){
        Tiles[] hand = new Tiles[numTiles];
        for(int i=0; i<numTiles; i++){
            int index = random(0, wall.length);
            Tiles tile = wall[index];
            hand[i] = tile;
            wall = remove(wall, index);
        }
        return hand;
    }


    public MahjongEngine(){
        wall = new Tiles[136];

        int insertIndex = 0;
        for(int i = 0; i<4; i++){
            for(int j = 0; j<9; j++){
                Character charTile = new Character(j+1);
                Dot dotTile = new Dot(j+1);
                Bamboo bambooTile = new Bamboo(j+1);

                wall[insertIndex] = charTile;
                wall[insertIndex+1] = dotTile;
                wall[insertIndex+2] = bambooTile;
                insertIndex += 3;
            }

            Honors eastHonor = new Honors(Honors.EAST_HONOR);
            Honors southHonor = new Honors(Honors.SOUTH_HONOR);
            Honors westHonor = new Honors(Honors.WEST_HONOR);
            Honors northHonor = new Honors(Honors.NORTH_HONOR);
            Honors redHonor = new Honors(Honors.RED_HONOR);
            Honors whiteHonor = new Honors(Honors.WHITE_HONOR);
            Honors greenHonor = new Honors(Honors.GREEN_HONOR);
            wall[insertIndex] = eastHonor;
            wall[insertIndex+1] = southHonor;
            wall[insertIndex+2] = westHonor;
            wall[insertIndex+3] = northHonor;
            wall[insertIndex+4] = redHonor;
            wall[insertIndex+5] = whiteHonor;
            wall[insertIndex+6] = greenHonor;
            insertIndex += 7;
        }

        System.out.println(wall.length);
        System.out.println(((Honors)wall[135]).GetHonorType());
        player1 = new Player(Player.EAST_WIND, DealTiles(14));
        player2 = new Player(Player.SOUTH_WIND, DealTiles(13));
        player3 = new Player(Player.WEST_WIND, DealTiles(13));
        player4 = new Player(Player.NORTH_WIND, DealTiles(13));
        System.out.println(wall.length);
    }
}