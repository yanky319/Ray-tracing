package primitives;

import java.util.Objects;

/**
 * Class Vector representing a Vector in 3D.
 */
public class Vector {
    /**
     * point of the head of the vector
     */
    protected Point3D _head;

    //*********************************** constructors ***************

    /**
     * Vector constructor receiving coordinates representing the head of the Vector.
     *
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @throws NullPointerException     In case of one of the coordinates is null
     * @throws IllegalArgumentException In case of all the coordinates values are zero
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) throws NullPointerException, IllegalArgumentException {
        Point3D p = new Point3D(x, y, z);
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("ERROR zero vector is Illegal");
        this._head = p;
    }

    /**
     * Vector constructor receiving coordinate values representing the head of the Vector.
     *
     * @param x coordinate value
     * @param y coordinate value
     * @param z coordinate value
     * @throws IllegalArgumentException In case of all the values are zero
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        Point3D p = new Point3D(x, y, z);
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("ERROR zero vector is Illegal");
        this._head = p;
    }

    /**
     * Vector constructor receiving a point representing the head of the Vector.
     *
     * @param point point representing the head of the Vector
     * @throws NullPointerException     In case of point is null
     * @throws IllegalArgumentException In case of point is The beginning of the contractions
     */
    public Vector(Point3D point) throws NullPointerException, IllegalArgumentException {
        if (point == null)
            throw new NullPointerException("ERROR arguments is NULL");
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("ERROR zero vector is Illegal");
        this._head = new Point3D(point);
    }

    /**
     * Vector copy constructor receiving.
     *
     * @param vec  object to be copied
     * @throws NullPointerException In case of vec5 is null
     */
    public Vector(Vector vec) throws NullPointerException {
        if (vec == null) {
            throw new NullPointerException("ERROR arguments is NULL");
        }
        this._head = new Point3D(vec._head);
    }

    //*********************************** getter ***************

    /**
     * Point3D head getter.
     *
     * @return point
     */
    public Point3D get_head() {
        return new Point3D(_head);
    }

//************** functions ******************

    /**
     * adds 2 vectors.
     *
     * @param vec Second vector
     * @return new Vector equal to the connection between the two vectors
     * @throws NullPointerException     In case of second vector is null
     * @throws IllegalArgumentException When the connection result is the zero vector
     */
    public Vector add(Vector vec) throws NullPointerException, IllegalArgumentException {
        if (vec == null) {
            throw new NullPointerException("ERROR arguments is NULL");
        }
        return new Vector(
                this._head.get_x().get() + vec._head.get_x().get(),
                this._head.get_y().get() + vec._head.get_y().get(),
                this._head.get_z().get() + vec._head.get_z().get());
    }

    /**
     * subtracts 2 vectors.
     *
     * @param vec Second vector
     * @return new Vector equal to the subtraction between the two vectors
     * @throws NullPointerException     In case of second vector is null
     * @throws IllegalArgumentException When the subtraction result is the zero vector
     */
    public Vector subtract(Vector vec) throws NullPointerException, IllegalArgumentException {
        if (vec == null) {
            throw new NullPointerException("ERROR arguments is NULL");
        }
        return new Vector(
                this._head.get_x().get() - vec._head.get_x().get(),
                this._head.get_y().get() - vec._head.get_y().get(),
                this._head.get_z().get() - vec._head.get_z().get());
    }

    /**
     * Multiplication of a vector with scalar.
     *
     * @param scale the scalar
     * @return new vector equal to the multiplication result
     * @throws IllegalArgumentException When the multiplication result is the zero vector
     */
    public Vector scale(double scale) throws IllegalArgumentException {
        return new Vector(
                this._head.get_x().get() * scale,
                this._head.get_y().get() * scale,
                this._head.get_z().get() * scale);
    }

    /**
     * Scalar multiplication (dot Product) of two vectors.
     *
     * @param vec Second vector
     * @return The result of the dot Product
     * @throws NullPointerException In case of second vector is null
     */
    public double dotProduct(Vector vec) throws NullPointerException {
        if (vec == null) {
            throw new NullPointerException("ERROR arguments is NULL");
        }
        return this._head.get_x().get() * vec._head.get_x().get()
                + this._head.get_y().get() * vec._head.get_y().get()
                + this._head.get_z().get() * vec._head.get_z().get();
    }

    /**
     * Vector multiplication (cross Product) of two vectors.
     *
     * @param vec Second vector
     * @return The result of the cross Product
     * @throws NullPointerException     In case of second vector is null
     * @throws IllegalArgumentException When the cross Product result is the zero vector
     */
    public Vector crossProduct(Vector vec) throws NullPointerException, IllegalArgumentException {
        if (vec == null) {
            throw new NullPointerException("ERROR arguments is NULL");
        }
        Point3D u = this._head;
        Point3D v = vec._head;
        return new Vector(
                u.get_y().get() * v.get_z().get() - u.get_z().get() * v.get_y().get(),
                u.get_z().get() * v.get_x().get() - u.get_x().get() * v.get_z().get(),
                u.get_x().get() * v.get_y().get() - u.get_y().get() * v.get_x().get());
    }

    /**
     * get length of a vector squared.
     *
     * @return The length of a vector squared
     */
    public double lengthSquared() {
        return this._head.distanceSquared(Point3D.ZERO);
    }

    /**
     * get length of a vector.
     *
     * @return The length of a vector
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes vector.
     *
     * @return Same vector after normalization
     */
    public Vector normalize() {
        this._head = new Vector(this.scale(1 / this.length()))._head;
        return this;
    }

    /**
     * Normalizes vector.
     *
     * @return new vector that is equal to the original vector after normalization
     */
    public Vector normalized() {
        return new Vector(this).normalize();
    }


    //******************** Admin ****************

    @Override
    public String toString() {
        return "" + _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return get_head().equals(vector.get_head());
    }

}
