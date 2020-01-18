public class Pion extends Piece{

    private boolean coup2;

    /**
     * Crée un Pion.
     * @param x
     * @param y
     * @param parite
     */

    public Pion(int x, int y, boolean parite){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=true;
        this.coup2=true;
    }

    public Pion(int x, int y, boolean parite, boolean poseInit){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=poseInit;
    }


    /**
     * Analyse les déplacements possibles du pion en fonction de son environnement.
     * @param tab_p
     * @return
     */
    public boolean[] deplacement(Piece[] tab_p) { //A mettre dans la classe Piece
        boolean[] tab_bol = new boolean[64];
        //if (this.ennemie==true){ //à voir dans echequier
        //    if (mouvementValide(this.y-1,this.x-1)) {
        //        if (tab_p[8*(this.y-1) + this.x-1].getParite()==this.parite)
        //            tab_bol[ 8*(this.y-1) + this.x-1] = false;
//
        //        tab_bol[ 8*(this.y-1) + this.x-1] = true;
        //    }
        //    if (mouvementValide(y-1,x+1)){
        //        if (tab_p[8*(this.y-1) + this.x+1].getParite()==this.parite)
        //            tab_bol[ 8*(this.y-1) + this.x+1] = false;
//
        //        tab_bol[ 8*(this.y-1) + this.x+1] = true;}
        //}
        if (this.parite == true) {
            if (poseInit == true) {
                if (mouvementValide(y - 1, this.x) && mouvementValide(this.y - 2, this.x)) {
                    if (tab_p[8 * (this.y - 2) + this.x] != null) {
                        //tab_bol[8*(this.y-1) + x] = true;
                        if (tab_p[8 * (this.y - 2) + this.x].getParite() == this.parite)
                            tab_bol[8 * (this.y - 2) + this.x] = false;
                        else {
                            tab_bol[8 * (this.y - 2) + this.x] = true;
                        }
                    }

                    if (tab_p[8 * (this.y - 2) + this.x] == null) {
                        tab_bol[8 * (this.y - 2) + this.x] = true;
                    }
                }
            }

            if (mouvementValide(this.x, this.y - 1)) {
                if (tab_p[8 * (this.y - 1) + this.x] != null) {
                    tab_bol[8 * (this.y - 1) + this.x] = false;

                    //tab_bol[8 * (this.y - 1) + this.x] = true;
                }

                if (tab_p[8 * (this.y - 1) + this.x] == null)
                    tab_bol[8 * (this.y - 1) + this.x] = true;
            }

            if (indiceValide(this.y - 1)) {
                if (indiceValide(this.x - 1)) {
                    if (tab_p[(8 * (this.y - 1)) + this.x - 1] != null) {
                        if (tab_p[8 * (this.y - 1) + this.x - 1].getParite() != this.parite)
                            //System.out.println("Je passe la");
                            tab_bol[8 * (this.y - 1) + this.x - 1] = true;
                    }
                }
                if (indiceValide(this.x + 1)) {
                    if (tab_p[8 * (this.y - 1) + this.x + 1] != null) {
                        if (tab_p[8 * (this.y - 1) + this.x + 1].getParite() != this.parite)
                            tab_bol[8 * (this.y - 1) + this.x + 1] = true;
                    }
                }

            }


            if (indiceValide(this.x - 1) && this.y==3) {
                if (tab_p[8*y+x-1]!=null){
                    if (tab_p[8*y+x-1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x-1];
                        if (p.getPoseInit()==false && p.getCoup2()==true)
                            tab_bol[(8*(y-1))+(x-1)]=true;
                    }
                }
            }

            if (indiceValide(this.x+1) && this.y==3) {
                if (tab_p[8*y+x+1]!=null){
                    if (tab_p[8*y+x+1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x+1];
                        if (p.getPoseInit()==false && p.getCoup2()==true)
                            tab_bol[(8*(y-1))+(x+1)]=true;
                    }
                }
            }



        }

        else {
            if (poseInit == true) {
                if (mouvementValide(y + 1, this.x) && mouvementValide(this.y + 2, this.x)) {
                    if (tab_p[8 * (this.y + 2) + this.x] != null) {
                        //tab_bol[8*(this.y-1) + x] = true;
                        if (tab_p[8 * (this.y + 2) + this.x].getParite() == this.parite)
                            tab_bol[8 * (this.y + 2) + this.x] = false;
                    }

                    if (tab_p[8 * (this.y + 2) + this.x] == null) {
                        tab_bol[8 * (this.y + 2) + this.x] = true;
                    }
                }
            }

            if (mouvementValide(this.x, this.y + 1)) {
                if (tab_p[8 * (this.y + 1) + this.x] != null) {
                    if (tab_p[8 * (this.y + 1) + this.x].getParite() == this.parite)
                        tab_bol[8 * (this.y + 1) + this.x] = false;

                    tab_bol[8 * (this.y + 1) + this.x] = true;
                }

                if (tab_p[8 * (this.y + 1) + this.x] == null)
                    tab_bol[8 * (this.y + 1) + this.x] = true;
            }

            if (indiceValide(this.y + 1)) {
                if (indiceValide(this.x - 1)){
                    if (tab_p[(8 * (this.y + 1)) + this.x - 1] != null) {
                        if (tab_p[8 * (this.y + 1) + this.x - 1].getParite() != this.parite)
                            //System.out.println("Je passe la");
                            tab_bol[8 * (this.y + 1) + this.x - 1] = true;
                    }
                }
                if (indiceValide(this.x + 1)) {
                    if (tab_p[8 * (this.y + 1) + this.x + 1] != null) {
                        if (tab_p[8 * (this.y + 1) + this.x + 1].getParite() != this.parite)
                            tab_bol[8 * (this.y + 1) + this.x + 1] = true;
                    }
                }

            }

            if (indiceValide(this.x - 1) && this.y==4) {
                System.out.println("Par ici");
                if (tab_p[8*y+x-1]!=null){
                    if (tab_p[8*y+x-1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x+1];
                        if (p.getPoseInit()==false && p.getCoup2()==true)
                            tab_bol[(8*(y+1))+(x-1)]=true;
                    }
                }
            }

            if (indiceValide(this.x+1) && this.y==4) {
                if (tab_p[8*y+x+1]!=null){
                    if (tab_p[8*y+x+1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x+1];
                        if (p.getPoseInit()==false && p.getCoup2()==true)
                            tab_bol[(8*(y+1))+(x+1)]=true;
                    }
                }
            }

        }



        //tab_bol[8*(this.y-1) + this.x] = true; //à Corriger

        return tab_bol;
    }

    /**
     * Détecte si la prise en passant peut être executée.
     * @param tab_p
     * @return
     */

    public boolean Passe(Piece[] tab_p) {
        if (this.parite == true) {
            if (indiceValide(this.x - 1) && this.y==3) {
                if (tab_p[8*y+x-1]!=null){
                    if (tab_p[8*y+x-1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x-1];
                        if (p.getPoseInit()==false && p.getCoup2()==true) {
                            return true;
                        }
                    }
                }
            }

            if (indiceValide(this.x+1) && this.y==3) {
                if (tab_p[8*y+x+1]!=null){
                    if (tab_p[8*y+x+1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x+1];
                        if (p.getPoseInit()==false && p.getCoup2()==true) {
                            return true;
                        }
                    }
                }
            }
        }
        else{
            if (indiceValide(this.x - 1) && this.y==4) {
                System.out.println("Par ici");
                if (tab_p[8*y+x-1]!=null){
                    if (tab_p[8*y+x-1] instanceof Pion) {
                        Pion p = (Pion) tab_p[8 * y + x + 1];
                        if (p.getPoseInit() == false && p.getCoup2() == true){
                            return true;
                        }
                    }
                }
            }

            if (indiceValide(this.x+1) && this.y==4) {
                if (tab_p[8*y+x+1]!=null){
                    if (tab_p[8*y+x+1] instanceof Pion){
                        Pion p = (Pion) tab_p[8*y+x+1];
                        if (p.getPoseInit()==false && p.getCoup2()==true) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Deplace le pion en haut à gauche et supprime l'ancien pion ainsi que le pion prit.
     * @param tab_p
     */
    public void passeHG(Piece[] tab_p){
        tab_p[8*(this.y-2)+(this.x-1)] = new Pion(this.x-1, this.y-1, this.parite); //on instancie unnouveau Pion aux coords vouluent
        tab_p[8*this.y+this.x-1]=null; // On retire le pion qu'on a mangé
        tab_p[8*this.y+this.x]=null; // On retire le pion qui a été déplacé
    }

    /**
     * Deplace le pion en haut à droite et supprime l'ancien pion ainsi que le pion prit.
     * @param tab_p
     */

    public void passeHD(Piece[] tab_p){
        tab_p[8*(this.y-1)+this.x+1] = new Pion(this.x+1, this.y-1, this.parite); //on instancie unnouveau Pion aux coords vouluent
        tab_p[8*this.y+this.x+1]=null; // On retire le pion qu'on a mangé
        tab_p[8*this.y+this.x]=null; // On retire le pion qui a été déplacé
    }
    /**
     * Deplace le pion en bas à gauche et supprime l'ancien pion ainsi que le pion prit.
     * @param tab_p
     */

    public void passeBG(Piece[] tab_p){
        tab_p[8*(this.y+1)+this.x-1] = new Pion(this.x-1, this.y+1, this.parite); //on instancie unnouveau Pion aux coords vouluent
        tab_p[8*this.y+this.x-1]=null; // On retire le pion qu'on a mangé
        tab_p[8*this.y+this.x]=null; // On retire le pion qui a été déplacé
    }

    /**
     * Deplace le pion en bas à droite et supprime l'ancien pion ainsi que le pion prit.
     * @param tab_p
     */
    public void passeBD(Piece[] tab_p){
        tab_p[8*(this.y+1)+this.x+1] = new Pion(this.x+1, this.y+1, this.parite); //on instancie unnouveau Pion aux coords vouluent
        tab_p[8*this.y+this.x+1]=null; // On retire le pion qu'on a mangé
        tab_p[8*this.y+this.x]=null; // On retire le pion qui a été déplacé
    }

    /**
     * Affiche le pion.
     * @return
     */
    public String toString() {
        if (this.getParite() == true)
            return "♙";
        return "♟";
    }

    /**
     *Récupère le 2nd coup.
     * @return
     */
    public boolean getCoup2(){
        return this.coup2;
    }

    /**
     * Modifie le 2nd coup courant.
     * @param coup2
     */

    public void setCoup2(boolean coup2) {
        this.coup2 = coup2;
    }
}