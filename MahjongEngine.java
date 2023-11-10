public class MahjongEngine {

    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Tiles[] wall;
    Tiles liveDiscard;
    Player currPlayer;
dd
    public MahjongEngine(){
        player1 = new Player(Player.EAST_WIND, );
        player2 = new Player(Player.SOUTH_WIND, );
        player3 = new Player(Player.WEST_WIND, );
        player4 = new Player(Player.NORTH_WIND, );
        wall = new Tiles[136];  

        int insertIndex = 0;
        for(int i = 0; i<4; i++){
            for(int j = 0; j<9; j++){
                Character charTile = new Character(j+1);
                Dot dotTile = new Dot(j+1);
                Bamboo bambooTile = new Bamboo(j+1);

                Tiles[insertIndex] = charTile;
                Tiles[insertIndex+1] = dotTile;
                Tiles[insertIndex+2] = bambooTileTile;
                insertIndex += 3;
            }

            Honors eastHonor = new Honors(Honors.EAST_HONOR);
            Honors southHonor = new Honors(Honors.SOUTH_HONOR);
            Honors westHonor = new Honors(Honors.WEST_HONOR);
            Honors northHonor = new Honors(Honors.NORTH_HONOR);
            Honors redHonor = new Honors(Honors.RED_HONOR);
            Honors whiteHonor = new Honors(Honors.WHITE_HONOR);
            Honors greenHonor = new Honors(Honors.GREEN_HONOR);
            Tiles[insertIndex] = eastHonor;
            Tiles[insertIndex+1] = southHonor;
            Tiles[insertIndex+2] = westHonor;
            Tiles[insertIndex+3] = northHonor;
            Tiles[insertIndex+4] = redHonor;
            Tiles[insertIndex+5] = whiteHonor;
            Tiles[insertIndex+6] = greenHonor;
            insertIndex += 7;
        }
    }
}