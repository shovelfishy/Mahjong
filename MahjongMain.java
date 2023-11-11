import java.util.Arrays;

public class MahjongMain {

    public static void main(String[] args) {
        MahjongEngine me = new MahjongEngine();
        Displayer disp = new Displayer();

        Tiles[] hand = {new Character(2),new Character(3),new Character(9), new Character(9), new Dot(2), new Dot(2), new Character(4), new Dot(3)};

        me.player2.SetHand(hand);
        me.SetLiveDiscard(new Dot(2));
        int a = me.GetLiveDiscard().CheckMeld(me.player2.GetHand());
        System.out.println(a);
    }
}