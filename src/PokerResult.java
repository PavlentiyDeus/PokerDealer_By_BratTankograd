public enum PokerResult {
    DRAW("комбинации игроков одинаковые"),
    PLAYER_ONE_WIN("выиграл первый игрок"),
    PLAYER_TWO_WIN("выиграл второй игрок");
    String descriptionPockerResult;

    PokerResult(String descriptionPockerResult) {
        this.descriptionPockerResult = descriptionPockerResult;
    }
    public String toString()
    {
        return descriptionPockerResult;
    }
}
