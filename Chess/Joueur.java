import java.util.Scanner;

public class Joueur {
	private String nom;
	private boolean couleur;

	public Joueur() {
		this.nom = this.saisie();
	}
	
	public Joueur(boolean couleur) {
		this.nom = this.saisie();
		this.couleur = couleur;
	}
	
	public Joueur(String nom, boolean couleur) {
		this.nom = nom;
		//this.couleur = couleur;
	}
	
/*	public String initialiser() {
		this.nom = this.saisie();
	}*/

	
	public String saisie() {
		System.out.println("Saissisez un nom de joueur :");
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();
		return s;
	}
	
	
	/*public boolean swapJoueur(Joueur[] j) {
		return 
	}*/
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public boolean getCouleur() {
		return this.couleur;
	}
	
	public void setCouleur(boolean couleur) {
		this.couleur = couleur;
	}
	
	public String toString() {
		return this.nom;
	}
}
