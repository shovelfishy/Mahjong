public class Displayer {

    // GLOBAL CONSTANT
    public static final int BOARD_WIDTH = 113;


    //================== PRIVATE METHODS ====================//
    // DEVELOPED BY: DANIEL
    /* Creates a string with a specified number of spaces
    * @param num      - Number of spaces to return
    * @return         - String with spaces*/
    private String spaces(int num){
        String spaces = "";
        for (int i = 0; i < num; i++) {
            spaces += " ";
        }
        return spaces;
    }

    // DEVELOPED BY: EDWARD
    /* Display tiles horizontally with a printing offset from the right side 
    * @param tiles            - The Tiles array to display
    * @param printOffset      - A string with the amount of spaces to shift the tiles during print */
    private void displayTiles(Tiles[] tiles, String printOffset){
        int handLength = 8*tiles.length+1; // Calculate how many characters to print

        System.out.print(printOffset); // The offset will be printed on every row the tiles display
        for (int i = 0; i < handLength; i++){ // Print first row of dashes
            System.out.print("-");
        }
        System.out.print("\n"+printOffset);
        for (int i = 0; i < tiles.length; i++){ // Print each tile's number
            System.out.print(tiles[i].DisplayTileNum());
        }
        System.out.println("|");
        System.out.print(printOffset);
        for (int i = 0; i < tiles.length; i++){ // Print each tile's suit
            System.out.print(tiles[i].DisplayTileSuit());
        }
        System.out.println("|");    

        System.out.print(printOffset);
        for (int i = 0; i < handLength; i++){ // Print last row of dashes
            System.out.print("-");
        }
        System.out.println();
    }

    // DEVELOPED BY: EDWARD
    /* Display a player's exposed melds. Will be used for current player and opposite player only
    * @param melds                      - The melds to print 
    * @param distanceFromBoardEdge      - Amount of space from the left side of board (to center printing the melds)  
    * @param numTilesInHand             - Number of tiles the player being printed has (to center printing the melds) */
    private void displayMelds(Tiles[][] melds, int distanceFromBoardEdge, int numTilesInHand){
        int meldLength = 9*melds.length; // Calculate how many characters to print
        int displayDist = (numTilesInHand*8+1-meldLength)/2 + distanceFromBoardEdge; // How far first meld should be printed from the left side of board
        String spaces = spaces(displayDist); // Used to help print empty spaces to offset and center each row of the meld

        System.out.print(spaces);
        for (int i = 0; i < meldLength; i++){ // First row of dashes
            System.out.print("-");
        }
        System.out.println();
        for(int i = 0; i < 3; i++){ // Iterate through each tile of each meld
            System.out.print(spaces);
            for (int j = 0; j < melds.length; j++){ // Iterate through each meld
                System.out.print(melds[j][i].DisplayTileNum()); // Print number of each tile of each meld
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.print(spaces);

        for (int i = 0; i < melds.length; i++){ // Print each meld's suit
            System.out.print(melds[i][0].DisplayTileSuit()); 
            System.out.print("|");
        }
        System.out.print("\n"+spaces);

        for (int i = 0; i < meldLength; i++){ // Last row of dashes
            System.out.print("-");
        }
        System.out.println();
    }

    // DEVELOPED BY: EDWARD
    /* Display a player's exposed melds. Will be used for current player and opposite player only
    * @param melds                      - The melds to print 
    * @param distanceFromBoardEdge      - Amount of space from the left side of board (to center printing the melds)  
    * @param numTilesInHand             - Number of tiles the player being printed has (to center printing the melds) */
    private void displayMelds(Tiles[][] melds, Tiles[][] melds2, Player left, Player right, Tiles discardTile){
        int meldLength = 9*melds.length; // Calculate how many characters to print for players on the left
        int meld2Length = 9*melds2.length; // Calculate how many characters to print for players on the right
        
        // Display left and right players' seat wind
        String playerLeft = left.DisplayPlayer(); 
        String playerRight = right.DisplayPlayer(); 
        int boardPlayerDist = (BOARD_WIDTH-playerLeft.length()-playerRight.length()); // Distance between both players seat display
        String spacesPlayer = spaces(boardPlayerDist);

        int boardCenterDist = BOARD_WIDTH - meldLength - meld2Length; // The gap in the center of the board between the left and right players' melds, minus the space that discard tile takes up
        // Distance on the left and right of discard pile so as to make it centered
        int boardLeftDist = (boardCenterDist-9)/2;
        int boardRightDist = boardCenterDist-boardLeftDist-9;
        String spacesCenter = spaces(boardCenterDist);
        String spacesLeft = spaces(boardLeftDist);
        String spacesRight = spaces(boardRightDist);

        // If both players have exposed melds, then both players' seat display will be on the edge of the board
        if(meldLength != 0 && meld2Length != 0){
            System.out.println(playerLeft+spacesPlayer+playerRight);
        }
        
        // If both players do not have exposed melds, then their seat display will be centered
        if(meldLength == 0 && meld2Length == 0){
            // Calculations to center display
            boardCenterDist = BOARD_WIDTH - (playerLeft.length()+10) - (playerRight.length()+10); // Distance between both players seat display but with some padding

            // Distance on the left and right of discard pile so as to make it centered
            boardLeftDist = (boardCenterDist - 9)/2;
            boardRightDist = boardCenterDist-boardLeftDist-9;
            spacesLeft = spaces(boardLeftDist + playerLeft.length()+10);
            spacesRight = spaces(boardRightDist);
            System.out.println();
        } else if(meldLength == 0){
            // Calculations to center display
            boardCenterDist = BOARD_WIDTH - (playerLeft.length()+10) - meld2Length; // Distance between both players seat display but with some padding for left player seat display
            
            // Distance on the left and right of discard pile so as to make it centered
            boardLeftDist = (boardCenterDist - 9)/2;
            boardRightDist = boardCenterDist-boardLeftDist-9;
            spacesLeft = spaces(boardLeftDist + playerLeft.length()+10);
            spacesRight = spaces(boardRightDist);
            System.out.println(spaces(playerLeft.length())+spacesPlayer+playerRight);
        } else if(meld2Length == 0){
            // Calculations to center display
            boardCenterDist = BOARD_WIDTH - meldLength - (playerRight.length()+10); // Distance between both players seat display but with some padding for right player seat display
            
            // Distance on the left and right of discard pile so as to make it centered
            boardLeftDist = (boardCenterDist - 9)/2;
            boardRightDist = boardCenterDist-boardLeftDist-9;
            spacesLeft = spaces(boardLeftDist);
            System.out.println(playerLeft+spacesPlayer+spaces(playerRight.length()));
        }

        // The strings of each row of the discard pile stored in an array
        // Will have different displays depending on if there is a discard tile or not
        String[] discardStrings;
        if(discardTile != null){ // If no discard tile in discard pile
            discardStrings = new String[]{"---------", discardTile.DisplayTileNum()+"|", discardTile.DisplayTileSuit()+"|", "---------", "DISCARDED"};
        } else{ // If there is a discard tile in discard pile
            discardStrings = new String[]{String.format("%9s",""), "---------", "|  NO   |", "|DISCARD|", "---------"};
        }

        
        for (int i = 0; i < meldLength; i++){ // First row of dashes
            System.out.print("-");
        }
        System.out.print(spacesCenter); // Empty space in between both sets of melds
        for (int i = 0; i < meld2Length; i++){ // First row of dashes
            System.out.print("-");
        }
        System.out.println();


        // Iterate through each tile of each meld
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < melds.length; j++){ // The tile number in left player melds
                System.out.print(melds[j][i].DisplayTileNum());
                System.out.print("|");
            }

            if(meldLength == 0 && i == 2){ // If left player has no melds, print their seat display here
                System.out.printf("%10s%s", "", playerLeft);
                System.out.print(spaces(boardLeftDist));
            } else { // Otherwise just print empty space
                System.out.print(spacesLeft);
            }
            System.out.print(discardStrings[i]); // Discard tile
            if(meld2Length == 0 && i == 2){ // If right player has no melds, print their seat display here
                System.out.print(spaces(boardRightDist));
                System.out.printf("%s", playerRight);
            } else { // Otherwise just print empty space
                System.out.print(spacesRight);
            }

            for (int j = 0; j < melds2.length; j++){ // The tile number in right player melds
                System.out.print(melds2[j][i].DisplayTileNum());
                System.out.print("|");
            }
            System.out.println();
        }


        for(int i = 0; i < melds.length; i++){ // Suit of all melds for left player
            System.out.print(melds[i][0].DisplayTileSuit());
            System.out.print("|");
        }
        System.out.print(spacesLeft);
        System.out.print(discardStrings[3]); // Discard tile
        System.out.print(spacesRight);
        for(int i = 0; i < melds2.length; i++){ // Suit of all melds for right player
            System.out.print(melds2[i][0].DisplayTileSuit());
            System.out.print("|");
        }

        System.out.println();

        for(int i = 0; i < meldLength; i++){ // Last row of dashes
            System.out.print("-");
        }
        System.out.print(spacesLeft);
        System.out.print(discardStrings[4]); // Discard tile
        System.out.print(spacesRight);
        for(int i = 0; i < meld2Length; i++){ // Last row of dashes
            System.out.print("-");
        }
        System.out.println();
    }


    //================== PUBLIC METHODS ====================//
    // DEVELOPED BY: DANIEL
    /* Display the discard tile 
    * @param discardTiles       - The live discard tile */
    public void DisplayDiscard(Tiles discardTile){
        System.out.println("  ---------");
        System.out.println("  "+discardTile.DisplayTileNum()+"|");
        System.out.println("  "+discardTile.DisplayTileSuit()+"|");
        System.out.println("  ---------");
        System.out.println("DISCARDED TILE");
    }

    // DEVELOPED BY: DANIEL
    /* Display a player's hand
    * @param discardTiles       - The player
    * @param offset             - An offset from the side of the board */
    public void DisplayHand(Player player, String offset){
        Tiles[] playerTiles = player.GetConcealedTiles();
        displayTiles(playerTiles, offset); // Display all the tiles in player's hand

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

    // DEVELOPED BY: EDWARD
    /* Prints the whole board from the point of view of the current player
    * @param player              - The current player
    * @param leftPlayer          - Player to the right of curremt
    * @param rightPlayer         - Player to the right of current player
    * @param upPlayer            - Player opposite current player
    * @param discardTile         - Current live discard tile */    
    public void DisplayBoard(Player player, Player leftPlayer, Player rightPlayer, Player upPlayer, Tiles discardTile){

        // Array of current player's hand and exposed tiles
        Tiles[] playerTiles = player.GetConcealedTiles();
        Tiles[][] playerMelds = player.GetExposedMelds();

        // Calculate the distance to print the current players' tiles in the center of board
        int distAdjacentPlayers = BOARD_WIDTH-9*(leftPlayer.GetExposedMelds().length+rightPlayer.GetExposedMelds().length); // Distance between the two adjacent players' melds
        int leftOffset = 9*(leftPlayer.GetExposedMelds().length);

        // Calculate the offset from the left of the board depending on whether left and right player have any exposed melds
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