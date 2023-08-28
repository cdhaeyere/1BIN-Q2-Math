import java.util.Iterator;

public class Ordre extends RelationAbstraite {

	private Relation couples;

	//construit l'identité sur e
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Ordre(EnsembleAbstrait e) {
		if (e==null) throw new IllegalArgumentException();
		couples = new Relation(e, e);
		for (Elt elt : e) {
			couples.ajouter(new Couple(elt, elt));
		}
	}

	//construit le plus petit ordre contenant r
	//lance une IllegalArgumentException si cette construction n'est pas possible
	public Ordre(Relation r) {
		if (r==null) throw new IllegalArgumentException();
		if (!r.arrivee().equals(r.depart())) throw new IllegalArgumentException();
		couples = new Relation(r);
		couples.cloReflex();
		couples.cloTrans();
		if (!couples.antisymetrique()) throw new IllegalArgumentException();
	}
	
	//constructeur pas recopie
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Ordre(Ordre or) {
		if (or==null) throw new IllegalArgumentException();
		couples = new Relation(or.depart(), or.arrivee());
		for (Couple couple : or.couples) {
			couples.ajouter(couple);
		}
	}

	//ajoute x à l'ensemble sous-jacent de la relation d'ordre
	//ne fait rien si x est déjà dans l'ensemble sous-jacent
	//lance une IllegalArgumentException en cas de paramètre invalide
	public void ajouterAuSousJacent(Elt x) {
		if (x==null) throw new IllegalArgumentException();
		if (!depart().contient(x)) {
			couples.ajouterDepart(x);
			couples.ajouterArrivee(x);
			couples.ajouter(new Couple(x, x));
		}
	}

	//enlève x de l'ensemble sous-jacent de la relation d'ordre
	//ainsi que toutes les flêches liées à x
	//ne fait rien si x n'est pas dans l'ensemble sous-jacent 
	//lance une IllegalArgumentException en cas de paramètre invalide
	public void enleverDuSousJacent(Elt x) {
		if (x==null) throw new IllegalArgumentException();
		if (depart().contient(x)) {
			couples.supprimerDepart(x);
			couples.supprimerArrivee(x);
		}
	}
	
	@Override
	public Iterator<Couple> iterator() {
		return couples.iterator();
	}

	@Override
	public boolean estVide() {
		return couples.estVide();
	}

	@Override
	public boolean contient(Couple c) {
		if (c== null) throw new IllegalArgumentException();
		if (!couples.depart().contient(c.getX())||!couples.arrivee().contient(c.getY())) throw new IllegalArgumentException();
		return couples.contient(c.getX(), c.getY());
	}

	@Override
	//crée (si possible) le plus petit ordre contenant this et c
	//lance une IllegalArgumentException en cas de paramètre invalide
	public void ajouter(Couple c) {
		if (c==null) throw new IllegalArgumentException();
		if (!contient(c)) {
			if (contient(c.reciproque())) throw new IllegalArgumentException();
			couples.ajouter(c);
			couples.cloReflex();
			couples.cloTrans();
			if (!couples.antisymetrique()) throw new IllegalArgumentException();
		}
	}


	@Override
	//Enlève (si possible) l'arête de Hasse c du la relation d'ordre
	//lance une IllegalArgumentException en cas de si le paramètre est invalide ou si c n'est pas une arête de Hasse
	//ne fait rien sinon
	public void enlever(Couple c) {
		if (c== null) throw new IllegalArgumentException();
		if (!this.depart().contient(c.getX())) throw new IllegalArgumentException();
		if (!this.depart().contient(c.getY())) throw new IllegalArgumentException();
		if (!this.contient(new Couple(c.getX(), c.getY()))) return;
		if (!estUneAreteDeHasse(c.getX(), c.getY()))
			throw new IllegalArgumentException();
		Ensemble plusPttX = this.plusPetitQue(c.getX());
		Ensemble plusGrdY = this.plusGrandQue(c.getY());
		for (Elt eX : plusPttX) {
			for (Elt eY : plusGrdY) {
				this.couples.enlever(eX, eY);
			}
		}
		this.couples.cloTrans();
	}
	
	private Ensemble plusPetitQue(Elt e){
		Ensemble min = new Ensemble();
		for (Elt eC : couples.depart()){
			if (couples.contient(eC, e)) min.ajouter(eC);
		}
		return min;
	}
	
	private Ensemble plusGrandQue(Elt e){
		Ensemble maj = new Ensemble();
		for (Elt eC : couples.depart()){
			if (couples.contient(e,eC)) maj.ajouter(eC);
		}
		return maj;
	}

	private boolean estUneAreteDeHasse(Elt x, Elt y) {
		if (!this.contient(new Couple(x, y)))
			return false;
		if (x.equals(y))
			return false;
		EnsembleAbstrait aParcourir = this.depart();
		aParcourir.enlever(x);
		aParcourir.enlever(y);
		for (Elt e : aParcourir) {
			if (this.contient(new Couple(x, e)) && this.contient(new Couple(e, y)))
				return false;
		}
		return true;
	}

	public boolean estUneAreteDeHasse(Couple c) {
		if (c== null) throw new IllegalArgumentException();
		return estUneAreteDeHasse(c.getX(), c.getY());
	}

	@Override
	public EnsembleAbstrait depart() {
		return couples.depart();
	}

	@Override
	public EnsembleAbstrait arrivee() {
		return couples.arrivee();
	}

	//renvoie true ssi x et y sont comparables pour l'ordre courant
	//lance une IllegalArgumentException en cas de paramètre invalide
	public boolean comparables(Elt x, Elt y) {
		if (x==null) throw new IllegalArgumentException();
		if (y==null) throw new IllegalArgumentException();
		return contient(new Couple(x, y))  || contient(new Couple(y, x));
	}

	// Renvoie l'ensemble des éléments minimaux de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait minimaux(EnsembleAbstrait b) {
		if (b==null || !b.inclusDans(depart())) throw new IllegalArgumentException();
		EnsembleAbstrait ensemble = new Ensemble();
		for (Elt e : b) {
			boolean min = true;
			for (Elt f : b){
				if(!f.equals(e) && contient(new Couple(f,e))) min = false;
			}
			if(min) ensemble.ajouter(e);
		}
		return ensemble;
	}
	
	// Renvoie l'ensemble des éléments maximaux de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait maximaux(EnsembleAbstrait b) {
		if (b == null || !b.inclusDans(couples.depart())) throw new IllegalArgumentException();
		EnsembleAbstrait ensemble = new Ensemble();
		for (Elt e : b){
			boolean grand = true;
			for (Elt f : b){
				if(!f.equals(e) && couples.contient(new Couple(e, f))) grand = false;
			}
			if(grand) ensemble.ajouter(e);
		}
		return ensemble;
	}

	// Renvoie le minimum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt minimum(EnsembleAbstrait b) {
		if (b == null) throw new IllegalArgumentException();
		if(minimaux(b).cardinal() == 1) return minimaux(b).unElement();
		return null;
	}
	
	// Renvoie le maximum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt maximum(EnsembleAbstrait b) {
		if (b == null) throw new IllegalArgumentException();
		if(maximaux(b).cardinal() == 1) return maximaux(b).unElement();
		return null;
	}

	// Renvoie l'ensemble des minorants de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait minor(EnsembleAbstrait b) {
		if (b == null || !b.inclusDans(couples.depart())) throw new IllegalArgumentException();
		EnsembleAbstrait ensemble = new Ensemble();
		for (Elt e : couples.depart()){
			boolean petit = true;
			for (Elt f : b){
				if(!f.equals(e) && !couples.contient(new Couple(e, f))) petit = false;
			}
			if(petit) ensemble.ajouter(e);
		}
		return ensemble;
	}
	
	// Renvoie l'ensemble des majorants de b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public EnsembleAbstrait major(EnsembleAbstrait b) {
		if (b == null || !b.inclusDans(couples.depart())) throw new IllegalArgumentException();
		EnsembleAbstrait ensemble = new Ensemble();
		for (Elt e : couples.depart()){
			boolean petit = true;
			for (Elt f : b){
				if(!f.equals(e) && !couples.contient(new Couple(f, e))) petit = false;
			}
			if(petit) ensemble.ajouter(e);
		}
		return ensemble;
	}

	// Renvoie l'infimum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt infimum(EnsembleAbstrait b) {
		if (b == null) throw new IllegalArgumentException();
		return maximum(minor(b));
	}
	
	// Renvoie le supremum de b s'il existe; renvoie null sinon
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Elt supremum(EnsembleAbstrait b) {
		if (b == null) throw new IllegalArgumentException();
		return minimum(major(b));
	}

	//Renvoie true ssi this est un treillis
	//lance une IllegalArgumentException en cas de paramètre invalide
	public boolean treillis(){
		for(Elt e : couples.depart()){
			for(Elt f : couples.depart()) {
				EnsembleAbstrait a = new Ensemble();
				a.ajouter(e);
				a.ajouter(f);
				if (infimum(a) == null || supremum(a) == null) return false;
			}
		}
		return true;
	}

	public String toString() {
		return couples.toString();
	}
	
}
