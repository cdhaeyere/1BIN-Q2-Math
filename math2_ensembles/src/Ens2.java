import java.util.Arrays;
import java.util.Objects;

public class Ens2 extends EnsembleAbstrait {

	private Elt[] elements; // contient les elements de l'ensemble. Il ne peut pas y avoir de doublon.
	private int cardinal;

	public Ens2() {
		elements = new Elt[MAX];
	}

	public boolean estVide() {
		return cardinal == 0;
	}
	
	public Elt unElement() {
		for (int i = 0; i < MAX; i++) {
			if (elements[i] != null) return elements[i];
		}
		return null ;
	}

	public boolean contient(Elt e) {
		if (e == null) throw new IllegalArgumentException();
		if (this.estVide()) return false;
		for (int i = 0; i < cardinal; i++) {
			if (e.val() == elements[i].val()) {
				return true;
			}
		}
		return false;
	}

	public void ajouter(Elt e) {
		if (e == null) throw new IllegalArgumentException();
		if (this.contient(e)) return;
		elements[cardinal] = e;
		cardinal++;
	}

	public void enlever(Elt e) {
		if (e == null) throw new IllegalArgumentException();
		if (this.contient(e)) {
			int indiceElt = 0;
			for (int i = 0; i < cardinal; i++) {
				if (elements[i].equals(e)) indiceElt = i;
			}
			for (int i = indiceElt; i < cardinal-1; i++) {
				elements[i] = elements[i+1];
			}
			cardinal--;
		}
	}

	public int cardinal() {
		return cardinal;
	}

	public void complementer() {
		Elt[] elts = new Elt[MAX-cardinal];
		int indice = 0;
		for (int i = 1; i <= MAX; i++) {
			Elt temp = new Elt(i);
			if (!this.contient(temp)){
				elts[indice] = temp;
				indice++;
			}
		}
		elements = elts;
		cardinal = MAX-cardinal;
	}

	public String toString() {
		String res = "{";
		int i = 0;
		if (estVide()){
			return "{}";
		}
		while (elements[i] != null){
			res += elements[i] + ",";
			i+=1;
		}
		res = res.substring(0, res.length()-1);
		return res + "}";
	}
}
