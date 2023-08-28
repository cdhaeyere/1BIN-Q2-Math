/**
 * Classe Io
 * Classe de lecture au clavier et de chargement de fichiers de données.
 *
 * @author M.Marchand
 * @version Mars 2008.
 */

import java.io.*;
import java.util.*;


public class Io {
    private static Scanner scanner = new Scanner(System.in);

    public Io() {
    }


    public static Elt lireElt() {
        int i = 1;
        boolean flag = false;
        do {
            flag = true;
            String str = scanner.nextLine();
            if (str.toUpperCase().equals("MAXELT"))
                return new Elt(Elt.MAXELT.val());
            try {
                i = Integer.parseInt(str);
                if ((i < 1) || (i > Elt.MAXELT.val())) {
                    System.out.println("Hors Univers (1.." + Elt.MAXELT.val() + ") : " + i);
                    System.out.println("Recommencez SVP.");
                    flag = false;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Entier incorrect. Recommencez SVP.");
                flag = false;
            }
        } while (!flag);
        return new Elt(i);
    } // lireElt()

    public static Ensemble lireEnsemble() {
        System.out.print("{");
        Ensemble ensemble = null;
        String s = "";
        boolean flag;
        do {
            flag = true;
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
            try {
                s = bufferedreader.readLine();
            } catch (IOException ioexception) {
            }
            try {
                ensemble = new Ensemble(s);
            } catch (MathException mathexception) {
                System.out.println(mathexception + "\nRecommencez SVP");
                flag = false;
            }
        } while (!flag);
        return ensemble;
    } // lireEnsemble()


    public static Relation lireRelation() {
        System.out.println("Lecture d'une relation");

        System.out.print("Ensemble de départ ? ");
        EnsembleAbstrait d = Io.lireEnsemble();
        System.out.print("Ensemble d'arrivée ? ");
        EnsembleAbstrait a = Io.lireEnsemble();
        Relation relation = new Relation(d, a);
        System.out.println("(Source = 0 pour quitter)");
        System.out.print("Source ? ");
        int i = 0;
        boolean flag;
        do {
            flag = true;
            String str = scanner.nextLine();
            if (str.toUpperCase().equals("MAXELT"))
                i = Elt.MAXELT.val();
            else
                try {
                    i = Integer.parseInt(str);
                    if (i == 0)
                        return relation;
                    if ((i < 1) || (i > Elt.MAXELT.val())) {
                        System.out.println("Hors Univers (1.." + Elt.MAXELT.val() + ") : " + i);
                        System.out.println("Recommencez SVP.");
                        flag = false;
                    }
                    if (!d.contient(new Elt(i))) {
                        System.out.println("En dehors de l'ensemble de départ");
                        System.out.println("Recommencez SVP.");
                        flag = false;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Entier incorrect. Recommencez SVP.");
                    flag = false;
                }

        } while (!flag);
        while (i != 0) {

            do {
                flag = true;
                System.out.print("  Buts : ");
                EnsembleAbstrait b = Io.lireEnsemble();

                if (!b.inclusDans(a)) {
                    System.out.println("Au moins un des éléments entrés n'appartient pas à l'ensemble d'arrivée");
                    System.out.println("Recommencez SVP.");
                    flag = false;
                } else {
                    Iterator<Elt> it = b.iterator();
                    while (it.hasNext()) relation.ajouter(new Elt(i), it.next());

                }
            } while (!flag);
            System.out.print("Source ? ");
            do {
                flag = true;
                String str = scanner.nextLine();
                if (str.toUpperCase().equals("MAXELT"))
                    i = Elt.MAXELT.val();
                else
                    try {
                        i = Integer.parseInt(str);
                        if (i == 0)
                            return relation;
                        if ((i < 1) || (i > Elt.MAXELT.val())) {
                            System.out.println("Hors Univers (1.." + Elt.MAXELT.val() + ") : " + i);
                            System.out.println("Recommencez SVP.");
                            flag = false;
                        }
                        if (!d.contient(new Elt(i))) {
                            System.out.println("En dehors de l'ensemble de départ");
                            System.out.println("Recommencez SVP.");
                            flag = false;
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Entier incorrect. Recommensez SVP.");
                        flag = false;
                    }
            } while (!flag);
        }

        return relation;
    }


    private static String lireFichier(String s) {
        char ac[] = {
                ' '
        };
        try {
            File file = new File(s);
            long l = file.length();
            ac = new char[(int) l];
            try (FileReader filereader = new FileReader(file)) {
                for (int i = 0; (long) i < l; i += filereader.read(ac, i, (int) l - i)) ;
            }
        } catch (IOException ioexception) {
            System.err.println("Erreur du chargement fichier : " + ioexception.toString());
        }
        return new String(ac);
    }

    private static String extrEns1(String s) {
        return s.substring(0, s.indexOf("}") + 1);
    }

    private static String extrReste1(String s) {
        return s.substring(s.indexOf("}") + 1, s.length());
    }

    public static Relation chargerRelation(String nom) {
        String w = lireFichier(nom);
        w = w.substring(0, w.indexOf("$")); // pour supprimer $<enter>
        EnsembleAbstrait dep = new Ensemble(extrEns1(w));
        w = extrReste1(w);
        EnsembleAbstrait arr = new Ensemble();
        if (w.charAt(0) == '{') {
            arr = new Ensemble(extrEns1(w));
            w = extrReste1(w);
        } else {
            arr = new Ensemble(dep);
        }
        Relation res = new Relation(dep, arr);
        StringTokenizer stz1 = new StringTokenizer(w, "*", false);
        while (stz1.hasMoreTokens()) {
            String v = stz1.nextToken();
            if (v.equals("r")) res.cloReflex();
            else if (v.equals("s")) res.cloSym();
            else if (v.equals("t")) res.cloTrans();
            else {
                StringTokenizer stz2 = new StringTokenizer(v, ",", false);
                Elt source = new Elt(Integer.parseInt(stz2.nextToken()));
                while (stz2.hasMoreTokens()) {
                    Elt but = new Elt(Integer.parseInt(stz2.nextToken()));
                    res.ajouter(source, but);
                }
            }
        }
        return res;
    }


} // class