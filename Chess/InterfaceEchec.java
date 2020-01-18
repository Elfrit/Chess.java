import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class InterfaceEchec extends JFrame {
	private Partie partie;
	private JLabel label_joueur = null;
	private JLabel label_tour = null;
	private JLabel label_historique;
	//private JLabel label_info = null;
	private JTextField tfpiece;
	private JTextField tfmouv;

	private JButton btnconfirm;
	
	public InterfaceEchec(String titre, int w, int h) {
		super(titre);
		this.partie = new Partie();
		this.partie.setPlateau(new Plateau());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.CentreEcran(w,h);
		this.initialise();
		this.initialiseMenu();
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void setLabelJoueur(String joueur) {
		this.label_joueur.setText("Joueur : " + joueur);
	}
	
	public JTextField get_tfPiece() {
		return this.tfpiece;
	}
	
	public JTextField get_tfMouv() {
		return this.tfmouv;
	}
	
	public Partie getPartie() { 
		return this.partie;
	}
	
	public void CentreEcran(int width,int height) {
		Toolkit aTK = Toolkit.getDefaultToolkit();
		Dimension dim = aTK.getScreenSize();
		this.setBounds((dim.width-width)/2,(dim.height-height)/2,width,height);
	}
	
	public void initialise() {
		 this.setLayout(new BorderLayout());
		 this.add(this.getPanelInformation(),BorderLayout.NORTH);
		 this.add(this.getPanelEchiquierVide(),BorderLayout.CENTER);
		 this.add(this.getPanelMenu(), BorderLayout.SOUTH);
		 this.setVisible(true);
	}

	public void initialiseMenu() {
		JMenuBar jmb = new JMenuBar();
		this.setJMenuBar(jmb);
		EcouteBoutons bl = new EcouteBoutons(this);

		JMenu mop = new JMenu("Options");
		jmb.add(mop);

		JMenuItem mnp = new JMenuItem("Nouvelle partie");
		mop.add(mnp);
		mnp.addActionListener(bl);
		
		JMenuItem mcp = new JMenuItem("Charger une partie");
		mop.add(mcp);
		mcp.addActionListener(bl);
		
		JMenuItem msp = new JMenuItem("Sauvegarder la partie");
		mop.add(msp);
		msp.addActionListener(bl);

		JMenuItem minfos = new JMenuItem("Informations");
		mop.add(minfos);
		minfos.addActionListener(bl);	
	}
	
	public JPanel getPanelInformation() {
		JPanel info = new JPanel();
		JPanel saisies = new JPanel();
		JPanel infojoueurtour = new JPanel();
	    info.setLayout(new BorderLayout());
		
		this.label_joueur = new JLabel("Joueur : " + "/");
		this.label_tour = new JLabel("Tour n°" + "/");
		//this.label_info = new JLabel("");
			
	    JLabel lspiece = new JLabel("Saisie pièce :");
	    tfpiece = new JTextField(2);
	    
		JLabel lsmouv = new JLabel("Saisie mouvement : ");
	    tfmouv = new JTextField(2);

	    btnconfirm = new JButton("Valider");
		EcouteBoutons bl = new EcouteBoutons(this);
		btnconfirm.addActionListener(bl);
		btnconfirm.setEnabled(false);
	    btnconfirm.setFocusable(false);

	    
		infojoueurtour.add(label_joueur);
		infojoueurtour.add(label_tour);
		saisies.add(lspiece);
		saisies.add(tfpiece);
		saisies.add(lsmouv);
		saisies.add(tfmouv);
		saisies.add(btnconfirm);
		
		info.add(infojoueurtour, BorderLayout.NORTH);
		info.add(saisies, BorderLayout.SOUTH);
		
		return info;
	}
	
	public JPanel getPanelEchiquierVide() {
	    JPanel echiquier = new JPanel();
	    echiquier.setLayout(new GridLayout(9,9,0,0));
		   
	    boolean p = true;
    	Font f = new Font("Serif", Font.PLAIN, 30);
 
    	echiquier.add(new JLabel());
    	for (int i = 0; i<8; i++) {
        	JLabel lettre = new JLabel();
        	lettre.setText(Character.toString((char)(i +65)));
        	lettre.setFont(f); 
        	lettre.setHorizontalAlignment(JLabel.CENTER);
        	lettre.setVerticalAlignment(JLabel.CENTER);
        	echiquier.add(lettre);

    	}
    	JLabel labelchiffre = new JLabel();
    	labelchiffre.setText("8");
    	labelchiffre.setFont(f);
    	labelchiffre.setHorizontalAlignment(JLabel.CENTER);
    	labelchiffre.setVerticalAlignment(JLabel.CENTER);
    	echiquier.add(labelchiffre);

    	
    	int chiffre = 7;

	    for(int i=1; i<65; i++) {
	    	if (i == 9 || i == 17 ||  i == 25 || i == 33 || i == 41 || i == 49 || i == 57) {
	        	JLabel labelchiffre1 = new JLabel();
	        	labelchiffre1.setText(Integer.toString(chiffre));
	        	labelchiffre1.setFont(f);
	        	labelchiffre1.setHorizontalAlignment(JLabel.CENTER);
	        	labelchiffre1.setVerticalAlignment(JLabel.CENTER);
	        	echiquier.add(labelchiffre1);
	        	chiffre -= 1;
	    	}
	    	
	    	JLabel cases = new JLabel();
	    	cases.setOpaque(true);
	    	cases.setBackground(Color.white);
	    	cases.setHorizontalAlignment(JLabel.CENTER);
	    	cases.setVerticalAlignment(JLabel.CENTER);
		    
	    	cases.setText(" " +  " " + " ");
	    	cases.setFont(f); 

	    	if (p == false) {
	    		cases.setBackground(Color.LIGHT_GRAY);
		    	p = !p;	
	    	}
	    	
	    	else {
		    	p = !p;
	    	}
	    
    		echiquier.add(cases);

	    	if (i%8 == 0)  {
	    		p = !p;
	    	}
	    }
		return echiquier;
	}
	
	
	public JPanel getPanelEchiquier() {
	    JPanel echiquier = new JPanel();
	    echiquier.setLayout(new GridLayout(9,9,0,0));
		
	    boolean p = true;
    	Font f = new Font("Serif", Font.PLAIN, 30);
 
    	echiquier.add(new JLabel());
    	for (int i = 0; i<8; i++) {
        	JLabel lettre = new JLabel();
        	lettre.setText(Character.toString((char)(i +65)));
        	lettre.setFont(f); 
        	lettre.setHorizontalAlignment(JLabel.CENTER);
        	lettre.setVerticalAlignment(JLabel.CENTER);
        	echiquier.add(lettre);

    	}
    	JLabel labelchiffre = new JLabel();
    	labelchiffre.setText("8");
    	labelchiffre.setFont(f);
    	labelchiffre.setHorizontalAlignment(JLabel.CENTER);
    	labelchiffre.setVerticalAlignment(JLabel.CENTER);
    	echiquier.add(labelchiffre);

    	int chiffre = 7;

	    for(int i=1; i<65; i++) {
	    	if (i == 9 || i == 17 ||  i == 25 || i == 33 || i == 41 || i == 49 || i == 57) {
	        	JLabel labelchiffre1 = new JLabel();
	        	labelchiffre1.setText(Integer.toString(chiffre));
	        	labelchiffre1.setFont(f);
	        	labelchiffre1.setHorizontalAlignment(JLabel.CENTER);
	        	labelchiffre1.setVerticalAlignment(JLabel.CENTER);
	        	echiquier.add(labelchiffre1);
	        	chiffre -= 1;
	    	}
	    	
	    	JLabel cases = new JLabel();
	    	cases.setOpaque(true);
	    	cases.setBackground(Color.white);
	    	cases.setHorizontalAlignment(JLabel.CENTER);
	    	cases.setVerticalAlignment(JLabel.CENTER);

	    	if (this.partie.getPlateau().getEchiquier()[i-1] == null) {
		    	cases.setText(" " +  " " + " ");

	    	}
	    	else {
		    	cases.setText(" " +  this.partie.getPlateau().getEchiquier()[i-1] + " ");

	    	}
	    	cases.setFont(f); 

	    	if (p == false) {
	    		cases.setBackground(Color.LIGHT_GRAY);
		    	p = !p;	
	    	}
	    	
	    	else {
		    	p = !p;
	    	}
	    

    		echiquier.add(cases);

	    	if (i%8 == 0)  {
	    		p = !p;
	    	}

	    }
	    

	    return echiquier;
	}
	
	public JPanel getPanelMenu() {
		JPanel menusup = new JPanel();
	    JPanel menu = new JPanel();
	    menu.setBackground(Color.white);
	    menusup.setLayout(new BorderLayout());

		label_historique = new JLabel("Historique :");
		
	    JTextArea affi_historique = new JTextArea(1,20);
	    
	    affi_historique.setEditable(false);
		
	    menu.add(label_historique);
	    menu.add(affi_historique);

	    menusup.add(menu, BorderLayout.NORTH);
	    JScrollPane sp = new JScrollPane(menu);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
	    menusup.add(sp, BorderLayout.SOUTH);
	    


	    return menusup;
	}
	
	
	public JPanel getPanelHistorique() {

	    JPanel historique = new JPanel();
	    historique.setPreferredSize(new Dimension(2000, 2000));
	    
		JLabel label_historique = new JLabel ("Historique :");
		
	    JTextArea affi_historique = new JTextArea();
	    
		JScrollPane sp = new JScrollPane(affi_historique);
		sp.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		historique.add(sp);

	    affi_historique.setEditable(false);
	    
	    JButton copier = new JButton("Copier");
	    
	    historique.add(label_historique);
	    historique.add(affi_historique);
	    historique.add(copier);
	    
	    return historique;
	}

	public void nouvellePartie() {
		this.initialiserPartie();
	}

	private void initialiserPartie() {
		this.demanderJoueur();
		this.partie.setPlateau(new Plateau());
		this.add(this.getPanelEchiquier(),BorderLayout.CENTER);
		btnconfirm.setEnabled(true);
		this.label_tour.setText("Tour n°1");
		this.label_historique.setText("Historique : ");
		this.partie.setHistorique(null);
	}
	
	
	public void majPlateau() {
		this.add(this.getPanelEchiquier(),BorderLayout.CENTER);
		this.setVisible(true);
	}

	private void demanderJoueur() {
		int js = JOptionPane.showConfirmDialog(null, "Jouez-vous tout seul ?", "Création des profils de joueurs", JOptionPane.YES_NO_OPTION);
		Joueur[] joueurs = new Joueur[2];


		String j1 = new String("camp des pièces blanches");
		String j2 = new String("camp des pièces noires");

		if (js == 1) {
			j1 = JOptionPane.showInputDialog("Veuillez saisir un nom pour le premier joueur (camp des pièces blanches) :", "camp des pièces blanches");
			j2 = JOptionPane.showInputDialog("Veuillez saisir un nom pour le deuxième joueur (camp des pièces noires) :", "camp des pièces noires");
		}
	
		joueurs[0] = new Joueur(j1, true);
		joueurs[1] = new Joueur(j2, false);
		
		this.partie.setJoueurs(joueurs);
		this.partie.setJoueurActuel(true);
		
		this.label_joueur.setText("Joueur : " + this.partie.getNomJoueurActuel());
		
		this.setTitle("Échec");


	
	}

	public void chargerPartie() {
		try {
			this.partie.initialiserChargementPartie(this.partie.chargementPartie(this.demanderFichierFenetre()));
			this.majPlateau();
			this.label_historique.setText("Historique : " + this.getPartie().getHistorique());
			this.majTour();
			this.btnconfirm.setEnabled(true);
			this.label_joueur.setText("Joueur : "+ this.partie.getNomJoueurActuel());
		}
		catch (Exception e) {
			//System.out.println(e);
			JOptionPane.showMessageDialog(this, "Erreur : Le chargement n'a pas été effectué correctement.\n" + e, "Chargement", JOptionPane.ERROR_MESSAGE);

		}
		
	}

	public String demanderFichierFenetre() throws Exception {
		  JFileChooser chooser = new JFileChooser();
		  FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier texte (.txt)", "txt");
		  chooser.setFileFilter(filter);
		  chooser.showOpenDialog(chooser.getParent());
		  return chooser.getSelectedFile().getCanonicalPath();
	}
	
	public void sauvegarderPartie() {
		PrintWriter sortie;
		try {
			String fichier = this.demanderFichierFenetre();
			sortie = new PrintWriter (new BufferedWriter (new FileWriter (fichier)));
			sortie.println(this.partie.formatPlateau() + "\n" + this.partie.getHistorique() + "\n" + this.partie.getNomJoueurs() + "\n" + this.partie.getJoueurActuel());
			sortie.close();
			JOptionPane.showMessageDialog(this, "Sauvegarde effectuer avec succès !", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);

		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Erreur : La sauvegarde n'a pas été effectué correctement.\n" + e, "Sauvegarde", JOptionPane.ERROR_MESSAGE);

		}
	}

	public void informations() {
		JOptionPane.showMessageDialog(this, "Jeu d'échec - IUT de Villetaneuse - S2 - 2019.\n\nContact :\nBerlemont Lucas - abc.d@xyz.xy\nMekhanef Sami - efgh.i@xyz.xy", "Informations", JOptionPane.INFORMATION_MESSAGE);
		
	}

	public void erreurSaisie() {
		JOptionPane.showMessageDialog(this, "Erreur : Saisie(s) invalide(s)", "Saisie invalide", JOptionPane.ERROR_MESSAGE);
		
	}
	
	public String saisiePromotion() {
		int l = 0;

		String[] choixpieces = new String[]{"Dame","Tour","Cavalier","Fou"};
		
		//System.out.println("test");
		String choix = (String)JOptionPane.showInputDialog(null,"Choississez la promotion de votre pion", "Choix de la promotion",JOptionPane.QUESTION_MESSAGE, null, choixpieces, choixpieces[0]);
	 
			if("Dame".equals(choix)){
				l = 0;
			}
			else if ("Tour".equals(choix)){
				l = 1;
			}
			else if ("Cavalier".equals(choix)){
				l = 2;
			}
			else if ("Fou".equals(choix)){
				l = 3;
			}
			
			return Character.toString(choixpieces[l].charAt(0));
	}
	
	public void majHistorique(String posini, String mouv) {
		this.getPartie().majHistorique(posini, mouv);
		this.label_historique.setText("Historique : " + this.getPartie().getHistorique());
	}

	public void majTour() {
		this.label_tour.setText("Tour n°" + Integer.toString(this.getPartie().compterTour(this.getPartie().getHistorique())));
		
	}
	
	public void finPartieGagnant() {
		if (this.getPartie().determinerSiMat()) {
			JOptionPane.showMessageDialog(this, "Victoire par mat de " + this.getPartie().getNomJoueurActuel() + " !", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);

		}
		
		if (this.getPartie().determinerSiPat()) {
			JOptionPane.showMessageDialog(this, "Partie déclarée nulle par pat.", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	public static void main(String[] args) {
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
	     
		for (UIManager.LookAndFeelInfo look : looks) {
	         //System.out.println(look.getClassName());
	     }
	     try {
	       UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	     }
	     catch (Exception e) {

	     }
	     
		new InterfaceEchec("Échec", 440, 520);
		
	}
}
