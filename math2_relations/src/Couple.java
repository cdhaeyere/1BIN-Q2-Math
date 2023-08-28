/**
 * Couples d'éléments de l'univers
 *
 * @author M.Marchand
 * @version mars 2009
 */


public class Couple {
    private Elt x;
    private Elt y;

    public Couple(Elt x, Elt y) {
        if (x == null) throw new IllegalArgumentException("paramètre null");
        if (y == null) throw new IllegalArgumentException("paramètre null");
        this.x = x;
        this.y = y;
    }

    public Couple(int x, int y) {
        this(new Elt(x), new Elt(y));
    }

    public Elt getX() {
        return this.x;
    }

    public Elt getY() {
        return this.y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Couple reciproque() {
        return new Couple(y, x);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Couple other = (Couple) obj;
        return x.equals(other.x) && y.equals(other.y);
    }

}