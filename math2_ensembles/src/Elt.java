/** Classe Elt
	 Classe d'objets dont les "valeurs" sont 1,2,3,...,MAXELT,
	 organisée de manière "circulaire" par les méthodes succ et pred.

 	 @author  M.Marchand
 	 @version Mars 2008
*/ 
   import java.util.*;
import java.io.*;

    public class Elt {
   
   	/** valeur numérique de MAXELT */
      private static final int MAXELTVALUE = 50;
      public  static final Elt MAXELT = new Elt(MAXELTVALUE);
      private int val;
   
   	/** constructeur par recopie */    
       public Elt(Elt e) {
    	 if (e == null) throw new IllegalArgumentException();
         this.val = e.val();
      }
   
      /** constructeur à partir d'un int n;
   	    génère une (runtime) exception si n est hors Univers */
       public Elt(int n) {
         if (n > MAXELTVALUE || n < 1) {
            throw new IllegalArgumentException("Hors Univers : " + n + " (1.." + MAXELTVALUE + ")");
         }
         this.val=n;
      }
   
      /** renvoie la valeur numérique */
       public int val() {
         return this.val;
      }
   
       public String toString() {
         return "" + this.val();
      }  
   
   
   
      @Override
	public int hashCode() {	
		return val;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elt other = (Elt) obj;
		if (val != other.val)
			return false;
		return true;
	}

	/** renvoie le successeur de l'Elt courant dans l'Univers "circulaire";
   		 le successeur de MAXELT est 1 */
       public Elt succ() {
         if (this.val() < MAXELTVALUE) {
            return new Elt(this.val()+1);
         }
         else {
            return new Elt(1);
         }
      }
   
      /** renvoie le prédécesseur de l'Elt courant dans l'Univers "circulaire";
   		 le prédécesseur de 1 est MAXELT */
       public Elt pred() {
         if ( this.val() == 1) {
            return new Elt(MAXELTVALUE);
         }
         else {
            return new Elt(this.val()-1);
         }
      }
   
   
   } // class