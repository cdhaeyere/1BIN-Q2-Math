/** Interface pour les classes Relation, Equivalence, Application, ...
	 Toute instance d'une classe implémentant cette interface est une relation d'un sous-ensemble
	 de l'Univers vers un sous-ensemble de l'Univers
	 
	 @author  M.Marchand
	 @version Mars 2008
*/
   

    public interface RelationInterface extends Iterable<Couple>{
    	int MAX = Elt.MAXELT.val();
   
   // renvoie true ssi la Relation courante est vide
       public boolean estVide();
   
   // renvoie true ssi le couple (x,y) appartient à la Relation courante
       //public boolean contient(Elt x, Elt y);
       public boolean contient(Couple c);
   
   // ajoute le couple (x,y) (éventuellement) à la Relation courante
       //public void ajouter(Elt x, Elt y);
       public void ajouter(Couple c);
    
   // enlève le couple (x,y) (éventuellement) de la Relation courante
       //public void enlever(Elt x, Elt y);
       public void enlever(Couple c);
   
   // renvoie l'ensemble de départ de la Relation courante
       public EnsembleAbstrait depart();
   
   // renvoie l'ensemble d'arrivée de la Relation courante
       public EnsembleAbstrait arrivee();
   
   // renvoie une chaîne de caractère décrivant la Relation courante
       public String toString();
   
   } // RelationInterface