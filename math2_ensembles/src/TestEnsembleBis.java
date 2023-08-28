import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class TestEnsembleBis {
	private static final int NB_IMPLEMENTATIONS = 2;
	private final static Scanner scanner = new Scanner(System.in);
	private static final String[] NOMS_METHODES = { "ajouter","enlever",
		"intersecter","inclusDans", "equals"};
	private static final Elt[] ELTS1={new Elt(5),new Elt(14),new Elt(9),new Elt(25),new Elt(17),new Elt(6)};
	private static final Elt[] ELTS2={Elt.MAXELT,new Elt(14),new Elt(27),new Elt(18),new Elt(25)};
	private static final Elt[] ELTS3={Elt.MAXELT,new Elt(1),new Elt(27),new Elt(15),new Elt(25),new Elt(13), new Elt(35)};
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		int choix;
			while (true) {
				for (int i = 0; i < NOMS_METHODES.length; i++) {
					System.out.println((i + 1) + " -> Test " + NOMS_METHODES[i]);
				}
				System.out.println("autre -> Quitter");
				choix = scanner.nextInt();
				boolean testOK = false;
				switch (choix) {
				case 1:
					testOK = testAjouter();
					break;
				case 2:
					testOK = testEnlever();
					break;
				case 3:
					testOK = testIntersecter();
					break;
				case 4:
					testOK = testInclusDans();
					break;
				case 5:
					testOK = testEquals();
					break;
				default : return;		
				}
				if (testOK)
					System.out.println("Le test de la m�thode " + NOMS_METHODES[choix - 1]
						+ " a r�ussi.");
				else
					System.out.println("Le test de la m�thode " + NOMS_METHODES[choix - 1]
						+ " a �chou�.");
			}
		}

	private static boolean testAjouter() {
		for (int i = 1; i <= NB_IMPLEMENTATIONS; i++){
			for (int j = 1; j <= NB_IMPLEMENTATIONS; j++){
				if (!testAjouter(i,j)) return false;
			}
		}
		return true;
	}
	
	private static boolean testAjouter(int typeEnsCourant,int typeEnsParametre){
		boolean testOK = true;
		EnsembleInterface ens1 = creerEnsi(typeEnsCourant, new Elt[0]);
		EnsembleInterface ens2 = null;
		try {
			ens1.ajouter(ens2);
			testOK = false;
			System.out.println("Appel de la m�thode ajouter avec un param�tre null --> Il fallait lancer une IllegalArgumentException !");
		} catch(IllegalArgumentException e){
			
		}
		HashSet<Elt> contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		ens1 = creerEnsi(typeEnsCourant, ELTS1);
		ens2 = creerEnsi(typeEnsParametre,new Elt[0]);
		//ajout de l'ensemble vide
		HashSet<Elt> avant = new HashSet<Elt>(contenuEns1);
		ens1.ajouter(ens2);
		int cardinal = ens1.cardinal();
		if (cardinal != contenuEns1.size()){
			testOK = false;
			System.out.println("On a ajout� l'ensemble "  + afficher(toSet(ens2))+ " � l'ensemble " + afficher(avant)+".");
			System.out.println("Le cardinal devrait valoir " + contenuEns1.size()+".");
			System.out.println("Le cardinal de votre ensemble est � " + cardinal+".");
		} else {
			HashSet<Elt> resultat = toSet(ens1);
			if (!resultat.equals(contenuEns1)){
				testOK = false;
				System.out.println("On a ajout� l'ensemble "  + afficher(toSet(ens2))+ " � l'ensemble " + afficher(avant)+".");
				System.out.println("El�ments qui devraient �tre pr�sent dans l'union : " + afficher(contenuEns1));
				System.out.println("El�ments pr�sents dans votre ensemble " + afficher(resultat));
			} else if(!toSet(ens2).isEmpty()) {
				testOK = false;
				System.out.println("Votre m�thode ajouter a modifi� l'ensemble pass� en param�tre !");
			}
		}
		ens1 = creerEnsi(typeEnsCourant, ELTS1);
		ens2 = creerEnsi(typeEnsParametre, ELTS2);
		HashSet<Elt> contenuEns2 = toSet(ens2);
		avant = new HashSet<Elt>(contenuEns1);
		contenuEns1.addAll(contenuEns2);
		ens1.ajouter(ens2);
		cardinal = ens1.cardinal();
		if (cardinal != contenuEns1.size()){
			testOK = false;
			System.out.println("On a ajout� l'ensemble "  + afficher(contenuEns2)+ " � l'ensemble " + afficher(avant)+".");
			System.out.println("Le cardinal devrait valoir " + contenuEns1.size()+".");
			System.out.println("Le cardinal de votre ensemble est � " + cardinal+".");
		} else {
			HashSet<Elt> resultat = toSet(ens1);
			if (!resultat.equals(contenuEns1)){
				testOK = false;
				System.out.println("On a ajout� l'ensemble "  + afficher(contenuEns2)+ " � l'ensemble " + afficher(avant)+".");
				System.out.println("El�ments qui devraient �tre pr�sent dans l'union : " + afficher(contenuEns1));
				System.out.println("El�ments pr�sents dans votre ensemble " + afficher(resultat));
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode ajouter a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode ajouter a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		

		}
		return testOK;
	}
	
	private static boolean testEnlever() {
		for (int i = 1; i <= NB_IMPLEMENTATIONS; i++){
			for (int j = 1; j <= NB_IMPLEMENTATIONS; j++){
				if (!testEnlever(i,j)) return false;
			}
		}
		return true;
	}
	
	private static boolean testEnlever(int typeEnsCourant,int typeEnsParametre){
		boolean testOK = true;
		EnsembleInterface ens1 = creerEnsi(typeEnsCourant, new Elt[0]);
		EnsembleInterface ens2 = null;
		try {
			ens1.enlever(ens2);
			testOK = false;
			System.out.println("Appel de la m�thode enlever avec un param�tre null --> Il fallait lancer une IllegalArgumentException !");
		} catch(IllegalArgumentException e){
			
		}
		HashSet<Elt> contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		ens1 = creerEnsi(typeEnsCourant, ELTS1);
		ens2 = creerEnsi(typeEnsParametre,new Elt[0]);
		//suppression de l'ensemble vide
		HashSet<Elt> avant = new HashSet<Elt>(contenuEns1);
		ens1.enlever(ens2);
		int cardinal = ens1.cardinal();
		if (cardinal != contenuEns1.size()){
			testOK = false;
			System.out.println("On a enlev� l'ensemble "  + afficher(toSet(ens2))+ " de l'ensemble " + afficher(avant)+".");
			System.out.println("Le cardinal devrait valoir " + contenuEns1.size()+".");
			System.out.println("Le cardinal de votre ensemble est � " + cardinal+".");
		} else {
			HashSet<Elt> resultat = toSet(ens1);
			if (!resultat.equals(contenuEns1)){
				testOK = false;
				System.out.println("On a enlev� l'ensemble "  + afficher(toSet(ens2))+ " de l'ensemble " + afficher(avant)+".");
				System.out.println("El�ments qui devraient �tre pr�sent dans la diff�rence : " + afficher(contenuEns1));
				System.out.println("El�ments pr�sents dans votre ensemble " + afficher(resultat));
			} else if(!toSet(ens2).isEmpty()) {
				testOK = false;
				System.out.println("Votre m�thode enlever a modifi� l'ensemble pass� en param�tre !");
			}
		}
		ens1 = creerEnsi(typeEnsCourant, ELTS3);
		ens2 = creerEnsi(typeEnsParametre, ELTS2);
		contenuEns1 = new HashSet<>();
		for (Elt e : ELTS3) contenuEns1.add(e);
		HashSet<Elt> contenuEns2 = toSet(ens2);
		avant = new HashSet<Elt>(contenuEns1);
		contenuEns1.removeAll(contenuEns2);
		ens1.enlever(ens2);
		cardinal = ens1.cardinal();
		if (cardinal != contenuEns1.size()){
			testOK = false;
			System.out.println("On a enlev� l'ensemble "  + afficher(contenuEns2)+ " de l'ensemble " + afficher(avant)+".");
			System.out.println("Le cardinal devrait valoir " + contenuEns1.size()+".");
			System.out.println("Le cardinal de votre ensemble est � " + cardinal+".");
		} else {
			HashSet<Elt> resultat = toSet(ens1);
			if (!resultat.equals(contenuEns1)){
				testOK = false;
				System.out.println("On a enlev� l'ensemble "  + afficher(contenuEns2)+ " de l'ensemble " + afficher(avant)+".");
				System.out.println("El�ments qui devraient �tre pr�sent dans la diff�rence : " + afficher(contenuEns1));
				System.out.println("El�ments pr�sents dans votre ensemble " + afficher(resultat));
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode enlever a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode enlever a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		}
		return testOK;
	}

	private static boolean testIntersecter() {
		for (int i = 1; i <= NB_IMPLEMENTATIONS; i++){
			for (int j = 1; j <= NB_IMPLEMENTATIONS; j++){
				if (!testIntersecter(i,j)) return false;
			}
		}
		return true;
	}

	private static boolean testIntersecter(int typeEnsCourant, int typeEnsParametre) {
		boolean testOK = true;
		EnsembleInterface ens1 = creerEnsi(typeEnsCourant, new Elt[0]);
		EnsembleInterface ens2 = null;
		try {
			ens1.intersecter(ens2);
			testOK = false;
			System.out.println("Appel de la m�thode intersecter avec un param�tre null --> Il fallait lancer une IllegalArgumentException !");
		} catch(IllegalArgumentException e){
			
		}
		HashSet<Elt> contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		ens1 = creerEnsi(typeEnsCourant, ELTS1);
		final EnsembleInterface ens3 = creerEnsi(typeEnsParametre,new Elt[0]);
		contenuEns1.removeIf((e)->!ens3.contient(e) );
		//intersection avec  l'ensemble vide
		HashSet<Elt> avant = new HashSet<Elt>(contenuEns1);
		ens1.intersecter(ens3);
		int cardinal = ens1.cardinal();
		if (cardinal != contenuEns1.size()){
			testOK = false;
			System.out.println("On a pris l'intersection de l'ensemble "  + afficher(avant)+ " avec l'ensemble " + afficher(toSet(ens3))+".");
			System.out.println("Le cardinal devrait valoir " + contenuEns1.size());
			System.out.println("Le cardinal de votre ensemble est � : " + cardinal);
		} else {
			HashSet<Elt> resultat = toSet(ens1);
			if (!resultat.equals(contenuEns1)){
				testOK = false;
				System.out.println("On a pris l'intersection de l'ensemble "  + afficher(avant)+ " avec l'ensemble " + afficher(toSet(ens3))+".");
				System.out.println("El�ments qui devraient �tre pr�sent dans l'intersection : " + afficher(contenuEns1));
				System.out.println("El�ments pr�sents dans votre ensemble " + afficher(resultat));
			} else if(!toSet(ens3).isEmpty()) {
				testOK = false;
				System.out.println("Votre m�thode intersecter a modifi� l'ensemble pass� en param�tre !");
			}
		}
		ens1 = creerEnsi(typeEnsCourant, ELTS2);
		ens2 = creerEnsi(typeEnsParametre, ELTS1);
		contenuEns1 = new HashSet<>();
		for (Elt e : ELTS2) contenuEns1.add(e);
		final HashSet<Elt> contenuEns2 = toSet(ens2);
		avant = new HashSet<Elt>(contenuEns1);
		contenuEns1.removeIf((e)->!contenuEns2.contains(e));
		ens1.intersecter(ens2);
		cardinal = ens1.cardinal();
		if (cardinal != contenuEns1.size()){
			testOK = false;
			System.out.println("On a pris l'intersection de l'ensemble "  + afficher(avant)+ " avec l'ensemble " + afficher(contenuEns2)+".");
			System.out.println("Le cardinal devrait valoir " + contenuEns1.size()+".");
			System.out.println("Le cardinal de votre ensemble est � " + cardinal+".");
		} else {
			HashSet<Elt> resultat = toSet(ens1);
			if (!resultat.equals(contenuEns1)){
				testOK = false;
				System.out.println("On a pris l'intersection de l'ensemble "  + afficher(avant)+ " avec l'ensemble " + afficher(contenuEns2)+".");
				System.out.println("El�ments qui devraient �tre pr�sent dans l'intersaction : " + afficher(contenuEns1));
				System.out.println("El�ments pr�sents dans votre ensemble " + afficher(resultat));
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode intersecter a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode intersecter a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		}
		return testOK;
	}
	
	private static boolean testInclusDans() {
		for (int i = 1; i <= NB_IMPLEMENTATIONS; i++){
			for (int j = 1; j <= NB_IMPLEMENTATIONS; j++){
				if (!testInclusDans(i,j)) return false;
			}
		}
		return true;
	}

	private static boolean testInclusDans(int typeEnsCourant, int typeEnsParametre) {
		boolean testOK = true;
		EnsembleAbstrait ens1 = creerEnsi(typeEnsCourant, new Elt[0]);
		EnsembleAbstrait ens2 = null;
		try {
			ens1.inclusDans(ens2);
			testOK = false;
			System.out.println("Appel de la m�thode inclusDans avec un param�tre null --> Il fallait lancer une IllegalArgumentException !");
		} catch(IllegalArgumentException e){
			
		}
		ens2 = creerEnsi(typeEnsParametre, new Elt[0]);
		//ens1 = {}
		//ens2 = {}
		if (!ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " est inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il ne l'est pas.");
		}
		ens2 = creerEnsi(typeEnsParametre,ELTS1);
		//ens1 = {}
		//ens2 = {5,6,9,14,17,25}
		if (!ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " est inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il ne l'est pas.");
		}
		ens1 = creerEnsi(typeEnsCourant, ELTS1);
		HashSet<Elt> contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		ens2 = creerEnsi(typeEnsParametre,new Elt[0]);
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {}
		if (ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il l'est.");
		}
		ens2 = creerEnsi(typeEnsCourant, ELTS1);
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {5,6,9,14,17,25}
		if (!ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " est inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il ne l'est pas.");
		}
		
		ens2 = creerEnsi(typeEnsParametre,ELTS2);
		HashSet<Elt> contenuEns2= new HashSet<>();
		for (Elt e : ELTS2) contenuEns2.add(e);
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {14,18,25,27,50}
		if (ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il l'est.");
		}
		if (testOK){
			int cardinal = ens1.cardinal();
			if (cardinal != contenuEns1.size()){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� le cardinal de l'ensemble courant");
			} else if (!contenuEns1.equals(toSet(ens1))){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� les �l�ments pr�sents dans l'ensemble courant !");
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		}
		contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		contenuEns2 = toSet(ens2);
		for (Elt e : ELTS1){
			ens2.ajouter(e);
			contenuEns2.add(e);
		}
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {5,6,9,14,17,18,25,27,50}
		if (!ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " est inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il ne l'est pas.");
		}
		if (testOK){
			int cardinal = ens1.cardinal();
			if (cardinal != contenuEns1.size()){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� le cardinal de l'ensemble courant");
			} else if (!contenuEns1.equals(toSet(ens1))){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� les �l�ments pr�sents dans l'ensemble courant !");
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		}
		ens1.ajouter(Elt.MAXELT);
		ens2.enlever(Elt.MAXELT);
		//ens1 = {5,6,9,14,17,25,50}
		//ens2 = {5,6,9,14,17,18,25,27}
		if (ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il l'est.");
		}	
		for (Elt e : ELTS2){
			ens1.ajouter(e);
			contenuEns1.add(e);
		}
		for (Elt e : ELTS3){
			ens1.ajouter(e);
			contenuEns1.add(e);
		}
		//ens1 = {1,5,6,9,13,14,15,17,18,25,27,35,50}
		//ens2 = {5,6,9,14,17,18,25,27}
		if (ens1.inclusDans(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas inclus dans l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode inclusDans dit qu'il l'est.");
		}		
		return testOK;
	}

	private static boolean testEquals() {
		for (int i = 1; i <= NB_IMPLEMENTATIONS; i++){
			for (int j = 1; j <= NB_IMPLEMENTATIONS; j++){
				if (!testEquals(i,j)) return false;
			}
		}
		return true;
	}


	
	private static boolean testEquals(int typeEnsCourant, int typeEnsParametre) {
		boolean testOK = true;
		EnsembleAbstrait ens1 = creerEnsi(typeEnsCourant, new Elt[0]);
		EnsembleAbstrait ens2 = creerEnsi(typeEnsParametre, new Elt[0]);		
		//ens1 = {}
		//ens2 = {}
		if (!ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " est �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il ne l'est pas.");
		}
		ens2 = creerEnsi(typeEnsParametre,ELTS1);
		//ens1 = {}
		//ens2 = {5,6,9,14,17,25}
		if (ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il l'est.");
		}
		ens1 = creerEnsi(typeEnsCourant, ELTS1);
		HashSet<Elt> contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {}
		ens2 = creerEnsi(typeEnsParametre,new Elt[0]);
		if (ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il l'est.");
		}		
		ens2 = creerEnsi(typeEnsCourant, ELTS1);
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {5,6,9,14,17,25}
		if (!ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " est �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il ne l'est pas.");
		}	
		ens1.ajouter(Elt.MAXELT);
		//ens1 = {5,6,9,14,17,25,50}
		//ens2 = {5,6,9,14,17,25}
		if (ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il l'est.");
		}	
		ens1.enlever(Elt.MAXELT);
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {14,18,25,27,50}
		ens2 = creerEnsi(typeEnsParametre,ELTS2);
		HashSet<Elt> contenuEns2= new HashSet<>();
		for (Elt e : ELTS2) contenuEns2.add(e);
		if (ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il l'est.");
		}
		if (testOK){
			int cardinal = ens1.cardinal();
			if (cardinal != contenuEns1.size()){
				testOK = false;
				System.out.println("Votre m�thode equals a modifi� le cardinal de l'ensemble courant");
			} else if (!contenuEns1.equals(toSet(ens1))){
				testOK = false;
				System.out.println("Votre m�thode equals a modifi� les �l�ments pr�sents dans l'ensemble courant !");
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode equals a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode equals a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		}
		contenuEns1 = new HashSet<>();
		for (Elt e : ELTS1) contenuEns1.add(e);
		contenuEns2 = toSet(ens2);
		for (Elt e : ELTS1){
			ens2.ajouter(e);
			contenuEns2.add(e);
		}
		//ens1 = {5,6,9,14,17,25}
		//ens2 = {5,6,9,14,17,18,25,27,50}
		if (ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il l'est.");
		}
		if (testOK){
			int cardinal = ens1.cardinal();
			if (cardinal != contenuEns1.size()){
				testOK = false;
				System.out.println("Votre m�thode inclusDans a modifi� le cardinal de l'ensemble courant");
			} else if (!contenuEns1.equals(toSet(ens1))){
				testOK = false;
				System.out.println("Votre m�thode enlever a modifi� les �l�ments pr�sents dans l'ensemble courant !");
			} else if(ens2.cardinal() != contenuEns2.size()) {
				testOK = false;
				System.out.println("Votre m�thode enlever a modifi� le cardinal de l'ensemble pass� en param�tre !");
			} else if (!contenuEns2.equals(toSet(ens2))){
				testOK = false;
				System.out.println("Votre m�thode enlever a modifi� les �l�ments pr�sents dans l'ensemble pass� en param�tre !");
			}
		}
		for (Elt e : ELTS2){
			ens1.ajouter(e);
			contenuEns1.add(e);
		}
		for (Elt e : ELTS3){
			ens1.ajouter(e);
			contenuEns1.add(e);
		}
		//ens1 = {1,5,6,9,13,14,15,17,18,25,27,35,50}
		//ens2 = {5,6,9,14,17,18,25,27,50}
		if (ens1.equals(ens2)){
			testOK = false;
			System.out.println("L'ensemble " + afficher(toSet(ens1)) + " n'est pas �gal � l'ensemble " + afficher(toSet(ens2))+".");
			System.out.println("Votre m�thode equals dit qu'il l'est.");
		}	
		return testOK;
	}

	private static EnsembleAbstrait creerEnsi(int i,Elt...elts){
		try {
			Class cls = Class.forName("Ens" + i); 
			EnsembleAbstrait ens = (EnsembleAbstrait) cls.newInstance();
			for (Elt e : elts) ens.ajouter(new Elt(e));
			return ens;
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException e) {
			return null;
		} 
	}
	
	private static HashSet<Elt> toSet(EnsembleInterface ens){
		HashSet<Elt> set = new HashSet<Elt>();
		for (int i = 1; i <= Elt.MAXELT.val();i++ ){
			Elt ei = new Elt(i);
			if (ens.contient(ei )) set.add(ei);
		}
		return set;
	}
	
	private static String afficher(HashSet<Elt> set){
		String result = "{";
		if (set.isEmpty()) 
				return result + "}";
		Iterator<Elt> it = set.iterator();
		result += it.next();
		while(it.hasNext()){
			result += ","+it.next();
		}
		return result + "}";
	}
	


}
