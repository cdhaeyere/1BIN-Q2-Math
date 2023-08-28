import java.lang.reflect.Field;
import java.util.Scanner;

public class TestOrdre {
	private final static Scanner scanner = new Scanner(System.in);
	private static final Class cl = Ordre.class;
	private static Field field;
	private static final String[] NOMS_METHODE = { "Ordre(EnsembleAbstrait)",
			"Ordre(Relation r)", "Ordre(Ordre or )", "ajouterAuSousJacent",
			"enleverDuSousJacent", "ajouter","comparables","minimaux", 
			"maximaux", "minimum","maximum", "minor", "major", "infimum",
			"supremum", "treillis" };

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		field = cl.getDeclaredField("couples");
		field.setAccessible(true);
		System.out.println("**************************************");
		System.out.println("Programme Test pour la classe Ordre");
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
			case 1:
				testOK = testOrdreEns();
				break;
			case 2:
				testOK = testOrdreRelation();
				break;
			case 3:
				testOK = testOrdreRecopie();
				break;
			case 4:
				testOK = testAjouterAuSousJacent();
				break;
			case 5:
				testOK = testEnleverDuSousJacent();
				break;
			case 6:
				testOK = testAjouter();
				break;
			case 7:
				testOK = testComparables();
				break;
			case 8:
				testOK = testMinimaux();
				break;
			case 9:
				testOK = testMaximaux();
				break;
			case 10:
				testOK = testMinimum();
				break;
			case 11:
				testOK = testMaximum();
				break;
			case 12:
				testOK = testMinor();
				break;
			case 13:
				testOK = testMajor();
				break;
			case 14:
				testOK = testInfimum();
				break;
			case 15:
				testOK = testSupremum();
				break;
			case 16:
				testOK = testTreillis();
				break;
		
			default:
				return;
			}
			
			System.out.println() ;
			if (testOK)
				System.out.println("Le test de la méthode "
						+ NOMS_METHODE[choix - 1] + " a réussi.");
			else
				System.out.println("Le test de la méthode "
						+ NOMS_METHODE[choix - 1] + " a échoué.");
			System.out.println() ;
		}
	}



	private static boolean testOrdreEns() {
		boolean testOK = true;
		Ensemble paramNull = null;
		try {
			new Ordre(paramNull);
			testOK = false;
			System.out
					.println("On a passé un paramètre null au constructeur à partir d'un ensemble.");
			System.out
					.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		Ensemble sousJac = new Ensemble("4,7,9,12,16");
		Ordre or = new Ordre(sousJac);
		if (!or.depart().equals(sousJac)) {
			System.out
					.println("L'ensemble de départ de l'ordre n'a pas été bien initialisé");
			testOK = false;
		}
		if (!or.arrivee().equals(sousJac)) {
			System.out
					.println("L'ensemble d'arrivée de l'ordre n'a pas été bien initialisé");
			testOK = false;
		}
		for (Elt e : sousJac) {
			if (!or.contient(new Couple(e, e))) {
				testOK = false;
				System.out
						.println("La relation d'ordre ne contient pas les bons couples");
				break;
			}
		}
		if (testOK) {
			for (Elt e1 : sousJac) {
				for (Elt e2 : sousJac) {
					if (!e1.equals(e2) && or.contient(new Couple(e1, e2))) {
						testOK = false;
						System.out
								.println("La relation d'ordre ne contient pas les bons couples");
						break;
					}
				}
				if (!testOK)
					break;
			}
		}
		return testOK;
	}

	private static boolean testOrdreRelation() {
		boolean testOK = true;
		Relation paramNull = null;
		try {
			new Ordre(paramNull);
			testOK = false;
			System.out
					.println("On a passé un paramètre null au constructeur à partir d'une relation.");
			System.out
					.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		Relation re1 = Io.chargerRelation("re1.rel");
		try {
			new Ordre(re1);
			testOK = false;
			System.out
					.println("Construction d'un ordre à partir d'une relation dont l'ensemble de départ ne correspond pas à l'ensemble d'arrivée");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException e) {

		}
		Relation dg1 = Io.chargerRelation("dg1.rel");
		try {
			new Ordre(dg1);
			testOK = false;
			System.out
					.println("Construction d'un ordre à partir d'une relation inadéquate (dg1.rel)");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException e) {

		}
		Relation nv = new Relation(new Ensemble("{1,2,3,4}"), new Ensemble(
				"{1,2,3,4}"));
		nv.ajouter(new Elt(1), new Elt(2));
		nv.ajouter(new Elt(2), new Elt(3));
		nv.ajouter(new Elt(3), new Elt(4));
		nv.ajouter(new Elt(4), new Elt(1));
		try {
			new Ordre(nv);
			testOK = false;
			System.out
					.println("Construction d'un ordre à partir de la relation : ");
			System.out.println(nv);
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException e) {

		}

		Relation dg3 = Io.chargerRelation("dg3.rel");
		Relation ordg3 = dg3.clone();
		dg3.enlever(new Elt(10), new Elt(12));
		dg3.enlever(new Elt(35), new Elt(14));
		Relation copiedg3 = dg3.clone();
		for (Elt e : ordg3.depart()){
			ordg3.ajouter(new Couple(e,e));
		}
		for (Elt inter : ordg3.depart()){
			for (Elt dep : ordg3.depart() ){
				if (ordg3.contient(dep, inter)){
					for (Elt arr : ordg3.depart()){
						if (ordg3.contient(inter, arr)) ordg3.ajouter(dep, arr);
					}
				}
			}
		}
		Ordre or = new Ordre(dg3);
		if (!or.depart().equals(dg3.depart())) {
			testOK = false;
			System.out
					.println("L'ordre créé par votre constructeur n'a pas le bon ensemble de départ.");
		}
		if (!or.arrivee().equals(dg3.arrivee())) {
			testOK = false;
			System.out
					.println("L'ordre créé par votre constructeur n'a pas le bon ensemble d'arrivée.");
		}
		if (!dg3.depart().equals(copiedg3.depart())) {
			testOK = false;
			System.out
					.println("Votre constructeur a modifié l'ensemble de départ de le relation passée en paramètre.");
		}
		if (!dg3.arrivee().equals(copiedg3.arrivee())) {
			testOK = false;
			System.out
					.println("Votre constructeur a modifié l'ensemble d'arrivée de le relation passée en paramètre.");
		}
		for (Elt eD : or.depart()) {
			for (Elt eA : or.arrivee()) {
				if (or.contient(new Couple(eD, eA)) != ordg3.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("L'ordre créé par votre constructeur ne contient pas les bons couples");
					break;
				}
				if (copiedg3.contient(eD, eA) != dg3.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("Votre constructeur a modifié la relation passée en paramètre !");
					break;
				}
			}
			if (!testOK)
				break;
		}
		return testOK;
	}

	private static boolean testOrdreRecopie() throws IllegalArgumentException,
			IllegalAccessException {
		boolean testOK = true;
		Ordre paramNull = null;
		try {
			new Ordre(paramNull);
			testOK = false;
			System.out
					.println("On a passé un paramètre null au constructeur à partir d'un ordre.");
			System.out
					.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		Ensemble sousJac = new Ensemble("4,7,9,12,16");
		Ordre or = new Ordre(sousJac);
		Ordre copie = new Ordre(or);
		if (!copie.depart().equals(sousJac)) {
			System.out
					.println("L'ensemble de départ de l'ordre n'a pas été bien initialisé");
			testOK = false;
		}
		if (!copie.arrivee().equals(sousJac)) {
			System.out
					.println("L'ensemble d'arrivée de l'ordre n'a pas été bien initialisé");
			testOK = false;
		}
		for (Elt e : sousJac) {
			if (!copie.contient(new Couple(e, e))) {
				testOK = false;
				System.out
						.println("La relation d'ordre ne contient pas les bons couples");
				break;
			}
		}
		if (testOK) {
			for (Elt e1 : sousJac) {
				for (Elt e2 : sousJac) {
					if (!e1.equals(e2) && copie.contient(new Couple(e1, e2))) {
						testOK = false;
						System.out
								.println("La relation d'ordre ne contient pas les bons couples");
						break;
					}
				}
				if (!testOK)
					break;
			}
		}
		Relation rOr = (Relation) field.get(or);
		Relation rCopie = (Relation) field.get(copie);
		if (rOr == rCopie) {
			testOK = false;
			System.out
					.println("Vous avez recopié l'adresse mémoire de la relation couples au lieu de faire une copie");
		}

		Relation dg3 = Io.chargerRelation("dg3.rel");
		or = new Ordre(dg3);
		copie = new Ordre(or);
		if (!or.depart().equals(copie.depart())) {
			testOK = false;
			System.out
					.println("L'ordre créé par votre constructeur n'a pas le bon ensemble de départ.");
		}
		if (!or.arrivee().equals(copie.arrivee())) {
			testOK = false;
			System.out
					.println("L'ordre créé par votre constructeur n'a pas le bon ensemble d'arrivée.");
		}

		for (Elt eD : or.depart()) {
			for (Elt eA : or.arrivee()) {
				if (or.contient(new Couple(eD, eA)) != copie.contient(new Couple(eD, eA))) {
					testOK = false;
					System.out
							.println("L'ordre créé par votre constructeur ne contient pas les bons couples");
					break;
				}
			}
			if (!testOK)
				break;
		}

		rOr = (Relation) field.get(or);
		rCopie = (Relation) field.get(copie);
		if (rOr == rCopie) {
			testOK = false;
			System.out
					.println("Vous avez recopié l'adresse mémoire de la relation couples au lieu de faire une copie");
		}

		return testOK;
	}

	private static boolean testAjouterAuSousJacent() {
		boolean testOK = true;
		Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
		try {
			or1.ajouterAuSousJacent(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode ajouterAuSousJacent avec un paramètre null.");
			System.out
					.println("--> IL fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		Ordre copie = new Ordre(Io.chargerRelation("or1.rel"));
		or1.ajouterAuSousJacent(new Elt(10));
		if (!copie.depart().equals(or1.depart())) {
			testOK = false;
			System.out
					.println("L'élement était déjà dans l'ensemble sous-jacent.");
			System.out
					.println("Il ne fallait pas modifier l'ensembble de départ.");
		}
		if (!copie.depart().equals(or1.arrivee())) {
			testOK = false;
			System.out
					.println("L'élement était déjà dans l'ensemble sous-jacent.");
			System.out
					.println("Il ne fallait pas modifier l'ensemble d'arrivée.");
		}
		for (Elt eD : copie.depart()) {
			for (Elt eA : copie.arrivee()) {
				if (copie.contient(new Couple(eD, eA)) != or1
						.contient(new Couple(eD, eA))) {
					testOK = false;
					System.out
							.println("Vous avez modifié les couples qui se trouvaient dans la relation alors qu'il ne fallait pas");
				}
				if (!testOK)
					break;
			}
		}
		or1 = new Ordre(Io.chargerRelation("or1.rel"));
		Elt eAj = new Elt(13);
		EnsembleAbstrait ssJacent = copie.depart();
		ssJacent.ajouter(eAj);
		or1.ajouterAuSousJacent(eAj);
		if (!or1.depart().equals(ssJacent)) {
			testOK = false;
			System.out
					.println("L'élement n'a pas été ajouté dans l'ensemble de départ de la relation.");
		}
		if (!or1.arrivee().equals(ssJacent)) {
			testOK = false;
			System.out
					.println("L'élement n'a pas été ajouté dans l'ensemble d'arrivée de la relation.");
		}
		if (!or1.contient(new Couple(eAj, eAj))) {
			System.out.println("La relation d'ordre n'est plus réflexive");
			testOK = false;
		}
		for (Elt eD : copie.depart()) {
			for (Elt eA : copie.arrivee()) {
				if (copie.contient(new Couple(eD, eA)) != or1
						.contient(new Couple(eD, eA))) {
					testOK = false;
					System.out
							.println("Vous avez ajouté ou enlevé des couples alors qu'il ne fallait pas");
					break;
				}
				if (!testOK)
					break;
			}
		}
		if (testOK) {
			for (Elt eD : copie.depart()) {
				if (or1.contient(new Couple(eAj, eD))
						|| or1.contient(new Couple(eD, eAj))) {
					testOK = false;
					System.out
							.println("Vous avez ajouté des couples alors qu'il ne fallait pas");
					break;
				}
			}
		}
		return testOK;
	}

	private static boolean testEnleverDuSousJacent() {
		boolean testOK = true;
		Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
		try {
			or1.enleverDuSousJacent(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode enleverDuSousJacent avec un paramètre null.");
			System.out
					.println("--> IL fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		Ordre copie = new Ordre(Io.chargerRelation("or1.rel"));
		or1.enleverDuSousJacent(new Elt(7));
		if (!copie.depart().equals(or1.depart())) {
			testOK = false;
			System.out
					.println("L'élement n'était pas dans l'ensemble sous-jacent.");
			System.out
					.println("Il ne fallait pas modifier l'ensembble de départ.");
		}
		if (!copie.depart().equals(or1.arrivee())) {
			testOK = false;
			System.out
					.println("L'élement  n'était pas dans l'ensemble sous-jacent.");
			System.out
					.println("Il ne fallait pas modifier l'ensemble d'arrivée.");
		}
		for (Elt eD : copie.depart()) {
			for (Elt eA : copie.arrivee()) {
				if (copie.contient(new Couple(eD, eA)) != or1
						.contient(new Couple(eD, eA))) {
					testOK = false;
					System.out
							.println("Vous avez modifié les couples qui se trouvaient dans la relation alors qu'il ne fallait pas");
				}
				if (!testOK)
					break;
			}
		}
		or1 = new Ordre(Io.chargerRelation("or1.rel"));
		Elt eEnl = new Elt(10);
		EnsembleAbstrait ssJacent = copie.depart();
		ssJacent.enlever(eEnl);
		or1.enleverDuSousJacent(eEnl);
		if (!or1.depart().equals(ssJacent)) {
			testOK = false;
			System.out
					.println("L'élement n'a pas été enlevé de l'ensemble de départ de la relation.");
		}
		if (!or1.arrivee().equals(ssJacent)) {
			testOK = false;
			System.out
					.println("L'élement n'a pas été enlevé de l'ensemble d'arrivée de la relation.");
		}
		for (Elt eD : ssJacent) {
			for (Elt eA : ssJacent) {
				if (copie.contient(new Couple(eD, eA)) != or1
						.contient(new Couple(eD, eA))) {
					testOK = false;
					System.out
							.println("Vous avez ajouté ou enlevé des couples alors qu'il ne fallait pas");
					break;
				}
				if (!testOK)
					break;
			}
		}
		return testOK;
	}

	private static boolean testAjouter() {
		boolean testOK = true;
		Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
		try {
			or1.ajouter(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode ajouter avec un paramètre null.");
			System.out
					.println("--> IL fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		Elt eD = new Elt(9);
		Elt eA = new Elt(34);
		Couple cAj = new Couple(eD, eA);
		try {
			or1.ajouter(cAj);
			testOK = false;
			System.out
					.println("Le point de départ du couple n'appartient pas à l'ensemble sous-jacent de l'ordre");
			System.out
					.println("Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException m) {

		}
		eD = new Elt(10);
		eA = new Elt(28);
		cAj = new Couple(eD, eA);
		try {
			or1.ajouter(cAj);
			testOK = false;
			System.out
					.println("Le point d'arrivée du couple n'appartient pas à l'ensemble sous-jacent de l'ordre");
			System.out
					.println("Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException m) {

		}
		eD = new Elt(43);
		eA = new Elt(46);
		cAj = new Couple(eD, eA);
		try {
			or1.ajouter(cAj);
			testOK = false;
			System.out.println("On a essayé d'ajouter le couple " + cAj
					+ " à or1");
			System.out
					.println("Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException m) {

		}
		eD = new Elt(46);
		eA = new Elt(43);
		cAj = new Couple(eD, eA);
		or1.ajouter(cAj);
		Ordre copie = new Ordre(Io.chargerRelation("or1.rel"));
		if (!copie.depart().equals(or1.depart())) {
			testOK = false;
			System.out
					.println("Il ne fallait pas modifier l'ensembble de départ.");
		}
		if (!copie.depart().equals(or1.arrivee())) {
			testOK = false;
			System.out
					.println("Il ne fallait pas modifier l'ensemble d'arrivée.");
		}
		for (Elt eDep : copie.depart()) {
			for (Elt eArr : copie.arrivee()) {
				if (copie.contient(new Couple(eDep, eArr)) != or1
						.contient(new Couple(eDep, eArr))) {
					testOK = false;
					System.out
							.println("Vous avez ajouté ou enlevé un couple alors qu'il ne fallait pas");
				}
				if (!testOK)
					break;
			}
		}
		Relation r = Io.chargerRelation("or1.rel");
		Ensemble ptt = new Ensemble("11,12,32,41");
		Ensemble gd = new Ensemble("36,49,50");
		for (Elt eP : ptt) {
			for (Elt eG : gd) {
				r.ajouter(eP, eG);
			}
		}
		eD = new Elt(41);
		eA = new Elt(49);
		cAj = new Couple(eD, eA);
		or1.ajouter(cAj);
		if (!copie.depart().equals(or1.depart())) {
			testOK = false;
			System.out
					.println("Il ne fallait pas modifier l'ensemble de départ.");
		}
		if (!copie.depart().equals(or1.arrivee())) {
			testOK = false;
			System.out
					.println("Il ne fallait pas modifier l'ensemble d'arrivée.");
		}
		for (Elt eDep : copie.depart()) {
			for (Elt eArr : copie.arrivee()) {
				if (or1.contient(new Couple(eDep, eArr)) != r
						.contient(new Couple(eDep, eArr))) {
					testOK = false;
					System.out
							.println("Vous avez ajouté ou enlevé un couple alors qu'il ne fallait pas");
				}
				if (!testOK)
					break;
			}
		}

		return testOK;
	}

	private static boolean testComparables() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);
		try {
			or1.comparables(null, new Elt(35));
			testOK = false;
			System.out
					.println("On a appelé la méthode comparable avec un paramètre x à null.");
			System.out
					.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		
		try {
			or1.comparables(new Elt(10), null);
			testOK = false;
			System.out
			.println("On a appelé la méthode comparable avec un paramètre y à null.");
	System.out
			.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}

		try {
			or1.comparables(new Elt(9), new Elt(35));
			testOK = false;
			System.out
					.println("On a appelé la méthode comparable avec un paramètre x n'appartenant pas à l'ensemble sous-jacent.");
			System.out
					.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		try {
			or1.comparables(new Elt(10), new Elt(31));
			testOK = false;
			System.out
			.println("On a appelé la méthode comparable avec un paramètre y n'appartenant pas à l'ensemble sous-jacent.");
	System.out
			.println("--> Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}

		if (or1.comparables(new Elt(40), new Elt(48))) {
			testOK = false;
			System.out
					.println("40 et 48 ne sont pas comparables pour l'ordre or1.");
			System.out.println("Votre méthode dit qu'ils le sont.");
		}
		if (!or1.comparables(new Elt(32), new Elt(32))) {
			testOK = false;
			System.out.println("32 et 32 sont comparables pour l'ordre or1.");
			System.out.println("Votre méthode dit qu'ils ne le sont pas.");
		}
		if (!or1.comparables(new Elt(32), new Elt(34))) {
			testOK = false;
			System.out.println("32 et 34 sont comparables pour l'ordre or1.");
			System.out.println("Votre méthode dit qu'ils ne le sont pas.");
		}
		if (!or1.comparables(new Elt(34), new Elt(32))) {
			testOK = false;
			System.out.println("34 et 32 sont comparables pour l'ordre or1.");
			System.out.println("Votre méthode dit qu'ils ne le sont pas.");
		}
		return testOK;
	}

	private static boolean testMinimaux() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);

		try {
			or1.minimaux(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode minimaux avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");

		try {
			or1.minimaux(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		EnsembleAbstrait min = or1.minimaux(ens);
		Ensemble result = new Ensemble();
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minimaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		min = or1.minimaux(ens);
		result = new Ensemble("41,46");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minimaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("32,40..49");
		min = or1.minimaux(ens);
		result = new Ensemble("32");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minimaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("34,39,48");
		min = or1.minimaux(ens);
		result = new Ensemble("34,39,48");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minimaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("35,38,42");
		min = or1.minimaux(ens);
		result = new Ensemble("42");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minimaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testMaximaux() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);

		try {
			or1.maximaux(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode maximaux avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");
		
		try {
			or1.maximaux(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		EnsembleAbstrait max = or1.maximaux(ens);
		Ensemble result = new Ensemble();
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		max = or1.maximaux(ens);
		result = new Ensemble("40,43,49");
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("36,40..49");
		max = or1.maximaux(ens);
		result = new Ensemble("36");
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("34,39,48");
		max = or1.maximaux(ens);
		result = new Ensemble("34,39,48");
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testMinimum() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);

		try {
			or1.minimum(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode minimum avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");
		try {
			or1.minimum(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		Elt min = or1.minimum(ens);
		Elt result = null;
		if (min != null) {
			testOK = false;
			System.out.println("On a demandé le minimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		min = or1.minimum(ens);
		if (min != null) {
			testOK = false;
			System.out.println("On a demandé le minimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("32,40..49");
		min = or1.minimum(ens);
		result = new Elt(32);
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé le minimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("34,39,48");
		min = or1.minimum(ens);
		result = null;
		if (min != null) {
			testOK = false;
			System.out.println("On a demandé le minimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("36,50");
		min = or1.minimum(ens);
		result = new Elt(50);
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé le minimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testMaximum() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);
		try {
			or1.maximum(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode maximum avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");
		try {
			or1.maximum(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		Elt max = or1.maximum(ens);
		Elt result = null;
		if (max != null) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		max = or1.maximum(ens);
		if (max != null) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("36,40..49");
		max = or1.maximum(ens);
		result = new Elt(36);
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("34,39,48");
		max = or1.maximum(ens);
		result = null;
		if (max != null) {
			testOK = false;
			System.out.println("On a demandé les maximaux de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("11,32");
		max = or1.maximum(ens);
		result = new Elt(32);
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé le maximum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testMinor() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);
		try {
			Ensemble paramNull = null;
			or1.minor(paramNull);
			testOK = false;
			System.out
					.println("On a appelé la méthode minor avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");
		try {
			or1.minor(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		EnsembleAbstrait min = or1.minor(ens);
		EnsembleAbstrait result = or1.depart();
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		min = or1.minor(ens);
		result = new Ensemble("11,32");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("32,40..49");
		min = or1.minor(ens);
		result = new Ensemble("32,11");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		ens = new Ensemble("34,39,48");
		min = or1.minor(ens);
		result = new Ensemble("11,32,33");
		if (!min.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les minorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + min);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testMajor() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);
		try {
			Ensemble paramNull = null;
			or1.major(paramNull);
			testOK = false;
			System.out
					.println("On a appelé la méthode major avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");
		try {
			or1.major(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		EnsembleAbstrait max = or1.major(ens);
		EnsembleAbstrait result = or1.depart();
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les majorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("41..49");
		max = or1.major(ens);
		result = new Ensemble("36,50");
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les majorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("41..50");
		max = or1.major(ens);
		result = new Ensemble("36,50");
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les majorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		ens = new Ensemble("34,39,48");
		max = or1.major(ens);
		result = new Ensemble("36");
		if (!max.equals(result)) {
			testOK = false;
			System.out.println("On a demandé les majorants de l'ensemble "
					+ ens + " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + max);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testInfimum() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);
		Ensemble ens = new Ensemble("9,10,46,45");
		try {
			
			or1.infimum(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode infimum avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		ens = new Ensemble();
		Elt inf = or1.infimum(ens);
		Elt result = new Elt(36);
		if (!result.equals(inf)) {
			testOK = false;
			System.out.println("On a demandé l'infimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + inf);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		inf = or1.infimum(ens);
		result = new Elt(32);
		if (!result.equals(inf)) {
			testOK = false;
			System.out.println("On a demandé l'infimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + inf);
			System.out.println();
		}
		ens = new Ensemble("32,40..49");
		inf = or1.infimum(ens);
		result = new Elt(32);
		if (!inf.equals(result)) {
			testOK = false;
			System.out.println("On a demandé l'infimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + inf);
			System.out.println();
		}
		ens = new Ensemble("34,45");
		inf = or1.infimum(ens);
		result = null;
		if (inf != null) {
			testOK = false;
			System.out.println("On a demandé l'infimum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + inf);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testSupremum() {
		boolean testOK = true;
		Relation or = Io.chargerRelation("or1.rel");
		Ordre or1 = new Ordre(or);
		try {
			
			or1.supremum(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode supremum avec un paramètre null.");
			System.out
					.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		Ensemble ens = new Ensemble("9,10,46,45");
		try {
			or1.supremum(ens);
			testOK = false;
			System.out.println("L'ensemble " + ens
					+ " n'est pas inclus dans l'ensemble sous-jacent de or 1.");
			System.out
					.println("Il fallait lancer une IllegalArgumentException !");
		} catch (IllegalArgumentException m) {

		}
		ens = new Ensemble();
		Elt sup = or1.supremum(ens);
		Elt result = new Elt(11);
		if (!sup.equals(result)) {
			testOK = false;
			System.out.println("On a demandé le supremum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + sup);
			System.out.println();
		}
		ens = new Ensemble("40..49");
		sup = or1.supremum(ens);
		result = new Elt(36);
		if (!sup.equals(result)) {
			testOK = false;
			System.out.println("On a demandé le supremum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + sup);
			System.out.println();
		}
		ens = new Ensemble("36,40..49");
		sup = or1.supremum(ens);
		result = new Elt(36);
		if (!sup.equals(result)) {
			testOK = false;
			System.out.println("On a demandé le supremum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + sup);
			System.out.println();
		}
		ens = new Ensemble("33,46");
		sup = or1.supremum(ens);
		result = null;
		if (sup != null) {
			testOK = false;
			System.out.println("On a demandé le supremum de l'ensemble " + ens
					+ " pour l'ordre or1.");
			System.out.println("Résultat attendu : " + result);
			System.out.println("Résultat trouvé : " + sup);
			System.out.println();
		}
		return testOK;
	}

	private static boolean testTreillis() {
		boolean testOK = true;
		Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
		if(or1.treillis()){
			testOK = false;
			System.out.println("L'ordre or1 n'est pas un treillis mais votre méthode dit qu'il en est un");
		}
		Ordre or2 = new Ordre(Io.chargerRelation("or2.rel"));
		if(!or2.treillis()){
			testOK = false;
			System.out.println("L'ordre or2 n'est pas un treillis mais votre méthode dit qu'il en est un");
		}
		
		return testOK;
	}

}
