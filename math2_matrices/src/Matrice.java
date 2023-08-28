public class Matrice {
    private final int nbLignes;              // nombre de lignes
    private final int nbColonnes;            // nombre de colonnes
    private double[][] data;           // matrice (nbLignes,nbColonnes)

    // ce constructeur cree la matrice nulle de genre (a,b)
    public Matrice(int a, int b) throws IllegalArgumentException {
        // on verifie que les dimensions sont valides
        if (a <= 0 || b <= 0) throw new IllegalArgumentException();
        // on cree la matrice et ces elements
        data = new double[a][b];
        nbLignes = a;
        nbColonnes = b;
    }

    //  Ce constructeur permet de construire la matrice correspondant
    //  au tableau en parametre.
    public Matrice(double[][] tab)  throws IllegalArgumentException {
        // on verifie que le tableau est conforme
        if (tab == null || tab.length == 0 || tab[0] == null || tab[0].length == 0) throw new IllegalArgumentException();
        // on recupere les dimensions du tableau
        int tmp = tab[0].length;
        // Verification que toutes les lignes ont la meme longueur
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] == null || tab[i].length != tmp) throw new IllegalArgumentException();
        }
        // Création de la matrice et de ces elements puis remplissage de celle-ci
        data = new double[tab.length][tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                data[i][j] = tab[i][j];
            }
        }
        nbLignes = tab.length;
        nbColonnes = tab[0].length;
    }

    // constructeur par recopie
    public Matrice(Matrice a)  throws IllegalArgumentException {
        // on verifie que la matrice a est conforme
        if (a == null) throw new IllegalArgumentException();
        // on crée la matrice et ces elements puis remplissage de celle-ci
        data = new double[a.nbLignes][a.nbColonnes];
        for (int i = 0; i < a.data.length; i++) {
            for (int j = 0; j < a.data[0].length; j++) {
                data[i][j] = a.data[i][j];
            }
        }
        nbLignes = a.nbLignes;
        nbColonnes = a.nbColonnes;
    }

    // cree la matrice identite d'ordre a
    public static Matrice identite(int a)  throws IllegalArgumentException {
        if (a <= 0) throw new IllegalArgumentException();
        Matrice m = new Matrice(a,a);
        for (int i = 0; i < m.data.length; i++) {
            m.data[i][i] = 1;
        }
        return m;
    }

    //Cette methode renvoie l'element de la ligne numLigne et de la
    //colonne numColonne de la matrice. Si cet element n'existe pas, la
    //methode lance une IllegalArgumentException
    public double getElement(int numLigne, int numColonne) throws IllegalArgumentException {
        if (numLigne <= 0 || numColonne <= 0 || numLigne > nbLignes || numColonne > nbColonnes) throw new IllegalArgumentException();
        return data[numLigne-1][numColonne-1];
     }

    // ajoute b a la matrice courante si c'est possible
    public Matrice somme(Matrice b)  throws IllegalArgumentException {
        // on verifie que la matrice b est conforme
        if (b == null || b.nbLignes != nbLignes || b.nbColonnes != nbColonnes) throw new IllegalArgumentException();
        // on cree la matrice puis remplissage de celle-ci
        Matrice m = new Matrice(nbLignes,nbColonnes);
        for (int i = 0; i < b.nbLignes; i++) {
            for (int j = 0; j < b.nbColonnes; j++) {
                m.data[i][j] = data[i][j] + b.data[i][j];
            }
        }
        return m;
    }

    // calcule le produit scalaire.this de la matrice courante avec scalaire
    public Matrice produitParScalaire(double scalaire){
        // on cree la matrice puis remplissage de celle-ci
        Matrice m = new Matrice(nbLignes,nbColonnes);
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                m.data[i][j] = data[i][j] * scalaire;
            }
        }
        return m;
    }

    // calcule le produit this*b de la matrice courante avec b si possible
    public Matrice produitAGauche(Matrice b)  throws IllegalArgumentException {
        if (b == null || nbColonnes != b.nbLignes)
            throw new IllegalArgumentException();
        Matrice m = new Matrice(nbLignes, b.nbColonnes);
        double tmp = 0;
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < b.nbColonnes; j++) {
                for (int k = 0; k < b.nbLignes; k++) {
                    tmp += this.data[i][k] * b.data[k][j];
                }
                m.data[i][j] = tmp;
                tmp = 0;
            }
        }
        return m;
    }

    // calcule le produit b*this de b avec la matrice courante si possible
    public Matrice produitADroite(Matrice b)  throws IllegalArgumentException {
        if (b == null || b.nbColonnes != nbLignes)
            throw new IllegalArgumentException();
        Matrice m = new Matrice(b.nbLignes, nbColonnes);
        double tmp = 0;
        for (int i = 0; i < b.nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                for (int k = 0; k < nbLignes; k++)
                    tmp += b.data[i][k] * this.data[k][j];
                m.data[i][j] = tmp;
                tmp = 0;
            }
        }
        return m;
    }

    // renvoie true si la matrice courante est carrée
     public boolean carree(){
         return nbLignes == nbColonnes;
    }

    // Calcule this^n. Lance une Mathexception si this n'est pas carrée
    public Matrice puissance(int n) throws  IllegalArgumentException {
        if (!this.carree()) throw new MathException();
        if (n < 0) throw new IllegalArgumentException();
        if(n == 0) return identite(nbLignes);
        Matrice matrice = new Matrice(this);
        for(int i = 2; i <= n; i++) {
            matrice = matrice.produitAGauche(this);
        }
        return matrice;
    }

    //Calcule this^T : la tranposée de this
    public Matrice transposee() {
        double[][] temp = new double[nbColonnes][nbLignes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                temp[j][i] = data[i][j];
            }
        }
        return new Matrice(temp);
    }

    // affiche la matrice en format standard
    public String toString(){
        StringBuilder aRenvoyer = new StringBuilder();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                aRenvoyer.append(data[i][j]);
                int nbCar = String.valueOf(data[i][j]).length();
                int nbEspaces = 15 - nbCar;
                for (int k = 0; k < nbEspaces; k++) {
                    aRenvoyer.append(" ");
                }
            }
            aRenvoyer.append("\n");
        }
        return aRenvoyer.toString();
    }

    public Matrice pageRank() {
        // Est une matrice carrée ?
        if (!carree()) throw new MathException();
        // Est une matrice composée de 0 et 1 ?
        for (int i = 0; i < nbColonnes; i++) {
            for (int j = 0; j < nbLignes; j++) {
                if (data[i][j] != 0 && data[i][j] != 1) {
                    throw new MathException();
                }
            }
        }
        // Matrice de transition
        Matrice transition = this.pageRankA();
        // Introduction de l'aléatoire
        Matrice google = this.pageRankB(transition);
        // Résultat
        return this.pageRankC(google);
    }

    private Matrice pageRankA() {
        // Somme des valeurs par colonne
        double[] sommeColonne = new double[nbLignes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                sommeColonne[i] += data[j][i];
            }
        }
        // Nombre de 1 par colonne
        int[] nb1Colonne = new int[nbColonnes];
        for (int i = 0; i < nbColonnes; i++) {
            for (int j = 0; j < nbLignes; j++) {
                if (data[j][i] == 1) {
                    nb1Colonne[i]++;
                }
            }
        }
        Matrice transition = new Matrice(nbColonnes, nbColonnes);
        for (int i = 0; i < nbLignes; i++) {
            if (sommeColonne[i] == 0) {
                for (int j = 0; j < nbColonnes; j++) {
                    transition.data[j][i] = (1.0 / nbColonnes);
                }
            } else {
                for (int j = 0; j < nbColonnes; j++) {
                    if (data[j][i] == 1) {
                        transition.data[j][i] = (1.0 / nb1Colonne[i]);
                    }
                }
            }
        }
        return transition;
    }

    private Matrice pageRankB(Matrice transition) {
        Matrice random = new Matrice(nbColonnes, nbColonnes);
        for (int i = 0; i < nbColonnes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                random.data[i][j] = 1.0 / nbColonnes;
            }
        }
        transition = transition.produitParScalaire(0.85);
        random = random.produitParScalaire(0.15);
        return transition.somme(random);
    }

    private Matrice pageRankC(Matrice google) {
        Matrice x = new Matrice(nbLignes, 1);
        x.data[0][0] = 1;
        for (int i = 0; i < 85; i++) {
            x = google.produitAGauche(x);
            double somme = 0;
            for (int j = 0; j < nbLignes; j++) {
                somme += x.data[j][0];
            }
            x = x.produitParScalaire(1 / somme);
        }
        return x;
    }
}
