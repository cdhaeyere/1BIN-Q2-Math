import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

public class TestPageRank {
	private static Class classe = Matrice.class;
	private static Field data;
	private static Field nbLigne;
	private static Field nbColonne;

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field[] champs = classe.getDeclaredFields();
		for (Field f : champs) {
			if (f.getType() == double[][].class) {
				data = f;
				data.setAccessible(true);
			} else if (f.getType() == int.class) {
				if (f.getName().equals("nbLignes")) {
					nbLigne = f;
					nbLigne.setAccessible(true);
				} else if (f.getName().equals("nbColonnes")) {
					nbColonne = f;
					nbColonne.setAccessible(true);
				}
			}
		}
		if (data == null) {
			System.out.println("Vous n'avez pas créé de champ de type double[][]");
			return;
		}
		if (nbLigne == null) {
			System.out.println("Vous avez enlevé le champ pour le nombre de lignes.");
			return;
		}
		if (nbColonne == null) {
			System.out.println("Vous avez enlevé le champ pour le nombre de colonnes.");
			return;
		}
		if (testPageRank()) System.out.println("Le test de la méthode pageRank a réussi.");
		else System.out.println("Le test de la méthode pageRank a échoué");
	}

	private static boolean testPageRank() throws IllegalArgumentException, IllegalAccessException {
		boolean testOK = true;
		double[][] tab1 = {
				{ 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0 },
				{ 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0 },
				{ 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0 },
				{ 1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0 },
				{ 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0 },
				{ 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }
		};
		double[][] resultatAttendu = { { 0.1611222710898724 },
				{ 0.16042129940607897 }, { 0.2090489902957677 },
				{ 0.18789386017166904 }, { 0.18541984246416277 },
				{ 0.07466516514387765 }, { 0.021428571428571425 } };
		Matrice mat1 = new Matrice(tab1);
		Matrice proba = mat1.pageRank();
		if (proba == null) {
			System.out.println("La matrice renvoyée est null");
			return false;
		}
		double[][] dataProba = (double[][]) data.get(proba);
		int nbL = nbLigne.getInt(proba);
		int nbCol = nbColonne.getInt(proba);
		if (nbL != tab1.length || nbCol != 1) {
			System.out.println("La matrice renvoyée doit être de genre ("
					+ tab1.length + ",1)");
			System.out.println("Votre méthode a renvoyé une matrice de genre ("
					+ nbL + "," + nbCol + ")");
			testOK = false;
		} else {
			for (int i = 0; i < tab1.length; i++) {
				if (Double.isNaN(dataProba[i][0])) {
					testOK = false;
					System.out.println("Résultat attendu : ");
					System.out.println(toStringTableau(resultatAttendu));
					System.out.println("Résultat trouvé : ");
					System.out.println(toStringTableau(dataProba));
					break;
				} 
			}
			if (testOK) {
				if (!testEgaliteAvecTolerance(resultatAttendu, dataProba, 1e-16)) {
					testOK = false;
					System.out.println("Résultat attendu : ");
					System.out.println(toStringTableau(resultatAttendu));
					System.out.println("Résultat trouvé : ");
					System.out.println(toStringTableau(dataProba));
				}
			}
		}
		if (testOK) {
			double[][] dataMat1 = (double[][]) data.get(mat1);
			if (!testEgaliteTableau(tab1, dataMat1)) {
				testOK = false;
				System.out.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode pageRank.");
			}
		}
		if (testOK) {
			double[][] tab2 = { { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0 },
					{ 0.0, 0.0, 1.0, 1.0, 1.0, 1.0 },
					{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
					{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
					{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
					{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } };

			double[][] resultatAttendu2 = { { 0.19680851063829782 },
					{ 0.37765957446808507 }, { 0.10638297872340427 },
					{ 0.10638297872340427 }, { 0.10638297872340427 },
					{ 0.10638297872340427 } };
			Matrice mat2 = new Matrice(tab2);
			proba = mat2.pageRank();
			if (proba == null) {
				System.out.println("La matrice renvoyée est null");
				return false;
			}
			dataProba = (double[][]) data.get(proba);
			nbL = nbLigne.getInt(proba);
			nbCol = nbColonne.getInt(proba);
			if (nbL != tab2.length || nbCol != 1) {
				System.out.println("La matrice renvoyée doit être de genre (" + tab2.length + ",1)");
				System.out.println("Votre méthode a renvoyé une matrice de genre (" + nbL + "," + nbCol + ")");
				testOK = false;
			} else {
				for (int i = 0; i < tab2.length; i++) {
					if (Double.isNaN(dataProba[i][0])) {
						testOK = false;
						System.out.println("Résultat attendu : ");
						System.out.println(toStringTableau(resultatAttendu2));
						System.out.println("Résultat trouvé : ");
						System.out.println(toStringTableau(dataProba));
						break;
					}
				}
				if (testOK) {
					if (!testEgaliteAvecTolerance(resultatAttendu2, dataProba,
							1e-16)) {
						testOK = false;
						System.out.println("Le résultat renvoyé si le réseau présente des trous noirs n'est pas correct ! ");
						System.out.println("Résultat attendu : ");
						System.out.println(toStringTableau(resultatAttendu2));
						System.out.println("Résultat trouvé : ");
						System.out.println(toStringTableau(dataProba));
					}
				}
			}
			if (testOK) {
				double[][] dataMat2 = (double[][]) data.get(mat2);
				if (!testEgaliteTableau(tab2, dataMat2)) {
					testOK = false;
					System.out.println("Vous avez modifié les données de la matrice sur laquelle on a appelée la méthode pageRank.");
				}
			}
		}
		
		double[][] tab3 = { { 0, 0, 1, 0 }, { 1, 0, 1, 0 }, { 1, 1, 0, 1 },
				{ 0, 0, 0, 0 }, { 1, 0, 0, 0 } };
		Matrice mat3 = new Matrice(tab3);
		try {
			mat3.pageRank();
			testOK = false;
			System.out.println("On a appelé la méthode pageRank sur une matrice qui n'est pas carrée --> Il fallait lancer une MathException");
		} catch (MathException ignored) {}
		double[][] tab4 = { { 0, 0, 0, 0.3 }, { 1, 0, 1, 0 }, { 1, 1, 0, 1 },
				{ 0, 0, 0.5, 0 } };
		Matrice mat4 = new Matrice(tab4);
		try {
			mat4.pageRank();
			testOK = false;
			System.out.println("On a appelé la méthode pageRank sur une matrice qui n'est pas uniquement constituée de 0 et de 1  --> Il fallait lancer une MathException");
		} catch (MathException ignored) {}
		return testOK;
	}

	private static boolean testEgaliteTableau(double[][] tab1, double[][] tab2) {
		for (int i = 0; i < tab1.length; i++) {
			if (!Arrays.equals(tab1[i], tab2[i]))
				return false;
		}
		return true;
	}

	private static boolean testEgaliteAvecTolerance(double[][] tab1,
			double[][] tab2, double tol) {
		for (int i = 0; i < tab1.length; i++) {
			for (int j = 0; j < tab1[i].length; j++) {
				if (Math.abs(tab1[i][j] - tab2[i][j]) > tol)
					return false;
			}
		}
		return true;
	}

	private static String toStringTableau(double[][] tab) {
		int lg = 2 + Stream.of(tab).mapToInt((t) -> Arrays.stream(t).mapToInt((d) -> Double.toString(d).length()).max().getAsInt()).max().getAsInt();
		StringBuilder st = new StringBuilder("");
		Stream.of(tab).forEach((t) -> {Arrays.stream(t).forEach((d) -> st.append(String.format("%-" + lg + "s", (d < 0) ? d : " " + d)));st.append('\n');});
		return st.substring(0, st.length() - 1);
	}
}
