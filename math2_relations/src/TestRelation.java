import java.util.Scanner;

public class TestRelation {
	private final static Scanner scanner = new Scanner(System.in);
	private static final String[] NOMS_METHODE = {"domaine","image",
			"complementaire", "reciproque", "ajouter", "enlever",
			"intersecter", "apres", "inclusDans", "equals", "cloReflex",
			"cloSym", "cloTrans", "reflexive", "antireflexive", "symetrique",
			"antisymetrique", "transitive","Relation(RelationInterface)", "identite", "produitCartesien" };

	public static void main(String[] args) {
		System.out.println("**************************************");
		System.out.println("Programme Test pour la classe Relation");
		System.out.println("**************************************");
		int choix = 0;
		while (true) {
			for (int i = 0; i < NOMS_METHODE.length; i++) {
				System.out.println((i + 1) + " -> Tester la méthode '"
						+ NOMS_METHODE[i] + "'");
			}

			choix = scanner.nextInt();
			boolean testOK;
			switch (choix) {
			case 1 :
				testOK = testDomaine();
				break;
			case 2 :
				testOK = testImage();
				break;
			case 3:
				testOK = testComplementaire();
				break;
			case 4:
				testOK = testReciproque();
				break;
			case 5:
				testOK = testAjouter();
				break;
			case 6:
				testOK = testEnlever();
				break;
			case 7:
				testOK = testIntersecter();
				break;
			case 8:
				testOK = testApres();
				break;
			case 9:
				testOK = testInclusDans();
				break;
			case 10:
				testOK = testEquals();
				break;
			case 11:
				testOK = testCloReflex();
				break;
			case 12:
				testOK = testCloSym();
				break;
			case 13:
				testOK = testCloTrans();
				break;
			case 14:
				testOK = testReflexive();
				break;
			case 15:
				testOK = testAntireflexive();
				break;
			case 16:
				testOK = testSymetrique();
				break;
			case 17:
				testOK = testAntisymetrique();
				break;
			case 18:
				testOK = testTransitive();
				break;
			case 19:
				testOK = testConstructeur();
				break;
			case 20:
				testOK = testIdentite();
				break;
			case 21:
				testOK = testProduitCartesien();
				break;
			default:
				return;
			}
			if (testOK)
				System.out.println("Le test de la méthode "
						+ NOMS_METHODE[choix - 1] + " a réussi.");
			else
				System.out.println("Le test de la méthode "
						+ NOMS_METHODE[choix - 1] + " a échoué.");
		}
	}
	
	
	private static boolean testDomaine() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		EnsembleAbstrait domaine = re1.domaine();
		Ensemble result = new Ensemble("{1,2,3,6,7}");	
		if (!domaine.equals(result)){
			System.out.println("Le domaine de la relation re1 vaut " + result);
			System.out.println("Votre méthode a renvoyé : " + domaine);
			testOK = false;
		}
		return testOK;
	}

	private static boolean testImage() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		EnsembleAbstrait image = re1.image();
		Ensemble result = new Ensemble("{2,5,6,8,9,10,20}");	
		if (!image.equals(result)){
			System.out.println("L'image de la relation re1 vaut " + result);
			System.out.println("Votre méthode a renvoyé : " + image);
			testOK = false;
		}
		return testOK;
	}

	private static boolean testComplementaire() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation copie = re1.clone();
		Relation complR2 = re1.complementaire();
		if (!copie.depart().equals(complR2.depart())) {
			System.out
					.println("L'ensemble de départ de la relation complémentaire n'est pas correct.");
			testOK = false;
		}
		if (!copie.depart().equals(re1.depart())) {
			System.out
					.println("Votre méthode complémentaire a modifié l'ensemble de départ de la relation courante");
		}
		if (!copie.arrivee().equals(complR2.arrivee())) {
			System.out
					.println("L'ensemble d'arrivée de la relation complémentaire n'est pas correct.");
			testOK = false;
		}
		if (!copie.arrivee().equals(re1.arrivee())) {
			System.out
					.println("Votre méthode complémentaire a modifié l'ensemble d'arrivée de la relation courante.");
			testOK = false;
		}
		for (Elt eD : copie.depart()) {
			for (Elt eA : copie.arrivee()) {
				if (copie.contient(eD, eA) == complR2.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("Les couples présent dans la relation complémentaire ne sont pas les bons.");
					break;
				}
				if (copie.contient(eD, eA) != re1.contient(eD, eA)) {
					System.out
							.println("Votre méthode complémentaire a modifié la relation courante.");
					testOK = false;
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

	private static boolean testReciproque() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation copie = re1.clone();
		Relation complR2 = re1.reciproque();
		if (!copie.arrivee().equals(complR2.depart())) {
			System.out
					.println("L'ensemble de départ de la relation réciproque n'est pas correct.");
			testOK = false;
		}
		if (!copie.depart().equals(re1.depart())) {
			System.out
					.println("Votre méthode réciproque a modifié l'ensemble de départ de la relation courante.");
		}
		if (!copie.depart().equals(complR2.arrivee())) {
			System.out
					.println("L'ensemble d'arrivée de la relation réciproque n'est pas correct.");
			testOK = false;
		}
		if (!copie.arrivee().equals(re1.arrivee())) {
			System.out
					.println("Votre méthode réciproque a modifié l'ensemble d'arrivée de la relation courante.");
			testOK = false;
		}
		for (Elt eD : copie.depart()) {
			for (Elt eA : copie.arrivee()) {
				if (copie.contient(eD, eA) != complR2.contient(eA, eD)) {
					testOK = false;
					System.out
							.println("Les couples présent dans la relation réciproque ne sont pas les bons.");
					break;
				}
				if (copie.contient(eD, eA) != re1.contient(eD, eA)) {
					System.out
							.println("Votre méthode réciproque a modifié la relation courante.");
					testOK = false;
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

	private static boolean testAjouter() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation r = null;
		try {
			re1.ajouter(r);
			testOK = false;
			System.out.println("La relation passée en paramétre est null.");
			System.out.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		r = new Relation(new Ensemble("{1..7}"), new Ensemble("{2,5..10}"));
		r.ajouter(new Couple(new Elt(3), new Elt(5)));
		try {
			re1.ajouter(r);
			testOK = false;
			System.out.println("Les relations n'avaient pas le même ensemble d'arrivée.");
			System.out.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		r = new Relation(new Ensemble("{1..6}"), new Ensemble("{2,5..10,20}"));
		r.ajouter(new Couple(new Elt(3), new Elt(5)));
		try {
			re1.ajouter(r);
			testOK = false;
			System.out.println("Les relations n'avaient pas le même ensemble de départ.");
			System.out.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		re1 = Io.chargerRelation("re1.rel");
		Relation copieRe1 = re1.clone();
		Relation rVide = new Relation(re1.depart(), re1.arrivee());
		re1.ajouter(rVide);
		if (!re1.depart().equals(copieRe1.depart()) || !re1.arrivee().equals(copieRe1.arrivee())) {
			System.out.println("L'ensemble de départ ou d'arrivée de la relation a été modifiée.");
			testOK = false;
		}
		for (Elt eD : re1.depart()) {
			for (Elt eA : re1.arrivee()) {
				if (copieRe1.contient(eD, eA)) {
					if (!re1.contient(eD, eA)) {
						testOK = false;
						System.out.println("La relation re1 ne contient pas les bons couples après l'ajout.");
						break;
					}
				} else if (re1.contient(eD, eA)) {
					testOK = false;
					System.out.println("La relation re1 ne contient pas les bons couples après l'ajout.");
					break;
				}
				if (rVide.contient(eD, eA)) {
					testOK = false;
					System.out.println("La relation passée en paramètre à la méthode ajouter a été mofifiée.");
					break;
				}
			}
			if (!testOK)
				break;
		}
		if (testOK) {
			re1 = Io.chargerRelation("re1.rel");
			Relation re3 = Io.chargerRelation("re3.rel");
			copieRe1 = re1.clone();
			Relation copieRe3 = re3.clone();
			re1.ajouter(re3);
			if (!re1.depart().equals(copieRe1.depart()) || !re1.arrivee().equals(copieRe1.arrivee())) {
				System.out.println("L'ensemble de départ ou d'arrivée de la relation a été modifiée.");
				testOK = false;
			}
			for (Elt eD : re1.depart()) {
				for (Elt eA : re1.arrivee()) {
					if (copieRe1.contient(eD, eA) || copieRe3.contient(eD, eA)) {
						if (!re1.contient(eD, eA)) {
							testOK = false;
							System.out.println("La relation re1 ne contient pas les bons couples après l'ajout.");
							break;
						}
					} else if (re1.contient(eD, eA)) {
						testOK = false;
						System.out.println("La relation re1 ne contient pas les bons couples après l'ajout.");
						break;
					}
					if (re3.contient(eD, eA) != copieRe3.contient(eD, eA)) {
						testOK = false;
						System.out.println("La relation passée en paramètre à la méthode ajouter a été mofifiée.");
						break;
					}
				}
				if (!testOK)
					break;
			}
		}
		return testOK;
	}

	private static boolean testEnlever() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation r = null;
		try {
			re1.enlever(r);
			testOK = false;
			System.out.println("La relation passée en paramètre est null.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		r = new Relation(new Ensemble("{1..7}"), new Ensemble("{2,5..10}"));
		try {
			re1.enlever(r);
			testOK = false;
			System.out
					.println("Les relations n'avaient pas le même ensemble d'arrivée.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		r = new Relation(new Ensemble("{1..6}"), new Ensemble("{2,5..10,20}"));
		try {
			re1.enlever(r);
			testOK = false;
			System.out
					.println("Les relations n'avaient pas le même ensemble de départ.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		Relation re3 = Io.chargerRelation("re3.rel");
		Relation copieRe1 = re1.clone();
		Relation copieRe3 = re3.clone();
		re1.enlever(re3);
		if (!re1.depart().equals(copieRe1.depart())
				|| !re1.arrivee().equals(copieRe1.arrivee())) {
			System.out
					.println("L'ensemble de départ ou d'arrivée de la relation a été modifiée.");
			testOK = false;
		}
		for (Elt eD : re1.depart()) {
			for (Elt eA : re1.arrivee()) {
				if (copieRe1.contient(eD, eA) && !copieRe3.contient(eD, eA)) {
					if (!re1.contient(eD, eA)) {
						testOK = false;
						System.out
								.println("La relation re1 ne contient pas les bons couples après la suppression.");
						break;
					}
				} else if (re1.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation re1 ne contient pas les bons couples après la suppression.");
					break;
				}
				if (re3.contient(eD, eA) != copieRe3.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation passée en paramètre à la méthode enlever a été mofifiée.");
					break;
				}
			}
			if (!testOK)
				break;
		}
		re1.enlever(re1);
		if (!re1.depart().equals(copieRe1.depart())
				|| !re1.arrivee().equals(copieRe1.arrivee())) {
			System.out
					.println("L'ensemble de départ ou d'arrivée de la relation a été modifiée.");
			testOK = false;
		}
		if (!re1.estVide()) {
			testOK = false;
			System.out
					.println("On a supprimé tous les couples de la relation re1 à la relation re1.");
			System.out.println("La relation devrait être vide.");
			System.out.println("Votre relation après suppression.");
			System.out.println(re1);
		}
		return testOK;
	}

	private static boolean testIntersecter() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation r = null;
		try {
			re1.intersecter(r);
			testOK = false;
			System.out.println("La relation passée en paramètre est null.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}

		r = new Relation(new Ensemble("{1..7}"), new Ensemble("{2,5..10}"));
		try {
			re1.intersecter(r);
			testOK = false;
			System.out
					.println("Les relations n'avaient pas le même ensemble d'arrivée.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		r = new Relation(new Ensemble("{1..6}"), new Ensemble("{2,5..10,20}"));
		try {
			re1.intersecter(r);
			testOK = false;
			System.out
					.println("Les relations n'avaient pas le même ensemble de départ.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		Relation re3 = Io.chargerRelation("re3.rel");
		Relation copieRe1 = re1.clone();
		Relation copieRe3 = re3.clone();
		re1.intersecter(re3);
		if (!re1.depart().equals(copieRe1.depart())
				|| !re1.arrivee().equals(copieRe1.arrivee())) {
			System.out
					.println("L'ensemble de départ ou d'arrivée de la relation a été modifiée.");
			testOK = false;
		}
		for (Elt eD : re1.depart()) {
			for (Elt eA : re1.arrivee()) {
				if (copieRe1.contient(eD, eA) && copieRe3.contient(eD, eA)) {
					if (!re1.contient(eD, eA)) {
						testOK = false;
						System.out
								.println("La relation re1 ne contient pas les bons couples après avoir fait l'intersection.");
						break;
					}
				} else if (re1.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation re1 ne contient pas les bons couples après avoir fait l'intersection.");
					break;
				}
				if (re3.contient(eD, eA) != copieRe3.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation passée en paramètre à la méthode intersecter a été mofifiée.");
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

	private static boolean testApres() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.apres(null);
			testOK = false;
			System.out.println("La relation passée en paramètre est null.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		Relation dg2 = Io.chargerRelation("dg2.rel");
		try {
			dg2.apres(re1);
			testOK = false;
			System.out
					.println("Les deux relations n'étaient pas conformables pour la composée.");
			System.out
					.println("Il falliat lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException me) {

		}
		Relation re2 = Io.chargerRelation("re2.rel");
		Relation copieRe1 = re1.clone();
		Relation copieRe2 = re2.clone();
		Relation compose = re2.apres(re1);
		Relation resultat = new Relation(re1.depart(), re2.arrivee());
		resultat.ajouter(new Elt(1), new Elt(10));
		resultat.ajouter(new Elt(1), new Elt(12));
		resultat.ajouter(new Elt(2), new Elt(10));
		resultat.ajouter(new Elt(2), new Elt(11));
		resultat.ajouter(new Elt(2), new Elt(12));
		resultat.ajouter(new Elt(3), new Elt(15));
		resultat.ajouter(new Elt(6), new Elt(15));
		resultat.ajouter(new Elt(7), new Elt(16));
		resultat.ajouter(new Elt(7), new Elt(19));
		if (!compose.depart().equals(re1.depart())) {
			testOK = false;
			System.out
					.println("L'ensemble de départ de la relation composée n'est pas le bon.");
		}
		if (!copieRe2.arrivee().equals(compose.arrivee())) {
			testOK = false;
			System.out
					.println("L'ensemble d'arrivée de la relation composée n'est pas le bon.");
		}
		if (!re1.depart().equals(copieRe1.depart())) {
			System.out
					.println("L'ensemble de départ de la relation passée en paramètre a été modifié.");
			testOK = false;
		}
		if (!re2.depart().equals(copieRe2.depart())) {
			System.out
					.println("L'ensemble de départ de la relation courante a été modifié.");
			testOK = false;
		}
		if (!re1.arrivee().equals(copieRe1.arrivee())) {
			System.out
					.println("L'ensemble d'arrivée de la relation passée en paramètre a été modifié.");
			testOK = false;
		}
		if (!re2.arrivee().equals(copieRe2.arrivee())) {
			System.out
					.println("L'ensemble d'arrivée de la relation pcourante a été modifié.");
			testOK = false;
		}
		for (Couple cr2 : copieRe1) {
			if (!re1.contient(cr2)) {
				testOK = false;
				System.out
						.println("La relation passée en paramètre a été modifiée.");
				break;
			}
			for (Couple cr3 : copieRe2) {
				if (!re2.contient(cr3)) {
					testOK = false;
					System.out.println("La relation courante a été modifiée.");
					break;
				}
			}
			if (!testOK)
				break;
		}
		for (Elt eD : resultat.depart()) {
			for (Elt eA : resultat.arrivee()) {
				if (resultat.contient(eD, eA) != compose.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation composée ne contient pas les bons couples.");
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

	private static boolean testInclusDans() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation re1Modif = re1.clone();
		re1Modif.ajouterDepart(new Elt(10));
		if (re1.inclusDans(re1Modif)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de re1 et de re1Modif sont différents.");
			System.out
					.println("La relation re1 n'est donc pas incluse dans la relation re1Modif.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		if (re1Modif.inclusDans(re1)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de re1 et de re1Modif sont différents.");
			System.out
					.println("La relation re1Modif n'est donc pas incluse dans dans la relation re1.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		re1Modif.supprimerDepart(new Elt(10));
		re1Modif.ajouterArrivee(new Elt(4));
		if (re1.inclusDans(re1Modif)) {
			testOK = false;
			System.out
					.println("Les ensembles d'arrivée de re1 et de re1Modif sont différents.");
			System.out
					.println("La relation re1 n'est donc pas incluse dans la relation re1Modif.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		if (re1Modif.inclusDans(re1)) {
			testOK = false;
			System.out
					.println("Les ensembles d'arrivée de re1 et de re1Modif sont différents.");
			System.out
					.println("La relation re1Modif n'est donc pas incluse dans dans la relation re1.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		re1Modif.supprimerArrivee(new Elt(4));
		re1Modif.ajouter(new Elt(3), new Elt(7));
		if (re1Modif.inclusDans(re1)) {
			testOK = false;
			System.out
					.println("re1Modif contient un couple qui ne se trouve pas dans re1.");
			System.out
					.println("La relation re1Modif n'est donc pas incluse dans dans la relation re1.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		if (!re1.inclusDans(re1Modif)) {
			testOK = false;
			System.out
					.println("re1Modif contient tous les couples de re1 et a le même ensemble de départ et d'arrivée que re1.");
			System.out
					.println("La relation re1 est donc incluse dans la relation re1Modif.");
			System.out.println("Votre méthode dit qu'elle ne l'est pas.");
		}
		Relation r1 = new Relation(new Ensemble("{1..7}"), new Ensemble(
				"{3,7,9}"));
		Relation r2 = new Relation(new Ensemble("{1..12}"), new Ensemble(
				"{3,7,9}"));
		if (r1.inclusDans(r2)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de r1 et de r2 sont différents.");
			System.out
					.println("La relation r1 n'est donc pas incluse dans la relation r2.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		if (r2.inclusDans(r1)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de r1 et de r2 sont différents.");
			System.out
					.println("La relation r2 n'est donc pas incluse dans la relation r1.");
			System.out.println("Votre méthode dit qu'elle l'est.");
		}
		if (testOK) {
			r1 = new Relation(new Ensemble("{1..7}"), new Ensemble("{3,7,9}"));
			r2 = new Relation(new Ensemble("{1..7}"), new Ensemble("{3,7,8,9}"));
			if (r1.inclusDans(r2)) {
				testOK = false;
				System.out
						.println("Les ensembles d'arrivée de r1 et de r2 sont différents.");
				System.out
						.println("La relation r1 n'est donc pas incluse dans la relation r2.");
				System.out.println("Votre méthode dit qu'elle l'est.");
			}
			if (r2.inclusDans(r1)) {
				testOK = false;
				System.out
						.println("Les ensembles d'arrivée de r2 et de r1 sont différents.");
				System.out
						.println("La relation r2 n'est donc pas incluse dans la relation r1.");
				System.out.println("Votre méthode dit qu'elle l'est.");
			}
		}
		return testOK;
	}

	private static boolean testEquals() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation re1Modif = re1.clone();
		if (!re1.equals(re1Modif)) {
			testOK = false;
			System.out.println("Les relations re1 et re1Modif sont égales.");
			System.out.println("Votre méthode qit qu'elles ne le sont pas.");
		}

		re1Modif.ajouterDepart(new Elt(10));
		if (re1.equals(re1Modif)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de re1 et de re1Modif sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		if (re1Modif.equals(re1)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de re1 et de re1Modif sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		re1Modif.supprimerDepart(new Elt(10));
		re1Modif.ajouterArrivee(new Elt(4));
		if (re1.equals(re1Modif)) {
			testOK = false;
			System.out
					.println("Les ensembles d'arrivée de re1 et de re1Modif sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		if (re1Modif.equals(re1)) {
			testOK = false;
			System.out
					.println("Les ensembles d'arrivée de re1 et de re1Modif sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		re1Modif.supprimerArrivee(new Elt(4));
		re1Modif.ajouter(new Elt(3), new Elt(7));
		if (re1.equals(re1Modif)) {
			testOK = false;
			System.out
					.println("Les relations re1 et re1Modif ne contiennent pas les mêmes couples.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		if (re1Modif.equals(re1)) {
			testOK = false;
			System.out
					.println("Les relations re1 et re1Modif ne contiennent pas les mêmes couples.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		re1Modif.enlever(new Elt(3), new Elt(8));
		if (re1.equals(re1Modif)) {
			testOK = false;
			System.out
					.println("Les relations re1 et re1Modif ne contiennent pas les mêmes couples.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		if (re1Modif.equals(re1)) {
			testOK = false;
			System.out
					.println("Les relations re1 et re1Modif ne contiennent pas les mêmes couples.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		Relation r1 = new Relation(new Ensemble("{1..7}"), new Ensemble(
				"{3,7,9}"));
		Relation r2 = new Relation(new Ensemble("{8..12}"), new Ensemble(
				"{3,7,9}"));
		if (r1.equals(r2)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de r1 et de r2 sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		if (r2.equals(r1)) {
			testOK = false;
			System.out
					.println("Les ensembles de départ de r2 et de r1 sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		r1 = new Relation(new Ensemble("{1..7}"), new Ensemble("{3,7,9}"));
		r2 = new Relation(new Ensemble("{1..7}"), new Ensemble("{3,8,9}"));
		if (r1.equals(r2)) {
			testOK = false;
			System.out
					.println("Les ensembles d'arrivée de r1 et de r2 sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		if (r2.equals(r1)) {
			testOK = false;
			System.out
					.println("Les ensembles d'arrivée de r2 et de r1 sont différents.");
			System.out
					.println("Les relations re1Modif et re1 ne sont donc pas égales.");
			System.out.println("Votre méthode dit qu'elles le sont.");
		}
		return testOK;
	}

	private static boolean testCloReflex() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.cloReflex();
			testOK = false;
			System.out
					.println("appel de la méthode cloReflex sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}
		Relation dg2 = Io.chargerRelation("dg2.rel");
		Relation copieDg2 = dg2.clone();
		dg2.cloReflex();
		for (int i = 1; i <= 16; i++) {
			copieDg2.ajouter(new Couple(new Elt(i), new Elt(i)));
		}

		for (Elt eD : copieDg2.depart()) {
			for (Elt eA : copieDg2.arrivee()) {
				if (copieDg2.contient(eD, eA) != dg2.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("On a fait la clôture réflexive de la relation dg2");
					if (copieDg2.contient(eD, eA)) {
						System.out
								.println("Le couple ("
										+ eD
										+ ","
										+ eA
										+ ") aurait dû être ajouté à la relation dg2 et ce n'est pas le cas.");
					} else {
						System.out
								.println("Le couple ("
										+ eD
										+ ","
										+ eA
										+ ") n'aurait pas dû être ajouté à la relation dg2.");
					}
					break;
				}
			}
			if (!testOK)
				break;
		}

		return testOK;
	}

	private static boolean testCloSym() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.cloSym();
			testOK = false;
			System.out
					.println("appel de la méthode cloSym sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}
		Relation dg4 = Io.chargerRelation("dg4.rel");
		Relation dg5 = Io.chargerRelation("dg5.rel");
		dg5.cloSym();
		for (Elt eD : dg4.depart()) {
			for (Elt eA : dg4.arrivee()) {
				if (dg4.contient(eD, eA) != dg5.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("On a fait la clôture symétrique de la relation dg5");
					if (dg4.contient(eD, eA)) {
						System.out
								.println("Le couple ("
										+ eD
										+ ","
										+ eA
										+ ") aurait dû être ajouté à la relation dg5 et ce n'est pas le cas.");
					} else {
						System.out
								.println("Le couple ("
										+ eD
										+ ","
										+ eA
										+ ") n'aurait pas dû être ajouté à la relation dg5.");
					}
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

	private static boolean testCloTrans() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.cloTrans();
			testOK = false;
			System.out
					.println("appel de la méthode cloTrans sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}
		Relation dg2 = Io.chargerRelation("dg2.rel");
		dg2.cloTrans();
		Relation dg6 = Io.chargerRelation("dg6.rel");
		for (Elt eD : dg6.depart()) {
			for (Elt eA : dg6.arrivee()) {
				if (dg6.contient(eD, eA) != dg2.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("On a fait la clôture transitive de la relation dg2");
					if (dg6.contient(eD, eA)) {
						System.out
								.println("Le couple ("
										+ eD
										+ ","
										+ eA
										+ ") aurait dû être ajouté à la relation dg2 et ce n'est pas le cas.");
					} else {
						System.out
								.println("Le couple ("
										+ eD
										+ ","
										+ eA
										+ ") n'aurait pas dû être ajouté à la relation dg2.");
					}
					break;
				}
			}
			if (!testOK)
				break;
		}
		if (testOK){
			Relation dg3 = Io.chargerRelation("dg3.rel");
			Relation copiedg3 = dg3.clone();
			dg3.enlever(new Elt(10), new Elt(12));
			dg3.enlever(new Elt(31), new Elt(12));
			dg3.enlever(new Elt(35), new Elt(14));
			dg3.enlever(new Elt(37), new Elt(17));
			dg3.cloTrans();
			for (Elt eD : dg3.depart()) {
				for (Elt eA : dg3.arrivee()) {
					if (copiedg3.contient(eD, eA) != dg3.contient(eD, eA)) {
						testOK = false;
						System.out
								.println("On a enlevé les couples (10,12), (31,12),(35,14) et (37,17) de la relation dg3 et, ensuite, on a fait la clôture transitive ");
						if (copiedg3.contient(eD, eA)) {
							System.out
									.println("Le couple ("
											+ eD
											+ ","
											+ eA
											+ ") aurait dû être ajouté à la relation et ce n'est pas le cas.");
						} else {
							System.out
									.println("Le couple ("
											+ eD
											+ ","
											+ eA
											+ ") n'aurait pas dû être ajouté à la relation.");
						}
						break;
					}
				}
				if (!testOK)
					break;
			}
		}
		return testOK;
	}

	private static boolean testReflexive() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.reflexive();
			testOK = false;
			System.out
					.println("appel de la méthode reflexive sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}

		Relation dg1 = Io.chargerRelation("dg1.rel");
		if (dg1.reflexive()) {
			testOK = false;
			System.out
					.println("La relation dg1.rel n'est pas réflexive mais votre méthode dit qu'elle l'est.");
		}

		Ensemble sousJacent = new Ensemble("{2,5,7,9..12}");
		Relation relRef = new Relation(sousJacent, sousJacent);
		for (Elt e : sousJacent) {
			relRef.ajouter(new Couple(e, e));
		}
		relRef.ajouter(new Elt(5),new Elt(7));
		if (!relRef.reflexive()) {
			testOK = false;
			System.out
					.println("La relation relRef décrite ci-dessous est réflexive mais votre méthode dit qu'elle ne l'est pas.");
			System.out.println(relRef);
		}
		relRef.ajouter(new Elt(7),new Elt(5));
		if (!relRef.reflexive()) {
			testOK = false;
			System.out
					.println("La relation relRef décrite ci-dessous est réflexive mais votre méthode dit qu'elle ne l'est pas.");
			System.out.println(relRef);
		}
		relRef.ajouter(new Elt(5),new Elt(9));
		if (!relRef.reflexive()) {
			testOK = false;
			System.out
					.println("La relation relRef décrite ci-dessous est réflexive mais votre méthode dit qu'elle ne l'est pas.");
			System.out.println(relRef);
		}
		return testOK;
	}

	private static boolean testAntireflexive() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.antireflexive();
			testOK = false;
			System.out
					.println("appel de la méthode antireflexive sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}

		Relation dg1 = Io.chargerRelation("dg1.rel");
		if (dg1.antireflexive()) {
			testOK = false;
			System.out
					.println("La relation dg1.rel n'est pas antiréflexive mais votre méthode dit qu'elle l'est.");
		}
		Relation dg3 = Io.chargerRelation("dg3.rel");
		if (!dg3.antireflexive()) {
			testOK = false;
			System.out
					.println("La relation dg3.rel est antiréflexive mais votre méthode dit qu'elle ne l'est pas.");
		}
		Relation dg4 = Io.chargerRelation("dg4.rel");
		dg4.enlever(new Elt(10), new Elt(10));
		if (!dg4.antireflexive()) {
			testOK = false;
			System.out
					.println("La relation dg4.rel de laquelle on a enlevée le couple (10,10) est antiréflexive mais votre méthode dit qu'elle ne l'est pas.");
		}
		return testOK;
	}

	private static boolean testSymetrique() {
		boolean testOK = true;
		Ensemble sousJacent = new Ensemble("{2,5,7,9..12}");
		Relation relRef = new Relation(sousJacent, sousJacent);
		if (!relRef.symetrique()) {
			testOK = false;
			System.out
					.println("La relation relRef décrite ci-dessous est symétrique mais votre méthode dit qu'elle ne l'est pas.");
			System.out.println(relRef);
		}
		Relation re1 = Io.chargerRelation("re1.rel");

		try {
			re1.symetrique();
			testOK = false;
			System.out
					.println("appel de la méthode symetrique sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}

		Relation dg1 = Io.chargerRelation("dg1.rel");
		if (dg1.symetrique()) {
			testOK = false;
			System.out
					.println("La relation dg1.rel n'est pas symétrique mais votre méthode dit qu'elle l'est.");
		}

		Relation dg4 = Io.chargerRelation("dg4.rel");
		if (!dg4.symetrique()) {
			testOK = false;
			System.out
					.println("La relation dg4.rel est symétrique mais votre méthode dit qu'elle ne l'est pas.");
		}

		return testOK;
	}

	private static boolean testAntisymetrique() {

		boolean testOK = true;
		
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.antisymetrique();
			testOK = false;
			System.out
					.println("appel de la méthode antisymetrique sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}
		Ensemble sousJacent = new Ensemble("{2,5,7,9..12}");
		Relation relRef = new Relation(sousJacent, sousJacent);
		if (!relRef.antisymetrique()) {
			testOK = false;
			System.out
					.println("La relation relRef décrite ci-dessous est antisymétrique mais votre méthode dit qu'elle ne l'est pas.");
			System.out.println(relRef);
		}
		Relation dg1 = Io.chargerRelation("dg1.rel");
		if (dg1.antisymetrique()) {
			testOK = false;
			System.out
					.println("La relation dg1.rel n'est pas antisymetrique mais votre méthode dit qu'elle l'est.");
		}

		Relation dg2 = Io.chargerRelation("dg2.rel");
		if (!dg2.antisymetrique()) {
			testOK = false;
			System.out
					.println("La relation dg2.rel est antisymetrique mais votre méthode dit qu'elle ne l'est pas.");
		}

		Relation dg3 = Io.chargerRelation("dg3.rel");
		if (!dg3.antisymetrique()) {
			testOK = false;
			System.out
					.println("La relation dg3.rel est antisymetrique mais votre méthode dit qu'elle ne l'est pas.");
		}


		
		return testOK;
	}

	private static boolean testTransitive() {
		boolean testOK = true;
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			re1.transitive();
			testOK = false;
			System.out
					.println("appel de la méthode transitive sur une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out.println("Il fallait lancer une MathException !");
		} catch (MathException me) {

		}
		
		Ensemble sousJacent = new Ensemble("{2,5,7,9..12}");
		Relation relRef = new Relation(sousJacent, sousJacent);
		if (!relRef.transitive()) {
			testOK = false;
			System.out
					.println("La relation relRef décrite ci-dessous est transitive mais votre méthode dit qu'elle ne l'est pas.");
			System.out.println(relRef);
		}

		Relation dg1 = Io.chargerRelation("dg1.rel");
		if (dg1.transitive()) {
			testOK = false;
			System.out
					.println("La relation dg1.rel n'est pas transitive mais votre méthode dit qu'elle l'est.");
		}

		Relation dg2 = Io.chargerRelation("dg2.rel");
		if (dg2.transitive()) {
			testOK = false;
			System.out
					.println("La relation dg2.rel n'est pas transitive mais votre méthode dit qu'elle l'est.");
		}

		Relation dg6 = Io.chargerRelation("dg6.rel");
		if (!dg6.transitive()) {
			testOK = false;
			System.out
					.println("La relation dg6.rel est transitive mais votre méthode dit qu'elle ne l'est pas.");
		}

		return testOK;
	}
	
	private static boolean testConstructeur() {
		boolean testOK = true;
		try {
			new Relation(null);
			testOK = false;
			System.out
					.println("Le paramètre passé au constructeur est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Relation re1 = Io.chargerRelation("re1.rel");
		Relation re1Copie = re1.clone();
		Relation copie = new Relation(re1);
		if (!copie.depart().equals(re1Copie.depart())) {
			testOK = false;
			System.out
					.println("La relation créée par votre constructeur n'a pas le bon ensemble de départ.");
		}
		if (!copie.arrivee().equals(re1Copie.arrivee())) {
			testOK = false;
			System.out
					.println("La relation créée par votre constructeur n'a pas le bon ensemble d'arrivée.");
		}
		if (!re1.depart().equals(re1Copie.depart())) {
			testOK = false;
			System.out
					.println("Votre constructeur a modifié l'ensemble de départ de le relation passée en paramètre.");
		}
		if (!re1.arrivee().equals(re1Copie.arrivee())) {
			testOK = false;
			System.out
					.println("Votre constructeur a modifié l'ensemble d'arrivée de le relation passée en paramètre.");
		}
		for (Elt eD : copie.depart()) {
			for (Elt eA : copie.arrivee()) {
				if (copie.contient(eD, eA) != re1Copie.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation créée par votre constructeur ne contient pas les bons couples");
					break;
				}
				if (re1.contient(eD, eA) != re1Copie.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("Votre constructeur a modifié la relation passée en paramètre !");
					break;
				}
			}
			if (!testOK)
				break;
		}
		if (testOK) {
			Ensemble depart = new Ensemble("{1,2}");
			Ensemble arrivee = new Ensemble("{3,4}");
			Relation relVide = new Relation(depart, arrivee);
			copie = new Relation(relVide);
			if (!relVide.depart().equals(copie.depart())) {
				testOK = false;
				System.out
						.println("Votre constructeur a modifié l'ensemble de départ de le relation passée en paramètre.");
			}
			if (!relVide.arrivee().equals(copie.arrivee())) {
				testOK = false;
				System.out
						.println("Votre constructeur a modifié l'ensemble d'arrivée de le relation passée en paramètre.");
			}
			for (Elt eD : copie.depart()) {
				for (Elt eA : copie.arrivee()) {
					if (copie.contient(eD, eA)) {
						testOK = false;
						System.out.println("On a copié la relation vide");
						System.out.println("La copie contient le couple (" + eD
								+ "," + eA
								+ ") alors qu'elle devrait être vide.");
						break;
					}

				}
				if (!testOK)
					break;
			}
		}
		return testOK;
	}

	private static boolean testIdentite() {
		boolean testOK = true;
		try {
			Relation.identite(null);
			testOK = false;
			System.out
					.println("Le paramètre passé à la méthode identite est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble sousJacent = new Ensemble("2,7..12,16,34");
		Relation identite = Relation.identite(sousJacent);
		if (!identite.depart().equals(sousJacent)) {
			testOK = false;
			System.out
					.println("L'ensemble de départ de la relation renvoyée par la méthode identite n'est pas correct.");
		}
		if (!identite.arrivee().equals(sousJacent)) {
			testOK = false;
			System.out
					.println("L'ensemble d'arrivée de la relation renvoyée par la méthode identite n'est pas correct.");
		}
		for (Elt e : sousJacent) {
			if (!identite.contient(e, e)) {
				testOK = false;
				System.out
						.println("La relation renvoyée par votre méthode identite ne contient pas les bons couples.");
				break;
			}
		}
		for (Couple c : identite) {
			if (!c.getX().equals(c.getY())) {
				testOK = false;
				System.out
						.println("La relation renvoyée par votre méthode identite ne contient pas les bons couples.");
				break;
			}
		}
		return testOK;
	}

	private static boolean testProduitCartesien() {
		boolean testOK = true;

		Ensemble depart = new Ensemble("2,7..12,16,34");
		Ensemble arrivee = new Ensemble("5,18,24,34,35");

		try {
			Relation.produitCartesien(depart, null);
			testOK = false;
			System.out
					.println("L'ensemble d'arrivée passé à la méthode produitCartesien est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		try {
			Relation.produitCartesien(null, arrivee);
			testOK = false;
			System.out
					.println("L'ensemble de départ passé à la méthode produitCartesien est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Relation produitCartesien = Relation.produitCartesien(depart, arrivee);
		if (!produitCartesien.depart().equals(depart)) {
			testOK = false;
			System.out
					.println("L'ensemble de départ de la relation renvoyée par la méthode produitCartesien n'est pas correct.");
		}
		if (!produitCartesien.arrivee().equals(arrivee)) {
			testOK = false;
			System.out
					.println("L'ensemble d'arrivée de la relation renvoyée par la méthode produitCartesien n'est pas correct.");
		}
		for (Elt eD : depart) {
			for (Elt eA : arrivee) {
				if (!produitCartesien.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation renvoyée par votre méthode produitCartesien ne contient pas les bons couples.");
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

}
