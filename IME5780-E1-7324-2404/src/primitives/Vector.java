package primitives;

import java.util.Objects;

/**
 *  Class Vector representing a Vector in 3D
 */
public class Vector {
    Point3D _head;

    //*********************************** constructors ***************
    /**
     * Vector constructor receiving coordinates representing the head of the Vector
     *
     * @param _x coordinate
     * @param _y coordinate
     * @param _z coordinate
     * @throws NullPointerException In case of one of the coordinates is null
     * @throws IllegalArgumentException In case of all the coordinates values are zero
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) throws NullPointerException, IllegalArgumentException  {
      Point3D p =  new Point3D(_x,_y,_z);
      if (p.equals(Point3D.ZERO))
          throw new IllegalArgumentException();
        this._head = p;
    }

    /**
     * Vector constructor receiving coordinate values representing the head of the Vector
     *
     * @param _x coordinate value
     * @param _y coordinate value
     * @param _z coordinate value
     * @throws IllegalArgumentException In case of all the values are zero
     */
    public Vector(double _x, double _y, double _z) throws  IllegalArgumentException  {
        Point3D p =  new Point3D(_x,_y,_z);
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        this._head = p;
    }

    /**
     * Vector constructor receiving a point representing the head of the Vector
     *
     * @param point
     * @throws NullPointerException  In case of point is null
     * @throws IllegalArgumentException  In case of point is The beginning of the contractions
     */
    public Vector(Point3D point)throws NullPointerException,IllegalArgumentException {
        if(point == null) {
            throw new NullPointerException();
        }
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        this._head = new Point3D(point);
    }

    /**
     * Vector copy constructor receiving
     *
     * @param vec
     * @throws NullPointerException In case of vec5 is null
     */
    public Vector(Vector vec)throws NullPointerException {
        if(vec == null) {
            throw new NullPointerException();
        }
         this._head = new Point3D(vec._head);
    }

    //*********************************** getter ***************

    /**
     * Point3D getter
     *
     * @return point
     */
    public Point3D get_head() {
        return new Point3D(_head);
    }

//************** functions ******************

    /**
     * adds 2 vectors
     *
     * @param vec Second vector
     * @return new Vector equal to the connection between the two vectors
     * @throws NullPointerException In case of second vector is null
     */
    public Vector add(Vector vec)throws NullPointerException {/*************************************/
        if (vec == null) {
            throw new NullPointerException();
        }
        return new Vector(
                this._head.get_x().get() + vec._head.get_x().get(),
                this._head.get_y().get() + vec._head.get_y().get(),
                this._head.get_z().get() + vec._head.get_z().get());
    }

    /**
     * subtracts 2 vectors
     *
     * @param vec Second vector
     * @return new Vector equal to the subtraction between the two vectors
     * @throws NullPointerException In case of second vector is null
     */
    public Vector subtract(Vector vec)throws NullPointerException {/*************************************/
        if (vec == null) {
            throw new NullPointerException();
        }
        return new Vector(
                this._head.get_x().get() - vec._head.get_x().get(),
                this._head.get_y().get() - vec._head.get_y().get(),
                this._head.get_z().get() - vec._head.get_z().get());
    }

    /**
     *Multiplication of a vector with scalar
     *
     * @param scale the scalar
     * @return new vector equal to the multiplication result
     */
    public Vector scale (double scale ){/*************************************/
        return new Vector(
                this._head.get_x().get() * scale,
                this._head.get_y().get() * scale,
                this._head.get_z().get() * scale);
    }

    /**
     * Scalar multiplication (dot Product) of two vectors
     *
     * @param vec Second vector
     * @return The result of the dot Product
     * @throws NullPointerException In case of second vector is null
     */
    public double dotProduct(Vector vec)throws NullPointerException {
        if (vec == null) {
            throw new NullPointerException();
        }
        return this._head.get_x().get() * vec._head.get_x().get()
            + this._head.get_y().get() * vec._head.get_y().get()
            + this._head.get_z().get() * vec._head.get_z().get();
    }

    /**
     * Vector multiplication (cross Product) of two vectors
     *
     * @param vec Second vector
     * @return The result of the cross Product
     * @throws NullPointerException In case of second vector is null
     */
    public Vector crossProduct(Vector vec)throws NullPointerException {/*************************************/
        if (vec == null) {
            throw new NullPointerException();
        }
        Point3D u = this._head;
        Point3D v = vec._head;
        return new Vector(
                u.get_y().get()*v.get_z().get() - u.get_z().get()*v.get_y().get(),
                u.get_z().get()*v.get_x().get() - u.get_x().get()*v.get_z().get(),
                u.get_x().get()*v.get_y().get() - u.get_y().get()*v.get_x().get());
    }

    /**
     *
     * @return The length of a vector squared
     */
    public double lengthSquared(){
        return this._head.distanceSquared(Point3D.ZERO);
    }

    /**
     *
     * @return The length of a vector
     */
    public double length(){
        return  Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes vector
     *
     * @return Same vector after normalization
     */
    public Vector normalize(){
        this._head = new Vector(this.scale(1/this.length()))._head;
        return this;
    }
    /**
     * Normalizes vector
     *
     * @return new vector that is equal to the original vector after normalization
     */
     public Vector normalized(){
        return new Vector(this).normalize();
     }


    //******************** Admin ****************

    @Override
    public String toString() {
        return "Vector{" +
                "point=" + _head +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return get_head().equals(vector.get_head());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_head());
    }
}
