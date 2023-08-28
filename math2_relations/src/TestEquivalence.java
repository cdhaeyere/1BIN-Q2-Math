import java.lang.reflect.Field;
import java.util.Scanner;

public class TestEquivalence {
	private final static Scanner scanner = new Scanner(System.in);
	private static final Class cl = Equivalence.class;
	private static Field fieldSsJac;
	private static Field fieldRep;
	private static Field fieldVersion;

	private static final String[] NOMS_METHODE = { "Equivalence(Ensemble)","contient(Couple c)",
			"ajouter(Couple)", "Equivalence(Relation)", 
			"classe", "enlever(Couple c)", "nbreClasses", "quotient" };

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		fieldSsJac = cl.getDeclaredField("sousJac");
		fieldSsJac.setAccessible(true);
		fieldRep = cl.getDeclaredField("tabRep");
		fieldRep.setAccessible(true);
		fieldVersion = cl.getDeclaredField("numVersion");
		fieldVersion.setAccessible(true);
		System.out.println("*****************************************");
		System.out.println("Programme Test pour la classe Equivalence");
		System.out.println("*****************************************");
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
				testOK = testEquivalenceEns();
				break;
			case 2:
				testOK = testContientCouple();
				break;
			case 3:
				testOK = testAjouterCouple();
				break;
			case 4:
				testOK = testEquivalenceRel();
				break;
			case 5:
				testOK = testClasse();
				break;
			case 6:
				testOK = testEnleverCouple();
				break;
			case 7:
				testOK = testNbreClasses();
				break;
			case 8:
				testOK = testQuotient();
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

	private static boolean testEquivalenceEns()
			throws IllegalArgumentException, IllegalAccessException {
		boolean testOK = true;
		Ensemble ens = null;
		try {
			new Equivalence(ens);
			testOK = false;
			System.out
					.println("Lde paramètre est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}

		ens = new Ensemble("3,7,8,13,16,23,34");
		Equivalence eq = new Equivalence(ens);
		Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
		if (sousJac == null) {
			testOK = false;
			System.out
					.println("L'ensemble sous-jacent n'a pas été initialisé.");
		} else if (sousJac == ens) {
			testOK = false;
			System.out
					.println("Vous avez copié l'adresse mémoire de l'ensemble sous-jacent dans l'attribut.");
		} else if (!sousJac.equals(ens)) {
			testOK = false;
			System.out
					.println("L'ensemble sous-jacent ne contient pas les bons éléments.");
		}
		Elt[] tabRep = (Elt[]) fieldRep.get(eq);
		if (tabRep == null) {
			testOK = false;
			System.out
					.println("Vous n'avez pas initialisé la table des représentants.");
		} else if (tabRep.length != Elt.MAXELT.val() + 1) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas la bonne dimension");
		} else {
			for (Elt e : ens) {
				if (!tabRep[e.val()].equals(e)) {
					testOK = false;
					System.out
							.println("La table des représentants n'a pas été bien initialisée.");
					break;
				}
			}
			if (testOK) {
				Ensemble ensComp = ens.clone();
				ensComp.complementer();
				for (Elt e : ensComp) {
					if (tabRep[e.val()] != null) {
						testOK = false;
						System.out
								.println("La table des représentants n'a pas été bien initialisée.");
						break;
					}
				}
			}

		}
		if (testOK) {
			ens = new Ensemble();
			eq = new Equivalence(ens);
			sousJac = (Ensemble) fieldSsJac.get(eq);
			if (sousJac == null) {
				testOK = false;
				System.out
						.println("L'ensemble sous-jacent n'a pas été initialisé.");
			} else if (sousJac == ens) {
				testOK = false;
				System.out
						.println("Vous avez copié l'adresse mémoire de l'ensemble sous-jacent dans l'attribut.");
			} else if (!sousJac.equals(ens)) {
				testOK = false;
				System.out
						.println("L'ensemble sous-jacent ne contient pas les bons éléments.");
			}
			tabRep = (Elt[]) fieldRep.get(eq);
			if (tabRep == null) {
				testOK = false;
				System.out
						.println("Vous n'avez pas initialisé la table des représentants.");
			} else if (tabRep.length != Elt.MAXELT.val() + 1) {
				testOK = false;
				System.out
						.println("La table des représentants n'a pas la bonne dimension");
			} else {

				Ensemble ensComp = ens.clone();
				ensComp.complementer();
				for (Elt e : ensComp) {
					if (tabRep[e.val()] != null) {
						testOK = false;
						System.out
								.println("La table des représentants n'a pas été bien initialisée.");
						break;
					}
				}
			}
		}

		return testOK;
	}
	
	private static boolean testContientCouple()
			throws IllegalAccessException {

		boolean testOK = true;
		Equivalence eq = createDg1();
		try {
			eq.contient(null);
			testOK = false;
			System.out.println("On a appelé la méthode avec un paramètre null.");
			System.out.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
		}
		
		
		Elt eD = new Elt(1);
		Elt eA = new Elt(2);
		Couple c = new Couple(eD, eA);
		int versionAv = fieldVersion.getInt(eq);
		try {	
			eq.contient(c);
			testOK = false;
			System.out.println("On a appelé la méthode contient sur un couple dont l'élément de départ n'est pas dans l'ensmble sous-jacent.");
			System.out.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e){
			
		}
		eD = new Elt(4);
		eA = new Elt(7);
		c = new Couple(eD, eA);
		try {	
			eq.contient(c);
			testOK = false;
			System.out.println("On a appelé la méthode contient sur un couple dont l'élément d'arrivée n'est pas dans l'ensmble sous-jacent.");
			System.out.println("-->Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e){
			
		}
		eD = new Elt(18);
		eA = new Elt(21);
		c = new Couple(eD, eA);
		if (eq.contient(c)) {
			System.out
					.println("On a créé une équivalence à partir de la relation dg1.");
			System.out.println("Elle ne devrait pas contenir le couple " + c
					+ ".");
			System.out.println("Votre méthode dit qu'elle le contient.");
		}
		int versionAp = fieldVersion.getInt(eq);
		if (versionAv != versionAp) {
			testOK = false;
			System.out
					.println("La méthode contient ne modifie pas la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer le numéro de version.");
		}
		eD = new Elt(18);
		eA = new Elt(29);
		c = new Couple(eD, eA);
		if (!eq.contient(c)) {
			testOK = false;
			System.out
					.println("On a créé une équivalence à partir de la relation dg1.");
			System.out
					.println("Elle devrait contenir le couple " + c + ".");
			System.out.println("Votre méthode dit qu'elle ne le contient pas.");
		}
		return testOK;
	}

	private static boolean testAjouterCouple() throws IllegalArgumentException,
			IllegalAccessException {
		boolean testOK = true;
		Ensemble ens = new Ensemble("3,7,8,13,16,23,34,35,36,37,40");
		Ensemble copie = ens.clone();
		Equivalence eq = createEqEns(ens);
		Elt eD = new Elt(5);
		Elt eA = new Elt(13);
		Couple cAj = new Couple(eD, eA);
		int versionAvantAjout = fieldVersion.getInt(eq);
		try {
			eq.ajouter(null);
			testOK = false;
			System.out
					.println("Le couple passé en paramètre est null.");
			System.out.println("Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionApresAjout = fieldVersion.getInt(eq);
			if (versionAvantAjout != versionApresAjout) {
				testOK = false;
				System.out
						.println("On essayé d'ajouter un couple null.");
				System.out.println("L'équivalence n'a donc pas été modidiée.");
				System.out
						.println("Il ne fallait donc pas changer le numéro de version.");
			}
		}
		try {
			eq.ajouter(cAj);
			testOK = false;
			System.out
					.println("Le point de départ du couple n'appartient pas à l'ensemble sous-jacent de l'équivalence");
			System.out.println("Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionApresAjout = fieldVersion.getInt(eq);
			if (versionAvantAjout != versionApresAjout) {
				testOK = false;
				System.out
						.println("On essayé d'ajouter un couple dont l'origine n'appartient pas à l'ensemble sous-jacent de l'équivalence.");
				System.out.println("L'équivalence n'a donc pas été modidiée.");
				System.out
						.println("Il ne fallait donc pas changer le numéro de version.");
			}
		}
		eq = createEqEns(ens);
		eD = new Elt(3);
		eA = new Elt(17);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		try {
			eq.ajouter(cAj);
			testOK = false;
			System.out
					.println("Le point d'arrivée du couple n'appartient pas à l'ensemble sous-jacent de l'équivalence");
			System.out.println("Il fallait lancer une IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionApresAjout = fieldVersion.getInt(eq);
			if (versionAvantAjout != versionApresAjout) {
				testOK = false;
				System.out
						.println("On essayé d'ajouter un couple dont l'arrivée n'appartient pas à l'ensemble sous-jacent de l'équivalence.");
				System.out.println("L'équivalence n'a donc pas été modidiée.");
				System.out
						.println("Il ne fallait donc pas changer le num�ro de version.");
			}
		}
		eq = createEqEns(ens);
		eD = new Elt(3);
		eA = new Elt(16);

		cAj = new Couple(eD, eA);
		copie.enlever(eD);
		copie.enlever(eA);
		Ensemble classeDe16 = new Ensemble(eD);
		versionAvantAjout = fieldVersion.getInt(eq);
		eq.ajouter(cAj); // ajout de 3-->16
		int versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout == versionApresAjout) {
			testOK = false;
			System.out.println("Le numéro de version n'a pas été mise à jour.");
		}
		classeDe16.ajouter(eA); // classeDe16 = {3,16}
		Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
		if (!sousJac.equals(ens)) {
			testOK = false;
			System.out.println("L'ensemble sous-jacent a été modifié.");
		}
		Elt[] tabRep = (Elt[]) fieldRep.get(eq);
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("La table des représentants n'a pas été bien mise à jour.");
				break;
			}
		}
		Elt repEA = tabRep[eA.val()];
		if (!classeDe16.contient(repEA)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe16) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("1");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		if (!testOK)
			return testOK;
		eD = new Elt(34);
		eA = new Elt(16);
		copie.enlever(eD);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		eq.ajouter(cAj); // ajout de 34-->16
		versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout == versionApresAjout) {
			testOK = false;
			System.out.println("Le numéro de version n'a pas été mis à jour.");
		}
		classeDe16.ajouter(eD);// classeDe16 = {3,16,34}
		if (!sousJac.equals(ens)) {
			testOK = false;
			System.out.println("L'ensemble sous-jacent a été modifié.");
		}
		tabRep = (Elt[]) fieldRep.get(eq);
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("Des éléments ont changé de représentant alors qu'ils ne devaient pas.");
				break;
			}
		}
		repEA = tabRep[eA.val()];
		if (!classeDe16.contient(repEA)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe16) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("2");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		eD = new Elt(23);
		eA = new Elt(37);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		eq.ajouter(cAj);// ajout de 23-->37
		versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout == versionApresAjout) {
			testOK = false;
			System.out.println("Le numéro de version n'a pas été mis à jour.");
		}
		Ensemble classeDe23 = new Ensemble(eD);
		classeDe23.ajouter(eA);// classe de 23 = {23,37}
		copie.enlever(eA);
		copie.enlever(eD);
		tabRep = (Elt[]) fieldRep.get(eq);
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("Des éléments ont changé de représentant alors qu'ils ne devaient pas.");
				break;
			}
		}
		repEA = tabRep[eA.val()];
		if (!classeDe23.contient(repEA)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe23) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("3");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		if (!testOK)
			return testOK;
		Elt rep16 = tabRep[16];
		if (!classeDe16.contient(rep16)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe16) {
				if (!rep16.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("4");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		if (!testOK)
			return testOK;
		eD = new Elt(7);
		eA = new Elt(37);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		eq.ajouter(cAj);// ajout de 7-->37
		versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout == versionApresAjout) {
			testOK = false;
			System.out.println("Le numéro de version n'a pas été mis à jour.");
		}
		classeDe23.ajouter(eD); // classe de 23 = {7,23,37}
		copie.enlever(eA);
		copie.enlever(eD);
		tabRep = (Elt[]) fieldRep.get(eq);
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("Des éléments ont changé de représentant alors qu'ils ne devaient pas.");
				break;
			}
		}
		repEA = tabRep[eA.val()];
		if (!classeDe23.contient(repEA)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe23) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("5");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		if (!testOK)
			return testOK;
		rep16 = tabRep[16];
		if (!classeDe16.contient(rep16)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe16) {
				if (!rep16.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("6");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		if (!testOK)
			return testOK;
		eD = new Elt(13);
		eA = new Elt(37);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		eq.ajouter(cAj);// ajout de 13-->37
		versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout == versionApresAjout) {
			testOK = false;
			System.out.println("Le numéro de version n'a pas été mis à jour.");
		}
		classeDe23.ajouter(eD); // classe de 23 = {7,13,23,37}
		copie.enlever(eD);
		tabRep = (Elt[]) fieldRep.get(eq);
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("Des éléments ont changé de représentant alors qu'ils ne devaient pas.");
				break;
			}
		}
		repEA = tabRep[eA.val()];
		if (!classeDe23.contient(repEA)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe23) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("7");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		if (!testOK)
			return testOK;
		rep16 = tabRep[16];
		if (!classeDe16.contient(rep16)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe16) {
				if (!rep16.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("8");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}

		eD = new Elt(23);
		eA = new Elt(16);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		;
		eq.ajouter(cAj);// ajout de 23-->16
		versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout == versionApresAjout) {
			testOK = false;
			System.out.println("Le numéro de version n'a pas été mis à jour.");
		}
		classeDe16.ajouter(classeDe23); // classe de 16 = {3,7,13,16,23,34,37}
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("Des éléments ont changé de représentant alors qu'ils ne devaient pas.");
				break;
			}
		}
		repEA = tabRep[eA.val()];
		if (!classeDe16.contient(repEA)) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas été bien mise à jour.");
		} else {
			for (Elt e : classeDe16) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out.println("9");
					System.out
							.println("Des éléments devant appartenir à la même classe n'ont pas le même représentant.");
					break;
				}
			}
		}
		eD = new Elt(37);
		eA = new Elt(13);
		cAj = new Couple(eD, eA);
		versionAvantAjout = fieldVersion.getInt(eq);
		eq.ajouter(cAj);// ajout de 23-->16
		versionApresAjout = fieldVersion.getInt(eq);
		if (versionAvantAjout != versionApresAjout) {
			testOK = false;
			System.out
					.println("On a essayé d'ajouter une flêche qui se trouvait déjà dans l'équivalence.");
			System.out
					.println("Il ne fallait dont pas mettre à jour le numéro de version.");
		}
		classeDe16.ajouter(classeDe23); // classe de 16 = {3,7,13,16,23,34,37}
		for (Elt e : copie) {
			if (!tabRep[e.val()].equals(e)) {
				testOK = false;
				System.out
						.println("Des éléments ont changé de représentant alors qu'ils ne devaient pas.");
				break;
			}
		}
		repEA = tabRep[eA.val()];
		if (!classeDe16.contient(repEA)) {
			testOK = false;
			System.out
					.println("On a essayé d'ajouter une flêche qui se trouvait déjà dans l'équivalence.");
			System.out
					.println("La table des représentants a été mofifiée alors qu'elle ne devait pas l'être.");
		} else {
			for (Elt e : classeDe16) {
				if (!repEA.equals(tabRep[e.val()])) {
					testOK = false;
					System.out
							.println("On a essayé d'ajouter une flêche qui se trouvait déjà dans l'équivalence.");
					System.out
							.println("La table des représentants a été mofifiée alors qu'elle ne devait pas l'être.");
					break;
				}
			}
		}
		return testOK;
	}

	private static boolean testEquivalenceRel() throws IllegalAccessException {
		boolean testOK = true;
		Relation rel = null;
		try {
			new Equivalence(rel);
			testOK = false;
			System.out
					.println("Lde paramêtre est null --> Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException e) {

		}
		try {
			new Equivalence(Io.chargerRelation("re1.rel"));
			testOK = false;
			System.out
					.println("On a essayé de créer une équivalence à partir d'une relation dont l'ensemble de départ est différent de l'ensemble d'arrivée.");
			System.out.println("Il fallait lancer une IllegalArgumentException.");
		} catch (IllegalArgumentException m) {

		}
		Ensemble[] quotient = { new Ensemble("{2}"), new Ensemble("{4}"),
				new Ensemble("{6,18,19,20,29}"),
				new Ensemble("8,10,21,31,33,35"), new Ensemble("12,14,22,23"),
				new Ensemble("{16}"), new Ensemble("{17}"),
				new Ensemble("{25,27}"), new Ensemble("{24,37,39}") };
		Relation dg1 = Io.chargerRelation("dg1.rel");
		Relation copie = dg1.clone();
		Equivalence eq = new Equivalence(dg1);

		if (!dg1.depart().equals(copie.depart())
				|| !dg1.arrivee().equals(copie.arrivee())) {
			System.out
					.println("L'ensemble de départ ou d'arrivée de la relation passée en paramètre a été modifiée.");
			testOK = false;
		}
		for (Elt eD : dg1.depart()) {
			for (Elt eA : dg1.arrivee()) {

				if (dg1.contient(eD, eA) != copie.contient(eD, eA)) {
					testOK = false;
					System.out
							.println("La relation passée en paramètre au constructeur a été mofifiée.");
					break;
				}
			}
		}
		Ensemble sousJac = (Ensemble) fieldSsJac.get(eq);
		if (sousJac == null) {
			testOK = false;
			System.out
					.println("L'ensemble sous-jacent n'a pas été initialisé.");
		} else if (!sousJac.equals(copie.depart())) {
			testOK = false;
			System.out
					.println("L'ensemble sous-jacent ne contient pas les bons éléments.");
		}
		Elt[] tabRep = (Elt[]) fieldRep.get(eq);
		if (tabRep == null) {
			testOK = false;
			System.out
					.println("Vous n'avez pas initialisé la table des représentants.");
		} else if (tabRep.length != Elt.MAXELT.val() + 1) {
			testOK = false;
			System.out
					.println("La table des représentants n'a pas la bonne dimension");
		} else {
			for (Ensemble cl : quotient) {
				Elt x = cl.unElement();
				Elt rep = tabRep[x.val()];
				if (!cl.contient(rep)) {
					testOK = false;
					System.out
							.println("On a créé une relation d'équivalence à partir de dg1.");
					System.out.println("La classe d'équivalence de " + x
							+ " devrait être " + cl + ".");
					System.out.println("Le représentant de " + x
							+ " dans votre relation d'équivalence est " + rep);
					System.out.println(rep + " n'appartient pas à " + cl + ".");
					break;
				}
				for (Elt e : cl) {
					if (!tabRep[e.val()].equals(rep)) {
						testOK = false;
						System.out
								.println("On a créé une relation d'équivalence à partir de dg1.");
						System.out
								.println(x
										+ " "
										+ e
										+ " devraient être dans la même classe d'équivalence et avoir le même représentant.");
						System.out
								.println("Représentant de " + x + " : " + rep);
						System.out.println("Représentant de " + e + " : "
								+ tabRep[e.val()]);
						break;
					}
				}
				if (!testOK)
					break;
			}
			if (testOK) {
				EnsembleAbstrait ensComp = copie.depart();
				ensComp.complementer();
				for (Elt e : ensComp) {
					if (tabRep[e.val()] != null) {
						testOK = false;
						System.out
								.println("La table des représentants n'a pas été bien initialisée.");
						break;
					}
				}
			}

		}		
		return testOK;
	}

	private static boolean testClasse() throws IllegalAccessException {

		boolean testOK = true;
		Ensemble[] quotient = { new Ensemble("{2}"), new Ensemble("{4}"),
				new Ensemble("{6,18,19,20,29}"),
				new Ensemble("8,10,21,31,33,35"), new Ensemble("12,14,22,23"),
				new Ensemble("{16}"), new Ensemble("{17}"),
				new Ensemble("{25,27}"), new Ensemble("{24,37,39}") };
		Equivalence eq = createDg1();
		int versionAv = fieldVersion.getInt(eq);
		Elt e = new Elt(5);
		try {
			eq.classe(e);
			testOK = false;
			System.out
					.println(e
							+ " n'appartient pas à l'ensemble sous-jacent de l'équivalence créée à partir de la relation dg1.");
			System.out.println("Il fallait lancre une IllegalArgumentException.");
		} catch (IllegalArgumentException m) {

		}
		int versionAp = fieldVersion.getInt(eq);
		if (versionAv != versionAp) {
			testOK = false;
			System.out
					.println("La méthode classe ne modifie pas la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer le numéro de version.");
		}
		for (Ensemble cl : quotient) {
			for (Elt eCl : cl) {
				EnsembleAbstrait classeECl = eq.classe(eCl);
				if (!classeECl.equals(cl)) {
					System.out
							.println("On a créé une relation d'équivalence à partir de la relation dg1.");
					System.out.println("La classe de " + eCl
							+ " pour cette équivalente devrait être " + cl
							+ ".");
					System.out
							.println("Votre méthode a renvoyé : " + classeECl);
					testOK = false;
					break;
				}
			}
			if (!testOK)
				break;
		}
		versionAp = fieldVersion.getInt(eq);
		if (versionAv != versionAp) {
			testOK = false;
			System.out
					.println("La méthode classe ne modifie pas la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer le numéro de version.");
		}
		return testOK;
	}

	private static boolean testEnleverCouple() throws IllegalAccessException {

		boolean testOK = true;
		Equivalence eq = createDg1();
		int versionAv = fieldVersion.getInt(eq);
		try {
			eq.enlever(null);
			testOK = false;
			System.out
					.println("On a appelé la méthode enlever avec un paramètre null.");
			System.out.println("Il fallait lancer de IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionAp = fieldVersion.getInt(eq);
			if (versionAp != versionAv) {
				testOK = false;
				System.out
						.println("On a appelé la méthode enlever dans un cas où une IllegalArgumentException est lancée.");
				System.out
						.println("Il ne fallait pas changer le numéro de version.");
			}
		}
		Elt eD = new Elt(3);
		Elt eA = new Elt(8);
		Couple cEnl = new Couple(eD, eA);
		try {
			eq.enlever(cEnl);
			testOK = false;
			System.out
					.println("On essaie d'enlever un couple dont le point de départ n'est pas dans l'ensemble sous-jacent.");
			System.out.println("Il fallait lancer de IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionAp = fieldVersion.getInt(eq);
			if (versionAp != versionAv) {
				testOK = false;
				System.out
						.println("On a appelé la méthode enlever dans un cas où une IllegalArgumentException est lancée.");
				System.out
						.println("Il ne fallait pas changer le numéro de version.");
			}
		}
		versionAv = fieldVersion.getInt(eq);
		eD = new Elt(3);
		eA = new Elt(8);
		cEnl = new Couple(eD, eA);
		try {
			eq.enlever(cEnl);
			testOK = false;
			System.out
					.println("On essaie d'enlever un couple dont le point d'arrivée n'est pas dans l'ensemble sous-jacent.");
			System.out.println("Il fallait lancer de IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionAp = fieldVersion.getInt(eq);
			if (versionAp != versionAv) {
				testOK = false;
				System.out
						.println("On a appelé la méthode enlever dans un cas où une IllegalArgumentException est lancée.");
				System.out
						.println("Il ne fallait pas changer le numéro de version.");
			}
		}
		versionAv = fieldVersion.getInt(eq);
		eD = new Elt(20);
		eA = new Elt(19);
		cEnl = new Couple(eD, eA);
		try {
			eq.enlever(cEnl);
			testOK = false;
			System.out
					.println("On essaie d'enlever la flêche (20,19) de la relation d'équivalence crée à partir de la relation dg1.");
			System.out.println("Il fallait lancer de IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionAp = fieldVersion.getInt(eq);
			if (versionAp != versionAv) {
				testOK = false;
				System.out
						.println("On a appelé la méthode enlever dans un cas où une IllegalArgumentException est lancée.");
				System.out
						.println("Il ne fallait pas changer le numéro de version.");
			}
		}
		versionAv = fieldVersion.getInt(eq);
		eD = new Elt(2);
		eA = new Elt(2);
		cEnl = new Couple(eD, eA);
		try {
			eq.enlever(cEnl);
			testOK = false;
			System.out
					.println("On essaie d'enlever la flêche (2,2) de la relation d'équivalence crée à partor de la relation dg1.");
			System.out.println("Il fallait lancer de IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionAp = fieldVersion.getInt(eq);
			if (versionAp != versionAv) {
				testOK = false;
				System.out
						.println("On a appelé la méthode enlever dans un cas où une IllegalArgumentException est lancée.");
				System.out
						.println("Il ne fallait pas changer le numéro de version.");
			}
		}
		eD = new Elt(27);
		eA = new Elt(27);
		cEnl = new Couple(eD, eA);
		try {
			eq.enlever(cEnl);
			testOK = false;
			System.out
					.println("On essaie d'enlever la flêche(27,27) de la relation d'équivalence crée à partir de la relation dg1.");
			System.out.println("Il fallait lancer de IllegalArgumentException");
		} catch (IllegalArgumentException m) {
			int versionAp = fieldVersion.getInt(eq);
			if (versionAp != versionAv) {
				testOK = false;
				System.out
						.println("On a appelé la méthode enlever dans un cas où une IllegalArgumentException est lancée.");
				System.out
						.println("Il ne fallait pas changer le numéro de version.");
			}
		}
		Elt[] repAv = (Elt[]) fieldRep.get(eq);
		Ensemble sousJacAv = (Ensemble) fieldSsJac.get(eq);
		versionAv = fieldVersion.getInt(eq);
		eD = new Elt(17);
		eA = new Elt(18);
		cEnl = new Couple(eD, eA);
		eq.enlever(cEnl);
		int versionAp = fieldVersion.getInt(eq);
		if (versionAp != versionAv) {
			testOK = false;
			System.out
					.println("On a appelé la méthode enlever avec en paramètre un couple n'appartenant pas à la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer le numéro de version.");
		}
		Ensemble sousJacAp = (Ensemble) fieldSsJac.get(eq);
		if (!sousJacAp.equals(sousJacAv)) {
			testOK = false;
			System.out
					.println("On a appelé la méthode enlever avec en paramètre un couple n'appartenant pas à la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer l'ensemble sous-jacent.");
		}
		Elt[] repAp = (Elt[]) fieldRep.get(eq);
		if (repAv.length != repAp.length) {
			testOK = false;
			System.out
					.println("La méthode enlever ne doit pas changer la taille de la table des représentants.");
		} else {
			for (Elt e : sousJacAp) {
				if (!repAv[e.val()].equals(repAp[e.val()])) {
					testOK = false;
					System.out
							.println("On a appelé la méthode enlever avec en paramètre un couple n'appartenant pas à la relation d'équivalence.");
					System.out
							.println("Il ne fallait pas changer la table des représentants.");
					break;
				}
			}
			if (testOK) {
				Ensemble compl = sousJacAv.clone();
				compl.complementer();
				for (Elt e : compl) {
					if (repAp[e.val()] != null) {
						testOK = false;
						System.out
								.println("On a appelé la méthode enlever avec en paramètre un couple n'appartenant pas à la relation d'équivalence.");
						System.out
								.println("Il ne fallait pas changer la table des représentants.");
						break;
					}
				}
			}
		}
		repAv = (Elt[]) fieldRep.get(eq);
		sousJacAv = (Ensemble) fieldSsJac.get(eq);
		versionAv = fieldVersion.getInt(eq);
		eD = new Elt(25);
		eA = new Elt(27);
		cEnl = new Couple(eD, eA);
		eq.enlever(cEnl);
		versionAp = fieldVersion.getInt(eq);
		if (versionAv == versionAp) {
			testOK = false;
			System.out
					.println("On a enlevé de la relation d'équivalence un couple qui pouvait l'être.");
			System.out
					.println("Il fallait mettre à jour le numéro de version.");
		}
		sousJacAp = (Ensemble) fieldSsJac.get(eq);
		if (!sousJacAp.equals(sousJacAv)) {
			testOK = false;
			System.out
					.println("On a enlevé un couple de la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas modifier l'ensemble sous-jacent.");
		}
		repAp = (Elt[]) fieldRep.get(eq);
		if (repAv.length != repAp.length) {
			testOK = false;
			System.out
					.println("La méthode enlever ne doit pas changer la taille de la table des représentants.");
		} else {
			Ensemble ensemble = sousJacAp.clone();
			ensemble.enlever(eD);
			ensemble.enlever(eA);
			for (Elt e : ensemble) {
				if (!repAv[e.val()].equals(repAp[e.val()])) {
					testOK = false;
					System.out
							.println("On a enlevé le couple ("
									+ eD
									+ ","
									+ eA
									+ ") de la relation d'équivalence créée à partir de la relation dg1.");
					System.out
							.println("Il ne fallait pas changer le représenant de l'élément "
									+ e + ".");
					break;
				}
			}
			if (!repAp[eD.val()].equals(eD)) {
				testOK = false;
				System.out
						.println("On a enlevé le couple ("
								+ eD
								+ ","
								+ eA
								+ ") de la relation d'équivalence créée à partir de la relation dg1.");
				System.out.println("Le nouveau représentant de " + eD
						+ " devrait être " + eD);
				System.out.println("Dans votre table, le représentant de " + eD
						+ " est " + repAp[eD.val()]);
			}
			if (!repAp[eA.val()].equals(eA)) {
				testOK = false;
				System.out
						.println("On a enlever le couple ("
								+ eD
								+ ","
								+ eA
								+ ") de la relation d'équivalence créée à partir de la relation dg1.");
				System.out.println("Le nouveau représentant de " + eA
						+ " devrait être " + eA);
				System.out.println("Dans votre table, le représentant de " + eA
						+ " est " + repAp[eA.val()]);
			}
			if (testOK) {
				Ensemble compl = sousJacAv.clone();
				compl.complementer();
				for (Elt e : compl) {
					if (repAp[e.val()] != null) {
						testOK = false;
						System.out
								.println("On a enlevé le couple ("
										+ eD
										+ ","
										+ eA
										+ ") de la relation d'équivalence créée à partir de la relation dg1.");
						System.out.println("Le représentant de " + e
								+ " n'est plus null.");
						break;
					}
				}
			}
		}
		return testOK;
	}

	private static boolean testNbreClasses() throws IllegalAccessException {

		boolean testOK = true;
		Equivalence eq = createDg1();
		int versionAv = fieldVersion.getInt(eq);
		int nbreClasses = eq.nbreClasses();
		if (nbreClasses != 9) {
			System.out
					.println("L'équivalence créée à partir de la relation dg1 devrait y avoir 9 classes.");
			System.out.println("Votre méthode a renvoyé : " + nbreClasses);
		}
		int versionAp = fieldVersion.getInt(eq);
		if (versionAv != versionAp) {
			testOK = false;
			System.out
					.println("La méthode nbreClasses ne modifie pas la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer le numéro de version.");
		}
		return testOK;
	}

	private static boolean testQuotient() throws IllegalAccessException {

		boolean testOK = true;
		Ensemble[] quotient = { new Ensemble("{2}"), new Ensemble("{4}"),
				new Ensemble("{6,18,19,20,29}"),
				new Ensemble("8,10,21,31,33,35"), new Ensemble("12,14,22,23"),
				new Ensemble("{16}"), new Ensemble("{17}"),
				new Ensemble("{25,27}"), new Ensemble("{24,37,39}") };
		Equivalence eq = createDg1();
		int versionAv = fieldVersion.getInt(eq);
		Ensemble sousJacAv = ((Ensemble) fieldSsJac.get(eq)).clone();
		EnsembleAbstrait[] quot = eq.quotient();
		if (quot.length != 9) {
			System.out
					.println("La relation d'équivalence créée à partir possèdent 9 classes.");
			System.out
					.println("Le tableau renvoyé par la méthode quotient devrait dans ce cas être de taille 9");
			System.out
					.println("La taille du tableau renvoyé par votre méthode est "
							+ quot.length);
			testOK = false;
		} else {
			for (EnsembleAbstrait qTrouve : quot) {
				boolean ok = false;
				for (Ensemble qAtt : quotient) {
					if (qTrouve.equals(qAtt)) {
						ok = true;
						break;
					}
				}
				if (!ok) {
					System.out
							.println("On a demandé le quotient pour la relation d'équivalence créée à partir de dg1.");
					System.out
							.println("Vous avez renvoyé comme classe "
									+ qTrouve
									+ " qui n'est pas une classe de cette équivalence.");
					testOK = false;
					break;
				}
			}
			if (testOK) {
				for (Ensemble qAtt : quotient) {
					boolean ok = false;
					for (EnsembleAbstrait qTrouve : quot) {
						if (qTrouve.equals(qAtt)) {
							ok = true;
							break;
						}
					}
					if (!ok) {
						System.out
								.println("On a demandé le quotient pour la relation d'équivalence créée à partir de dg1.");
						System.out
								.println("Votre tableau ne contient pas la classe "
										+ qAtt + ".");
						testOK = false;
						break;
					}
				}
			}
		}
		int versionAp = fieldVersion.getInt(eq);
		if (versionAv != versionAp) {
			testOK = false;
			System.out
					.println("La méthode quotient ne modifie pas la relation d'équivalence.");
			System.out
					.println("Il ne fallait pas changer le numéro de version.");
		}
		Ensemble sousJacAp = ((Ensemble) fieldSsJac.get(eq)).clone();
		if (!sousJacAv.equals(sousJacAp)) {
			testOK = false;
			System.out
					.println("La méthode quotient a modifié l'ensemble sous-jacent de la relation.");
		}
		return testOK;
	}


	private static Equivalence createDg1(){
		Ensemble ens = new Ensemble("{2,4,6,8,10,12,14,16..25,27,29,31,33,35,37,39}");
		Equivalence eq = new Equivalence(ens);
		try {
			fieldSsJac.set(eq, ens.clone());
			Elt[] tabRep = new Elt[Elt.MAXELT.val()+1];
			tabRep[2] = new Elt(2);
			tabRep[4] = new Elt(4);
			tabRep[16] = new Elt(16);
			tabRep[17] = new Elt(17);
			tabRep[18] = new Elt(20);
			tabRep[19] = new Elt(20);
			tabRep[20] = new Elt(20);
			tabRep[29] = new Elt(20);
			tabRep[6] = new Elt(20);
			tabRep[25] = new Elt(25);
			tabRep[27] = new Elt(25);
			tabRep[8] = new Elt(10);
			tabRep[10] = new Elt(10);
			tabRep[21] = new Elt(10);
			tabRep[31] = new Elt(10);
			tabRep[33] = new Elt(10);
			tabRep[35] = new Elt(10);
			tabRep[12] = new Elt(22);
			tabRep[14] = new Elt(22);
			tabRep[22] = new Elt(22);
			tabRep[23] = new Elt(22);
			tabRep[24] = new Elt(24);
			tabRep[37] = new Elt(24);
			tabRep[39] = new Elt(24);
			fieldRep.set(eq, tabRep);
		} catch (IllegalArgumentException | IllegalAccessException e) {
	
		}
		return eq;
	}
	private static Equivalence createEqEns(Ensemble ens){
		Equivalence eq = new Equivalence(ens);
		try {
			fieldSsJac.set(eq, ens.clone());
			Elt[] tabRep = new Elt[Elt.MAXELT.val()+1];
			for (Elt e : ens) tabRep[e.val()] = e;
			fieldRep.set(eq, tabRep);
		} catch (IllegalArgumentException e) {


		} catch (IllegalAccessException e) {

		}
		return eq;

	}

}
