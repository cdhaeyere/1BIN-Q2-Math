/** Classe Relation héritant de RelationDeBase
	 Fournit des outils de manipulation des relations entre sous-ensembles de l'Univers
 */
/**
 * @author Corentin D'haeyere
 */
public class Relation extends RelationDeBase {

	/** Valeur numérique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();

	/** Construit la Relation vide sur l'ensemble vide */
	public Relation() {
		super();
	}

	/** Construit la Relation vide de d vers a */
	public Relation(EnsembleAbstrait d, EnsembleAbstrait a) {
		super(d, a);
	}

	/** Clone */
	public Relation clone() {
		return (Relation) super.clone();
	}
	
	//Ex1
	//renvoie le domaine de la relation courante
	public EnsembleAbstrait domaine() {
		Ensemble domaine = new Ensemble();
		for (Couple couple : this) {
			domaine.ajouter(couple.getX());
		}
		return domaine;
	}
	
	//renvoie l'image de la relation courante
	public EnsembleAbstrait image() {
		Ensemble image = new Ensemble();
		for (Couple couple : this) {
			image.ajouter(couple.getY());
		}
		return image;
	}
	
	// EX 2
	// renvoie la complémentaire de la relation courante
	public Relation complementaire() {
		Relation complementaire = new Relation(this.depart(), this.arrivee());
		for(Elt elt : this.depart()){
			for(Elt elt2 : this.arrivee()){
				Couple couple = new Couple(elt, elt2);
				if(!this.contient(couple)) complementaire.ajouter(couple);
			}
		}
		return complementaire;
	}

	// renvoie la réciproque de la relation courante
	public Relation reciproque() {
		Relation reciproque = new Relation(this.arrivee(), this.depart());
		for(Couple c : this){
			reciproque.ajouter(c.reciproque());
		}
		return reciproque;
	}

	// si possible, remplace la relation courante par son union avec r
	//sinon, lance une IllegalArgumentException
	public void ajouter(RelationInterface r) {
		if(r == null || !r.depart().equals(this.depart()) ||!r.arrivee().equals(this.arrivee())) throw new IllegalArgumentException();
		for(Couple c : r){
			this.ajouter(c);
		}
	}

	// si possible, remplace this par sa différence avec r
	//sinon, lance une IllegalArgumentException
	public void enlever(RelationInterface r) {
		if(r == null || !r.depart().equals(this.depart()) ||!r.arrivee().equals(this.arrivee())) throw new IllegalArgumentException();
		for(Elt elt : r.depart()){
			for(Elt elt2 : r.arrivee()){
				Couple couple = new Couple(elt, elt2);
				if(this.contient(couple) && r.contient(couple)) this.enlever(couple);
			}
		}
	}

	// si possible, remplace this par son intersection avec r
	//sinon, lance une IllegalArgumentException
	public void intersecter(RelationInterface r) {
		if(r == null || !r.depart().equals(this.depart()) ||!r.arrivee().equals(this.arrivee())) throw new IllegalArgumentException();
		Relation tmp = clone();
		for(Couple c : tmp) {
			if (!r.contient(c)) enlever(c);
		}
	}

	// si possible, renvoie la composée : this après r
	//sinon, lance une IllegalArgumentException
	public Relation apres(RelationInterface r) {
		if(r == null || !r.arrivee().equals(this.depart())) throw new IllegalArgumentException();
		Relation aRenvoyer = new Relation(r.depart(), this.arrivee());
		for(Couple couple : r){
			for(Couple couple2 : this){
				if(couple.getY().equals(couple2.getX())){
					aRenvoyer.ajouter(new Couple(couple.getX(), couple2.getY()));
				}
			}
		}
		return aRenvoyer;
	}
	
	/*Les exercices 4 et 5 ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	
	/* Ex 4 */
		
	// Clôture la Relation courante pour la réflexivité
	public void cloReflex() {
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		for(Elt elt : this.depart()) {
			this.ajouter(new Couple(elt, elt));
		}
	}

	// Clôture la Relation courante pour la symétrie
	public void cloSym() {
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		Relation tmp = clone();
		for(Couple c : tmp) {
			ajouter(c.reciproque());
		}
	}

	// Clôture la Relation courante pour la transitivité (Warshall)
	public void cloTrans() {
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		Relation tmp = clone();
		for(Elt k : tmp.depart()){
			for(Elt sDepart : tmp.depart()) {
				if(contient(sDepart, k)) {
					for(Elt sArrivee : tmp.arrivee()) {
						if(contient(k, sArrivee)){
							ajouter(sDepart, sArrivee);
						}
					}
				}
			}
		}
	}

	//Ex 5
	/*Les questions qui suivent ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	// renvoie true ssi this est réflexive
	public boolean reflexive(){
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		for(Elt elt : this.depart()){
			if(!this.contient(elt, elt)) return false;
		}
		return true;
	}

	// renvoie true ssi this est antiréflexive
	public boolean antireflexive(){
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		for(Elt elt : this.depart()){
			if(this.contient(new Couple(elt, elt))) return false;
		}
		return true;
	}

	// renvoie true ssi this est symétrique
	public boolean symetrique(){
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		for(Couple couple : this){
			if(!this.contient(couple.reciproque())) return false;
		}
		return true;
	}

	// renvoie true ssi this est antisymétrique
	public boolean antisymetrique(){
		if(!this.depart().equals(this.arrivee())) throw new MathException();
		for(Couple couple : this){
			if(!couple.getX().equals(couple.getY()) && this.contient(couple.reciproque())) return false;
		}
		return true;
	}

	// renvoie true ssi  this est transitive
	public boolean transitive(){
		if(!depart().equals(arrivee())) throw new MathException();
		for(Couple couple : this){
			for(Couple couple2 : this){
				if(couple.getY().equals(couple2.getX()) && !contient(couple.getX(), couple2.getY())) return false;
			}
		}
		return true;
	}
	
	// Ex 6
	//Construit une copie de la relation en paramètre
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Relation(RelationInterface r) {
		super();
		if(r == null) throw new IllegalArgumentException();
		for(Elt elt : r.depart()) {
			this.ajouterDepart(elt);
		}
		for(Elt elt : r.arrivee()) {
			this.ajouterArrivee(elt);
		}
		for(Couple c : r) {
			this.ajouter(c);
		}
	}

	//renvoie l'identité sur e
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation identite(EnsembleAbstrait e) {
		if(e == null)
			throw new IllegalArgumentException();
		Relation aRenvoyer = new Relation(e, e);
		for(Elt elt : e ){
			Couple couple = new Couple(elt, elt);
			aRenvoyer.ajouter(couple);
		}
		return aRenvoyer;
	}

	//renvoie le produit cartésien de a et b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation produitCartesien(EnsembleAbstrait a, EnsembleAbstrait b) {
		if(a == null || b == null){
			throw new IllegalArgumentException();
		}
		Relation aRenvoyer = new Relation(a, b);
		for(Elt elt : a){
			for(Elt elt2 : b){
				Couple couple = new Couple(elt, elt2);
				aRenvoyer.ajouter(couple);
			}
		}
		return aRenvoyer;
	}

} // class Relation
