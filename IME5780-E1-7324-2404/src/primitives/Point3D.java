package primitives;


import java.util.Objects;

/**
 * Class Point3D representing a Point in 3D.
 */
public class Point3D {
    /**
     * Coordinate on x axis.
     */
    protected Coordinate _x;
    /**
     * Coordinate on y axis.
     */
    protected Coordinate _y;
    /**
     * Coordinate on z axis.
     */
    protected Coordinate _z;

    /**
     * static point that is the beginning of the axises
     */
    public final static Point3D ZERO = new Point3D(0, 0, 0);

    //*********************************** constructors ***************

    /**
     * Point3D constructor receiving coordinates.
     *
     * @param x coordinate on x axis
     * @param y coordinate on y axis
     * @param z coordinate on z axis
     * @throws NullPointerException In case of one of the coordinates is null
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) throws NullPointerException {
        if (x == null || y == null || z == null) {
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        }
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }

    /**
     * Point3D constructor receiving a coordinate values.
     *
     * @param x value of coordinate on x axis
     * @param y value of coordinate on y axis
     * @param z value of coordinate on z axis
     */
    public Point3D(double x, double y, double z) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }

    /**
     * Copy constructor for Point3D.
     *
     * @param point object to be copied
     * @throws NullPointerException In case of point is null
     */
    public Point3D(Point3D point) throws NullPointerException {
        if (point == null) {
            throw new NullPointerException("ERROR arguments is NULL");
        }
        this._x = new Coordinate(point._x);
        this._y = new Coordinate(point._y);
        this._z = new Coordinate(point._z);
    }

    //*********************************** getters ***************

    /**
     * Coordinate x getter.
     *
     * @return x coordinate
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     * Coordinate y getter.
     *
     * @return y coordinate
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    /**
     * Coordinate z getter.
     *
     * @return z coordinate
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    //************** functions ******************

    /**
     * subtracts a point from a point.
     *
     * @param other point to subtracted
     * @return a vector from a point to a point
     * @throws NullPointerException     In case of "other" is null
     * @throws IllegalArgumentException When trying to subtract the point from itself
     */
    public Vector subtract(Point3D other) throws NullPointerException, IllegalArgumentException {
        if (other == null)
            throw new NullPointerException("ERROR arguments is NULL");
        return new Vector(
                this._x.get() - other._x.get(),
                this._y.get() - other._y.get(),
                this._z.get() - other._z.get());

    }

    /**
     * Adds a vector to a point.
     *
     * @param vec The vector
     * @return A new point that is shifted from the original point, the length and direction of the vector
     * @throws NullPointerException In case of Vector is null
     */
    public Point3D add(Vector vec) throws NullPointerException {
        if (vec == null)
            throw new NullPointerException("ERROR arguments is NULL");
        return new Point3D(
                this.get_x().get() + vec._head.get_x().get(),
                this.get_y().get() + vec._head.get_y().get(),
                this.get_z().get() + vec._head.get_z().get());
    }

    /**
     * Calculates the distance between two points squared.
     *
     * @param other Second point
     * @return result of the calculation
     * @throws NullPointerException In case of second Point is null
     */
    public double distanceSquared(Point3D other) throws NullPointerException {
        if (other == null)
            throw new NullPointerException("ERROR arguments is NULL");
        return (this.get_x().get() - other.get_x().get()) * (this.get_x().get() - other.get_x().get())
                + (this.get_y().get() - other.get_y().get()) * (this.get_y().get() - other.get_y().get())
                + (this.get_z().get() - other.get_z().get()) * (this.get_z().get() - other.get_z().get());
    }

    /**
     * Calculates the distance between two points.
     *
     * @param other Second point
     * @return result of the calculation
     * @throws NullPointerException In case of second Point is null
     */
    public double distance(Point3D other) throws NullPointerException {
        if (other == null)
            throw new NullPointerException("ERROR arguments is NULL");
        return Math.sqrt(this.distanceSquared(other));
    }

    //******************** Admin ****************

    @Override
    public String toString() {
        return "(" + _x +
                "," + _y +
                "," + _z + ")";
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

}
