public enum NominalCardPicture {
    P(1),
    J(11),
    Q(12),
    K(13),
    A(14);
    int nominalNumber;

    NominalCardPicture(int nominalNumber)
    {
        this.nominalNumber= nominalNumber;
    }

    public int getNumber()
    {
        return nominalNumber;


    }

    public static NominalCardPicture getNCPByNominal(int nominal) {
        for (NominalCardPicture NCP : values()) {

            if (NCP.getNumber()==nominal) {
                return NCP;
            }
        }


        throw new IllegalArgumentException("No card found with nominal: [" + nominal + "]");
    }
}
