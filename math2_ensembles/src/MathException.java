/** Classe MathException
	 Classe d'exceptions (runtime) pour le cours de Math.

	 @author  M.Marchand
	 @version Mars 2002
*/

   import java.util.*;
   import java.io.*;

    public class MathException extends RuntimeException {
 
       public MathException() {
         super();
      }
   
       public MathException(String s) {
         super(s);
      }
   
   }