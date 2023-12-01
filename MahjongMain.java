import java.util.Scanner;

// DEVELOPED BY: EDWARD
public class MahjongMain {

    public static void main(String[] args) {
        Displayer disp = new Displayer();
        Scanner input = new Scanner(System.in);

        while(true) {
            MahjongEngine me = new MahjongEngine(); // Starts new game 
            System.out.println("Welcome To Mahjong!");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");
            int option = input.nextInt();
            if(option >= 1 && option <=3) System.out.println(); // Removes awkward newline when user invalid input
    
            // Sentinel values to check when game ends
            boolean hasGameEnded = false; // When game ends in a draw or only 1 player remains
            boolean hasPlayerWon = false; // When a player wins
            
            if(option == 1){ // Start game
                boolean isActionTaken = false; // Sentinel value for whether a pong or chow was called
                while(true){
                    if(!isActionTaken){ // Draw a tile if player did not previously make a pong or chow call
                        me.DrawTile();
                    }

                    boolean isPlayerAction = true; // Sentinel value for pong, chow & Mahjong menu
                    isActionTaken = false; // Reset sentinel value

                    // Calculate the position of each player relative to the current player
                    int currPlayerIndex = me.FindPlayerIndex(me.GetCurrPlayer());
                    Player leftPlayer = me.GetPlayers()[(currPlayerIndex+3)%4];
                    Player rightPlayer = me.GetPlayers()[(currPlayerIndex+1)%4];
                    Player upPlayer = me.GetPlayers()[(currPlayerIndex+2)%4];

                    disp.DisplayBoard(me.GetCurrPlayer(), leftPlayer, rightPlayer, upPlayer, me.GetLiveDiscard());
                    
                    boolean hasDiscarded = false; // Sentinel value to track whether player has discard a tile
                    while(true){ // Continue player turn until they end

                        // Removes the "Discard Tile" option if they have discarded already
                        int[] availableOptions = {1,2,3,4}; // Option numbers
    
                        System.out.println("What would you like to do? ");
                        if(!hasDiscarded){ 
                            System.out.println("1. Discard tile");
                            System.out.println("2. Arrange tiles");
                            System.out.println("3. Mahjong");
                            System.out.println("4. End turn");
                        } else{
                            System.out.println("1. Arrange tiles");
                            System.out.println("2. Mahjong");
                            System.out.println("3. End turn");
                            availableOptions = new int[]{-1,1,2,3}; // Update option numbers without discard option
                        }
                        System.out.print("Select option: ");
                        option = input.nextInt();
                        input.nextLine();
                        if(!hasDiscarded && option == availableOptions[0]){ // Display discard option if not discarded yet
                            while(true) { // Prompt user until valid tile position entered
                                System.out.print("Select which tile position to discard: ");
                                int pos = input.nextInt();

                                int discardTile = me.DiscardTile(me.GetCurrPlayer(), pos); // Check for valid tile position 
                                if(discardTile == MahjongEngine.SUCCESSFUL){
                                    hasDiscarded = true; // Tile has been discareded
                                    break;
                                } else if(discardTile == MahjongEngine.UNSUCCESSFUL){
                                    System.out.println("ERROR: Invalid tile position");
                                }
                            }
                            disp.DisplayBoard(me.GetCurrPlayer(), leftPlayer, rightPlayer, upPlayer, me.GetLiveDiscard());
                        } else if(option == availableOptions[1]){ // Arrange player hand
                            while(true){ // 
                                System.out.print("Arrange tiles (0: quit, a: auto sort): ");
                                String arrangePos = input.nextLine().toLowerCase();
                                String[] splitPos = arrangePos.split(" "); // Split the string into the two tile posiiton
                                
                                if(arrangePos.equals("0")){ // Exit this sub menu
                                    System.out.println();
                                    break;
                                } else if(arrangePos.equals("a")){ // Auto sort hand
                                    me.GetCurrPlayer().AutoSortHand();
                                    System.out.println();
                                    disp.DisplayHand(me.GetCurrPlayer(), "");
                                } else if(splitPos.length == 2){
                                    // Convert tile positions to array index (substract 1)
                                    int pos1 = Integer.parseInt(splitPos[0])-1;
                                    int pos2 = Integer.parseInt(splitPos[1])-1;
                                    
                                    // Check whether the tile positions entered are valid
                                    if(pos1 < 0 || pos2 < 0 || pos1 > me.GetCurrPlayer().GetConcealedTiles().length-1 || pos2 > me.GetCurrPlayer().GetConcealedTiles().length-1){ 
                                        System.out.println("ERROR: Invalid tile position");
                                    } else{
                                        System.out.println();
                                        me.GetCurrPlayer().ArrangeHand(pos1, pos2); // Shift tiles
                                        disp.DisplayHand(me.GetCurrPlayer(), "");
                                    }
                                } else{ // Invalid input
                                    System.out.println("ERROR: Invalid input");
                                }
                            }
                        } else if(option == availableOptions[2]){
                            // Prompt user to confirm whether they want they call Mahjong
                            while(true) {  
                                System.out.print("Are you sure you want to call Mahjong? (Y/N): ");
                                String choice = input.nextLine().toLowerCase();
                                if(choice.equals("y")){
                                    boolean isWin = me.Mahjong(me.GetCurrPlayer(), null); // Check whether player has winning hand
                                    if(isWin){ // If player has winning hand
                                        hasPlayerWon = true; // Update sentinel values to end game
                                    } else{ // Remove player if they do not have winning hand
                                        System.out.println("You do not have a winning hand. "+me.GetCurrPlayer().DisplayPlayer()+" will be removed from the game");
                                        System.out.print("Press enter to proceed ");
                                        input.nextLine(); // User buffer

                                        // If the player did not discard a tile, then other players cannot call pong or chow
                                        if(!hasDiscarded){
                                            isPlayerAction = false; // Prevent the Pong & Chow call menu
                                        }
                                    }
                                    break;
                                } else if(choice.equals("n")){ // Exit sub menu
                                    System.out.println();
                                    break;
                                } else{ // Invalid input
                                    System.out.println("ERROR: Invalid input");
                                }
                            }
                            if(hasPlayerWon || !me.GetCurrPlayer().GetIsPlaying()) break; // End turn if player has won or was eliminated
                        } else if(option == availableOptions[3]){ // End player's turn
                            if(hasDiscarded){ // End turn only if they have already discarded tile
                                System.out.println(me.GetCurrPlayer().DisplayPlayer()+" has ended their turn");
                                break;
                            } else{
                                System.out.println("ERROR: You must discard a tile before ending your turn\n");
                            }
                        } else{ // Invalid input
                            System.out.println("ERROR: Invalid input\n");
                        }
                    }    
                    
                    // Check whether game is over, either all but one player remains or wall is empty
                    if(me.IsGameOver() == MahjongEngine.NO_PLAYERS){
                        hasGameEnded = true;
                    } else if(me.IsGameOver() == MahjongEngine.EMPTY_WALL){
                        hasGameEnded = true;
                    }
                    
                    // Prompt the Pong & Chow call menu for other players
                    while(isPlayerAction && !hasGameEnded && !hasPlayerWon){ // Only show menu if game hasn't ended and a tile was discarded in previous turn
                        System.out.println();
                        disp.DisplayDiscard(me.GetLiveDiscard());
                        Player[] otherPlayers = me.OtherPlayers(me.GetCurrPlayer()); // List of players except the one who discarded
                        int numOtherPlayers = otherPlayers.length;

                        // Dyanamic selection list. Changes number of options depending on how many players there are
                        // Loops through otherPlayers array and print each player with a number
                        System.out.println("Do any other players want to perform an action?");
                        for (int i = 1; i <= otherPlayers.length; i++) { 
                            System.out.println(i+". "+otherPlayers[i-1].DisplayPlayer());
                        }
                        System.out.println((numOtherPlayers+1)+". No, go to next turn");
                        System.out.print("Select option: ");
                        option = input.nextInt();
                        input.nextLine();

                        if(option == numOtherPlayers+1){ // Last option selected --> Exit menu and continue to next player 
                            break;
                        } else if(option >= 1 && option <= numOtherPlayers){ // One of the players selected
                            Player activePlayer = otherPlayers[option-1]; // Store selected player

                            // Choose to make Pong, Chow or Mahjong call
                            while(true) {
                                System.out.println("\n"+activePlayer.DisplayPlayer() + "'S HAND");
                                disp.DisplayHand(activePlayer, "");
                                System.out.println("Actions");
                                System.out.println("1. Pong");
                                System.out.println("2. Chow");
                                System.out.println("3. Mahjong");
                                System.out.println("4. Quit");
                                System.out.print("Select option: ");
                                option = input.nextInt();
                                input.nextLine();
                                
                                if(option == 1){ // Pong call
                                    // Check whether player has the tiles to complete meld
                                    int meldable = me.Meldable(activePlayer, me.GetCurrPlayer(), Tiles.PONG_MELD);
                                    
                                    if(meldable == MahjongEngine.NO_COMPLETE_MELD){ // Return to previous menu if player does not have correct tiles
                                        System.out.println("ERROR: You do not have the tiles required to form a pong meld");
                                        break; 
                                    } else if(meldable == MahjongEngine.SUCCESSFUL){ // Player has correct tiles
                                        System.out.println();
                                        disp.DisplayDiscard(me.GetLiveDiscard());
                                        disp.DisplayHand(activePlayer, "");
                                        System.out.println("Select the tiles you want to use in your meld");
                                        
                                        // Select the tiles from their hand they want to make the Pong meld with
                                        int pos1, pos2;

                                        // Check that the tile positions they select are valid
                                        while(true) {
                                            System.out.print("Tile 1 position: ");
                                            pos1 = input.nextInt();
                                            if(pos1 >= 1 && pos1 <= activePlayer.GetConcealedTiles().length){ // Positions must be between 1 and length of their hand
                                                break;
                                            } else{ // Invalid position
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }

                                        // Check that the tile positions they select are valid
                                        while(true) {
                                            System.out.print("Tile 2 position: ");
                                            pos2 = input.nextInt();
                                            if(pos2 == pos1){ // Selected position cannot be duplicated
                                                System.out.println("ERROR: You have already selected this tile");
                                            } else if(pos2 >= 1 && pos2 <= activePlayer.GetConcealedTiles().length){ // Positions must be between 1 and length of their hand
                                                break;
                                            } else{ // Invalid position
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }
                                    
                                        // Check whether selected tiles are the correct ones
                                        if(!me.Pong(activePlayer, pos1, pos2)){ // Incorrect selections
                                            System.out.println("ERROR: The tiles selected are not valid for a pong meld");
                                            break; // Return to the Pong & Chow menu
                                        } else{ // Successful meld
                                            isPlayerAction = false; // Exit Pong & Chow menu
                                            isActionTaken = true; // Prevent drawing a new tile next turn
                                            System.out.println("Successful meld");
                                            me.SetCurrPlayer(activePlayer); // Make this player who made the call the next player
                                            break;
                                        }
                                    }
                                } else if(option == 2){ // Chow call
                                    // Check whether player has the tiles to complete meld
                                    int meldable = me.Meldable(activePlayer, me.GetCurrPlayer(), Tiles.CHOW_MELD); 
                                    if(meldable == MahjongEngine.INCORRECT_POSITION){ // Return to previous menu if player is not in correct sitting position
                                        System.out.println("ERROR: You are not in the correct sitting position to make a chow call");
                                        break;
                                    } else if(meldable == MahjongEngine.NO_COMPLETE_MELD){ // Return to previous menu if player does not have correct tiles
                                        System.out.println("ERROR: You do not have the tiles required to form a chow meld");
                                        break;
                                    } else if(meldable == MahjongEngine.SUCCESSFUL){// Player has correct tiles
                                        System.out.println();
                                        disp.DisplayDiscard(me.GetLiveDiscard());
                                        disp.DisplayHand(activePlayer, "");
                                        System.out.println("Select the tiles you want to use in your meld");

                                        // Select the tiles from their hand they want to make the Pong meld with
                                        int pos1, pos2;

                                        // Check that the tile positions they select are valid
                                        while(true) {
                                            System.out.print("Tile 1 position: ");
                                            pos1 = input.nextInt();
                                            if(pos1 >= 1 && pos1 <= activePlayer.GetConcealedTiles().length){ // Positions must be between 1 and length of their hand
                                                break;
                                            } else{ // Invalid position
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }

                                        // Check that the tile positions they select are valid
                                        while(true) {
                                            System.out.print("Tile 2 position: ");
                                            pos2 = input.nextInt();
                                            if(pos2 == pos1){ // Selected position cannot be duplicated
                                                System.out.println("ERROR: You have already selected this tile");
                                            } else if(pos2 >= 1 && pos2 <= activePlayer.GetConcealedTiles().length){ // Positions must be between 1 and length of their hand
                                                break; 
                                            } else{ // Invalid position
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }
                                    
                                        // Check whether selected tiles are the correct ones
                                        if(!me.Chow(activePlayer, pos1, pos2)){ // Incorrect selections
                                            System.out.println("ERROR: The tiles selected are not valid for a chow meld");
                                            break; 
                                        } else{ // Successful meld
                                            isPlayerAction = false; // Exit Pong & Chow menu
                                            isActionTaken = true; // Prevent drawing a new tile next turn
                                            me.SetCurrPlayer(activePlayer); // Make this player who made the call the next player
                                            System.out.println("Successful meld");
                                            break;
                                        }
                                    }
                                } else if(option == 3){ // Mahjong call
                                    // Confirm whether player wants to make the call
                                    while(true){
                                        System.out.print("Are you sure you want to call Mahjong? (Y/N): ");
                                        String choice = input.nextLine().toLowerCase();
                                        if(choice.equals("y")){
                                            boolean isWin = me.Mahjong(activePlayer, me.GetLiveDiscard()); // Check whether player has winning hand
                                            if(isWin){ // If player has winning hand
                                                me.SetCurrPlayer(activePlayer);
                                                hasPlayerWon = true;// Update sentinel values to end game
                                            } else{ // Remove player if they do not have winning hand
                                                System.out.println("You do not have a winning hand. "+activePlayer.DisplayPlayer()+" will be removed from the game");
                                                System.out.print("Press enter to proceed ");
                                                input.nextLine(); // User buffer
                                            }
                                            break;
                                        } else if(choice.equals("n")){ // Exit sub menu
                                            break;
                                        } else{ // Invalid input
                                            System.out.println("ERROR: Invalid input");
                                        }
                                    }
                                    break;
                                } else if(option == 4){ // Exit player option menu, return to Pong & Chow menu
                                    break;
                                } else{ // Invalid input
                                    System.out.println("ERROR: Invalid input");
                                }
                            }
                        } else{
                            System.out.println("ERROR: Invalid input");
                        }
                        
                        // Check whether there are still existing players
                        if(me.IsGameOver() == MahjongEngine.NO_PLAYERS){
                            hasGameEnded = true;
                        }
                    }

                    // Check game snetinel values
                    if(hasPlayerWon || hasGameEnded){
                        break;
                    }
                    
                    // Switch to next player in order if nobody called Pong or Chow 
                    if(!isActionTaken){
                        me.SwitchPlayer();
                    }
                }


                if(hasGameEnded){
                    System.out.println();
                    if(me.IsGameOver() == MahjongEngine.EMPTY_WALL){ // Tied game
                        System.out.println("The game has ended in a tie as the wall has ran out of tiles. We are all winners yay!");
                    } else{ // Player win by default
                        System.out.println("All other players have been eliminated so "+me.CurrentlyPlaying()[0].DisplayPlayer()+" has won the game by default");
                    }
                } else if(hasPlayerWon){ // Player win with winning hand
                    // Calculate the position of each player relative to the winning player
                    int winnerPlayerIndex = me.FindPlayerIndex(me.GetCurrPlayer());
                    Player leftPlayer = me.GetPlayers()[(winnerPlayerIndex+3)%4];
                    Player rightPlayer = me.GetPlayers()[(winnerPlayerIndex+1)%4];
                    Player upPlayer = me.GetPlayers()[(winnerPlayerIndex+2)%4];

                    // Display winner
                    disp.DisplayBoard(me.GetCurrPlayer(), leftPlayer, rightPlayer, upPlayer, me.GetLiveDiscard());
                    System.out.println(me.GetCurrPlayer().DisplayPlayer()+" has won the Mahjong game!");
                }

                // Display all players' total points 
                System.out.println();
                System.out.println("ALL PLAYERS' POINTS:");
                // Iterate through all players and their tiles and exposed melds to calculate their points
                for(int i = 0; i < me.GetPlayers().length; i++){
                    Player player = me.GetPlayers()[i];
                    int points = 0;
                    for (int j = 0; j < player.GetConcealedTiles().length; j++) { // Calculate points from tiles in their hand
                        points += player.GetConcealedTiles()[j].CalcPoints(Tiles.NO_MELD);
                    }

                    for (int j = 0; j < player.GetExposedMelds().length; j++) { // Calculate points from exposed melds
                        if(player.GetExposedMelds()[j][0].Equals(player.GetExposedMelds()[j][1])){ // Points from Pong melds
                            points += player.GetExposedMelds()[j][0].CalcPoints(Tiles.PONG_MELD);
                        } else{ // Points from Chow melds
                            points += player.GetExposedMelds()[j][0].CalcPoints(Tiles.CHOW_MELD);
                        }
                    }
                    System.out.println(player.DisplayPlayer() + ": "+points);
                }
                System.out.println();

                System.out.print("Press enter to continue to main menu ");
                input.nextLine(); // Creates a user buffer before resuming program
                System.out.println();
            } else if(option == 2){ // Exit program
                System.out.println("Thank you for playing. Come again soon!");
                break;
            } else{ // Invalid input
                System.out.println("ERROR: Invalid input\n");
            } 
        }
    }
}

