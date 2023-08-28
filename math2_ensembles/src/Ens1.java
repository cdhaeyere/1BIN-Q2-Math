/**
 * @author Corentin D'haeyere
 */
public class Ens1 extends EnsembleAbstrait {

	private boolean[] tabB; // e appartient � l'ensemble courant ssi tabE[e.val()] est � true.
	private int cardinal;

	public Ens1() {
		tabB = new boolean[MAX + 1];
	}

	public boolean estVide() {
		return cardinal == 0;
	}

	public Elt unElement() {
		if (estVide()) throw new MathException();
		for (int i = 1; i <= MAX; i++) {
			if (tabB[i]) return new Elt(i);
		}
		return null;
	}

	public boolean contient(Elt e) {
		if (e == null) throw new IllegalArgumentException();
		for (int i = 1; i <= MAX; i++) {
			if (tabB[i] && new Elt(i).equals(e)) {
				return true;
			}
		}
		return false;
	}

	public void ajouter(Elt e) {
		if (!this.contient(e)) {
			tabB[e.val()] = true;
			cardinal++;
		}
	}

	public void enlever(Elt e) {
		if (this.contient(e)) {
			tabB[e.val()] = false;
			cardinal--;
		}
	}

	public int cardinal() {
		return cardinal;
	}

	public void complementer() {
		for (int i = 1; i <= MAX; i++) {
			if (tabB[i]) {
				tabB[i] = false;
				cardinal--;
			} else {
				tabB[i] = true;
				cardinal++;
			}
		}
	}

	public String toString() {
		if (estVide()) {
			return "{}";
		}
		String res = "{";
		for (int i = 1; i <= MAX; i++) {
			if (tabB[i]) {
				res += String.valueOf(i) + ",";
			}
		}
		res = res.substring(0, res.length() - 1);
		return res + "}";
	}
}
