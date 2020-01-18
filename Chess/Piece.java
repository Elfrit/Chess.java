public abstract class Piece {
    protected int x;
    protected int y;
    protected boolean parite;
    protected boolean poseInit;


    public Piece(){}
    /**
     * Crée une Piece.
     * @param x
     * @param y
     * @param parite
     */
    public Piece(int x, int y, boolean parite){
        this.x=x;
        this.y=y;
        this.parite=parite;
        this.poseInit=true;
    }

    /**
     * Valide l'indice donné en paramètre.
     * @param indice
     */
    public static boolean indiceValide(int indice){
        return (indice<8)&&(indice>-1);
    }

    /**
     * Valide les indices donnés en paramètre.
     * @param i
     * @param j
     */
    public static boolean mouvementValide(int i, int j){
        return indiceValide(i) && indiceValide(j);
    }

   // public void placer(int[] tab, int n){  //A mettre dans la classe Piece
   //     tab[n]=this.y*n+this.x;
    // }
    /**
     * Détecte si la prise en passant peut être executée.
     * @param tab
     * @return
     */
    public boolean Passe(Piece[] tab){return false;}

    /**
     * Analyse les déplacements possibles de la pièce en fonction de son environnement.
     * @param p
     * @return
     */
    public abstract boolean[] deplacement(Piece[] p);

    /**
     *Récupère la parite.
     * @return
     */
    public boolean getParite(){
        return this.parite;
    }

    /**
     *Récupère x.
     * @return
     */
    public int getX(){
        return this.x;
    }

    /**
     *Récupère y.
     * @return
     */
    public int getY(){
        return this.y;
    }

    /**
     *Récupère PoseInit.
     * @return
     */
    public boolean getPoseInit(){
        return this.poseInit;
    }

    /**
     * Modifie le x courant.
     * @param x
     */
    public void setX(int x){
        this.x=x;
    }

    /**
     * Modifie le y courant
     * @param y
     */
    public void setY(int y){
        this.y=y;
    }

    /**
     * Modifie la position initale courante.
     * @param poseInit
     */
    public void setPoseInit(boolean poseInit) {
        this.poseInit = poseInit;
    }

}