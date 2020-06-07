package primitives;

import java.util.Objects;

/**
 * class Ray representing a ray from a point in a specific direction.
 */
public class Ray {
    /**
     * a small const value used for moving head of ray to be sure we are not in a bad position
     * for example moving intersection point
     * before checking if it is shaded by another object so we doe not accidentally find it is shading it self
     */
    private static final double DELTA = 0.1;
    /**
     * start point of the ray
     */
    private final Point3D _p0;
    /**
     * the direction vector of the ray
     */
    private final Vector _direction;


    // ***************** Constructors ********************** //

    /**
     * Ray constructor receiving Point3D representing the start of the Ray and Vector representing the direction.
     *
     * @param p0  Point3D representing the start of the Ray
     * @param dir Vector representing the direction
     * @throws NullPointerException In case of one of the Arguments is null
     */
    public Ray(Point3D p0, Vector dir) throws NullPointerException {
        if (p0 == null || dir == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._p0 = new Point3D(p0);
        this._direction = new Vector(dir).normalize();
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

    /**
     * construct ray with head moved a little bit on normal vector.
     *
     * @param point original head point
     * @param direction direction of the ray
     * @param normal normal vector
     */
    public Ray(Point3D point, Vector direction, Vector normal) {
        //_p0 = _p0 + normal.scale(±DELTA)
        double nv = normal.dotProduct(direction);
        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = point.add(normalDelta);
        _direction = new Vector(direction).normalized();
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

    /**
     * calculates a point on the Ray with distance t from the head of the Ray.
     *
     * @param t the distance wanted
     * @return the point calculated
     */
    public Point3D getPoint(double t){
        return this.get_p0().add(this.get_direction().scale(t));
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











