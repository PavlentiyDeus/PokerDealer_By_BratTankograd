import java.util.*;

public class GenerationCard {
    private String currentCard;
    public GenerationCard(List<String> cardsInGame)
    {
        Random rand = new Random();
            do{
                int gen1 = rand.nextInt(13);
                int gen2 = rand.nextInt(4);
                NominalCardPicture n;
                switch (gen1) {
                    case (1):
                        n= NominalCardPicture.J;
                        currentCard =n.toString();

                        break;
                    case (11):
                        n= NominalCardPicture.Q;
                        currentCard =n.toString();

                        break;
                    case (12):
                        n= NominalCardPicture.K;
                        currentCard =n.toString();

                        break;
                    case (0):
                        n= NominalCardPicture.A;
                        currentCard =n.toString();

                        break;
                    default:
                        currentCard = "" + gen1;
                }


                switch (gen2) {
                    case (0):
                        currentCard += "C";
                        break;
                    case (1):
                        currentCard += "D";
                        break;
                    case (2):
                        currentCard += "H";
                        break;
                    case (3):
                        currentCard += "S";
                        break;
                }

            }while((cardsInGame.contains(currentCard)));


    }
    public String getCurrentCard()
    {
     return currentCard;
    }
}
