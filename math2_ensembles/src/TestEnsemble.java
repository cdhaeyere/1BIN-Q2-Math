import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class TestEnsemble {
	public static final int NB_IMPLEMENTATIONS = 2;
	private final static Scanner scanner = new Scanner(System.in);
	private static final String[] NOMS_METHODES = { "Ensi()","estVide","unElement",
		"contient","ajouter", "enlever", "cardinal","complementer","Ensi(EnsembleInterface)","Ensi(Elt)"};
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException {
		while (true){
			int choix = 0;
			System.out.println("Quelle classe voulez-vous tester ?");
			for (int i = 1; i <= NB_IMPLEMENTATIONS; i++)
				System.out.println(i + ". Ens" + i);
			System.out.println("autre -> Quitter");
			choix = scanner.nextInt();
			Class cls;
			try {
				cls = Class.forName("Ens" + choix + "Test");
			} catch (ClassNotFoundException e) {return;}
			AbstractTest at = (AbstractTest) cls.newInstance();
			while (true) {
				for (int i = 0; i < NOMS_METHODES.length; i++) {
					System.out.println((i + 1) + " -> Test " + NOMS_METHODES[i]);
				}
				System.out.println((NOMS_METHODES.length+1) + "-> Visualiser la cha�ne de caract�res renvoy�e par votre m�thode toString.");
				System.out.println("autre -> revenir au choix de la classe � tester.");
				choix = scanner.nextInt();
				boolean testOK = true;
				switch (choix) {
				case 1:
					testOK = at.testConstructeur();
					break;
				case 2:
					testOK = at.testEstVide();
					break;
				case 3:
					testOK = at.testUnElement();
					break;
				case 4:
					testOK = at.testContient();
					break;
				case 5:
					testOK = at.testAjouter();
					break;
				case 6:
					testOK = at.testEnlever();
					break;
				case 7:
					testOK = at.testCardinal();
					break;
				case 8:
					testOK = at.testComplementer();
					break;
				case 9:
					testOK = at.testConstructeurRecopie();
					break;
				case 10 :
					testOK = at.testConstructeurSingleton();
					break;
				case 11:
					at.visualiserToString();
					continue;		
				}
				if (choix > NOMS_METHODES.length+1 || choix <= 0) break;
				if (testOK)
					System.out.println("Le test de la m�thode " + NOMS_METHODES[choix - 1]
						+ " a r�ussi.");
				else
					System.out.println("Le test de la m�thode " + NOMS_METHODES[choix - 1]
						+ " a �chou�.");
			}
		}
	}
}
