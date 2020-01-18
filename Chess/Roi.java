public class Roi extends Piece{


    /**
     * Crée un roi.
     * @param x
     * @param y
     * @param parite
     */
    public Roi(int x, int y, boolean parite){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=true;
    }

    public Roi(int x, int y, boolean parite, boolean poseInit){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=poseInit;
    }

    /**
     * Analyse les déplacements possibles du roi en fonction de son environnement.
     * @param tab_p
     * @return
     */
    public boolean[] deplacement(Piece[] tab_p) { //Penser à créer un constructeur de confirmation{
        boolean[] tab_bol = new boolean[64];
        int[] tab_y = {-1,-1,-1,0,1,1,1, 0}; //Toutes les directions possible avec x et y
        int[] tab_x = {-1, 0, 1,1,1,0,-1,-1};
        int j = 0; //Iterateur
        int x;
        int y;

        while (j < tab_y.length) {
            x=this.x+tab_x[j];
            y=this.y+tab_y[j];

            if (mouvementValide(x,y)){
                //positionCase += this.y*tab[i] + this.x + tab_bis[i];
                /*
                if (tab_p[(y*8)+x]!=null && tab_p[(y*8)+x].getParite()!=this.parite) {
                    tab_bol[(y * 8) + x] = true;
                    peut_passer=false; //retourner au while et changer de y
                }
                */
                if (tab_p[(y*8)+x]!=null) {
                    if (tab_p[(y * 8) + x].getParite() != this.parite)
                        tab_bol[(y * 8) + x] = true;
                }

                else
                    tab_bol[(y * 8) + x] = true;

            }
            j++;

        }

        if (this.poseInit==true){
            if (tab_p[8*this.y+this.x+1]==null && tab_p[8*this.y+this.x+2]==null && tab_p[8*this.y+this.x+3]!=null){
                if (tab_p[8*this.y+this.x+3] instanceof Tour){
                    if (tab_p[8*this.y+this.x+3].getPoseInit()==true){
                        tab_bol[8*this.y+this.x+2]=true;
                    }
                }
            }

            if (tab_p[8*this.y+this.x-1]==null && tab_p[8*this.y+this.x-2]==null && tab_p[8*this.y+this.x-3]==null && tab_p[8*this.y+this.x-4]!=null){
                if (tab_p[8*this.y+this.x+3] instanceof Tour){
                    if (tab_p[8*this.y+this.x-4].getPoseInit()==true){
                        tab_bol[8*this.y+this.x-3]=true;
                    }
                }
            }
        }

        return tab_bol;
    }

    /**
     * Exécute le roque à la gauche du roi.
     * @param tab_p
     */
    public void RoqueG(Piece[] tab_p){
        System.out.println(8*this.y+this.x+2);
        tab_p[8*this.y+this.x+2] = new Roi(this.x+2, this.y, this.parite);
        tab_p[8*this.y+this.x]=null;
        tab_p[8*this.y+this.x+1] = new Tour(this.x+1, this.y, this.parite);
        tab_p[8*this.y+this.x+3]=null;

    }

    /**
     * Exécute le roque à la droite du roi.
     * @param tab_p
     */
    public void RoqueD(Piece[] tab_p){
        tab_p[8*this.y+this.x-2] = new Roi(this.x-2, this.y, this.parite);
        tab_p[8*this.y+this.x]=null;
        tab_p[8*this.y+this.x-1] = new Tour(this.x-1, this.y, this.parite);
        tab_p[8*this.y+this.x-3]=null;

    }

    /**
     * Vérifie si le roi est entouré par ses alliés.
     * @param tab_p
     * @return
     */
    public boolean entoure(Piece[] tab_p){
        int[] tab_y = {-1,-1,-1,0,1,1,1, 0}; //Toutes les directions possible avec x et y
        int[] tab_x = {-1, 0, 1,1,1,0,-1,-1};
        int j = 0; //Iterateur
        int x;
        int y;

        while (j < tab_y.length) {
            x=this.x+tab_x[j];
            y=this.y+tab_y[j];

            if (mouvementValide(x,y)){
                if (tab_p[(y*8)+x]==null)
                    return false;
                if (tab_p[(y*8)+x]!=null) {
                    if (tab_p[(y * 8) + x].getParite() != this.parite)
                        return false;
                }
            }
            j++;
        }
        return true;
    }

    /**
     * Affiche le roi.
     * @return
     */
    public String toString() {
        if (this.getParite() == true)
            return "♔";
        return "♚";
    }
}