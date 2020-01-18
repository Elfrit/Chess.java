public class Cavalier extends Piece{

    /**
     * Crée un cavalier.
     * @param x
     * @param y
     * @param parite
     */
    public Cavalier(int x, int y, boolean parite){
        this.x=x;
        this.y=y;
        this.parite=parite;
    }

    /**
     * Analyse les déplacements possibles du cavalier en fonction de son environnement.
     * @param tab_p
     * @return
     */
    public boolean[] deplacement(Piece[] tab_p) { //Penser à créer un constructeur de confirmation{
        boolean[] tab_bol= new boolean[64];
        int[] tab_y = {-2,-2,-1,1,2,2,1,-1}; //Toutes les directions possible avec x et y
        int[] tab_x = {-1,1,2,2,1,-1,-2,-2};

        int j = 0; //Iterateur
        int x;
        int y;

        while (j < tab_y.length) {
            x=this.x+tab_x[j];
            y=this.y+tab_y[j];

            if (mouvementValide(x,y)){
                //positionCase += this.y*tab[i] + this.x + tab_bis[i];
                if (tab_p[(y*8)+x]!=null) {
                    if (tab_p[(y * 8) + x].getParite() == this.parite)
                        tab_bol[(y * 8) + x] = false;
                    if (tab_p[(y * 8) + x].getParite() != this.parite)
                        tab_bol[(y * 8) + x] = true;
                }

                else
                    tab_bol[(y * 8) + x] = true;
            }
            j++;
        }
        return tab_bol;
    }

    /**
     * Affiche le cavalier.
     * @return
     */
    public String toString() {
        if (this.getParite() == true)
            return "♘";
        return "♞";
    }

}