public abstract class EnsembleAbstrait implements EnsembleInterface {

	// renvoie true ssi this est inclus dans a
	// lance une IllegalArgumentException en cas de paramètre invalide
	public boolean inclusDans(EnsembleAbstrait a) {
		if (a == null)
			throw new IllegalArgumentException();
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (this.contient(ei) && !a.contient(ei))
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
			return false;
		EnsembleAbstrait ens = (EnsembleAbstrait) o;		
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (this.contient(ei) != ens.contient(ei))
				return false;
		}
		return true;
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
	
	@Override
	public EnsembleAbstrait clone()  {
		try {
			return (EnsembleAbstrait) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

}
