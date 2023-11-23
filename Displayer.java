import java.util.Arrays;

public class Displayer {

    public static final int BOARD_WIDTH = 113;

    /* JAVA DOC DESC 
    * @param PARAM NAME      - 
    * @return         -  */
    private String spaces(int num){
        String spaces = "";
        for (int i = 0; i < num; i++) {
            spaces += " ";
        }
        return spaces;
    }

    /* JAVA DOC DESC 
    * @param PARAM NAME      - 
    * @return         -  */
    private void displayTiles(Tiles[] tiles, String printOffset){
        int handLength = 8*tiles.length+1;

        System.out.print(printOffset);
        for (int i = 0; i < handLength; i++){
            System.out.print("-");
        }
        System.out.print("\n"+printOffset);
        for (int i = 0; i < tiles.length; i++){
            System.out.print(tiles[i].DisplayTileNum());
        }
        System.out.println("|");
        System.out.print(printOffset);
        for (int i = 0; i < tiles.length; i++){
            System.out.print(tiles[i].DisplayTileSuit());
        }
        System.out.println("|");

        System.out.print(printOffset);
        for (int i = 0; i < handLength; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /* JAVA DOC DESC 
    * @param PARAM NAME      - 
    * @return         -  */
    private void displayMelds(Tiles[][] melds, int distanceFromBoardEdge, int numTilesInHand){
        int meldLength = 9*melds.length;
        int displayDist = (numTilesInHand*8+1-meldLength)/2 + distanceFromBoardEdge;
        String spaces = spaces(displayDist);

        System.out.print(spaces);
        for (int i = 0; i < meldLength; i++){
            System.out.print("-");
        }
        System.out.println();
        for(int i = 0; i < 3; i++){
            System.out.print(spaces);
            for (int j = 0; j < melds.length; j++){
                System.out.print(melds[j][i].DisplayTileNum());
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.print(spaces);

        for (int i = 0; i < melds.length; i++){
            System.out.print(melds[i][0].DisplayTileSuit());
            System.out.print("|");
        }
        System.out.print("\n"+spaces);

        for (int i = 0; i < meldLength; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /* JAVA DOC DESC 
    * @param PARAM NAME      - 
    * @return         -  */
    private void displayMelds(Tiles[][] tiles, Tiles[][] tiles2, Player left, Player right, Tiles discardTile){
        int meldLength = 9*tiles.length;
        int meld2Length = 9*tiles2.length;
        
        String playerLeft = left.DisplayPlayer(); 
        String playerRight = right.DisplayPlayer(); 
        int boardPlayerDist = (BOARD_WIDTH-playerLeft.length()-playerRight.length());
        String spacesPlayer = spaces(boardPlayerDist);

        int boardCenterDist = BOARD_WIDTH - meldLength - meld2Length; // The gap in the center of the board between the left and right players' melds, minus the space that discard tile takes up
        // Distance on the left and right of discard pile so as to make it centered
        int boardLeftDist = (boardCenterDist-9)/2;
        int boardRightDist = boardCenterDist-boardLeftDist-9;
        String spacesCenter = spaces(boardCenterDist);
        String spacesLeft = spaces(boardLeftDist);
        String spacesRight = spaces(boardRightDist);

        if(meldLength != 0 && meld2Length != 0){
            System.out.println(playerLeft+spacesPlayer+playerRight);
        }
        
        if(meldLength == 0 && meld2Length == 0){
            boardCenterDist = BOARD_WIDTH - (playerLeft.length()+10) - (playerRight.length()+10);
            boardLeftDist = (boardCenterDist - 9)/2;
            boardRightDist = boardCenterDist-boardLeftDist-9;
            spacesLeft = spaces(boardLeftDist + playerLeft.length()+10);
            spacesRight = spaces(boardRightDist);
            System.out.println();
        } else if(meldLength == 0){
            boardCenterDist = BOARD_WIDTH - (playerLeft.length()+10) - meld2Length;
            boardLeftDist = (boardCenterDist - 9)/2;
            boardRightDist = boardCenterDist-boardLeftDist-9;
            spacesLeft = spaces(boardLeftDist + playerLeft.length()+10);
            spacesRight = spaces(boardRightDist);
            System.out.println(spaces(playerLeft.length())+spacesPlayer+playerRight);
        } else if(meld2Length == 0){
            boardCenterDist = BOARD_WIDTH - meldLength - (playerRight.length()+10);
            boardLeftDist = (boardCenterDist - 9)/2;
            boardRightDist = boardCenterDist-boardLeftDist-9;
            spacesLeft = spaces(boardLeftDist);
            System.out.println(playerLeft+spacesPlayer+spaces(playerRight.length()));
        }

        String[] discardStrings;
        if(discardTile != null){
            discardStrings = new String[]{"---------", discardTile.DisplayTileNum()+"|", discardTile.DisplayTileSuit()+"|", "---------", "DISCARDED"};
        } else{
            discardStrings = new String[]{String.format("%9s",""), "---------", "|  NO   |", "|DISCARD|", "---------"};
        }

        
        for (int i = 0; i < meldLength; i++){
            System.out.print("-");
        }
        System.out.print(spacesCenter);
        for (int i = 0; i < meld2Length; i++){
            System.out.print("-");
        }
        System.out.println();


        for(int i = 0; i < 3; i++){
            for (int j = 0; j < tiles.length; j++){
                System.out.print(tiles[j][i].DisplayTileNum());
                System.out.print("|");
            }

            if(meldLength == 0 && i == 2){
                System.out.printf("%10s%s", "", playerLeft);
                System.out.print(spaces(boardLeftDist));
            } else {
                System.out.print(spacesLeft);
            }
            System.out.print(discardStrings[i]);
            if(meld2Length == 0 && i == 2){
                System.out.print(spaces(boardRightDist));
                System.out.printf("%s", playerRight);
            } else {
                System.out.print(spacesRight);
            }

            for (int j = 0; j < tiles2.length; j++){
                System.out.print(tiles2[j][i].DisplayTileNum());
                System.out.print("|");
            }
            System.out.println();
        }


        for(int i = 0; i < tiles.length; i++){
            System.out.print(tiles[i][0].DisplayTileSuit());
            System.out.print("|");
        }
        System.out.print(spacesLeft);
        System.out.print(discardStrings[3]);
        System.out.print(spacesRight);
        for(int i = 0; i < tiles2.length; i++){
            System.out.print(tiles2[i][0].DisplayTileSuit());
            System.out.print("|");
        }

        System.out.println();

        for(int i = 0; i < meldLength; i++){
            System.out.print("-");
        }
        System.out.print(spacesLeft);
        System.out.print(discardStrings[4]);
        System.out.print(spacesRight);
        for(int i = 0; i < meld2Length; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    public void DisplayDiscard(Tiles discardTile){
        System.out.println("  ---------");
        System.out.println("  "+discardTile.DisplayTileNum()+"|");
        System.out.println("  "+discardTile.DisplayTileSuit()+"|");
        System.out.println("  ---------");
        System.out.println("DISCARDED TILE");
    }

    public void DisplayHand(Player player, String offset){
        Tiles[] playerTiles = player.GetConcealedTiles();
        displayTiles(playerTiles, offset);

        // Print position number under each tile
        System.out.print(offset);
        for (int i = 1; i <= playerTiles.length; i++){
            for (int j = 1; j <= 7; j++) {
                if(j == 4){
                    System.out.printf("%2d", i);
                } else System.out.print(" ");
            }
        }
        System.out.println();
    }

    /* JAVA DOC DESC 
    * @param PARAM NAME      - 
    * @return         -  */    
    public void DisplayBoard(Player player, Player leftPlayer, Player rightPlayer, Player upPlayer, Tiles discardTile){

        // Array of current player's hand and exposed tiles
        Tiles[] playerTiles = player.GetConcealedTiles();
        Tiles[][] playerMelds = player.GetExposedMelds();

        // Calculate the distance to print the current players' tiles in the center of board
        int distAdjacentPlayers = BOARD_WIDTH-9*(leftPlayer.GetExposedMelds().length+rightPlayer.GetExposedMelds().length); // Distance between the two adjacent players' melds
        int leftOffset = 9*(leftPlayer.GetExposedMelds().length);

        if(leftPlayer.GetExposedMelds().length == 0 && rightPlayer.GetExposedMelds().length == 0){
            distAdjacentPlayers = BOARD_WIDTH-(20+leftPlayer.DisplayPlayer().length()+rightPlayer.DisplayPlayer().length());
            leftOffset = 10+leftPlayer.DisplayPlayer().length();
        } else if(leftPlayer.GetExposedMelds().length == 0){
            distAdjacentPlayers = BOARD_WIDTH-(10+leftPlayer.DisplayPlayer().length()+9*rightPlayer.GetExposedMelds().length);
            leftOffset = 10+leftPlayer.DisplayPlayer().length();
        } else if(rightPlayer.GetExposedMelds().length == 0){
            distAdjacentPlayers = BOARD_WIDTH-(10+rightPlayer.DisplayPlayer().length()+9*leftPlayer.GetExposedMelds().length);
        }
        int displayDistHandTiles = (distAdjacentPlayers-(8*playerTiles.length+1))/2 + leftOffset; // Distance from edge of board to current player's tiles
        String spaces = spaces(displayDistHandTiles);
        
        // Distance from edge of board to opposite player's melds
        int displayDistUpPlayer = (distAdjacentPlayers - upPlayer.DisplayPlayer().length())/2+leftOffset;
        String spaces2 = spaces(displayDistUpPlayer);

        System.out.println();
        if(upPlayer.GetExposedMelds().length != 0){ // Print the exposed melds of the player opposite current player, if any
            displayMelds(upPlayer.GetExposedMelds(), displayDistHandTiles, playerTiles.length);
        }
        System.out.println(spaces2 + upPlayer.DisplayPlayer()+"\n"); // Print opposite player's seat wind
        displayMelds(leftPlayer.GetExposedMelds(), rightPlayer.GetExposedMelds(), leftPlayer, rightPlayer, discardTile); // Print exposed melds of adjacent players (left & right)

        // Print empty spaces (new lines) if current player has no exposed melds
        if(playerMelds.length != 0){
            System.out.println("\n\n");
            displayMelds(playerMelds, displayDistHandTiles, playerTiles.length);        
        } else{
            System.out.println("\n\n");
        }

        DisplayHand(player, spaces);// Print player's hand
        System.out.println();
    }  
  }