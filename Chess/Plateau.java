import java.util.Scanner;

public class Plateau {

	private Piece[] echiquier;

	public Plateau() {
		this.echiquier = this.initialiser();
	}

	public Plateau(Piece[] echiquier) {
		this.echiquier = echiquier;
	}

	/**
	 * Créer un plateau à partir d'un fichier de sauvegarde.
	 * @param plateautexte
	 */
	public Plateau(String plateautexte) {
		String[] tabtexte = plateautexte.split(",");
		Piece[] echiquier = new Piece[64];
		//int j;
		for(int i=0; i<8; i++) {
			//j = 0;
			for(int j=0; j<8; j++) {
			switch(tabtexte[i*8+j]) {
				case "♜":
					echiquier[i*8+j] = new Tour(j, i, false, false);
					//echiquier[i*8+j} = new Tour (j, i, false, tabposeinit[i];
					break;
				case "♞":
					echiquier[i*8+j] = new Cavalier(j, i, false);
					break;
				case "♝":
					echiquier[i*8+j] = new Fou(j, i, false);
					break;
				case "♛":
					echiquier[i*8+j] = new Roi(j, i, false, false);
					break;
				case "♚":
					echiquier[i*8+j] = new Dame(j, i, false);
					break;
				case "♟":
					echiquier[i*8+j] = new Pion(j, i, false);
					break;
				case "♖":
					echiquier[i*8+j] = new Tour(j, i, true, false);
					break;
				case "♘":
					echiquier[i*8+j] = new Cavalier(j, i, true);
					break;
				case "♗":
					echiquier[i*8+j] = new Fou(j, i, true);
					break;
				case "♕":
					echiquier[i*8+j] = new Roi(j, i, true, false);
					break;
				case "♔":
					echiquier[i*8+j] = new Dame(j, i, true);
					break;
				case "♙":
					echiquier[i*8+j] = new Pion(j, i, true, false);
					break;
				case "-":
					echiquier[i*8+j] = null;
					break;
				} 
			}
		}
		this.echiquier = echiquier;
	}
	

	/**
	 * Initialise un plateau de jeu d'échec au début d'une partie.
	 * @return
	 */
	public Piece[] initialiser() {
		Piece[] echiquier = new Piece[64];
		boolean jo = false;
		int y = 8;
		int z = 16;
		int a = 0;
		int li = 0;
		
		int lip = 1;
		int cop = 0;
		
		for (int i=0; i<2; i++) {
			echiquier[0+a] = new Tour(0, li, jo);
			echiquier[1+a] = new Cavalier(1, li, jo);
			echiquier[2+a] = new Fou(2, li, jo);
			echiquier[3+a] = new Dame(3, li, jo);
			echiquier[4+a] = new Roi(4, li, jo);
			echiquier[5+a] = new Fou(5, li, jo);
			echiquier[6+a] = new Cavalier(6, li, jo);
			echiquier[7+a] = new Tour(7, li, jo);

			while (y < z) {
				echiquier[y] = new Pion(cop, lip, jo);
				cop += 1;
				y += 1;
		    }
		    y = 48;
		    z = 56;
		    a = 56;
		    li = 7;
		    lip = 6;
		    cop = 0;
		    jo = true;
		}
		return echiquier;
	}

	public boolean indiceValide(int indice) {
		return (indice < 8) && (indice > -1);
	}

	public boolean caseValide(int i, int j) {
		return indiceValide(i) && indiceValide(j);
	}

	public Piece getPiece(int i, int j) {
		return this.echiquier[i+j*8];

	}

	public void setPiece(Piece p, int i, int j) {
		if (caseValide(i, j)) {
			this.echiquier[i+j*8] = p;
		}
	}
	
	public boolean caseOccupee(int i, int j) {
		return this.getPiece(i, j) != null; // à tester
	}

	public boolean caseJoueur(int i, int j, boolean joueur) {
		//return this.getPiece(i, j).getJoueur();
		return this.getPiece(i, j) != null; // enlever le toString et les "" à null ?, à voir§!!! CA MARCHe
	}

	public boolean caseSelectionValide(int i, int j, boolean joueur) {
		return this.caseOccupee(i, j) && this.caseJoueur(i, j, joueur);
	}
	
	public Piece[] getEchiquier() {
		return this.echiquier;
	}

	public String toString() {
		String affi_echiquier = new String("   ");
		int j = 7;
		for(int i=65; i<73; i++) {
			affi_echiquier +=  " " + (char) i + " ";
		}
		affi_echiquier += "\n 8 ";

		for(int i=1; i<65; i++) {
			if (this.echiquier[i-1] == null) {
				affi_echiquier += " - ";
			}

			else {
				affi_echiquier += " " + this.echiquier[i-1].toString() + " ";
			}

			if ((i%8 == 0) && (i != 64)) {
				affi_echiquier += "\n " + Integer.toString(j) + " ";
				j -= 1;
			}
		}
		return affi_echiquier;

	}
	
	/**
	 * Demande à l'utilisateur de saisir le choix de promotion qu'il souhaite pour son pion.
	 * @return
	 */
	public String choixPromotion() {
		String s = "-1";
		while (!(s.equals("D") || s.equals("T") || s.equals("C") || s.equals("F"))){
			System.out.println("Choisissez la promotion de votre pion : Dame (D), Tour (T), Cavalier(C) ou Fou (F).");
			s = this.saisie();

			if  (!(s.equals("D") || s.equals("T") || s.equals("C") || s.equals("F"))) {
				System.out.println("Saisie invalide !");
			}
		}
		return s;
		
	}
	
	/**
	 * Exécute la promotion avec le choix et l'indice de la pièce en paramètre.
	 * @param s
	 * @param i
	 */
	public void fairePromotion(String s, int i) {
		Piece p = this.echiquier[i];
		switch (s)	{
		case "D":
			this.echiquier[i]= new Dame(p.getX(),p.getY(),p.getParite());
			break;
		case "T":
			this.echiquier[i]= new Tour(p.getX(),p.getY(),p.getParite());
			break;
		case "C":
			this.echiquier[i]= new Cavalier(p.getX(),p.getY(),p.getParite());
			break;
		case "F":
			this.echiquier[i]= new Fou(p.getX(),p.getY(),p.getParite());
			break;
		}
		
	}
	
	/**
	 * Exécute tout le processus de promotion si une promotion est possible.
	 */
	public void Promotion() {
		int promo = this.detecterPromotion();
		if (promo != -1) {
			System.out.println(this.toString());
			this.fairePromotion(this.choixPromotion(), promo);
		}
	}
	
	/**
	 * Détecte si une promotion est disponible.
	 * @return
	 */
	public int detecterPromotion() {
		int i=0;
		while (i<64){
			if (this.echiquier[i] instanceof Pion){
				return i;
			}
			i++;
			
			if (i == 7) {
				i = 56;
			}
		}
		return -1;
	}

	public String saisie() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();
		return s;
	}

	/**
	 *Retourne le nombre d'occurence de true d'un tableau de boolean.
	 * @param tab
	 * @return
	 */
    public static int occurence(boolean[] tab) {
        int occur = 0;
        for (int i = 0; i < 64; i++) {
            if (tab[i] == true)
                occur++;
        }
        return occur;
    }

	/**
	 *Concatene deux tableaux de boolean.
	 * @param tab
	 * @param tab_bis
	 * @return
	 */
    public static boolean[] concatener(boolean[] tab, boolean[] tab_bis) {
        boolean[] tab_conc = new boolean[64];
        int i;
        for (i = 0; i < 64; i++) { // a voir si il faut mettre tab.length, comme c'est une taille fixe
            if (tab[i] == true) { // autant mettre 64 pour pas avoir de calcules inutiles, ensuite les profs
                tab_conc[i] = true; // peuvent nous la mettre en mode "Ouai mais c'est pas pro"
            }
        }
        for (i = 0; i < 64; i++) {
            if (tab_bis[i] == true) {
                tab_conc[i] = true;
            }
        }
        return tab_conc;
    }

	/**
	 *Capture le pion indiqué aux coordonnés x et y.
	 * @param x
	 * @param y
	 * @param p
	 */
    public void capture(int x, int y, Piece p){ //Forcement ce sera toujours dans le cas d'un joueur contre un autre

		this.echiquier[8 * p.getY() + p.getX()] = null;
		p.setX(x);
		p.setY(y);
		this.echiquier[8 * y + x] = p;
	}

	/**
	 *Confirme la possibilité de déplacer la pièce de coordonées (x_2,y_2) aux point de coordonées (x,y).
	 * @param x
	 * @param y
	 * @param x_2
	 * @param y_2
	 * @return
	 */

    public boolean mouvement_echec(int x, int y, int x_2, int y_2){ //x,y = deplacement de la piece || x_2,y_2 = la piece
    	if (this.getPiece(x_2, y_2) == null) {
    		return false;
    	}
    	    	
		if (caseValide(x, y) && caseValide(x_2,y_2)) {
			boolean[] tab_deplace = this.echiquier[8*y_2+x_2].deplacement(this.echiquier);
			Piece p = this.echiquier[8*y_2+x_2];
			
			for (int i=0; i<64; i++){
				if (this.echiquier[i] instanceof Roi){
					if (this.echiquier[8*y_2+x_2].getParite()==this.echiquier[i].getParite()){
						if (echec(this.echiquier[i])) {
							if (p instanceof Roi) {
								boolean[] tab_prise = all_deplacement(parite_inverse(p));
								if (tab_prise[8 * y + x] == false) {
									return true;
								}
								return false;
							}

							else{
								boolean[] p_move = p.deplacement(this.echiquier); //On prends tous les deplacements de la piece en question
								if (p_move[8*y+x]==true){ //Si le deplacement voulu est valide
									Piece[] tab_bis = new Piece[64]; //On cree un tableau annexe
									System.arraycopy(this.echiquier, 0, tab_bis, 0, 64); // dans lequel sera stocké l'echiquier actuel
									tab_bis[8*y+x]=p; // dans ce tableau annexe on met la piece courante
									p.setX(x);
									p.setY(y);
									tab_bis[8*y_2+x_2]=null; //On supprime l'ancienne case occupé par la piece
									Plateau plateau = new Plateau(tab_bis);
									//System.out.println(plateau.toString());
									//System.out.println("Echec est false = "+plateau.echec(tab_bis[i]));
									if ((plateau.echec(tab_bis[i]))==false) {
										p.setX(x_2);
										p.setY(y_2);
										return true;
									}
									else
										return false;
								}
							}
						}
					}
				}
			}

			if (tab_deplace[8 * y + x] == false) {
				return false;
			}

			if (tab_deplace[8 * y + x] == true) {
				return true;
			}

		}

		//System.out.println(this.toString());
		return false;
	}

	/**
	 *Déplace la pièce de coordonées (x_2,y_2) aux point de coordonées (x,y).
	 * @param x
	 * @param y
	 * @param p
	 */
    public void mouvement(int x, int y, Piece p){
		boolean[] tab_deplace = p.deplacement(this.echiquier);

		if (caseValide(x, y)) {
			if (tab_deplace[8 * y + x] == true) {
				if (p instanceof Roi && (y==0 || y==7) && (x==1 || x==6)){
					Roi pR = (Roi) p;
					if (x==1){
						pR.RoqueD(this.echiquier);
					}
					if (x==6){
						pR.RoqueG(this.echiquier);
					}
				}
				else if (p instanceof Pion && p.Passe(this.echiquier)){
					Pion pP = (Pion) p;
					if (pP.getY()==3) {
						if (x < pP.getX()){
							System.out.println("Je suis passé 1 ");
							pP.passeHG(this.echiquier);
						}
						else if (x>pP.getX()){
							System.out.println("Je suis passé 2 ");
							pP.passeHD(this.echiquier);
						}
					}
					else if (pP.getY()==4){
						if (x<pP.getX()) {
							System.out.println("Je suis passé 3 ");
							pP.passeBG(this.echiquier);
						}
						else if (x>pP.getX()) {
							System.out.println("Je suis passé 4 ");
							pP.passeBD(this.echiquier);
						}
					}

				}

				else {
					this.echiquier[(8 * p.getY()) + p.getX()] = null;
					p.setY(y);
					p.setX(x);
					this.echiquier[8 * y + x] = p;
				}

			}
			if (this.echiquier[8 * y + x] != null && tab_deplace[8 * y + x] == true) { // Prend la piece

				this.capture(x, y, p); // sert elle vraiment encore à quelque chose ??

			}

			if (p instanceof Pion){
				Pion pP= (Pion) p;
				if (pP.getPoseInit()==false && pP.getCoup2()==true)
					pP.setCoup2(false);
			}

			p.setPoseInit(false);

		}

        }

	/**
	 *Retourne tous les déplacements de pièce possible d'une certaine parité.
	 * @param parite
	 * @return
	 */
	public boolean[] all_deplacement(boolean parite) {
		boolean[] tab_all = new boolean[64];
		boolean[] newTab = new boolean[64];
		for (int i = 0; i < this.echiquier.length; i++) {
			if (this.echiquier[i] != null) {
				if (this.echiquier[i].getParite() == parite)
					newTab = this.echiquier[i].deplacement(this.echiquier);
				tab_all = concatener(tab_all, newTab);
			}
		}
		return tab_all;
	}

	/**
	 *Retourne tous les déplacements de pièce possible d'une certaine parité sans prendre en compte les déplacements du Roi.
	 * @param parite
	 * @return
	 */
	public boolean[] all_deplacement_without_piece(boolean parite) {
		boolean[] tab_all = new boolean[64];
		boolean[] newTab = new boolean[64];
		for (int i = 0; i < this.echiquier.length; i++) {
			if (this.echiquier[i] != null) {
				if (!(this.echiquier[i] instanceof Roi)) {
					if (this.echiquier[i].getParite() == parite)
						newTab = this.echiquier[i].deplacement(this.echiquier);
					tab_all = concatener(tab_all, newTab);

				}
			}
		}
		return tab_all;
	}

	/**
	 *Inverse la parité d'une pièce.
	 * @param p
	 * @return
	 */
    public boolean parite_inverse(Piece p) {
		return (!(p.getParite()));
	}



	/**
	 *Retourne la mise en échec ou non du roi.
	 * @param p
	 * @return
	 */
	public boolean echec(Piece p) { //le roi
		boolean par = parite_inverse(p);
		boolean[] tab_all = all_deplacement(par);
		return (tab_all[8 * (p.getY()) + p.getX()] == true); //Voir si la case du roi peut être prise
	}


	/**
	 *Retourne la mise en Pat du roi.
	 * @param p
	 * @return
	 */
	public boolean Pat(Piece p) { //le roi
		Roi pR = (Roi) p;
		if (pR.entoure(this.echiquier)==false) {

			boolean[] deplacement_roi = p.deplacement(this.echiquier);
			Piece[] tab_bis = new Piece[64]; //On cree un tableau annexe
			System.arraycopy(this.echiquier, 0, tab_bis, 0, 64);
			for (int i =0; i<64; i++){
				if (tab_bis[i]!=null) {
					if (tab_bis[i] instanceof Roi && p.getParite()==tab_bis[i].getParite())
						tab_bis[i]=null;
				}
			}

			Plateau plateau = new Plateau(tab_bis);

			boolean[] deplacement_autre_parite = plateau.all_deplacement(parite_inverse(p)); //Recherche tous les déplacement possible dans un plateau où le roi ennemie n'existe pas
			String txt = "";

			boolean[] deplacement_parite = all_deplacement_without_piece(p.getParite()); //Recherche tous les déplacement possibles sans le roi

			int occur = occurence(deplacement_roi); //Cherche le nmbre d'occurence dans Roi
			System.out.println(occur);

			int occur_compteur = 0;
			int compte_bcl=0;

			for (int i = 0; i < 64; i++) {
				if (deplacement_roi[i] == true && deplacement_autre_parite[i] == true && deplacement_parite[i] == false){ //La case doit pouvoir être prise par le roi, par les autres pieces, mais pas par les alliés du roi
					occur_compteur++;
				}
			}
			if (occur_compteur == occur)
				return true;
			return false;
		}
		return false;
	}

	/**
	 *Retourne la mise en Echec et mat du roi.
	 * @param p
	 * @return
	 */
	public boolean Mat(Piece p) { // Le roi en état d'échec et mat

		if (Pat(p) && echec(p))
			return true;
		return false;
	}

}
