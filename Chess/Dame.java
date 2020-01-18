public class Dame extends Piece {

    /**
     * Crée une dame.
     * @param x
     * @param y
     * @param parite
     */

    public Dame(int x, int y, boolean parite) {
        this.x = x;
        this.y = y;
        this.parite = parite;
    }

    /**
     * Analyse les déplacements possibles de la dame en fonction de son environnement.
     * @param tab_p
     * @return
     */
    public boolean[] deplacement(Piece[] tab_p) { //Penser à créer un constructeur de confirmation{
        boolean[] tab_bol = new boolean[64];

        int[] tab_x = {0, 1, 0, -1};
        int[] tab_y = {1, 0, -1, 0};
        int j = 0; //Iterateur

        while (j < tab_y.length) {
            int y = this.y + (tab_y[j]);
            int x = this.x + (tab_x[j]);
            boolean peut_passer = true;

            while (mouvementValide(x, y) && peut_passer) {
                if (tab_p[(8 * y) + x] != null) {
                    if (tab_p[(y * 8) + x].getParite() != this.parite) {
                        tab_bol[(8 * y) + x] = true;
                        peut_passer = false;
                    }


                    if (tab_p[(y * 8) + x].getParite() == this.parite) {
                        tab_bol[(8 * y) + x] = false; //à Corriger
                        peut_passer = false;
                    }
                } else
                    tab_bol[(8 * y) + x] = true; //à Corriger

                y = y + tab_y[j];
                x = x + tab_x[j];
            }
            j++;
        }

        int[] tab_y_bis = {1, -1, 1, -1}; //8 pour la partie verticale && 1 pour la partie horizontale
        int[] tab_x_bis = {1, -1, -1, 1};
        int j_bis = 0;

        while (j_bis < tab_y_bis.length) {
            int y_bis = this.y + (tab_y_bis[j_bis]);
            int x_bis = this.x + (tab_x_bis[j_bis]);
            boolean peut_passer_bis = true;

            while (mouvementValide(x_bis, y_bis) && peut_passer_bis) {
                if (tab_p[(8 * y_bis) + x_bis] != null) {
                    if (tab_p[(y_bis * 8) + x_bis].getParite() == this.parite) {
                        tab_bol[(8 * y_bis) + x_bis] = false;
                        peut_passer_bis = false;
                    }

                    if (tab_p[(y_bis * 8) + x_bis].getParite() != this.parite) {
                        tab_bol[(8 * y_bis) + x_bis] = true; //à Corriger
                        peut_passer_bis = false;
                    }
                }
                else
                    tab_bol[(8 * y_bis) + x_bis] = true; //à Corriger

                y_bis = y_bis + tab_y_bis[j_bis];
                x_bis = x_bis + tab_x_bis[j_bis];
            }
            j_bis++;
        }
        return tab_bol;
    }

    /**
     * Affiche la dame.
     * @return
     */
    public String toString() {
        if (this.getParite() == true)
            return "♕";
        return "♛";
    }

}
