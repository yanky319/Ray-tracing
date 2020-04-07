package primitives;

import java.util.Objects;

public class Ray {
    private final Point3D _p0;
    private final Vector _direction;


    // ***************** Constructors ********************** //

    /**
     * Ray constructor receiving Point3D representing the start of the Ray and Vector representing the direction.
     *
     * @param _p0  Point3D representing the start of the Ray
     * @param _dir Vector representing the direction
     * @throws NullPointerException In case of one of the Arguments is null
     */
    public Ray(Point3D _p0, Vector _dir) throws NullPointerException {
        if (_p0 == null || _dir == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._p0 = new Point3D(_p0);
        this._direction = new Vector(_dir);
    }

    /**
     * Ray copy constructor
     *
     * @param ray  object to be copied
     * @throws NullPointerException In case of the Argument is null
     */
    public Ray(Ray ray) throws NullPointerException {
        if (ray == null)
            throw new NullPointerException("ERROR arguments is NULL");
        this._p0 = new Point3D(ray.get_p0());
        this._direction = new Vector(ray.get_direction());
    }

    // ***************** Getters ********************** //

    /**
     * direction getter.
     *
     * @return the direction
     */
    public Vector get_direction() {
        return new Vector(_direction);
    }

    /**
     * start point getter.
     *
     * @return the start point
     */
    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    //******************** Admin ****************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return this._p0.equals(ray.get_p0())
                && this._direction.equals(ray.get_direction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_p0(), get_direction());
    }

    @Override
    public String toString() {
        return "point " + _p0 +
                ", direction " + _direction;
    }

}











