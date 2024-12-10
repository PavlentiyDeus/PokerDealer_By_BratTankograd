import java.util.*;
public class PokerGame {
    public static void main(String[] args) {

        System.out.println("Hello ladies and gentelmens, welcome to poker card game");
        Dealer dealer = new CurrentDealer();
        Board board = dealer.dealCardsToPlayers();
        System.out.println(board.toString());
        Scanner response = new Scanner(System.in);
        System.out.println("выдать flop?(нажмите y и enter для подтверждения)");
        if(response.nextLine().equals("y"))
        {
            board = dealer.dealFlop(board);
            System.out.println(board.toString());

        }
        else System.out.println("!1");
        Scanner response2 = new Scanner(System.in);
        System.out.println("выдать turn?(нажмите y и enter для подтверждения)");
        if((response2.nextLine() + "").equals("y"))
        {
            board = dealer.dealTurn(board);
            System.out.println(board.toString());

        }
        else System.out.println("!2");
        Scanner response3 = new Scanner(System.in);
        System.out.println("выдать river?(нажмите 'y' и enter для подтверждения)");
        if(response3.nextLine().equals("y"))
        {
            board = dealer.dealRiver(board);
            System.out.println(board.toString());

        }
        else System.out.println("!");

        dealer.decideWinner(board);
    }
}