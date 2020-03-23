package primitives;

/*
*
לבדוק האם צריך בהכרזת פונקציה להודיע על חריגה שנזרקת לא בצורה ישירה
חיסור בין שני נקודות האם מחזיר ווקטור מהראשית
הבדיקה שמיכל עשתה בבנאי של קרן
* */

import java.util.Objects;

/**
 * Class Point3D representing a Point in 3D
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
   public final static Point3D ZERO = new Point3D(0,0,0);

    //*********************************** constructors ***************
    /**
     * Point3D constructor receiving coordinates
     *
     * @param _x coordinate
     * @param _y coordinate
     * @param _z coordinate
     * @throws NullPointerException In case of one of the coordinates is null
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z)throws NullPointerException {
        if(_x == null || _y == null || _z == null ) {
            throw new NullPointerException();
        }
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * Point3D constructor receiving a coordinate values
     *
     * @param _x coordinate value
     * @param _y coordinate value
     * @param _z coordinate value
     */
    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * Copy constructor for Point3D
     *
     * @param point
     * @throws NullPointerException In case of point is null
     */
    public Point3D(Point3D point)throws NullPointerException {
        if(point == null) {
            throw new NullPointerException();
        }
        this._x = new Coordinate(point.get_x());
        this._y = new Coordinate(point.get_y());
        this._z = new Coordinate(point.get_z());
    }

    //*********************************** getters ***************

    /**
     * Coordinate getter
     *
     * @return x coordinate
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }
    /**
     * Coordinate getter
     *
     * @return y coordinate
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }
    /**
     * Coordinate getter
     *
     * @return z coordinate
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    //************** functions ******************

    public Vector subtract(Point3D other) { //***************************************
     return new Vector(
             this._x.get(),
             this._x.get(),
             this._x.get());

    }

    /**
     * Adds a vector to a point
     *
     * @param vec The vector
     * @return A new point that is shifted from the original point, the length and direction of the vector
     * @throws NullPointerException In case of Vector is null
     */
    public Point3D add(Vector vec)throws NullPointerException {
        if(vec == null)
            throw new NullPointerException();
        return new Point3D(
                this.get_x().get()+vec._head.get_x().get(),
                this.get_y().get()+vec._head.get_y().get(),
                this.get_z().get()+vec._head.get_z().get());
    }

    /**
     *Calculates the distance between two points squared
     *
     * @param other Second point
     * @return result of the calculation
     * @throws NullPointerException In case of second Point is null
     */
    public double distanceSquared(Point3D other)throws NullPointerException {
        if(other == null)
            throw new NullPointerException();
        return (this.get_x().get() - other.get_x().get()) * (this.get_x().get() - other.get_x().get())
                + (this.get_y().get() - other.get_y().get()) * (this.get_y().get() - other.get_y().get())
                + (this.get_z().get() - other.get_z().get()) * (this.get_z().get() - other.get_z().get());
    }

    /**
     *Calculates the distance between two points
     *
     * @param other Second point
     * @return result of the calculation
     * @throws NullPointerException In case of second Point is null
     */
    public double distance (Point3D other)throws NullPointerException {
        if(other == null)
            throw new NullPointerException();
        return Math.sqrt(this.distanceSquared(other));
    }

    //******************** Admin ****************

    @Override
    public String toString() {
        return "Point3D{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", _z=" + _z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Point3D)) return false;
        Point3D p = (Point3D) o;
        return _x.equals(p._x)
                && _y.equals(p._y)
                && _z.equals(p._z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_x(), get_y(), get_z());
    }
}
