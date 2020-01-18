import java.util.Scanner;

import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Partie {
	private Plateau plateau;
	private String historique;
	private Joueur[] joueurs;
	private boolean joueuractuel; //true pour le joueur des pièces blanches, false pour le joueur des pièces noires

	public Partie() {

	}

	/**
	 * Lance les actions renseignés dans la méthode saisirAction().
	 * Permet de quitter le jeu dans sa globalité (0), créer une nouvelle partie (1), charger une partie (2), sauvegarder une partie courante (3), de jouer la partie courante(4) et d'abandonner la partie courante (5).
	 *
	 */
	public void echec() {
		boolean encours = false;
		String act = this.saisirAction(encours);

		while(!(act.equals("0"))) { // Tant qu'on ne quitte pas le jeu d'échec, on peut saisir...
			if (act.equals("1")) { // 1 pour créer une nouvelle partie
				this.creerPartie();
				encours = true;
			}

			if (act.equals("2")) { //2 pour charger une partie
				encours = this.chargerPartie();
			}

			if (act.equals("3")) { //3 pour sauvegarder une partie
				this.sauvegarderPartie();
			}

			if (act.equals("4")) { //4 pour débuter/reprendre une partie jouer la partie
				if (this.jouer()) { //si la partie est terminé
					System.out.println("Partie terminée !");

					if (this.determinerSiMat()) {
						System.out.println("Le gagnant est" + this.getNomJoueurActuel() + "par mat.");
					}

					else if (this.determinerSiPat()) {
						System.out.println("Partie déclarée nulle par pat.");
					}
					
					System.out.println("Voulez-vous sauvegarder la partie (pour consultation externe) ? (o/n)");
					String s = this.saisie();
					while (!s.equals("o") && !s.equals("n")) {
						s = this.saisie();
					}
					if (s.equals("o")) {
						this.sauvegarderPartie();
					}
					this.abandonnerPartie();
					encours = false;
				}
			}

			if (act.equals("5")) { //5 pour abandonner la partie courante
				this.abandonnerPartie();
				encours = false;
			}

			act = this.saisirAction(encours);
		}
		return;
	}
	
	/**
	 * Détermine si il y a un pat
	 * @return
	 */
	public boolean determinerSiPat() {
		int count_piece=0;
		Piece[] echiquier = this.plateau.getEchiquier();
		for (int i=0; i<echiquier.length; i++) {
			if (echiquier[i] != null) {
				count_piece++;
				if (echiquier[i] instanceof Roi && echiquier[i].getParite() != this.joueuractuel) {
					if (this.plateau.Pat(echiquier[i]) == true) {
						return true;
					}
				}
			}
		}
		if (count_piece==2)
			return true;
		return false; //true false
	}
	
	/**
	 * Détermine si il y a un échec et mat.
	 * @return
	 */
	public boolean determinerSiMat() {
		int count_piece=0;
		Piece[] echiquier = this.plateau.getEchiquier();
		for (int i=0; i<echiquier.length; i++) {
			if (echiquier[i] != null) {
				count_piece++;
				if (echiquier[i] instanceof Roi && echiquier[i].getParite() != this.joueuractuel) {
					if (this.plateau.Mat(echiquier[i]) == true) {
						return true;
					}
				}
			}
		}
		if (count_piece==2)
			return true;
		return false; //true false
	}

	/**
	 * Créer la partie (initialise les joueurs et le plateau).
	 */
	public void creerPartie() {
		this.creationJoueur();
		this.plateau = new Plateau();
	}

	/**
	 * Crée les joueurs : vous pouvez jouez seul, ou à deux.s
	 */
	public void creationJoueur() {
		System.out.println("Jouez vous tous seul : (o/n)");
		String s = this.saisie();
		while (!s.equals("o") && !s.equals("n")) {
			s = this.saisie();
		}

		if (s.equals("o")) {
			this.joueurs = new Joueur[2];
			this.joueurs[0] = new Joueur("camp des pièces blanches", true);
			this.joueurs[1] = new Joueur("camp des pièces noires", false);

		}

		else {
			this.joueurs = new Joueur[2];
			this.joueurs[0] = new Joueur(true);
			this.joueurs[1] = new Joueur(false);
		}
		
		this.setJoueurActuel(true);
	}
	
	/**
	 * Affiche le menu du jeu d'échec et les actions possibles par l'utilisateur et lui demande de saisir une action.
	 * @param partie
	 * @return
	 */
	public String saisirAction(boolean partie) {
		System.out.println("-----------------------------------");
		System.out.println("Menu principal du jeu d'échec");
		System.out.println("Liste des commandes possibles :\n- 0 pour quitter le jeu\n- 1 pour créer une nouvelle partie\n- 2 pour charger une partie");

		ArrayList<String> listep = new ArrayList<String>();
		listep.add("0");
		listep.add("1");
		listep.add("2");

		if (partie == true) {
			System.out.print("- 3 pour sauvegarder la partie\n- 4 pour jouer la partie\n- 5 pour abandonner la partie en cours\n");
			listep.add("3");
			listep.add("4");
			listep.add("5");
		}

		System.out.println("Veuillez saisir une action :");
		String saisie = this.saisie();

		while (!(listep.contains(saisie))) {
			System.out.println("Saisie invalide !");
			saisie = this.saisie();
		}
		return saisie;
	}

	/**
	 * Vérifie que la saisie donnée en paramètre est conforme aux bornes du plateau.
	 * @param saisie
	 * @return
	 */
	public boolean saisieValide(String saisie) {
		if (!(saisie.equals(""))) {
			char c1 = Character.toLowerCase(saisie.charAt(0));
			return (saisie.length() == 2) && c1 >= 'a' && c1 <= 'h' && saisie.charAt(1) > '0' && saisie.charAt(1) <= '8';
		}
		return false;
	}

	/**
	 * Permet d'effectuer un tour de jeu. Renvoi un booléean indiquant si on doit continuer à faire d'autres tours (true), ou si l'on souhaite revenir au menu (false).
	 * @return
	 */
	public boolean tourJeu() {
		this.afficher();
		
		System.out.println("Vous pouvez à tout moment de la partie saisir la lettre M pour revenir au menu principal.");
		
		String saisiemp = new String("");
		String[] saisies = new String[2];

		boolean saisieok = false;
		
		System.out.println("Veuillez saisir le choix de pièce et le mouvement voulu séparé par un tiret (exemple : a7-a6 (départ-arrivé)) :");
		while (saisieok == false) {
			saisiemp = this.saisie();
			saisies = saisiemp.split("-");

			if (saisies[0].equals("M")) {
				return false;
			}
			
			
			if (!(saisiemp.contains("-")) || saisiemp.lastIndexOf("-") != 2 || saisiemp.indexOf("-") != 2 || saisiemp.length() > 5) {
				System.out.println("Saisie incorrecte ! Veuillez saisir le choix de pièce et le mouvement voulu séparé par un tiret (exemple : a1-b2) ou la lettre M pour revenir au menu principal :");
			}
			
			else if (this.saisieValide(this.formatSaisie(saisies[0]))
					&& this.saisieValide(this.formatSaisie(saisies[1]))
					&& this.getJoueurActuel() == this.plateau.getPiece(this.traduireSaisie(this.formatSaisie(saisies[0]).charAt(0)), this.traduireSaisie(this.formatSaisie(saisies[0]).charAt(1))).getParite()
					&& this.plateau.mouvement_echec(this.traduireSaisie(this.formatSaisie(saisies[1]).charAt(0)),this.traduireSaisie(this.formatSaisie(saisies[1]).charAt(1)), this.traduireSaisie(this.formatSaisie(saisies[0]).charAt(0)), this.traduireSaisie(this.formatSaisie(saisies[0]).charAt(1)))) {
				saisieok = true;
			}
			
			else {
				System.out.println("Saisie incorrecte ! Veuillez saisir le choix de pièce et le mouvement voulu séparé par un tiret (exemple : a1-b2) ou la lettre M pour revenir au menu principal :");
			}	
		}
	
		this.majHistorique(saisies[0], saisies[1]);
		this.plateau.mouvement(this.traduireSaisie(this.formatSaisie(saisies[1]).charAt(0)), this.traduireSaisie(this.formatSaisie(saisies[1]).charAt(1)), this.plateau.getPiece(this.traduireSaisie(this.formatSaisie(saisies[0]).charAt(0)), this.traduireSaisie(this.formatSaisie(saisies[0]).charAt(1))));
		this.plateau.Promotion();
		this.setJoueurActuel(this.swapJoueur(this.getJoueurActuel()));
		
		return true;
	}


	/**
	 * Permet d'effectuer une saisie.
	 * @return
	 */
	public String saisie() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();
		return s;
	}

	/**
	 * Change la possible lettre majuscule d'une chaine de caractère représentant une coordonnée donnée en paramètre en lettre minuscule et renvoi la chaine.
	 * @param s
	 * @return
	 */
	public String formatSaisie(String s) {
		char c = Character.toLowerCase(s.charAt(0));
		s = Character.toString(c) + Character.toString(s.charAt(1));
		return s;
	}
	
	/**
	 * Traduie la saisie de l'utilisateur sous formes de données exploitables pour les autres méthodes.
	 * @param c
	 * @return
	 */
	public int traduireSaisie(char c) {
		if (c >= 'a' && c <= 'h') {
			return (int) c-97;
		}
		
		else {
			return (int) abs((c-49)-7);

		}
	}
	
	/**
	 * Met à jour l'historique de la partie à partir de la position initiale d'un pion et de son mouvement.
	 * @param posini
	 * @param mouv
	 */
	public void majHistorique(String posini, String mouv) {
		posini = this.formatSaisie(posini);
		mouv = this.formatSaisie(mouv);
		
		if (this.plateau.getPiece(this.traduireSaisie(mouv.charAt(0)), this.traduireSaisie(mouv.charAt(1))) == null) {
			if (this.getHistorique() == null) {
				this.historique = this.plateau.getPiece(this.traduireSaisie(posini.charAt(0)), this.traduireSaisie(posini.charAt(1))) + posini + "-" + mouv;
			}
			else {
				this.historique = this.historique + ", " + this.plateau.getPiece(this.traduireSaisie(posini.charAt(0)), this.traduireSaisie(posini.charAt(1))) + posini + "-" + mouv;	
			}
		}
		else {
			this.historique = this.historique + ", " + this.plateau.getPiece(this.traduireSaisie(posini.charAt(0)), this.traduireSaisie(posini.charAt(1))) + posini + "-" + this.plateau.getPiece(this.traduireSaisie(mouv.charAt(0)), this.traduireSaisie(mouv.charAt(1))) + mouv;
		}
	}

	/**
	 * Affiche la partie en cours.
	 */
	public void afficher() {
		System.out.println(this.toString());
	}
	

	/**
	 * Renvoi le nom des joueurs actuels de la partie.
	 * @return
	 */
	public String getNomJoueurActuel() {
		if(this.getJoueurActuel()) 
			return new String(this.getJoueurs()[0].getNom());
		else
			return new String(this.getJoueurs()[1].getNom());
	}
	
	public String getNomJoueurs() {
		return new String(this.getJoueurs()[0].getNom() + "," + this.getJoueurs()[1].getNom());
	}

	/**
	 * Renvoi l'inverse du booléen donné en paramètre. Simule l'alternance de tour.
	 * @param j
	 * @return
	 */
	public boolean swapJoueur(boolean j) {
		return !j;
	}

	public Plateau getPlateau() {
		return this.plateau;
	}

	public String getHistorique() {
		return this.historique;
	}

	public Joueur[] getJoueurs() {
		return this.joueurs;
	}

	public boolean getJoueurActuel() {
		return this.joueuractuel;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public void setHistorique(String historique) {
		this.historique = historique;
	}

	public void setJoueurs(Joueur[] joueurs) {
		this.joueurs = joueurs;
	}

	public void setJoueurActuel(boolean joueuractuel) {
		this.joueuractuel = joueuractuel;
	}

	/**
	 * Formate le plateau en chaine de caractère pour la sauvegarde.
	 * @return
	 */
	public String formatPlateau() {
		String formatP = new String();
		Piece[] p = this.getPlateau().getEchiquier();
		for (int i=0; i<64; i++) {
			if (p[i] != null)
				formatP += p[i].toString() + ",";
			else
				formatP += "-" + ",";
		}
		return formatP;
	}

	public String toString() {
		String affi_partie = new String(this.plateau.toString());
		if (this.getHistorique() != null)  {
			affi_partie += "\nHistorique : " + this.getHistorique();
			affi_partie = affi_partie.replace("null", "");
			affi_partie += "\nTour n°" + this.compterTour(this.getHistorique()) + " : " + this.getNomJoueurActuel();

		}
		else {
			affi_partie += "\nTour n°1 : " + this.getNomJoueurActuel();
		}

		return affi_partie;
	}

	/**
	 * Compte les tirets (symbolique d'un tiret par tour) et renvoi leur nombres.
	 * @return
	 */
	public int compterTour(String affi_historique) {
		int i = 0;
		int c = 1;
		while(i < affi_historique.length()) {
			if (Character.toString(affi_historique.charAt(i)).equals("-")) {
				//System.out.println(Character.toString(affi_historique.charAt(i)));
				c += 1;	
			}
			i+=1;
		}
		return c;
	}
	
	/**
	 * Charge la partie, renvoi un booléen pour définir si le chargement c'est bien déroulé (true, false dans le cas échéant).
	 * @return
	 */
	public boolean chargerPartie() {
		System.out.println("Quel est le fichier que vous souhaitez charger ?");
		String fichier = this.saisie();
		String[] data = new String[4];
		boolean etatcharg = true;

		try {
			data = this.chargementPartie(fichier);
			this.initialiserChargementPartie(data);
			System.out.println("Partie chargé avec succès !");
		}
		
		catch (Exception e) {
			System.out.println("La partie n'as pas été chargé correctement.");
			System.out.println(e);
			etatcharg = false;
		}
		
		return etatcharg;
	}
	
	/**
	 * Enregistre le contenu d'un fichier dans une chaine de caractère.
	 * @param fichier
	 * @return
	 * @throws Exception
	 */
	public String[] chargementPartie(String fichier) throws Exception {
		String[] data = new String[4];
		BufferedReader entree;
		entree = new BufferedReader(new FileReader(fichier));
		String ligne = entree.readLine();
		int i = 0;
		while (ligne != null) {
			data[i] = ligne;
			ligne = entree.readLine();
			i += 1;
		}
		entree.close();
		return data;
	}

	/**
	 * Initialise une partie à partir d'une chaine de caractère.
	 * @param data
	 * @throws Exception
	 */
	public void initialiserChargementPartie(String[] data) throws Exception {
			this.setPlateau(new Plateau(data[0]));
			
			if (data[1].equals("null")) {
				this.setHistorique("");
			}
				
			else {
				this.setHistorique(data[1]);
			}

			Joueur[] joueurs = new Joueur[2];
			String[] jstring = data[2].split(",");
			joueurs[0] = new Joueur(jstring[0], true);
			joueurs[1] = new Joueur(jstring[1], false);
			this.setJoueurs(joueurs);

			this.setJoueurActuel(Boolean.parseBoolean(data[3]));
	}
	

	
	/**
	 * Sauvegarde la partie en cours.
	 */
	public void sauvegarderPartie() {
		PrintWriter sortie;
		System.out.println("Quel nom souhaitez-vous donner à votre fichier de sauvegarde ?");
		String fichier = this.saisie();
		try {
			sortie = new PrintWriter (new BufferedWriter (new FileWriter (fichier)));
			sortie.println(this.formatPlateau() + "\n" + this.getHistorique() + "\n" + this.getNomJoueurs() + "\n" + this.getJoueurActuel());
			sortie.close();
			System.out.println("Partie sauvegarder avec succès !");
		}
		catch(IOException e) {
			System.out.println(e);
			System.out.println("La partie n'as pas été sauvegardé correctement.");
		}
	}

	/*public Sring getPosiIni() {
	for (int i = 0; i<64; i++) {

	}

		return str;
	}*/

	/**
	 * Permet de jouer aux échecs en faisant s'enchainer les tours de jeux.
	 * @return
	 */
	public boolean jouer() {
		while (this.finPartie() == false) {
			if (this.tourJeu() == false)
				return false;
		}
		return true;

	}

	/**
	 * Permet d'abandonner la partie en mettant les paramètres par défaut.
	 */
	public void abandonnerPartie() {
		this.plateau = new Plateau();
		this.joueuractuel = false;
		this.joueurs = null;
		this.historique = null;
	}


	/**
	 * Détecte la fin de partie, renvoi false tant qu'elle n'est pas fini.
	 * @return
	 */
	public boolean finPartie() {
		int count_piece=0;
		Piece[] echiquier = this.plateau.getEchiquier();
		for (int i=0; i<echiquier.length; i++) {
			if (echiquier[i] != null) {
				count_piece++;
				if (echiquier[i] instanceof Roi && echiquier[i].getParite() == this.joueuractuel) {
					if (this.plateau.Pat(echiquier[i]) == true) {
						System.out.println("La partie est nulle.");
						return true;
					}
					if (this.plateau.Mat(echiquier[i]) == true) {
						System.out.println("Bravo ! Joueur: "+this.joueuractuel+" a gagné !");
						return true;
					}
				}
			}
		}
		if (count_piece==2)
			System.out.println("Nul par manque de matériel.")
			return true; //Nul par anque de materiel
		return false; //true false
	}

	public static void main(String[] args) {
		Partie partie1 = new Partie();
		partie1.echec();
	}
}
