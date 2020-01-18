public class Fou extends Piece{


    /**
     * Crée un fou.
     * @param x
     * @param y
     * @param parite
     */
    public Fou(int x, int y, boolean parite){
        this.x=x;
        this.y=y;
        this.parite=parite;
    }

    /**
     * Analyse les déplacements possibles du fou en fonction de son environnement.
     * @param tab_p
     * @return
     */
    public boolean[] deplacement(Piece[] tab_p) { //Penser à créer un constructeur de confirmation{
        boolean[] tab_bol= new boolean[64];

        int[] tab_y = {1, -1, 1, -1}; //8 pour la partie verticale && 1 pour la partie horizontale
        int[] tab_x = {1, -1, -1, 1};
        int j = 0;

        while (j < tab_y.length) {
            int y = this.y + (tab_y[j]);
            int x = this.x + (tab_x[j]);
            boolean peut_passer = true;

            while (this.mouvementValide(x,y) && peut_passer) {
                if (tab_p[(8 * y) + x] != null ) {
                    if (tab_p[(y * 8) + x].getParite() == this.parite) {
                        tab_bol[(8 * y) + x] = false;
                        peut_passer = false;
                    }

                    if (tab_p[(y*8)+x].getParite()!=this.parite){
                        tab_bol[(8*y) + x] = true ; //à Corriger
                        peut_passer=false;
                    }
                }
                else
                    tab_bol[(8*y) + x] = true ; //à Corriger

                y = y + tab_y[j];
                x = x + tab_x[j];
            }
            j++;
        }
        return tab_bol;
    }

    /**
     * Affiche le fou.
     * @return
     */

    public String toString() {
        if (this.getParite() == true)
            return "♗";
        return "♝";
    }

}