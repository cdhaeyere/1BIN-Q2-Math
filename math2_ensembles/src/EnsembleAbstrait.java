public abstract class EnsembleAbstrait implements EnsembleInterface {

	// renvoie true ssi this est inclus dans a
	// lance une IllegalArgumentException en cas de param�tre invalide
	public boolean inclusDans(EnsembleAbstrait a) {
		if (a == null)
			throw new IllegalArgumentException();
		if (this.estVide())
			return true;
		if (a.estVide())
			return false;
		for (int i = 1; i <= MAX; i++) {
			Elt eltCourant = new Elt(i);
			if (this.contient(eltCourant) && !a.contient(eltCourant))
				return false;
		}
		return true;
	}

	// renvoie true ssi this est égal à o
	public final boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof EnsembleAbstrait))
			return false ;

		return false;
	}


	@Override
	public final int hashCode() {
		int result = 1;
		int prime = 31;
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (this.contient(ei))
				result = result * prime + ei.hashCode();
		}
		return result;
	}
	
}
