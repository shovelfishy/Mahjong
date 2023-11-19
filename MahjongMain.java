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
        Displayer disp = new Displayer(me);
        Scanner input = new Scanner(System.in);
        boolean TEST = false;


        if(true){
    
            me.DrawTile();
            disp.DisplayBoard(me.GetCurrPlayer());
            System.out.println();
            
            boolean hasDiscarded = false;
            while(true){
                int[] availableOptions = {1,2,3,4,5};

                System.out.println("What would you like to do? ");
                if(!hasDiscarded){
                    System.out.println("1. Discard tile");
                    System.out.println("2. Arrange tiles");
                    System.out.println("3. Chow");
                    System.out.println("4. Mahjong");
                    System.out.println("5. End turn");
                } else{
                    System.out.println("1. Arrange tiles");
                    System.out.println("2. Chow");
                    System.out.println("3. Mahjong");
                    System.out.println("4. End turn");
                    availableOptions = new int[]{-1,1,2,3,4};
                }
                System.out.print("Select option: ");
                int option = input.nextInt();
                input.nextLine();
                if(!hasDiscarded && option == availableOptions[0]){
                    System.out.print("Select which tile position to discard: ");
                    int pos = input.nextInt();
                    me.DiscardTile(me.GetCurrPlayer(), pos);
                    hasDiscarded = true;
                    disp.DisplayBoard(me.GetCurrPlayer());
                } else if(option == availableOptions[1]){
                    while(true){
                        System.out.print("Arrange tiles (0: quit, v: view): ");
                        String arrangePos = input.nextLine().toLowerCase();
                        String[] splitPos = arrangePos.split(" ");
            
                        if(arrangePos.equals("0")){
                            break;
                        } else if(arrangePos.equals("v")) {
                            disp.DisplayBoard(me.GetCurrPlayer());
                        } else if(splitPos.length == 2){
                            int pos1 = Integer.parseInt(splitPos[0])-1;
                            int pos2 = Integer.parseInt(splitPos[1])-1;
                            me.GetCurrPlayer().ArrangeHand(pos1, pos2);
                        } else{
                            System.out.println("ERROR: Invalid input\n");
                        }
                    }
                } else if(option == availableOptions[2]){
                    
                } else if(option == availableOptions[3]){
                    
                } else if(option == availableOptions[4]){
                    if(hasDiscarded){
                        System.out.println(disp.displayPlayer(me.GetCurrPlayer().GetWind())+" has ended their turn");
                        break;
                    } else{
                        System.out.println("ERROR: You must discard a tile before ending your turn");
                    }
                } else{
                    System.out.println("ERROR: Invalid input");
                }
            }
    
    
            disp.DisplayBoard(me.GetCurrPlayer());
        }

        while(TEST) {
            System.out.println("Welcome To Mahjong!");
            System.out.println("1. Start Game");
            System.out.println("2. Tutorial");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = input.nextInt();
    
            if(option == 1){ // Start game

            } else if(option == 3){ // Start tutorial
                
            } else if(option == 3){ // Exit program
                System.out.println("Thank you for visiting. Come back soon!");
                break;
            } else{ // Invalid input
                System.out.println("ERROR: Invalid input");
            } 
        }
    }
}