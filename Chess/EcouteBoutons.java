import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteBoutons implements ActionListener {
	private InterfaceEchec fenetre;

	public EcouteBoutons(InterfaceEchec fenetre) {
		super();
		this.fenetre = fenetre;
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		switch (s) {
		case "Valider":
			if (this.fenetre.getPartie().finPartie() == false) {
			
			
			String[] saisies = new String[2];
			String piece = this.fenetre.get_tfPiece().getText();
			String mouv = this.fenetre.get_tfMouv().getText();
			saisies[0] = piece;
			saisies[1] = mouv;
					
			if (saisies[0].length() == 2 && saisies[1].length() == 2) {
				saisies[0] = this.fenetre.getPartie().formatSaisie(saisies[0]);
				saisies[1] = this.fenetre.getPartie().formatSaisie(saisies[1]);
				
				if (this.fenetre.getPartie().saisieValide(this.fenetre.getPartie().formatSaisie(saisies[0]))
						&& this.fenetre.getPartie().saisieValide(this.fenetre.getPartie().formatSaisie(saisies[1]))
						&& this.fenetre.getPartie().getPlateau().mouvement_echec(this.fenetre.getPartie().traduireSaisie(saisies[1].charAt(0)),this.fenetre.getPartie().traduireSaisie(saisies[1].charAt(1)), this.fenetre.getPartie().traduireSaisie(saisies[0].charAt(0)), this.fenetre.getPartie().traduireSaisie(saisies[0].charAt(1)))
						&& this.fenetre.getPartie().getJoueurActuel() == this.fenetre.getPartie().getPlateau().getPiece(this.fenetre.getPartie().traduireSaisie(saisies[0].charAt(0)), this.fenetre.getPartie().traduireSaisie(saisies[0].charAt(1))).getParite()) {
					
					
					this.fenetre.majHistorique(saisies[0], saisies[1]);
					this.fenetre.getPartie().getPlateau().mouvement(this.fenetre.getPartie().traduireSaisie(saisies[1].charAt(0)), this.fenetre.getPartie().traduireSaisie(saisies[1].charAt(1)), this.fenetre.getPartie().getPlateau().getPiece(this.fenetre.getPartie().traduireSaisie(saisies[0].charAt(0)), this.fenetre.getPartie().traduireSaisie(saisies[0].charAt(1))));

					int promo = this.fenetre.getPartie().getPlateau().detecterPromotion();
					if (promo != -1) {
						this.fenetre.majPlateau();
						String choix = this.fenetre.saisiePromotion();
						this.fenetre.getPartie().getPlateau().fairePromotion(choix, promo);
					}
					
					this.fenetre.getPartie().setJoueurActuel(this.fenetre.getPartie().swapJoueur(this.fenetre.getPartie().getJoueurActuel()));
					this.fenetre.setLabelJoueur(this.fenetre.getPartie().getNomJoueurActuel());

					//System.out.println(this.fenetre.getPartie().getPlateau().toString());

					this.fenetre.majPlateau();
					this.fenetre.majTour();
					//check promotion
				}
				
				else {
					this.fenetre.erreurSaisie();
				}
				
			
			}
			else {
				this.fenetre.erreurSaisie();
			}
			
			
			}
			else {
				System.out.println("Je passe ici");
				this.fenetre.finPartieGagnant();
			}
			
			break;
		case "Nouvelle partie":
			this.fenetre.nouvellePartie();
			break;
		case "Charger une partie":
			this.fenetre.chargerPartie();
			break;
		case "Sauvegarder la partie":
			this.fenetre.sauvegarderPartie();
			break;
		case "Informations":
			this.fenetre.informations();
			break;
		}
	}

}
