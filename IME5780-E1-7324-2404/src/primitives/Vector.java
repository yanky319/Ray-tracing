package primitives;

import java.util.Objects;

/**
 *  Class Vector representing a Vector in 3D
 */
public class Vector {
    Point3D point;

    //*********************************** constructors ***************
    /**
     * Vector constructor receiving coordinates representing the head of the Vector
     *
     * @param _x coordinate
     * @param _y coordinate
     * @param _z coordinate
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) throws IllegalArgumentException  {
      Point3D p =  new Point3D(_x,_y,_z);
      if (p.equals(Point3D.ZERO))
          throw new IllegalArgumentException();
        this.point = p;
    }

    /**
     * Vector constructor receiving coordinate values representing the head of the Vector
     *
     * @param _x coordinate value
     * @param _y coordinate value
     * @param _z coordinate value
     */
    public Vector(double _x, double _y, double _z) throws IllegalArgumentException  {
        Point3D p =  new Point3D(_x,_y,_z);
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        this.point = p;
    }

    /**
     * Vector constructor receiving a point representing the head of the Vector
     *
     * @param point
     */
    public Vector(Point3D point)throws NullPointerException,IllegalArgumentException {
        if(point == null) {
            throw new NullPointerException();
        }
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        this.point = new Point3D(point);
    }

    /**
     * Vector copy constructor receiving
     *
     * @param vec
     */
    public Vector(Vector vec)throws NullPointerException {
        if(vec == null) {
            throw new NullPointerException();
        }
         this.point = new Point3D(vec.point);
    }

    //*********************************** getter ***************

    /**
     * Point3D getter
     *
     * @return point
     */
    public Point3D getPoint() {
        return new Point3D(point);
    }

    //************** functions ******************

    /**
     * adds 2 vectors
     *
     * @param vec Second vector
     * @return new Vector equal to the connection between the two vectors
     * @throws NullPointerException In case of second vector is null
     */
    public Vector add(Vector vec)throws NullPointerException {
        if (vec == null) {
            throw new NullPointerException();
        }
        return new Vector(
                this.point.get_x().get() + vec.point.get_x().get(),
                this.point.get_y().get() + vec.point.get_y().get(),
                this.point.get_z().get() + vec.point.get_z().get());
    }

    /**
     * subtracts 2 vectors
     *
     * @param vec Second vector
     * @return new Vector equal to the subtraction between the two vectors
     * @throws NullPointerException In case of second vector is null
     */
    public Vector subtract(Vector vec)throws NullPointerException {
        if (vec == null) {
            throw new NullPointerException();
        }
        return new Vector(
                this.point.get_x().get() - vec.point.get_x().get(),
                this.point.get_y().get() - vec.point.get_y().get(),
                this.point.get_z().get() - vec.point.get_z().get());
    }

    /**
     *Multiplication of a vector in scalar
     *
     * @param scale the scalar
     * @return new vector equal to the multiplication result
     */
    public Vector scale (double scale ){
        return new Vector(
                this.point.get_x().get() * scale,
                this.point.get_y().get() * scale,
                this.point.get_z().get() * scale);
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
        return this.point.get_x().get() * vec.point.get_x().get()
            + this.point.get_y().get() * vec.point.get_y().get()
            + this.point.get_z().get() * vec.point.get_z().get();
    }

    /**
     * Vector multiplication (cross Product) of two vectors
     *
     * @param vec Second vector
     * @return The result of the cross Product
     * @throws NullPointerException In case of second vector is null
     */
    public Vector crossProduct(Vector vec)throws NullPointerException {
        if (vec == null) {
            throw new NullPointerException();
        }
        Point3D u = this.point;
        Point3D v = vec.point;
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
        return this.point.distanceSquared(Point3D.ZERO);
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
        this.point = new Vector(this.scale(1/this.length())).point;
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
                "point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return getPoint().equals(vector.getPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPoint());
    }
}
