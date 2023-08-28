/** Interface pour les classes Ensi ( i = 1,2,3,...).
 */



public interface EnsembleInterface extends Iterable<Elt>,Cloneable{
	public static final int MAX = Elt.MAXELT.val();

	// renvoie true ssi l'ensemble courant est vide
	public boolean estVide();

	// renvoie true ssi e appartient à l'ensemble courant
	// lance une IllegalArgumentException en cas de paramètre invalide
	public boolean contient(Elt e);

	// ajoute e (éventuellement) à l'ensemble courant
	// lance une IllegalArgumentException en cas de paramètre invalide
	public void ajouter(Elt e);

	// enlève e (éventuellement) de l'ensemble courant
	// lance une IllegalArgumentException en cas de paramètre invalide
	public void enlever(Elt e);

	// this <- complémentaire de this
	public void complementer();

	// renvoie le cardinal de l'ensemble courant
	public int cardinal();

	// renvoie une chaîne de caractèree d&crivant this en extension
	public String toString();
	
	public Elt unElement();

	// remplace this par this union a
	// lance une IllegalArgumentException en cas de paramètre invalide
	public default void ajouter(EnsembleInterface a) {
		if (a == null)
			throw new IllegalArgumentException();
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (a.contient(ei))
				this.ajouter(ei);
		}
	}

	// remplace this par this moins a
	// lance une IllegalArgumentException en cas de paramètre invalide
	public default void enlever(EnsembleInterface a) {
		if (a == null)
			throw new IllegalArgumentException();
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (a.contient(ei))
				this.enlever(ei);
		}
	}

	// remplace this par this inter a
	// lance une IllegalArgumentException en cas de paramètre invalide
	public default void intersecter(EnsembleInterface a) {
		if (a == null)
			throw new IllegalArgumentException();
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (!a.contient(ei))
				this.enlever(ei);
		}
	}

} // interface
