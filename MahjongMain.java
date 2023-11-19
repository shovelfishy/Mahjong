import java.util.Arrays;
import java.util.Scanner;

public class MahjongMain {

    public static void main(String[] args) {
        MahjongEngine me = new MahjongEngine();
        Displayer disp = new Displayer(me);
        Scanner input = new Scanner(System.in);
        boolean TEST = false;
        me.SetLiveDiscard(new Bamboo(9));


        Tiles[] a = {new Character(2),new Character(2),new Character(2),new Dot(7),new Dot(7),new Dot(7),new Character(4),new Character(5),new Character(3),new Bamboo(6),new Bamboo(7),new Bamboo(8),new Honors(Honors.WHITE_HONOR),new Honors(Honors.GREEN_HONOR)};
        Player player = new Player(0, null);
        player.SetConcealedTiles(a);

        System.out.println(player.CheckWinningHand());

        Tiles[] winHand = {new Character(2),new Character(2),new Character(2),new Dot(7),new Dot(7),new Dot(7),new Character(4),new Character(5),new Character(3),new Bamboo(6),new Bamboo(7),new Bamboo(8),new Honors(Honors.EAST_HONOR),new Honors(Honors.EAST_HONOR)};
        
        Tiles[] NOTwinHand = {new Character(2),new Character(2),new Character(2),new Dot(7),new Dot(7),new Dot(7),new Character(4),new Character(5),new Character(3),new Bamboo(6),new Bamboo(7),new Bamboo(8),new Honors(Honors.EAST_HONOR),new Honors(Honors.WEST_HONOR)};

        
        

        if(TEST){

            // me.player1.SetExposedMelds(new Tiles[][]{{new Character(1),new Character(1),new Character(1)},{new Dot(5),new Dot(6),new Dot(7)},{new Character(1),new Character(1),new Character(1)}});
            // me.player2.SetExposedMelds(new Tiles[][]{{new Character(1),new Character(1),new Character(1)},{new Dot(5),new Dot(6),new Dot(7)},{new Character(1),new Character(1),new Character(1)}});
            // me.player3.SetExposedMelds(new Tiles[][]{{new Character(1),new Character(1),new Character(1)}});
            // me.player4.SetExposedMelds(new Tiles[][]{{new Character(9),new Character(2),new Character(2)},{new Character(9),new Character(2),new Character(2)}});
    
            me.DrawTile();
            disp.DisplayBoard(me.GetCurrPlayer());
    
    
            System.out.print("Select which tile to discard: ");
            int pos = input.nextInt();
            me.DiscardTile(me.GetCurrPlayer(), pos);
            disp.DisplayBoard(me.GetCurrPlayer());
            input.nextLine();
    
            while(true){
                System.out.print("Arrange tiles: ");
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