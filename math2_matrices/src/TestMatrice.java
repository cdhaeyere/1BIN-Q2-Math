import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.stream.Stream;

public class TestMatrice {
	private static Class classe = Matrice.class;
	private static Field data;
	private static Field nbLigne;
	private static Field nbColonne;
	private static final Scanner scanner = new Scanner(System.in);
	private static final String[] NOMS_METHODES = {
			"du constructeur Matrice(int a, int b)",
			"du constructeur Matrice(double[][] tab)",
			"du constructeur Matrice(Matrice a)",
			"de la méthode identite",
			"de la méthode getElement",
			"de la méthode somme",
			"de la méthode produitParScalaire",
			"de la méthode produitAGauche",
			"de la méthode produitADroite",
			"de la méthode carree",
			"de la méthode puissance",
				"de la méthode transposee" };

	public static void main(String[] args) throws IllegalAccessException {
		Field[] champs = classe.getDeclaredFields();
		for (Field f : champs) {
			if (f.getType() == double[][].class) {
				data = f;
				data.setAccessible(true);
				if (!Modifier.isPrivate(f.getModifiers())) {
					System.out.println("\nLE TABLEAU " + f.getName()
							+ " DOIT ÊTRE MIS EN PRIVATE !!!");
					return;
				}
			} else if (f.getType() == int.class) {
				if (f.getName().equals("nbLignes")) {
					nbLigne = f;
					nbLigne.setAccessible(true);
				} else if (f.getName().equals("nbColonnes")) {
					nbColonne = f;
					nbColonne.setAccessible(true);
				}
				if (!Modifier.isPrivate(f.getModifiers())) {
					System.out.println("\nVOUS AURIEZ DU DECLARER LE CHAMP "
							+ f.getName() + " EN PRIVATE!!!");
					return;
				}

			} else {
				System.out.println("QUE REPRESENTE LE CHAMP " + f.getName()
						+ " DE VOTRE CLASSE Matrice?");
				return;
			}
		}
		if (data == null) {
			System.out
					.println("Vous n'avez pas créé de champ de type double[][]");
			return;
		}
		if (nbLigne == null) {
			System.out
					.println("Vous avez enlevé le champ pour le nombre de lignes.");
			return;
		}
		if (nbColonne == null) {
			System.out
					.println("Vous avez enlevé le champ pour le nombre de colonnes.");
			return;
		}

		System.out.println("**************************************");
		System.out.println("Programme Test pour la classe Matrice");
		System.out.println("**************************************");

		int choix = 0;
		while (true) {
			for (int i = 0; i < NOMS_METHODES.length; i++) {
				System.out.println((i + 1) + " -> Test " + NOMS_METHODES[i]);
			}
			System.out.println((NOMS_METHODES.length+1) + "-> Visualiser la chaîne de caractères renvoyée par votre méthode toString.");
			System.out.println("autre -> Quitter");

			choix = scanner.nextInt();
			boolean testOK;
			switch (choix) {
			case 1:
				testOK = testConstructeur1();
				break;
			case 2:
				testOK = testConstructeur2();
				break;
			case 3:
				testOK = testConstructeurParRecopie();
				break;
			case 4:
				testOK = testIdentite();
				break;
			case 5:
				testOK = testGetElement();
				break;
			case 6:
				testOK = testSomme();
				break;
			case 7:
				testOK = testProduitParSaclaire();
				break;
			case 8:
				testOK = testProduitAGauche();
				break;
			case 9:
				testOK = testProduitADroite();
				break;
			case 10:
				testOK = testCarree();
				break;
			case 11:
				testOK = testPuissance();
				break;
			case 12 :
				testOK = testTransposee(); 
				break;
			case 13 : 
				visualiserToString();
				continue;		
			default:
				return;
			}
			if (testOK)
				System.out.println("Le test " + NOMS_METHODES[choix - 1]
						+ " a réussi.");
			else
				System.out.println("Le test " + NOMS_METHODES[choix - 1]
						+ " a échoué.");
		}
	}


	private static boolean testConstructeur1() throws IllegalAccessException {
		Matrice m;
		boolean testOK = true;
		m = new Matrice(5, 3);
		double[][] coefficients = (double[][]) data.get(m);
		int nbLignes = nbLigne.getInt(m);
		int nbColonnes = nbColonne.getInt(m);
		if (coefficients == null) {
			System.out.println("L'attribut " + data.getName()
					+ " a été initialisé à null.");
			testOK = false;
		} else if (coefficients.length != 5) {
			System.out.println("Le tableau " + data.getName()
					+ " n'a pas le bon nombre de lignes.");
			testOK = false;
		} else {
			for (int i = 0; i < 5; i++) {
				if (coefficients[i] == null) {
					System.out.println("Le tableau d'indice " + i
							+ " de l'attribut " + data.getName()
							+ " a été initialisé à null !");
					testOK = false;
					break;
				} else if (coefficients[i].length != 3) {
					System.out.println("Le tableau d'indice " + i
							+ " de l'attribut " + data.getName()
							+ " n'a pas la bonne dimension !");
					testOK = false;
					break;
				}
			}
		}
		if (testOK) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					if (coefficients[i][j] != 0) {
						System.out
								.println("On a créé la matrice nulle de taille (5,3)");
						System.out
								.println("Les éléments de la matrice ne sont pas tous à 0.");
						testOK = false;
						break;
					}
				}
				if (!testOK)
					break;
			}
		}
		if (nbLignes != 5) {
			System.out.println("L'attribut " + nbLigne.getName()
					+ " est mal initialisé.");
			testOK = false;
		}
		if (nbColonnes != 3) {
			System.out.println("L'attribut " + nbColonne.getName()
					+ " est mal initialisé.");
			testOK = false;
		}
		try {
			new Matrice(0, 6);
			System.out
					.println("Création d'une matrice ayant 0 ligne --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		try {
			new Matrice(-1, 6);
			System.out
					.println("Création d'une matrice ayant -1 ligne --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		try {
			new Matrice(3, 0);
			System.out
					.println("Création d'une matrice ayant 0 colonne  --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		try {
			new Matrice(3, -2);
			System.out
					.println("Création d'une matrice ayant -2 colonne  --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		return testOK;
	}

	private static boolean testConstructeur2() throws IllegalAccessException {
		boolean testOK = true;
		double[][] tab1 = { { 1, 2, 3, 4 }, { 4, 5, 6, 7 }, { 7, 8, 9, 10 } };
		double[][] copie = { { 1, 2, 3, 4 }, { 4, 5, 6, 7 }, { 7, 8, 9, 10 } };
		Matrice m = new Matrice(tab1);
		int nbL = nbLigne.getInt(m);
		int nbC = nbColonne.getInt(m);
		if (nbL != 3) {
			System.out
					.println("Le nombre de lignes n'a pas été bien initialisé.");
			testOK = false;
		}
		if (nbC != 4) {
			System.out
					.println("Le nombre de colonnes n'a pas été bien initialisé.");
			testOK = false;
		}
		double[][] coefficients = (double[][]) data.get(m);
		if (coefficients == tab1) {
			testOK = false;
			System.out
					.println("Vous avez recopié l'adresse du tableau passé en paramètre plutôt que de recopier les données dans un nouveau tableau.");
		} else {
			for (int i = 0; i < tab1.length; i++) {
				if (tab1[i] == coefficients[i]) {
					System.out
							.println("Vous avez recopié l'adresse des lignes du tableau plutôt que de recopier les données.");
					testOK = false;
					break;
				}
			}
		}
		if (testOK) {
			if (!testEgaliteTableau(copie, coefficients)) {
				testOK = false;
				System.out
						.println("Les éléments de la matrice ont été mal initialisés");
				System.out.println("matrice qu'il fallait créer :");
				System.out.println(toStringTableau(copie));
				System.out.println("matrice que vous avez créée : ");
				System.out.println(toStringTableau(coefficients));
			}
		}
		if (testOK) {
			if (!testEgaliteTableau(copie, tab1)) {
				testOK = false;
				System.out
						.println("Vous avez modifié le tableau passé en paramètre");
			}
		}
		double[][] tab2 = null;
		try {
			m = new Matrice(tab2);
			System.out
					.println("Paramètre null --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e1) {

		}
		double[][] tab3 = {};
		try {
			m = new Matrice(tab3);
			System.out
					.println("Le tableau passé en paramètre est de dimension 0x0 --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		double[][] tab4 = { {}, {}, {} };
		try {
			m = new Matrice(tab4);
			System.out
					.println("Le tableau passé en paramètre est de dimension 3x0 --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		
		double[][] tab5 = { null, { 2, 3, 6 }, { 8, 6, 4 } };
		try {
			m = new Matrice(tab5);
			System.out
					.println("Une des lignes du tableau passé en paramètre est à null --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}

		double[][] tab6 = { { 2, 3, 6 }, null, { 8, 6, 4 } };
		try {
			m = new Matrice(tab6);
			System.out
					.println("Une des lignes du tableau passé en paramètre est à null --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		double[][] tab7 = { { 2, 3 }, { 5, 7, 12, 6 }, { 8, 6, 4,45 } };
		try {
			m = new Matrice(tab7);
			System.out.print("tableau passé en paramètre : ");
			System.out.println(toStringTableau(tab7));
			System.out
					.println("Le tableau passé en paramètre n'est pas \"rectangulaire \" --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {

		}
		double[][] tab8 = { { 2, 3, 6 }, { 5, 7, 12 }, { 8, 6 } };
		try {
			m = new Matrice(tab8);
			System.out.print("tableau passé en paramètre : ");
			System.out.println(toStringTableau(tab8));
			System.out
					.println("Le tableau passé en paramètre n'est pas \"rectangulaire \" --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		return testOK;
	}

	private static boolean testConstructeurParRecopie()
			throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		Matrice m = new Matrice(tab1);
		double[][] coefficientsM = (double[][]) data.get(m);
		Matrice copie = new Matrice(m);
		int nbL = nbLigne.getInt(copie);
		int nbC = nbColonne.getInt(copie);
		if (nbL != 3) {
			System.out
					.println("Le nombre de lignes n'a pas été bien initialisé.");
			testOK = false;
		}
		if (nbC != 4) {
			System.out
					.println("Le nombre de colonnes n'a pas été bien initialisé.");
			testOK = false;
		}
		double[][] coefficientsCopie = (double[][]) data.get(copie);
		if (coefficientsCopie == null) {
			System.out.println("L'attribut " + data.getName()
					+ " a été initialisé é null.");
			testOK = false;
		} else if (coefficientsM == coefficientsCopie) {
			testOK = false;
			System.out
					.println("Vous avez recopié l'adresse du tableau de la matrice passée en paramètre plutôt que de recopier les données dans un nouveau tableau.");
		} else {
			if (coefficientsCopie.length != 3) {
				System.out.println("Le tableau " + data.getName()
						+ " n'a pas le bon nombre de lignes.");
				testOK = false;
			} else {
				for (int i = 0; i < 3; i++) {
					if (coefficientsCopie[i] == null) {
						System.out.println("Le tableau d'indice " + i
								+ " de l'attribut " + data.getName()
								+ " a été initialisé à null !");
						testOK = false;
						break;
					} else if (coefficientsCopie[i].length != 4) {
						System.out.println("Le tableau d'indice " + i
								+ " de l'attribut " + data.getName()
								+ " n'a pas la bonne dimension !");
						testOK = false;
						break;
					} else if (coefficientsM[i] == coefficientsCopie[i]) {
						System.out
								.println("Vous avez recopié le tableau d'indice "
										+ i
										+ " au lieu de recopier les éléments dans un nouveau tableau.");
						testOK = false;
						break;
					}
				}
			}
		}
		if (testOK) {
			if (!testEgaliteTableau(tab1, coefficientsCopie)) {
				testOK = false;
				System.out
						.println("Les éléments de la matrice ont été mal initialisés");
				System.out.println("matrice qu'il fallait créer :");
				System.out.println(toStringTableau(tab1));
				System.out.println("matrice que vous avez créée : ");
				System.out.println(toStringTableau(coefficientsCopie));
			}
		}
		if (testOK){
			if (!testEgaliteTableau(tab1, coefficientsM)) {
				testOK = false;
				System.out.println("Vous avez modifié la matrice passée en paramètre.");
			}
		}
		m = null;
		try {
			new Matrice(m);
			testOK = false;
			System.out
					.println("Paramètre null --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		return testOK;
	}

	private static boolean testIdentite() throws IllegalAccessException {
		boolean testOK = true;
		double[][] id = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
				{ 0, 0, 0, 1 } };
		Matrice m = Matrice.identite(4);
		double[][] dataM = (double[][]) data.get(m);
		if (dataM.length != 4) {
			testOK = false;
			System.out.println("On a créé la matrice identité d'ordre 4.");
			System.out.println("Le tableau " + data.getName() + " a "
					+ dataM.length + " ligne(s) au lieu de 4.");
		} else if (dataM[0].length != 4) {
			testOK = false;
			System.out.println("On a créé la matrice identité d'ordre 4.");
			System.out.println("Le tableau d'indice 0 du tableau "
					+ data.getName() + " a " + dataM[0].length
					+ " éléments  au lieu de 4.");
		}
		if (testOK) {
			if (!testEgaliteTableau(id, dataM)) {
				testOK = false;
				System.out
						.println("Les éléments de la matrice sont mal initialisés.");
				System.out.println("Matrice attendue : ");
				System.out.println(toStringTableau(id));
				System.out.println("Matrice trouvée : ");
				System.out.println(toStringTableau(dataM));
			}
		}
		try {
			Matrice.identite(-1);
			testOK = false;
			System.out
					.println("On a demandé de créer la matrice identité d'ordre -1 --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Matrice.identite(0);
			testOK = false;
			System.out
					.println("On a demandé de créer la matrice identité d'ordre 0 --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
		}
		return testOK;
	}

	private static boolean testGetElement() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		Matrice m = new Matrice(tab);
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 4; j++) {
				if (m.getElement(i, j) != j + 4 * (i - 1)) {
					System.out.println("On a demandé l'élément d'indice " + i
							+ ", " + j + " de la matrice :");
					System.out.println(toStringTableau(tab));
					System.out
							.println("La méthode getElement devait renvoyer : "
									+ (j + 4.0 * (i - 1)));
					System.out.println("Votre méthode a renvoyé : "
							+ m.getElement(i, j));
					testOK = false;
					break;
				}
			}
			if (!testOK)
				break;
		}
		try {
			m.getElement(-1, 2);
			testOK = false;
			System.out
					.println("On a appelé getElement avec comme ligne -1 --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		try {
			m.getElement(0, 2);
			testOK = false;
			System.out
					.println("On a appelé getElement avec comme ligne 0 --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		try {
			m.getElement(4, 2);
			testOK = false;
			System.out
					.println("On a appelé getElement avec comme ligne 4 pour un matrice de taille (3,4) --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		try {
			m.getElement(2, -1);
			testOK = false;
			System.out
					.println("On a appelé getElement avec comme colonne -1 --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		try {
			m.getElement(2, 0);
			testOK = false;
			System.out
					.println("On a appelé getElement avec comme colonne 0 --> Il fallait une exception");
		} catch (IllegalArgumentException e) {
		}
		try {
			m.getElement(2, 5);
			testOK = false;
			System.out
					.println("On a appelé getElement avec comme colonne 4 pour un matrice de taille (3,4) --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		return testOK;
	}

	private static boolean testSomme() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab1 = { { 4, 6, 2, 5 }, { 7, 1, 9.5, 3 }, { -5, 3, 7, 4 } };
		Matrice m = new Matrice(tab1);
		double[][] tab2 = { { 4, 5, 7, 8 }, { 3, 1, -3, 2 }, { 2, 7, 4, -2 } };
		Matrice mS = new Matrice(tab2);
		Matrice somme = m.somme(mS);
		if (somme == m) {
			System.out
					.println("Votre méthode a renvoyé la matrice courante ! Elle doit renvoyer une nouvelle matrice.");
			return false;
		}
		if (somme == mS) {
			System.out
					.println("Votre méthode a renvoyé la matrice passée en paramètre ! Elle doit renvoyer une nouvelle matrice.");
			return false;
		}
		double[][] dataSomme = (double[][]) data.get(somme);
		if (dataSomme.length != 3) {
			System.out
					.println("Le tableau "
							+ data.getName()
							+ " de la matrice renvoyée par la méthode somme n'a pas le bon nombre de lignes.");
			testOK = false;
		} else if (dataSomme[0].length != 4) {
			testOK = false;
			System.out
					.println("Le tableau à l'indice 0 du tableau "
							+ data.getName()
							+ " de la matrice renvoyée par la méthode somme n'a pas le bon nombre d'éléments.");
		} else {
			double[][] tabSomme = { { 8, 11, 9, 13 }, { 10, 2, 6.5, 5 },
					{ -3, 10, 11, 2 } };
			if (!testEgaliteTableau(tabSomme, dataSomme)) {

				testOK = false;
				System.out
						.println("La matrice renvoyée par votre méthode somme n'est pas correcte");
				System.out.println("matrice qu'il fallait obtenir :");
				System.out.println(toStringTableau(tabSomme));
				System.out
						.println("matrice renvoyée par votre méthode somme : ");
				System.out.println(toStringTableau(dataSomme));
			}
		}
		if (testOK) {
			double[][] dataM = (double[][]) data.get(m);
			if (!testEgaliteTableau(tab1, dataM)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode somme.");
			}
		}
		if (testOK) {
			double[][] dataMS = (double[][]) data.get(mS);
			if (!testEgaliteTableau(dataMS, tab2)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice passée en paramètre à la méthode somme.");
			}
		}
		try {
			m.somme(null);
			System.out
					.println(" On a appelé la méthode somme avec un paramètre null --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		double[][] tab3 = { { 4, 5 }, { 3, 1 }, { 2, 7 } };
		mS = new Matrice(tab3);
		try {
			m.somme(mS);
			System.out
					.println(" On a fait la somme d'une matrice (3,4) avec une matrice (3,2) --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		double[][] tab4 = { { 4, 5, 7, 8 }, { 3, 1, 3, 2 }, { 2, 7, 4, 2 },
				{ 6, 3, 5, 4 } };
		mS = new Matrice(tab4);
		try {
			m.somme(mS);
			System.out
					.println(" On a fait la somme d'une matrice (3,4) avec une matrice (4,4) --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		return testOK;
	}

	private static boolean testProduitParSaclaire()
			throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab1 = { { 4, 6, 2, 5 }, { 7, 1, 9.5, 3 }, { -5, 3, 7, 4 } };
		Matrice m = new Matrice(tab1);
		Matrice produit = m.produitParScalaire(3);
		if (produit == m) {
			System.out
					.println("Votre méthode a renvoyé la matrice courante ! Elle doit renvoyer une nouvelle matrice.");
			return false;
		}
		double[][] dataProduit = (double[][]) data.get(produit);
		if (testOK) {
			double[][] tabProduit = { { 12, 18, 6, 15 }, { 21, 3, 28.5, 9 },
					{ -15, 9, 21, 12 } };
			if (dataProduit.length != 3) {
				System.out
						.println("Le tableau "
								+ data.getName()
								+ " de la matrice renvoyée par la méthode produitParSaclaire n'a pas le bon nombre de lignes.");
				testOK = false;
			} else if (dataProduit[0].length != 4) {
				testOK = false;
				System.out
						.println("Le tableau à l'indice 0 du tableau "
								+ data.getName()
								+ " de la matrice renvoyée par la méthode produitParSaclaire n'a pas le bon nombre d'éléments.");
			}
			if (!testEgaliteTableau(tabProduit, dataProduit)) {
				testOK = false;
				System.out
						.println("La matrice renvoyée par votre méthode produitParSaclaire n'est pas correcte");
				System.out.println("matrice qu'il fallait obtenir :");
				System.out.println(toStringTableau(tabProduit));
				System.out
						.println("matrice renvoyée par votre méthode produitParSclaire : ");
				System.out.println(toStringTableau(dataProduit));
			}
		}
		if (testOK) {
			double[][] dataM = (double[][]) data.get(m);
			if (!testEgaliteTableau(tab1, dataM)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode produitParSclaire.");
			}
		}
		
		if (testOK){
			produit = m.produitParScalaire(-3);
			double[][] tabProduit = { { -12, -18, -6, -15 }, { -21, -3, -28.5, -9 },
					{ 15, -9, -21, -12 } };
			dataProduit = (double[][]) data.get(produit);
			if (dataProduit.length != 3) {
				System.out
						.println("Le tableau "
								+ data.getName()
								+ " de la matrice renvoyée par la méthode produitParSclaire n'a pas le bon nombre de lignes.");
				testOK = false;
			} else if (dataProduit[0].length != 4) {
				testOK = false;
				System.out
						.println("Le tableau à l'indice 0 du tableau "
								+ data.getName()
								+ " de la matrice renvoyée par la méthode produitParSclaire n'a pas le bon nombre d'éléments.");
			}
			if (!testEgaliteTableau(tabProduit, dataProduit)) {
				testOK = false;
				System.out
						.println("La matrice renvoyée par votre méthode produitParSclaire n'est pas correcte");
				System.out.println("matrice qu'il fallait obtenir :");
				System.out.println(toStringTableau(tabProduit));
				System.out
						.println("matrice renvoyée par votre méthode produitParSclaire : ");
				System.out.println(toStringTableau(dataProduit));
			}
		}
		if (testOK){
			produit = m.produitParScalaire(0);
			double[][] tabProduit = { { 0, 0, 0, 0 }, { 0, 0,0, 0 },{ -0, 0, 0, 0} };
			dataProduit = (double[][]) data.get(produit);
			if (dataProduit.length != 3) {
				System.out
						.println("Le tableau "
								+ data.getName()
								+ " de la matrice renvoyée par la méthode produitParSclaire n'a pas le bon nombre de lignes.");
				testOK = false;
			} else if (dataProduit[0].length != 4) {
				testOK = false;
				System.out
						.println("Le tableau à l'indice 0 du tableau "
								+ data.getName()
								+ " de la matrice renvoyée par la méthode produitParSclaire n'a pas le bon nombre d'éléments.");
			}
			if (!testEgaliteTableau(tabProduit, dataProduit)) {
				testOK = false;
				System.out
						.println("La matrice renvoyée par votre méthode produitParSclaire n'est pas correcte");
				System.out.println("matrice qu'il fallait obtenir :");
				System.out.println(toStringTableau(tabProduit));
				System.out
						.println("matrice renvoyée par votre méthode produitParSclaire : ");
				System.out.println(toStringTableau(dataProduit));
			}
		}
		return testOK;
	}

	private static boolean testProduitAGauche() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab1 = {{3,4,2,4},{1,3,4,5}};
		Matrice m1 = new Matrice(tab1);
		double[][] tab2 = {{-5,2,6},{3,5.5,4},{1,-2,3},{4,2,3}};
		Matrice m2 = new Matrice(tab2);
		Matrice produitAGauche = m1.produitAGauche(m2);
		int nbLP = nbLigne.getInt(produitAGauche);
		int nbCP = nbColonne.getInt(produitAGauche);
		if (nbLP != 2||nbCP!=3){
			testOK = false;
			System.out.println("On a multiplié une matrice (2,4) avec une matrice (4,3).");
			System.out.println("Le résultat devrait être de taille (2,3)");
			System.out.println("Votre méthode a renvoyé une matrice de taille (" + nbLP +"," + nbCP +").");
		}
		double[][] tabProduit ={{15,32,52},{28,20.5,45}};
		if (testOK){
			double[][] dataProduit = (double[][]) data.get(produitAGauche);
			if (!testEgaliteTableau(tabProduit, dataProduit)){
				testOK = false;
				System.out.println("On a fait le produit de la matrice : ");
				System.out.println(toStringTableau(tab1));
				System.out.println("avec la matrice ");
				System.out.println(toStringTableau(tab2));
				System.out.println("Résultat attendu : ");
				System.out.println(toStringTableau(tabProduit));
				System.out.println("Résultat obtenu : ");
				System.out.println(toStringTableau(dataProduit));
			}
		}		
		if (testOK) {
			double[][] dataM1 = (double[][]) data.get(m1);
			if (!testEgaliteTableau(tab1, dataM1)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode produitAGauche.");
			}
		}
		if (testOK) {
			double[][] dataM2 = (double[][]) data.get(m2);
			if (!testEgaliteTableau(dataM2, tab2)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice passée en paramètre à la méthode produitAGauche.");
			}
		}
		try {
			m1.produitAGauche(null);
			System.out
					.println(" On a appelé la méthode produitAGauche avec un paramètre null --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		double[][] tab3 = {{-5,2,6},{3,5,4},{1,-2,3}};
		Matrice m3 = new Matrice(tab3);
		try {
			m1.produitAGauche(m3);
			testOK = false;
			System.out.println("On a fait le produit d'une matrice (2,4) avec une matrice (3,3) --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e){			
		}
		double[][] tab4 = {{-5,2,6},{3,5,4},{1,-2,3},{4,2,3},{2,1,4}};
		Matrice m4 = new Matrice(tab4);
		try {
			m1.produitAGauche(m4);
			testOK = false;
			System.out.println("On a fait le produit d'une matrice (2,4) avec une matrice (5,3) --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e){			
		}
		return testOK ;
	}

	private static boolean testProduitADroite() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab1 = {{-5,2,6},{3,5.5,4},{1,-2,3},{4,2,3}};
		Matrice m1 = new Matrice(tab1);
		double[][] tab2 = {{3,4,2,4},{1,3,4,5}};
		Matrice m2 = new Matrice(tab2);
		Matrice produitAGauche = m1.produitADroite(m2);
		int nbLP = nbLigne.getInt(produitAGauche);
		int nbCP = nbColonne.getInt(produitAGauche);
		if (nbLP != 2||nbCP !=3){
			testOK = false;
			System.out.println("On a multiplié une matrice (2,4) avec une matrice (4,3).");
			System.out.println("Le résultat devrait être de taille (2,3)");
			System.out.println("Votre méthode a renvoyé une matrice de taille (" + nbLP +"," + nbCP +").");
		}
		double[][] tabProduit ={{15,32,52},{28,20.5,45}};
		if (testOK){
			double[][] dataProduit = (double[][]) data.get(produitAGauche);
			if (!testEgaliteTableau(tabProduit, dataProduit)){
				testOK = false;
				System.out.println("On a fait le produit de la matrice : ");
				System.out.println(toStringTableau(tab1));
				System.out.println("avec la matrice ");
				System.out.println(toStringTableau(tab2));
				System.out.println("Résultat attendu : ");
				System.out.println(toStringTableau(tabProduit));
				System.out.println("Résultat obtenu : ");
				System.out.println(toStringTableau(dataProduit));
			}
		}		
		if (testOK) {
			double[][] dataM1 = (double[][]) data.get(m1);
			if (!testEgaliteTableau(tab1, dataM1)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode produitADroite.");
			}
		}
		if (testOK) {
			double[][] dataM2 = (double[][]) data.get(m2);
			if (!testEgaliteTableau(dataM2, tab2)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice passée en paramètre à la méthode produitADroite.");
			}
		}
		try {
			m1.produitADroite(null);
			System.out
					.println(" On a appelé la méthode produitADroite avec un paramètre null --> Il fallait lancer une IllegalArgumentException");
			testOK = false;
		} catch (IllegalArgumentException e) {
		}
		double[][] tab3 = {{-5,2,6,4,2},{3,5,4,1,2},{1,-2,3,1,2}};
		Matrice m3 = new Matrice(tab3);
		try {
			m1.produitADroite(m3);
			testOK = false;
			System.out.println("On a fait le produit d'une matrice (3,5) avec une matrice (4,3) --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e){			
		}
		double[][] tab4 = {{-5,2,6},{3,5,4}};
		Matrice m4 = new Matrice(tab4);
		try {
			m1.produitADroite(m4);
			testOK = false;
			System.out.println("On a fait le produit d'une matrice (2,3) avec une matrice (4,3) --> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e){			
		}
		return testOK;
	}

	private static boolean testCarree() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		boolean testOK = true;
		double[][] tab1 = {{5,4,6},{7,5,2},{3,6,5}};
		Matrice m = new Matrice(tab1);
		if (!m.carree()){
			testOK = false;
			System.out.println("On a créé une matrice carrée d'ordre 3.");
			System.out.println("Votre méthode indique qu'elle n'est pas carrée.");
		}
		double[][] tab2 = {{5,4,6},{7,5,2}};
		m = new Matrice(tab2);
		if (m.carree()){
			testOK = false;
			System.out.println("On a créé une matrice carrée de taille (2,3).");
			System.out.println("Votre méthode indique qu'elle est carrée.");
		}
		return testOK;
	}

	private static boolean testPuissance() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out
					.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		if (!testIdentite()){
			System.out.println("Il faut d'abord écrire la méthode identite.");
			return false;
		}
		boolean testOK = true;
		double[][] tab = {{3,5,1},{2,1,3},{3,1,2}};
		Matrice m = new Matrice(tab);
		double[][] tabPuissance = {{1,0,0},{0,1,0},{0,0,1}};
		for (int p = 0; p < 15;p++){
			Matrice puissance = m.puissance(p);
			if (puissance == m){
				testOK = false;
				System.out.println("Votre méthode puissance a renvoyé la matrice courante.");
				break;
			}
			int nbLP = nbLigne.getInt(puissance);
			int nbCP = nbColonne.getInt(puissance);
			if (nbLP != 3||nbCP!=3){
				testOK = false;
				System.out.println("On a calculé une puissance d'une matrice carrée d'ordre 3.");
				System.out.println("Le résultat devrait être de taille (3,3)");
				System.out.println("Votre méthode a renvoyé une matrice de taille (" + nbLP +"," + nbCP +").");
				break;
			}
			double[][] dataPuissance = (double[][]) data.get(puissance);
			if (!testEgaliteTableau(tabPuissance, dataPuissance)){
				testOK = false;
				System.out.println("On a calculé la puissance "  + p + " ème de la matrice : ");
				System.out.println(toStringTableau(tab));
				System.out.println("Résultat attendu : ");
				System.out.println(toStringTableau(tabPuissance));
				System.out.println("Résultat obtenu : ");
				System.out.println(toStringTableau(dataPuissance));
				break;
			}	
			double[][] nvPuissance = new double[3][3];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					double somme = 0;
					for (int k = 0; k < 3 ; k++){
						somme = somme + tabPuissance[i][k] * tab[k][j]; 
					}
					nvPuissance[i][j] = somme;
				}
			}
			tabPuissance = nvPuissance;
		}
		if (testOK) {
			double[][] dataM = (double[][]) data.get(m);
			if (!testEgaliteTableau(tab, dataM)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode puissance.");
			}
		}
		try {
			m.puissance(-1);
			testOK = false;
			System.out.println("On a demandé la -1 ème puissance d'une matrice --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
		}
		double[][] tab2 = {{1,2,3},{4,5,6}};
		m = new Matrice(tab2);
		try {
			m.puissance(3);
			testOK = false;
			System.out.println("On a demandé la puissance d'une matrice qui n'est pas carrée --> IL fallait lancer une MathException.");
		} catch(MathException e){
		}
		return testOK;
	}
	
	private static boolean testTransposee() throws IllegalAccessException {
		if (!testConstructeur2()) {
			System.out.println("Il faut d'abord écrire le constructeur Matrice(double[][] tab) de manière correcte.");
			return false;
		}
		System.out.println("Test 1 : transposée d'une matrice carrée") ;
		boolean testOK = true;
		double[][] tab = {{3,5,1},{2,1,3},{3,1,2}};
		Matrice m = new Matrice(tab);
		double[][] tabTransposee = {{3,2,3},{5,1,1},{1,3,2}};
		Matrice transposee = m.transposee();
		if (transposee == m){
			testOK = false;
			System.out.println("Votre méthode puissance a renvoyé la matrice courante.");
		}
		int nbLP = nbLigne.getInt(transposee);
		int nbCP = nbColonne.getInt(transposee);
		if (nbLP != 3||nbCP!=3){
			testOK = false;
			System.out.println("On a calculé la transposee d'une matrice carrée d'ordre 3.");
			System.out.println("Le résultat devrait être de taille (3,3)");
			System.out.println("Votre méthode a renvoyé une matrice de taille (" + nbLP +"," + nbCP +").");
		}
		double[][] dataTransposee = (double[][]) data.get(transposee);
		if (!testEgaliteTableau(tabTransposee, dataTransposee)){
			testOK = false;
			System.out.println("On a calculé la transposee  de la matrice");
			System.out.println(toStringTableau(tab));
			System.out.println("Résultat attendu : ");
			System.out.println(toStringTableau(tabTransposee));
			System.out.println("Résultat obtenu : ");
			System.out.println(toStringTableau(dataTransposee));
		}	
		if (testOK) {
			double[][] dataM = (double[][]) data.get(m);
			if (!testEgaliteTableau(tab, dataM)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode puissance.");
			}
		}
		
		System.out.println("Test 2 : transposée d'une matrice rectangulaire") ;
		
		double[][] tab2 = {{-3,5,0,2},{7,1,-4,3},{-3,0,1,-2}};
		Matrice m2 = new Matrice(tab2);
		double[][] tabTransposee2 = {{-3,7,-3},{5,1,0},{0,-4,1},{2,3,-2}};
		Matrice transposee2 = m2.transposee();
		if (transposee2 == m2){
			testOK = false;
			System.out.println("Votre méthode puissance a renvoyé la matrice courante.");
		}
		nbLP = nbLigne.getInt(transposee2);
		nbCP = nbColonne.getInt(transposee2);
		if (nbLP != 4||nbCP!=3){
			testOK = false;
			System.out.println("On a calculé la transposee d'une matrice carr�e 3x4.");
			System.out.println("Le résultat devrait être de taille (4,3)");
			System.out.println("Votre méthode a renvoyé une matrice de taille (" + nbLP +"," + nbCP +").") ;
		}
		double[][] dataTransposee2 = (double[][]) data.get(transposee2);
		if (!testEgaliteTableau(tabTransposee, dataTransposee)){
			testOK = false;
			System.out.println("On a calculé la transposee  de la matrice");
			System.out.println(toStringTableau(tab2));
			System.out.println("Résultat attendu : ");
			System.out.println(toStringTableau(tabTransposee2));
			System.out.println("Résultat obtenu : ");
			System.out.println(toStringTableau(dataTransposee2));
		}	
		if (testOK) {
			double[][] dataM2 = (double[][]) data.get(m2);
			if (!testEgaliteTableau(tab2, dataM2)) {
				testOK = false;
				System.out
						.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode puissance.");
			}
		}
		
		return testOK;
	}

	private static void visualiserToString() throws IllegalAccessException {
		if (!testConstructeur2()){
			System.out.println("Il faut d'abord �crire le constructeur Matrice(double[][] tab) de mani�re correcte.");
			return;
		}
		double[][] tab1 = {{-5,2,6,4,2},{3,5,4,1,2},{1,-2,3,1,2}};
		Matrice m1 = new Matrice(tab1);
		System.out.println("Matrice é afficher : ");
		System.out.println(toStringTableau(tab1));
		System.out.println("Chaîne de caractères renvoyée par votre toString : ");
		System.out.println(m1.toString());
		double[][] tab2 = {{-5,2,60000,4.235464879,2},{3,5,4.456486,1,2},{1,-2.15646,3,1,2}};
		Matrice m2 = new Matrice(tab2);
		System.out.println("Matrice à afficher : ");
		System.out.println(toStringTableau(tab2));
		System.out.println("Chaîne de caractères renvoyée par votre toString : ");
		System.out.println(m2.toString());
	}
	
	private static boolean testEgaliteTableau(double[][] tab1, double[][] tab2) {
		for (int i = 0; i < tab1.length; i++) {
			for (int j = 0; j < tab1[0].length; j++) {
				if (tab1[i][j]  != tab2[i][j]) return false;
			}
		}
		return true;
	}
	
	private static String toStringTableau(double[][] tab) {
		int lg = 2+Stream.of(tab).mapToInt( (t) -> Arrays.stream(t).mapToInt((d)->Double.toString(d).length()).max().getAsInt()).max().getAsInt();
		StringBuilder st = new StringBuilder("");
		Stream.of(tab).forEach((t)-> {
			Arrays.stream(t).forEach((d)-> st.append(String.format("%-"+lg+"s", (d<0)?d:" "+d)));
			st.append('\n');
		});
		return st.substring(0, st.length() - 1);
	}

}
