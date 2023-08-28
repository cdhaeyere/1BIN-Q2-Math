/**
 * Classe Ensemble implémentant EnsembleInterface
 * Un ensemble d'Elt est représenté ici par un tableau de
 * booléens, indicé de 1 à MAXELT (l'élément d'indice 0 du tableau
 * existe physiquement, mais est inutilisé).
 *
 * @author M. Marchand
 * @version Fevrier 2008
 */

import java.util.*;

public class Ensemble extends EnsembleAbstrait {

    private int version = 0;
    /** tabB[i] est true si i appartient à l'Ensemble courant */
    private boolean[] tabB;
    /** Cardinal de l'Ensemble courant */
    private int cardinal;

    /** Constructeur de l'Ensemble vide */
    public Ensemble() {
        tabB = new boolean[MAX + 1];
        for (int i = 1; i <= MAX; i++) {
            this.tabB[i] = false;
        }
        this.cardinal = 0;
    }

    /**
     * Constructeur à partir d'une String. Exemples de Strings acceptées :
     * "{1,2,3}" "1 2 3" "1..5,maXelT,1   2" "uNIverS" Génére une (runtime)
     * exception en cas de String incorrecte.
     */
    public Ensemble(String s) {
        this();
        if (s == null)
            throw new IllegalArgumentException(
                    "La chaîne de caractères ne peut pas être null.");
        if (s.toUpperCase().equals("UNIVERS")) {
            for (int i = 1; i <= MAX; i++) {
                this.tabB[i] = true;
            }
            this.cardinal = MAX;
        } else {
            if (s.length() != 0 && s.charAt(0) == '{') {
                s = s.substring(1, s.length());
            }
            if (s.length() != 0 && s.charAt(s.length() - 1) == '}') {
                s = s.substring(0, s.length() - 1);
            }

            StringTokenizer stk = new StringTokenizer(s, "\t ,");
            String token;
            while (stk.hasMoreTokens()) {
                token = stk.nextToken();
                int posPointPoint = token.indexOf("..");
                if (posPointPoint > 0) {
                    String debut = token.substring(0, posPointPoint);
                    String fin = token.substring(posPointPoint + 2,
                            token.length());
                    int debutVal;
                    int finVal;
                    if (debut.toUpperCase().equals("MAXELT"))
                        debutVal = Elt.MAXELT.val();
                    else {
                        try {
                            debutVal = Integer.parseInt(debut);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException(
                                    "Entier non valide : " + debut + " ");
                        }
                    }
                    if (fin.toUpperCase().equals("MAXELT"))
                        finVal = Elt.MAXELT.val();
                    else {
                        try {
                            finVal = Integer.parseInt(fin);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException(
                                    "Enumération non valide : " + token + " ");
                        }
                    }
                    if (finVal >= debutVal)
                        for (int ii = debutVal; ii <= finVal; ii++) {
                            this.ajouter(new Elt(ii));
                        }
                    else {
                        if (debutVal > MAX)
                            throw new MathException("Hors univers : "
                                    + debutVal);
                        for (int ii = debutVal; ii <= MAX; ii++) {
                            this.ajouter(new Elt(ii));
                        }
                        for (int ii = 1; ii <= finVal; ii++) {
                            this.ajouter(new Elt(ii));
                        }

                    }

                } // if posPointPoint > 0
                else {
                    int i;
                    if (token.toUpperCase().equals("MAXELT"))
                        i = Elt.MAXELT.val();
                    else
                        try {
                            i = Integer.parseInt(token);
                        } catch (Exception e) {
                            throw new IllegalArgumentException(
                                    "Nombre entier incorrect : " + token + " ");
                        }

                    this.ajouter(new Elt(i));
                }
            }
        }
    }

    /** Constructeur par recopie */
    public Ensemble(EnsembleInterface e) {
        if (e == null)
            throw new IllegalArgumentException(
                    "Le paramêtre ne peut pas être null.");
        tabB = new boolean[MAX + 1];
        for (int i = 1; i <= MAX; i++) {
            this.tabB[i] = e.contient(new Elt(i));
        }
        this.cardinal = e.cardinal();
    }

    /** Construit le singleton {x} */
    public Ensemble(Elt x) {
        this();
        this.ajouter(x);
    }

    /**
     * Construit l'intervalle [x,y] dans l'Univers circulaire. Par exemple : new
     * Ensemble(Elt.MAXELT,new Elt(3)) construit l'ensemble {MAXELT,1,2,3}
     */
    private Ensemble(Elt x, Elt y) {
        this();
        Elt z = new Elt(y.succ());
        do {
            z = z.pred();
            this.ajouter(z);
        } while (!z.equals(x));
    }

    /** Renvoie un clone de l'Ensemble courant */
    public Ensemble clone() {
        Ensemble ens = (Ensemble) super.clone();
        ens.version++;
        ens.tabB = Arrays.copyOf(this.tabB, MAX + 1);
        return ens;
    }

    /** Renvoie true si l'Ensemble courant est vide */
    public boolean estVide() {
        return (this.cardinal == 0);
    }

    /** Renvoie le cardinal de l'Ensemble courant */
    public int cardinal() {
        return this.cardinal;
    }

    /** Renvoie true ssi e appartient à l'Ensemble courant */
    public boolean contient(Elt e) {
        if (e == null)
            throw new IllegalArgumentException(
                    "Le paramêtre ne peut pas être null.");
        return (this.tabB[e.val()]);
    }

    /**
     * Ajoute e à l'Ensemble courant. Sans effet si e appartient déjà à
     * l'Ensemble courant.
     */
    public void ajouter(Elt e) {
        if (!this.contient(e)) {
            version++;
            this.tabB[e.val()] = true;
            this.cardinal++;
        }
    }

    /**
     * Enlève e de l'Ensemble courant. Sans effet si l'Ensemble courant ne
     * contient pas e.
     */
    public void enlever(Elt e) {
        if (this.contient(e)) {
            this.version++;
            this.tabB[e.val()] = false;
            this.cardinal--;
        }
    }

    /** Renvoie un élément quelconque de this; renvoie null si this est vide */
    public Elt unElement() {
        if (this.estVide())
            return null;
        int i = 0;
        do
            i++;
        while (!tabB[i]);
        return new Elt(i);
    }

    /** Remplace l'Ensemble courant par son complémentaire dans l'Univers */
    public void complementer() {
        this.version++;
        for (int i = 1; i <= MAX; i++) {
            this.tabB[i] = !this.tabB[i];
        }
        this.cardinal = MAX - this.cardinal;
    }

    /**
     * Renvoie le premier élément de this rencontré en "tournant dans l'Univers"
     * à partir de a
     */
    private Elt premierEltApres(Elt a) {
        if (this.estVide() || ((this.cardinal == 1) && this.contient(a)))
            return null;
        Elt x = new Elt(a);
        do
            x = x.succ();
        while (!this.contient(x));
        return x;
    }

    /** Renvoie la borne c du plus grand intervalle [a,c] inclus dans this. */
    private Elt plusGrandIntervInclus(Elt a) {
        Elt c = new Elt(a);
        while (this.contient(c))
            c = c.succ();
        return c.pred();
    }

    /** Renvoie une description synthétique de l'Ensemble courant. */
    public String toStringSynthetique() {
        if (this.estVide())
            return "{}";
        if (this.cardinal == MAX)
            return "{1.." + MAX + "}";
        Ensemble copie = new Ensemble(this);
        String st = "{";
        Elt a = copie.premierEltApres(new Elt(MAX));
        while (a != null) {
            Elt b = copie.plusGrandIntervInclus(a);
            if (b.equals(a)) {
                st += a + ",";
            } else if (b.equals(a.succ())) {
                st += a + "," + b + ",";
            } else {
                st += a + ".." + b + ",";
            }
            copie.enlever(new Ensemble(a, b));
            a = copie.premierEltApres(b);
        }
        st = st.substring(0, st.length() - 1);
        return st + "}";
    }

    @Override
    public String toString() {
        if (cardinal == 0)
            return "{}";
        String res = "{";
        for (int i = 1; i <= MAX; i++) {
            if (tabB[i]) {
                res += i + ",";
            }
        }
        return res.substring(0, res.length() - 1) + "}";
    }

    /** renvoie un it�rateur sur l'ensemble courant */
    public Iterator<Elt> iterator() {
        return new EnsembleIterator();
    }

    public class EnsembleIterator implements Iterator<Elt> {

        private int courant; // indice courant
        private int out; // nombre de Elt déjà renvoyés
        private int v;
        private int cardDepart;

        public EnsembleIterator() {
            cardDepart = cardinal;
            v = version;
            courant = 0;
            out = 0;
        }

        public boolean hasNext() {
            return out < cardDepart;
        }

        public Elt next() {
            if (v != version) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext())
                throw new NoSuchElementException("No such element!");
            do
                courant++;
            while (!tabB[courant]);
            out++;
            return new Elt(courant);
        }

    } // Iterator

} // fin class