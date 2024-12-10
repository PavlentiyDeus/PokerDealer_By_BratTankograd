public enum PockerCombinations {
    C0(0,"Старшая карта"),
    C1(1,"Старшая пара карт"),
    C2(2,"Две пары карт"),
    C3(3,"Сет(Тройка)"),
    C4(4,"Стрит"),
    C5(5,"Флэш"),
    C6(6,"ФуллХаус"),
    C7(7,"Каре"),
    C8(8,"СтритФлэш"),
    C9(9,"ФлэшРояль:)");
    private int rang;
    private String name;

    PockerCombinations(int rang,String name) {
        this.rang=rang;
        this.name=name;
    }


    @Override
    public String toString()
    {
        return name;
    }


}
