import java.util.Arrays;
import java.util.Scanner;

/*TO-DO
         * 1. choose to discard, arrange tiles, or end turn
         * 2. pong, chow, or mahjong
         * 3. win screen, draw screen, lose screen (if someone accidentlaly mahjongs wrongly)
         * 4. tutorial
        */

public class MahjongMain {

    public static void main(String[] args) {
        MahjongEngine me = new MahjongEngine();
        Displayer disp = new Displayer();
        Scanner input = new Scanner(System.in);

        // Tiles[][] a = {{new Dot(1),new Dot(2),new Dot(3)},{new Dot(4),new Dot(5),new Dot(6)},{new Honors(7),new Honors(7),new Honors(7)}};
        // Tiles[] b = {new Bamboo(4),new Bamboo(4),new Bamboo(4),new Bamboo(3),new Bamboo(3)};
        // Tiles[] c = {new Bamboo(1),new Bamboo(2),new Bamboo(6),new Bamboo(5),new Bamboo(4),new Bamboo(5),new Bamboo(6)};
        // me.player1.SetConcealedTiles(b);
        // me.player2.SetConcealedTiles(c);
        // me.player1.SetExposedMelds(a);
        // me.player2.SetExposedMelds(a);

        boolean test = true;

        while(test) {
            // me = new MahjongEngine();
            System.out.println("Welcome To Mahjong!");
            System.out.println("1. Start Game");
            System.out.println("2. Tutorial");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = input.nextInt();
            if(option >= 1 && option <=3) System.out.println(); // Removes awkward newline when user invalid input
    
            boolean hasGameEnded = false;
            boolean hasPlayerWon = false;
            
            if(option == 1){ // Start game
                boolean isActionTaken = false; // Sentinel value for whether a pong or chow was called

                while(true){
                    if(!isActionTaken){
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
                    
                    boolean hasDiscarded = false;
                    while(true){
                        int[] availableOptions = {1,2,3,4};
    
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
                            availableOptions = new int[]{-1,1,2,3};
                        }
                        System.out.print("Select option: ");
                        option = input.nextInt();
                        input.nextLine();
                        if(!hasDiscarded && option == availableOptions[0]){
                            while(true) {
                                System.out.print("Select which tile position to discard: ");
                                int pos = input.nextInt();
                                if(pos >= 1 && pos <= me.GetCurrPlayer().GetConcealedTiles().length){
                                    me.DiscardTile(me.GetCurrPlayer(), pos);
                                    hasDiscarded = true;
                                    break;
                                } else{
                                    System.out.println("ERROR: Invalid tile position");
                                }
                            }
                            disp.DisplayBoard(me.GetCurrPlayer(), leftPlayer, rightPlayer, upPlayer, me.GetLiveDiscard());
                        } else if(option == availableOptions[1]){
                            while(true){
                                System.out.print("Arrange tiles (0: quit, a: auto sort): ");
                                String arrangePos = input.nextLine().toLowerCase();
                                String[] splitPos = arrangePos.split(" ");
                                
                                if(arrangePos.equals("0")){
                                    System.out.println();
                                    break;
                                } else if(arrangePos.equals("a")) {
                                    me.GetCurrPlayer().AutoSortHand();
                                    System.out.println();
                                    disp.DisplayHand(me.GetCurrPlayer(), "");
                                } else if(splitPos.length == 2){
                                    int pos1 = Integer.parseInt(splitPos[0])-1;
                                    int pos2 = Integer.parseInt(splitPos[1])-1;
                                    if(pos1 < 0 || pos2 < 0 || pos1 > me.GetCurrPlayer().GetConcealedTiles().length-1 || pos2 > me.GetCurrPlayer().GetConcealedTiles().length-1){
                                        System.out.println(pos1 + " "+pos2);
                                        System.out.println("ERROR: Invalid tile position");
                                    } else{
                                        System.out.println();
                                        me.GetCurrPlayer().ArrangeHand(pos1, pos2);
                                        disp.DisplayHand(me.GetCurrPlayer(), "");
                                    }
                                } else{
                                    System.out.println("ERROR: Invalid input");
                                }
                            }
                        } else if(option == availableOptions[2]){
                            boolean isWin = me.Mahjong(me.GetCurrPlayer(), null);
                            if(isWin){
                                hasPlayerWon = true;
                                isActionTaken = true;
                                break;
                            } else{
                                System.out.println("You do not have a winning hand. "+me.GetCurrPlayer().DisplayPlayer()+" will be removed from the game");
                                isActionTaken = true;
                                break;
                            }
                        } else if(option == availableOptions[3]){
                            if(hasDiscarded){
                                System.out.println(me.GetCurrPlayer().DisplayPlayer()+" has ended their turn");
                                break;
                            } else{
                                System.out.println("ERROR: You must discard a tile before ending your turn\n");
                            }
                        } else{
                            System.out.println("ERROR: Invalid input\n");
                        }
                    }        

                    if(me.IsGameOver() == MahjongEngine.EMPTY_WALL){
                        hasGameEnded = true;
                    }

                    while(isPlayerAction && !hasGameEnded && !hasPlayerWon){
                        System.out.println();
                        disp.DisplayDiscard(me.GetLiveDiscard());
                        Player[] otherPlayers = me.OtherPlayers(me.GetCurrPlayer());
                        int numOtherPlayers = otherPlayers.length;
                        System.out.println("Do any other players want to perform an action?");
                        for (int i = 1; i <= otherPlayers.length; i++) {
                            System.out.println(i+". "+otherPlayers[i-1].DisplayPlayer());
                        }
                        System.out.println((numOtherPlayers+1)+". No, go to next turn");
                        System.out.print("Select option: ");
                        option = input.nextInt();

                        
                        if(option == numOtherPlayers+1){
                            break;
                        } else if(option >= 1 && option <= numOtherPlayers){
                            Player activePlayer = otherPlayers[option-1];

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
                                
                                if(option == 1){
                                    int meldable = me.Meldable(activePlayer, me.GetCurrPlayer(), Tiles.PONG_MELD);
                                    if(meldable == MahjongEngine.NO_COMPLETE_MELD){
                                        System.out.println("ERROR: You do not have the tiles required to form a pong meld");
                                        break; 
                                    } else if(meldable == MahjongEngine.SUCCESSFUL){
                                        System.out.println();
                                        disp.DisplayDiscard(me.GetLiveDiscard());
                                        disp.DisplayHand(activePlayer, "");
                                        System.out.println("Select the tiles you want to use in your meld");
                                        int pos1, pos2;
                                        while(true) {
                                            System.out.print("Tile 1 position: ");
                                            pos1 = input.nextInt();
                                            if(pos1 >= 1 && pos1 <= activePlayer.GetConcealedTiles().length){
                                                break;
                                            } else{
                                                System.out.println("ERROR: Invalud tile position");
                                            }
                                        }
                                        while(true) {
                                            System.out.print("Tile 2 position: ");
                                            pos2 = input.nextInt();
                                            if(pos2 >= 1 && pos2 <= activePlayer.GetConcealedTiles().length){
                                                break;
                                            } else{
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }
                                    
                                        if(!me.Pong(activePlayer, pos1, pos2)){
                                            System.out.println("ERROR: The tiles selected are not valid for a pong meld");
                                            break; 
                                        } else{
                                            isPlayerAction = false;
                                            isActionTaken = true;
                                            System.out.println("Successful meld");
                                            me.SwitchPlayer(activePlayer);
                                            break;
                                        }
                                    }
                                } else if(option == 2){
                                    int meldable = me.Meldable(activePlayer, me.GetCurrPlayer(), Tiles.CHOW_MELD);
                                    if(meldable == MahjongEngine.INCORRECT_POSITION){
                                        System.out.println("ERROR: You are not in the correct sitting position to make a chow call");
                                        break;
                                    } else if(meldable == MahjongEngine.NO_COMPLETE_MELD){
                                        System.out.println("ERROR: You do not have the tiles required to form a chow meld");
                                        break;
                                    } else if(meldable == MahjongEngine.SUCCESSFUL){
                                        System.out.println();
                                        disp.DisplayDiscard(me.GetLiveDiscard());
                                        disp.DisplayHand(activePlayer, "");
                                        System.out.println("Select the tiles you want to use in your meld");
                                        int pos1, pos2;
                                        while(true) {
                                            System.out.print("Tile 1 position: ");
                                            pos1 = input.nextInt();
                                            if(pos1 >= 1 && pos1 <= activePlayer.GetConcealedTiles().length){
                                                break;
                                            } else{
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }
                                        while(true) {
                                            System.out.print("Tile 2 position: ");
                                            pos2 = input.nextInt();
                                            if(pos2 >= 1 && pos2 <= activePlayer.GetConcealedTiles().length){
                                                break;
                                            } else{
                                                System.out.println("ERROR: Invalid tile position");
                                            }
                                        }
                                    
                                        if(!me.Chow(activePlayer, pos1, pos2)){
                                            System.out.println("ERROR: The tiles selected are not valid for a chow meld");
                                            break; 
                                        } else{
                                            isPlayerAction = false;
                                            isActionTaken = true;
                                            me.SwitchPlayer(activePlayer);
                                            System.out.println("Successful meld");
                                            break;
                                        }
                                    }
                                } else if(option == 3){
                                    boolean isWin = me.Mahjong(activePlayer, me.GetLiveDiscard());
                                    if(isWin){
                                        me.SwitchPlayer(activePlayer);
                                        isActionTaken = true;
                                        hasPlayerWon = true;
                                    } else{
                                        System.out.println("You do not have a winning hand. "+activePlayer.DisplayPlayer()+" will be removed from the game");
                                        System.out.print("Press enter to proceed ");
                                        input.nextLine(); // User buffer
                                    }
                                    isPlayerAction = false;
                                    break;
                                } else if(option == 4){
                                    break;
                                } else{
                                    System.out.println("ERROR: Invalid input");
                                }
                            }

                        } else{
                            System.out.println("ERROR: Invalid input");
                        }
                    }

                    if(me.IsGameOver() == MahjongEngine.NO_PLAYERS){
                        hasGameEnded = true;
                    }

                    if(hasPlayerWon || hasGameEnded){
                        break;
                    }
                    
                    if(!isActionTaken){
                        me.SwitchPlayer();
                    }
                }

                if(hasGameEnded){
                    System.out.println();
                    if(me.IsGameOver() == MahjongEngine.EMPTY_WALL){
                        System.out.println("The game has ended in a tie as the wall has ran out of tiles. We are all winners yay!");
                    } else{
                        System.out.println("All other players have been eliminated so "+me.GetCurrPlayer().DisplayPlayer()+" has won the game by default");
                    }
                } else if(hasPlayerWon){
                    // Calculate the position of each player relative to the current player
                    int currPlayerIndex = me.FindPlayerIndex(me.GetCurrPlayer());
                    Player leftPlayer = me.GetPlayers()[(currPlayerIndex+3)%4];
                    Player rightPlayer = me.GetPlayers()[(currPlayerIndex+1)%4];
                    Player upPlayer = me.GetPlayers()[(currPlayerIndex+2)%4];

                    disp.DisplayBoard(me.GetCurrPlayer(), leftPlayer, rightPlayer, upPlayer, me.GetLiveDiscard());
                    System.out.println(me.GetCurrPlayer().DisplayPlayer()+" has won the Mahjong game!");
                }
                System.out.print("Press enter to continue to main menu ");
                input.nextLine(); // Creates a user buffer before resuming program
                System.out.println();
            } else if(option == 2){ // Start tutorial
                
            } else if(option == 3){ // Exit program
                System.out.println("Thank you for playing. Come again soon!");
                break;
            } else{ // Invalid input
                System.out.println("ERROR: Invalid input\n");
            } 
        }
    }
}


// System.out.println("\n- There are 136 tiles that will be drawn to 4 players with 13 tiles each player. ");
// System.out.println("- The tiles consists of 4 copies of 3 suits: BAMBOOS, DOTS, and CHARACTERS. Which go from 1 to 9");
// System.out.println("- Each player will take turns discarding any tile from their hand and placing it into the discard tile and taking a tile from the wall or from the discard tile if it satisfies the following condition:\n");
// System.out.println("1. Player calls PONG. A pong meld are 3 identical tiles.");
// System.out.println("2. Player calls CHOW. A chow meld is 3 consecutive tiles of the same suit. This tile can only be taken if it was discarded by the previous player");
// System.out.println("- If a player takes a tile from the discard pile, the meld must be displayed on the table for everyone to see");
// System.out.println("\nWinning:\n A player must have a winning hand which consists of:\n 4 melds, (either Pong or Chow) and an eye (2 identical tiles)");
// System.out.println("When a player completes a winning hand, player must call MAHJONG in order to win.\n IF the player calls MAHJONG and the hand is not a winning hand. The player will be disqualified.");
// System.out.println("\nAll players LOSE if there are no more tiles on the wall and no one has won yet.");
