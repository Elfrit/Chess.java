public class Tour extends Piece{

    /**
     * Crée une tour.
     * @param x
     * @param y
     * @param parite
     */
    public Tour(int x, int y, boolean parite){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=true;
    }

    public Tour(int x, int y, boolean parite, boolean poseInit){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=poseInit;
    }


    /**
     * Analyse les déplacements possibles de la tour en fonction de son environnement.
     * @param tab_p
     * @return
     */
    public boolean[] deplacement(Piece[] tab_p) { //Penser à créer un constructeur de confirmation{
        boolean[] tab_bol= new boolean[64];
        int[] tab_x ={0, 1, 0, -1};
        int[] tab_y ={1, 0, -1, 0};
        int j = 0; //Iterateur

        while (j < tab_y.length) {
            int y = this.y + (tab_y[j]);
            int x = this.x + (tab_x[j]);
            boolean peut_passer = true;

            while (mouvementValide(x,y) && peut_passer){
                if (tab_p[(8*y) + x]!=null) {
                    if (tab_p[(y * 8) + x].getParite() != this.parite) {
                        tab_bol[(8 * y) + x] = true;
                        peut_passer = false;
                    }


                    if (tab_p[(y*8)+x].getParite()==this.parite){
                        tab_bol[(8*y) + x] = false ; //à Corriger
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
     * Affiche la tour.
     * @return
     */
    public String toString() {
        if (this.getParite() == true)
            return "♖";
        return "♜";
    }

}