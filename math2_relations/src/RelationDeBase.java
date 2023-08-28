/** Classe RelationDeBase
	 Classe abstraite implémentant RelationInterface au moyen d'un tableau de
	 Suites triées; la Suite i donne les buts des couples de source i.

	 @author M.Marchand
	 @version mars 2008	*/

import java.util.*;

public  class RelationDeBase extends RelationAbstraite implements Cloneable {

	private int numVersion = 0;
	/** Valeur numérique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();

	private EnsembleAbstrait[] t;
	private EnsembleAbstrait dep;
	private EnsembleAbstrait arr;
	private int card; // nombre de couples

	/** Constructeur de la Relation vide sur l'Ensemble vide */
	public RelationDeBase() {
		this.dep = new Ensemble();
		this.arr = new Ensemble();
		this.t = new Ensemble[MAX + 1];
		this.card = 0;
	}

	/** Constructeur de la Relation vide de l'Ensemble d vers l'Ensemble a */
	public RelationDeBase(EnsembleAbstrait d, EnsembleAbstrait a) {
		if (d== null) throw new IllegalArgumentException("L'ensemble de départ ne peut pas être null.");
		if (a== null) throw new IllegalArgumentException("L'ensemble d'arrivée ne peut pas être null.");
		this.dep =d.clone();
		this.arr = a.clone();
		this.card = 0;
		this.t = new Ensemble[MAX + 1];
		Iterator<Elt> it = d.iterator();
		while (it.hasNext())
			this.t[it.next().val()] = new Ensemble();
	}

	/** renvoie l'ensemble de départ de la Relation courante */
	public EnsembleAbstrait depart() {
		return dep.clone();
	}

	/** renvoie l'ensemble d'arrivée de la Relation courante */
	public EnsembleAbstrait arrivee() {
		return arr.clone();
	}

	/** renvoie le nombre de couples de la Relation courante */
	public int cardinal() {
		return this.card;
	}

	/** Renvoie true si la Relation courante est vide */
	public boolean estVide() {
		return this.card == 0;
	}

	public void ajouterDepart(Elt e) {
		if (this.dep.contient(e))
			return;
		numVersion++;
		this.dep.ajouter(e);
		this.t[e.val()] = new Ensemble();
	}

	public void ajouterArrivee(Elt e) {
		if (this.arr.contient(e)) return;
		numVersion++;
		this.arr.ajouter(e);
	}

	public void supprimerDepart(Elt e) {
		if (!this.dep.contient(e))
			return;
		numVersion++;
		this.dep.enlever(e);
		this.card = this.card- this.t[e.val()].cardinal();
		this.t[e.val()] = null;
	}

	public void supprimerArrivee(Elt e) {
		if (!this.arr.contient(e))
			return;
		numVersion++;
		Iterator<Elt> it = dep.iterator();
		while (it.hasNext()) {
			Elt suivant = it.next();
			if (this.t[suivant.val()].contient(e)){
				this.t[suivant.val()].enlever(e);
				this.card--;
			}
		}
		this.arr.enlever(e);
	}

	/** Renvoie true si la Relation courante contient le couple (x,y) */
	public boolean contient(Elt x, Elt y) {
		if (this.dep.contient(x))
			return this.t[x.val()].contient(y);
		else
			return false;
	}

	public boolean contient(Couple c) {
		if (c==null) throw new IllegalArgumentException("Le couple passé en paramètre ne peut pas être null");
		return this.contient(c.getX(), c.getY());
	}

	/**
	 * Ajoute le couple (x,y) à la Relation courante. Sans effet si le couple
	 * (x,y) appartient déjà à la Relation courante. Lance une MathException si
	 * x n'appartient pas au départ ou si y n'appartient pas à l'arrivée
	 */
	public void ajouter(Elt x, Elt y) {
		if (!this.dep.contient(x))
			throw new IllegalArgumentException(x
					+ " ne fait pas partie de l'ensemble de départ");
		if (!this.arr.contient(y))
			throw new IllegalArgumentException(y
					+ " ne fait pas partie de l'ensemble d'arrivée");
		if (this.contient(x, y))
			return;
		this.card++;
		numVersion++;
		this.t[x.val()].ajouter(y);
	}

	/** Ajoute le couple c � la Relation courante */
	public void ajouter(Couple c) {
		if (c==null) throw new IllegalArgumentException("Le couple passé en paramètre ne peut pas être null");
		this.ajouter(c.getX(), c.getY());
	}

	/**
	 * Enlève le couple (x,y) de la Relation courante. Ne modifie ni départ ni
	 * arrivée. Sans effet si le couple (x,y) n'appartient pas à la Relation
	 * courante.
	 */
	public void enlever(Elt x, Elt y) {
		if (!this.dep.contient(x))
			throw new IllegalArgumentException(x
					+ " ne fait pas partie de l'ensemble de départ");
		if (!this.arr.contient(y))
			throw new IllegalArgumentException(y
					+ " ne fait pas partie de l'ensemble d'arrivée");
		if (this.contient(x, y)) {
			numVersion++;
			this.card--;
			this.t[x.val()].enlever(y);
		}
	}

	/** enl�ve le couple c de la Relation courante */
	public void enlever(Couple c) {
		if (c==null) throw new IllegalArgumentException("Le couple passé en paramètre ne peut pas être null");
		this.enlever(c.getX(), c.getY());
	}

	/**
	 * Renvoie une description de la Relation courante par énumération des buts
	 * des couples de même source
	 */
	public String toString() {
		String st = "\nRelation\nDépart  : " + this.dep;
		st += "\nArrivée : " + this.arr;
		if (this.estVide())
			return st + "\nRELATION VIDE";
		st += "\n";
		Iterator<Elt> it = this.dep.iterator();
		while (it.hasNext()) {
			Elt x = it.next();
			if (!this.t[x.val()].estVide()) {
				String sx = this.t[x.val()].toString();
				st += "\tSource " + x + " : "
						+ sx.substring(1, sx.length() - 1) + "\n";
			}
		}
		return st;
	}
	
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		RelationDeBase r;
		try {
			
			r = (RelationDeBase) super.clone();
			r.dep = this.dep.clone();
			r.arr = this.arr.clone();
			r.t = new Ensemble[MAX + 1];
			Iterator<Elt> it = this.dep.iterator();
			while (it.hasNext()) {
				Elt next = it.next();
				r.t[next.val()] = (Ensemble) this.t[next.val()].clone();
			}
			return r;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	public Iterator<Couple> iterator() {
		return new RelationIterator();
	}

	private class RelationIterator implements Iterator<Couple> {
		private int version;
		private int out; // nombre de couples déjà renvoyés
		private Iterator<Elt> itd;
		private Iterator<Elt> ita;
		private Elt x;
		private Elt y;
		private int cardinal = card;
		public RelationIterator() {
			out = 0;
			itd = dep.iterator();
			x = null;
			y = null;
			version = numVersion;
		}

		public boolean hasNext() {
			return out < cardinal;
		}

		public Couple next() {
			if (version!=numVersion)
				throw new ConcurrentModificationException();
			if (!hasNext())
				throw new MathException("No such element!");
			boolean ok = false;
			do {
				if (x == null || !ita.hasNext()) {
					x = itd.next();
					ita = arr.iterator();
				}
				while (ita.hasNext() && !ok) {
					y = ita.next();
					ok = contient(x, y);
				}
			} while (!ok);
			out++;
			return new Couple(x, y);
		}
	} // RelationIterator

} // fin de classe
