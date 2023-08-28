/** Interface pour les classes Ensi ( i = 1,2,3,...).
 */

import java.util.*;

public interface EnsembleInterface {
	public static final int MAX = Elt.MAXELT.val();

	// renvoie true ssi l'ensemble courant est vide
	public boolean estVide();
	
	// renvoie un element de l'ensemble s'il n'est pas vide
	// lance une MathException si l'ensemble est vide
	public Elt unElement();

	// renvoie true ssi e appartient � l'ensemble courant
	// lance une IllegalArgumentException en cas de param�tre invalide
	public boolean contient(Elt e);

	// ajoute e (�ventuellement) � l'ensemble courant
	// lance une IllegalArgumentException en cas de param�tre invalide
	public void ajouter(Elt e);

	// enl�ve e (�ventuellement) de l'ensemble courant
	// lance une IllegalArgumentException en cas de param�tre invalide
	public void enlever(Elt e);

	// this <- compl�m. de this
	public void complementer();

	// renvoie le cardinal de l'ensemble courant
	public int cardinal();

	// renvoie une cha�ne de caract�re d�crivant this en extension
	public String toString();

	// remplace this par this union a
	// lance une IllegalArgumentException en cas de param�tre invalide
	public default void ajouter(EnsembleInterface a) {
		if (a == null) throw new IllegalArgumentException();
		if (!this.estVide()) {
			for (int i = 1; i <= MAX; i++) {
				Elt e = new Elt(i);
				if (a.contient(e)) {
					this.ajouter(e);
				}
			}
		}
	}

	// remplace this par this moins a
	// lance une IllegalArgumentException en cas de param�tre invalide
	public default void enlever(EnsembleInterface a) {
		if (a == null) throw new IllegalArgumentException();
		if (!this.estVide()) {
			for (int i = 1; i <= MAX; i++) {
				Elt e = new Elt(i);
				if (a.contient(e)) {
					this.enlever(e);
				}
			}
		}
	}

	// remplace this par this inter a
	// lance une IllegalArgumentException en cas de param�tre invalide
	public default void intersecter(EnsembleInterface a) {
		if (a == null) throw new IllegalArgumentException();
		if (!this.estVide()) {
			for (int i = 1; i <= MAX; i++) {
				Elt e = new Elt(i);
				if (!a.contient(e)) {
					this.enlever(e);
				}
			}
		}
	}
	
} // interface
