import java.lang.reflect.Array;
import java.util.*;
import  java.util.stream.*;

public class CurrentDealer implements Dealer{

    public List<String> cardsInGame = new ArrayList<>();


    public CurrentDealer()
    {
//        Генерация случайных неповторяющихся карт
        for (int i =0;i<9;i++)
        {
            GenerationCard card = new GenerationCard(cardsInGame);
            cardsInGame.add(card.getCurrentCard());
        }

//проверка на ФлэшРоял(не работает по неизвестной причине без исключений и ошибок)
//        cardsInGame=Stream.of("2H", "AH", "AC", "9H","10H","AS","QH","KH","JH")
//                .collect(Collectors.toList());;


//проверка на высший стритФлэш
//        cardsInGame=Stream.of("8C", "JH", "7H", "6H","8H","10H","QH","KH","9H")
//                .collect(Collectors.toList());;
        //СтритФлэш с AH до 5H
//        cardsInGame=Stream.of("2H", "AH", "6H", "7H","3H","4H","5H","KC","JD")
//                .collect(Collectors.toList());;



//        проверка на высший Флэш
//        cardsInGame=Stream.of("2H", "AH", "KH", "6H","8C","10S","QH","3H","9H")
//                .collect(Collectors.toList());;


//        проверка на высший Стрит
//        cardsInGame=Stream.of("JD", "JS", "7H", "6S","8S","10C","QD","9H","QC")
//                .collect(Collectors.toList());;

//        проверка на высшую ФуллХаус
//        cardsInGame=Stream.of("3D", "4S", "5H", "5S","5D","4C","3C","3H","KH")
//                .collect(Collectors.toList());;
//                проверка на Старшинство пары ФуллХаус
//        cardsInGame=Stream.of("3D", "5S", "5H", "9S","5D","AC","5C","3H","9H")
//                .collect(Collectors.toList());;

    }
    public String [] playerOneScore ={"","","","","","","","","",""};
//    public List <String> playerTwoScore = new ArrayList<>();
    public String [] playerTwoScore ={"","","","","","","","","",""};


    @Override
    public Board dealCardsToPlayers() {
        return new Board(cardsInGame.get(0)+cardsInGame.get(1), cardsInGame.get(2)+cardsInGame.get(3), null, null, null);
    }

    @Override
    public Board dealFlop(Board board) {

        return new Board(cardsInGame.get(0)+cardsInGame.get(1), cardsInGame.get(2)+cardsInGame.get(3), cardsInGame.get(4)+cardsInGame.get(5)+cardsInGame.get(6), null, null);

    }

    @Override
    public Board dealTurn(Board board) {
        return new Board(cardsInGame.get(0)+cardsInGame.get(1), cardsInGame.get(2)+cardsInGame.get(3), cardsInGame.get(4)+cardsInGame.get(5)+cardsInGame.get(6), cardsInGame.get(7), null);
    }

    @Override
    public Board dealRiver(Board board) {
        return new Board(cardsInGame.get(0)+cardsInGame.get(1), cardsInGame.get(2)+cardsInGame.get(3), cardsInGame.get(4)+cardsInGame.get(5)+cardsInGame.get(6), cardsInGame.get(7), cardsInGame.get(8));

    }

    public String cardCut( String [] cardOne)
    {
        cardOne[cardOne.length-1]="";
        StringBuilder cardCuting = new StringBuilder();
        for(String s : cardOne)
        {
            cardCuting.append(s);
        }
//        return cardOne.stream().collect(Collectors.joining(""));
        return cardCuting.toString();
    }
//    Функция возвращающая номинал карт с изображениями
    public Integer CardToNominaling(String s)
    {
        try{
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            NominalCardPicture g = NominalCardPicture.valueOf(s);
            return g.getNumber();
        }
    }

//    Функция возвращающая ассоциативный массив карт, где уникальный ключ - название, а значение - Номинал карты
    public Map <String,Integer> CardsToNominaling(Map <String,Integer> x,int playerNumber)
    {

        int checkOneCard1 = CardToNominaling(cardCut(cardsInGame.get(playerNumber).split("")));
        int checkTwoCard1 = CardToNominaling(cardCut( cardsInGame.get(playerNumber+1).split("")));

        for(int i =4;i<cardsInGame.size();i++){

            int s =CardToNominaling(cardCut(cardsInGame.get(i).split("")));

            x.put(cardsInGame.get(i),s);

        }
        x.put(cardsInGame.get(playerNumber),checkOneCard1);

        x.put(cardsInGame.get(playerNumber+1),checkTwoCard1);


        //Дополнительная запись в массив для представления туза как 1
        if(x.containsValue(14)){
            List <String> acePlusList = x.entrySet().stream().filter(s->s.getKey().startsWith("A")).map(Map.Entry::getKey).toList();
            for(String acePlus : acePlusList)

                x.put("P"+acePlus.split("")[(acePlus.split("").length)-1],1);
        }
        return x;
    }





    //поиск всех повторяющихся номиналов первой и второй карты игрока 1 + поиск комбинаций повторяющихся номиналов на столе
    public Map<Integer, String> CheckCards(String checkOneCard1, String checkTwoCard1,int playerNumber)
    {

        Map<Integer, String> resultChecking = new HashMap<Integer, String>();
        int count1 = 0;
        int count2 = 0;
        List <String> cardsWithOneCard1=new ArrayList<>();
        cardsWithOneCard1.addAll(cardsInGame.subList(4, cardsInGame.size()).stream()
                .filter(s -> s.startsWith(checkOneCard1)).toList());
        List <String> cardsWithTwoCard1=new ArrayList<>();
        cardsWithTwoCard1.addAll(cardsInGame.subList(4, cardsInGame.size()).stream()
                .filter(s -> s.startsWith(checkTwoCard1)).toList());
        count1 += cardsWithOneCard1.size();

        if (CardToNominaling(checkOneCard1) == CardToNominaling(checkTwoCard1)) count1++;

        else {
            count2 = cardsWithTwoCard1.size();
        }

        switch (count1) {
            case (3):
                resultChecking.put(7, cardsInGame.get(playerNumber));
                break;

            case (1): {
                if (CardToNominaling(checkOneCard1) < CardToNominaling(checkTwoCard1)) {
                    resultChecking.put(1 , cardsInGame.get(playerNumber));
                    switch (count2) {
                        case (1): {
                            resultChecking.put(2, cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case (2): {
                            resultChecking.put(6, cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case (3): {
                            resultChecking.put(7,  cardsInGame.get(playerNumber+1));
                        }
                        break;
                    }
                } else if (CardToNominaling(checkOneCard1) > CardToNominaling(checkTwoCard1)) {

                    switch (count2) {
                        case (0): {
                            resultChecking.put(1, cardsInGame.get(playerNumber));
                        }
                            break;
                        case (1): {
                            resultChecking.put(2, cardsInGame.get(playerNumber));
                            resultChecking.put(1, cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case (2): {
                            resultChecking.put(6, cardsInGame.get(playerNumber+1));
                            resultChecking.put(1,cardsInGame.get(playerNumber));
                        }
                        case (3): {
                            resultChecking.put(1, cardsInGame.get(playerNumber));
                            resultChecking.put(7, cardsInGame.get(playerNumber+1));
                        }
                    }
                }
            }
            break;
            case (2): {
                if (CardToNominaling(checkOneCard1) < CardToNominaling(checkTwoCard1))
                {
                    switch (count2) {
                        case (1): {

                            resultChecking.put(6,cardsInGame.get(playerNumber));
                            resultChecking.put(1, cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case (2): {
                            resultChecking.put(1,cardsInGame.get(playerNumber));
                            resultChecking.put(6,cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case (3): {
                            resultChecking.remove(7);
                            resultChecking.put(7,cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case(0):{resultChecking.put(3, cardsInGame.get(playerNumber));}
                        break;
                    }
                }
                else if (CardToNominaling(checkOneCard1) > CardToNominaling(checkTwoCard1))
                {
                    switch (count2) {
                        case (0):
                            resultChecking.put(3, cardsInGame.get(playerNumber));
                            break;
                        case (1): {
                            resultChecking.put(6,cardsInGame.get(playerNumber));
                            resultChecking.put(1,cardsInGame.get(playerNumber+1));
                        }
                        break;
                        case (2): {
                            resultChecking.put(3,cardsInGame.get(playerNumber));
                        }
                        case (3): {

                            resultChecking.put(3,cardsInGame.get(playerNumber));
                            resultChecking.put(7,cardsInGame.get(playerNumber+1));
                        }
                    }
                }
                else
                    resultChecking.put(3,cardsInGame.get(playerNumber));
            }
            break;
            case (0):
            {


                switch (count2){
                    case(1):
                    {
                        resultChecking.put(1,cardsInGame.get(playerNumber+1));
                    }break;

                    case(2):
                    {
                        resultChecking.put(3,cardsInGame.get(playerNumber+1));
                    }break;

                    case(3):
                    {
                        resultChecking.put(7,cardsInGame.get(playerNumber+1));
                    }break;
                }
            }
            break;
        }

        if (CardToNominaling(checkOneCard1) < CardToNominaling(checkTwoCard1))
            resultChecking.put(0,cardsInGame.get(playerNumber+1));
        else  resultChecking.put(0,cardsInGame.get(playerNumber));

        Map <String,Integer []> supportListCombinationsCards =  new HashMap<>();
//        String IterationCardString=new String();

        for (int i=(cardsInGame.size()-1);i>3;i--)
        {
            String oneCardInGame =cardsInGame.get(i);
            String IterationSupportArray = cardCut(oneCardInGame.split(""));
            List<String> IterationSupportArray2 =cardsInGame.subList(4, cardsInGame.size()).stream()
                    .filter(s -> s.startsWith(IterationSupportArray)).toList();
            int rangCard =IterationSupportArray2.size();

            supportListCombinationsCards.put(oneCardInGame,new Integer[]{rangCard,CardToNominaling(IterationSupportArray)});

        }
        List <Integer> previousMarkedCards=new ArrayList<>();
        previousMarkedCards.add(CardToNominaling(checkOneCard1));
        previousMarkedCards.add(CardToNominaling(checkTwoCard1));

        CardsIterator:for (Map.Entry<String,Integer[]> entry : supportListCombinationsCards.entrySet() )
        {
            int rang = entry.getValue()[0];
            int activeCardNominal =entry.getValue()[1];
            if(rang>0) {
                for(int card : previousMarkedCards){if(card==activeCardNominal)continue CardsIterator;}
                previousMarkedCards.add(activeCardNominal);
            }
                switch (rang)
                {
                    case(2):{
                        if(resultChecking.containsKey(2))
                        {
                            if(CardToNominaling(cardCut(resultChecking.get(2).split("")))>activeCardNominal)
                            {

                                if (CardToNominaling(cardCut(resultChecking.get(1).split(""))) < activeCardNominal)
                                {
                                    resultChecking.replace(1, entry.getKey());
                                }

                            }
                            else
                            {
                                resultChecking.replace(1,resultChecking.get(2));
                                resultChecking.replace(2, entry.getKey());
                            }
                        }
                        else if(resultChecking.containsKey(1))
                        {
                            if (CardToNominaling(cardCut(resultChecking.get(1).split(""))) < activeCardNominal)
                            {
                                resultChecking.put(2, entry.getKey());
                            }
                            else
                            {
                                resultChecking.put(2,resultChecking.get(1));
                                resultChecking.replace(1,entry.getKey());
                            }
                        }
                        else if(resultChecking.containsKey(3))
                        {
                            resultChecking.put(6,resultChecking.get(3));
                            resultChecking.put(1, entry.getKey());
                        }
                        else resultChecking.put(1, entry.getKey());
                    }break;

                    case(3):
                    {
                        if(resultChecking.containsKey(3))
                        {
                            if(resultChecking.containsKey(6)) {
                                if (CardToNominaling(cardCut(resultChecking.get(6).split(""))) > activeCardNominal)
                                {
                                    if (CardToNominaling(cardCut(resultChecking.get(1).split(""))) < activeCardNominal)
                                    {
                                        resultChecking.replace(1, entry.getKey());
                                    }
                                }
                                else
                                {
                                    resultChecking.replace(1, resultChecking.get(6));
                                    resultChecking.replace(6, entry.getKey());
                                    resultChecking.replace(3,entry.getKey());
                                }
                            }
                            else
                            {
                                if(CardToNominaling(cardCut(resultChecking.get(3).split("")))>activeCardNominal)
                                {
                                    resultChecking.put(6,resultChecking.get(3));
                                    resultChecking.replace(1,entry.getKey());
                                }
                                else
                                {
                                    resultChecking.put(6,entry.getKey());
                                    resultChecking.put(3,entry.getKey());
                                    resultChecking.replace(1,resultChecking.get(3));
                                }
                            }
                        }
                        else
                        {
                            if(resultChecking.containsKey(1))
                            {
                                resultChecking.put(6,entry.getKey());
                                if(resultChecking.containsKey(2))
                                {
                                    if(CardToNominaling(cardCut(resultChecking.get(2).split("")))>CardToNominaling(cardCut(resultChecking.get(1).split(""))))
                                    resultChecking.replace(1,resultChecking.get(2));

                                }
                            }
                            else resultChecking.put(3,entry.getKey());
                        }

                    }
                    case (1):
                        {

                        }


                }


        }


        return resultChecking;
    }


//    поиск карт на стрит
    public String StreetFound(int playerNumber)
    {

        Map <String,Integer> x = new HashMap<>();
        x = CardsToNominaling(x,playerNumber);
        int streetCounter=0;
        List <Integer> x2 = new ArrayList<>(x.values());
        Collections.sort(x2);

        List <String> highestCardStreet = new ArrayList<>();
        for(int i=1;i<x2.size();i++)
        {
            int r = x2.get(i)-1;
            if(x2.get(i-1)==r)
            {
                if(highestCardStreet.isEmpty())
                {
                    int i2=x2.get(i-1);

                    x.entrySet().stream()
                            .filter(s->s.getValue().equals(i2))
                            .forEach(s->highestCardStreet.add(s.getKey()));
                }
                int i1=x2.get(i);
                x.entrySet().stream()
                        .filter(s->s.getValue().equals(i1))
                        .forEach(s->highestCardStreet.add(s.getKey()));
                streetCounter++;

            }
            else if (x2.get(i-1)==x2.get(i)) {
                continue;
            }
            else
            {
                if(streetCounter<4) {
                    streetCounter = 0;
                    highestCardStreet.clear();
                }
                else break;
            }
        }

        if(streetCounter>3)
        {
            return highestCardStreet.get(highestCardStreet.size()-1);
        }
        else return null;
    }

    public String StreetFlashAndRoyalFound(int playerNumber)
    {

        Map <String,Integer> SFR = new HashMap<>();
        SFR = CardsToNominaling(SFR,playerNumber);

        int streetCounter=0;
        List <Integer> x2 = new ArrayList<>(SFR.values());
        Collections.sort(x2);
        List <String> highestCardStreet = new ArrayList<>();

        for(int i=1;i<x2.size();i++)
        {
            int r = x2.get(i)-1;
            if(x2.get(i-1)==r) {
                if(highestCardStreet.isEmpty())
                {
                    int i2=r;

                    SFR.entrySet().stream()
                            .filter(s->s.getValue().equals(i2))
                            .forEach(s->highestCardStreet.add(s.getKey()));


                }
                int i1=x2.get(i);
                SFR.entrySet().stream()
                        .filter(s->s.getValue().equals(i1))
                        .forEach(s->highestCardStreet.add(s.getKey()));
                streetCounter++;

            }
            else if(x2.get(i-1)==x2.get(i))
            {
                continue;
            }

            else
            {
                if (streetCounter<4)
                {
                    streetCounter=0;
                    highestCardStreet.clear();
                }
                else break;
            }


        }


        if(streetCounter>3)
        {   int flashCounter=0;
            String strhighestcard="";

            for(int i =highestCardStreet.size()-1;i>0;i--)
            {   String cardScale=highestCardStreet.get(i);
                String cardScaleParam=cardScale.split("")[(cardScale.split("").length)-1];
                //

                List <String> SFRScales = highestCardStreet.stream()
                        .filter(s->s.endsWith(cardScaleParam)).toList();

                 flashCounter = SFRScales.size();
//                 if(SFRScales.get(SFRScales.size()-1).startsWith("A"))flashCounter--;
                 strhighestcard=SFRScales.get(SFRScales.size()-1);
                if(flashCounter>4){
                    return strhighestcard;
                }

            }
            return null;
        }
        else return null;
    }



    //Метод Поиска флэша из карт игрока и лежащих на доске

    public String FlashFound(String cardFlashFinding,int playerNumber)
    {
        String checkFlashParam = cardFlashFinding.split("")[(cardFlashFinding.split("").length)-1];

        Map <String,Integer> checkingCards=new HashMap<String,Integer>();
        for(String card:cardsInGame.subList(4,cardsInGame.size()))
        {

            checkingCards.put(card,CardToNominaling(cardCut(card.split(""))));
        }

        String secondPlayerCard = cardsInGame.get(playerNumber+1);

        checkingCards.put(secondPlayerCard,CardToNominaling(cardCut(secondPlayerCard.split(""))));
        Map <String,Integer> checkingCards2=new LinkedHashMap<>();

        for(Map.Entry<String,Integer> card:checkingCards.entrySet())
        {
            if(card.getKey().endsWith(checkFlashParam))
            {
                checkingCards2.put(card.getKey(),card.getValue());
            }
        }


        int countFlash=checkingCards2.size();
        if(countFlash>3)
        {

            checkingCards2.put(cardFlashFinding,CardToNominaling(cardCut(cardFlashFinding.split(""))));
            List <String>learn = new ArrayList<>();
            checkingCards2.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(s->learn.add(s.getKey()));

            return learn.get(learn.size()-1);
        }
        else return "";
    }


    //метод набора очков игрока
    public void SetterPointsPlayer(String [] playerScore,int playerNumber)
    {
        //!!!
        //поиск стрита
        playerScore[4]=StreetFound(playerNumber);

        //!!!
        // Поиска повторяющихся номиналов карт с определением ранга комбинации
        String checkOneCard1 = cardCut(cardsInGame.get(playerNumber).split(""));
        String checkTwoCard1 = cardCut( cardsInGame.get(playerNumber+1).split(""));
        Map <Integer,String> checkingCardsResult = CheckCards(checkOneCard1,checkTwoCard1,playerNumber);
        for(Map.Entry<Integer,String> entry : checkingCardsResult.entrySet())
            playerScore[entry.getKey()]=entry.getValue();


        //!!!
        //поиск Флэша
        String flashOne =FlashFound(cardsInGame.get(playerNumber), playerNumber);
        String flashTwo =FlashFound(cardsInGame.get(playerNumber+1), playerNumber-1);
        if(flashOne.isEmpty())
        {
            playerScore[5]=(flashTwo.isEmpty())?"":flashTwo;
        }
        else if(flashTwo.isEmpty())
        {
            playerScore[5]=flashOne;
        }
        else
        {
            playerScore[5]=
                    (CardToNominaling(cardCut(flashOne.split("")))>CardToNominaling(cardCut(flashTwo.split(""))))
                    ?flashOne:flashTwo;
        }



        if(!(playerScore[5].isEmpty())&&playerScore[4]!=null)
        {
            playerScore[8]=StreetFlashAndRoyalFound(playerNumber);
            if(playerScore[8]!=null) {
                if (playerScore[8].startsWith("A")) {
                    playerScore[9] = playerScore[8];
                }
            }
        }
    }

    @Override
    public PokerResult decideWinner(Board board) throws InvalidPokerBoardException {

    SetterPointsPlayer(playerOneScore,0);
    SetterPointsPlayer(playerTwoScore,2);

    System.out.println("First Player's Scores");
    Arrays.stream(playerOneScore).forEach(s->System.out.print("/-"+s+"-/"));
    System.out.println("\nSecond Player's Scores");
    Arrays.stream(playerTwoScore).forEach(s->System.out.print("/-"+s+"-/"));
    System.out.print("\n");
        /* проверка очков и комбинаций игроков и выигрыш */
        for(int i=9;i>-1;i--)
        {
            if((playerOneScore[i]!=""&&playerOneScore[i]!=null)&&(playerTwoScore[i]!=""&&playerTwoScore[i]!=null))
            {
                int winScorePlayerOne = CardToNominaling(cardCut(playerOneScore[i].split("")));
                System.out.println("Выигрышная комбинация игрока1 : "+PockerCombinations.valueOf("C"+i).toString()+"  с высшей картой "+playerOneScore[i]);
                System.out.println("Выигрышная комбинация игрока2 : "+PockerCombinations.valueOf("C"+i).toString()+"  с высшей картой "+playerTwoScore[i]);

                int winScorePlayerTwo = CardToNominaling(cardCut(playerTwoScore[i].split("")));
                System.out.println("номинал выигрышной карты для дальнейшего сравнения -"+winScorePlayerOne);
                System.out.println("номинал выигрышной карты для дальнейшего сравнения -"+winScorePlayerTwo);

                int differenceScores = winScorePlayerOne-winScorePlayerTwo;
                if (differenceScores==0)
                { System.out.println("на доске общая комбинация");
                    if(i==6||i==2)
                    {
                        int doubleValueCombinationPlayerOne = CardToNominaling(cardCut(playerOneScore[1].split("")));
                        int doubleValueCombinationPlayerTwo = CardToNominaling(cardCut(playerTwoScore[1].split("")));
                        if(doubleValueCombinationPlayerOne>doubleValueCombinationPlayerTwo)
                        {
                            System.out.println("Выигрышная комбинация игрока1 : "+PockerCombinations.valueOf("C"+i).toString()+"  с высшей картой "+playerOneScore[i]);
                            System.out.println(PokerResult.PLAYER_ONE_WIN.toString());

                        }
                        else if(doubleValueCombinationPlayerOne<doubleValueCombinationPlayerTwo)
                        {
                            System.out.println("Выигрышная комбинация игрока1 : "+PockerCombinations.valueOf("C"+i).toString()+"  с высшей картой "+playerOneScore[i]);
                            System.out.println(PokerResult.PLAYER_TWO_WIN.toString());

                        }

                    }
                    else {
                        if (CardToNominaling(cardCut(playerOneScore[0].split(""))) > CardToNominaling(cardCut(playerTwoScore[0].split("")))) {
                            System.out.println(PokerResult.PLAYER_ONE_WIN.toString());

                        } else if (CardToNominaling(cardCut(playerOneScore[0].split(""))) == CardToNominaling(cardCut(playerTwoScore[0].split("")))) {
                            System.out.println("Обе комбинации совпадают!");
                            System.out.println(PokerResult.DRAW.toString());

                        } else System.out.println(PokerResult.PLAYER_TWO_WIN.toString());

                    }
                }
                else if(differenceScores>0)
                {
                    System.out.println(PokerResult.PLAYER_ONE_WIN.toString());

                    break;
                }
                else if(differenceScores<0)
                {
                    System.out.println(PokerResult.PLAYER_TWO_WIN.toString());

                    break;
                }
            }
            else if(playerOneScore[i]!=""&&playerOneScore[i]!=null)
            {
                System.out.println(PokerResult.PLAYER_ONE_WIN.toString());

                System.out.println("Выигрышная комбинация игрока1 : "+PockerCombinations.valueOf("C"+i).toString()+"  с высшей картой "+playerOneScore[i].toString());
                int winScorePlayerOne = CardToNominaling(cardCut(playerOneScore[i].split("")));
                break;
            }
            else if(playerTwoScore[i]!=""&&playerTwoScore[i]!=null)
            {
                System.out.println(PokerResult.PLAYER_TWO_WIN.toString());
                System.out.println("Выигрышная комбинация игрока1 : "+PockerCombinations.valueOf("C"+i).toString()+"  с высшей картой "+playerOneScore[i].toString());
                int winScorePlayerTwo = CardToNominaling(cardCut(playerTwoScore[i].split("")));

                break;

            }
        }





        return PokerResult.DRAW;
    }
}
