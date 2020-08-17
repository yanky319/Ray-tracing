package geometries;

import primitives.*;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * class Sphere representing a Sphere in 3D
 */
public class Sphere extends RadialGeometry {
    /**
     * the center of the sphere
     */
    private final Point3D _center;

    //*********************************** constructor ***************

    /**
     * Sphere constructor receiving Sphere radius value and the Sphere Center point.
     * calls {@link geometries.Sphere#Sphere(Color, double, Point3D)}.
     *
     * @param radius Sphere radius value
     * @param po     Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(double radius, Point3D po) {
        this(Color.BLACK, radius, po);
    }

    /**
     * Sphere constructor receiving Sphere radius value and the Sphere Center point and emission light.
     * calls {@link geometries.Sphere#Sphere(Material, Color, double, Point3D)}.
     *
     * @param emission Spheres emission light
     * @param radius   Sphere radius value
     * @param po       Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(Color emission, double radius, Point3D po) throws NullPointerException, IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, po);
    }

    /**
     * Sphere constructor receiving Sphere radius value and the Sphere Center point and emission light.
     * calls {@link geometries.RadialGeometry#RadialGeometry(Material, Color, double)}.
     *
     * @param material Spheres material
     * @param emission Spheres emission light
     * @param radius   Sphere radius value
     * @param po       Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(Material material, Color emission, double radius, Point3D po) throws NullPointerException, IllegalArgumentException {
        super(material, emission, radius);
        if (po == null)
            throw new NullPointerException("ERROR Point arguments is NULL");
        _center = new Point3D(po);
        setBox();
    }
    //*********************************** getters ***************

    /**
     * point3D getter.
     *
     * @return Center point
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    //------------ Override functions -----------------------

    @Override
    public Vector getNormal(Point3D point) {
        return point.subtract(_center).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if (!boundaryBox.intersectBox(ray)) // if no intersection with the box return null
            return null;
        Vector u;
        try {
            u = this._center.subtract(ray.get_p0());
        } catch (IllegalArgumentException e) {  // Ray starts at the center of the sphere
            {
                double t = this._radius;
                if (alignZero(t - maxDistance) <= 0) // if the point is further then max distance
                    return List.of(new GeoPoint(this, ray.getPoint(t))); // the point on the Ray and the sphere
                return null;
            }

        }
        double tm = alignZero(ray.get_direction().dotProduct(u));
        double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));
        if (alignZero(d - this._radius) >= 0) // if(d>r) the ray does not intersect with the sphere
            return null;
        double th = alignZero(Math.sqrt(this._radius * this._radius - d * d));
        double t1 = tm + th;
        double t2 = tm - th;
        if ((alignZero(t1) > 0 || alignZero(t2) > 0) //if there is at least one intersection
                && ((alignZero(t1 - maxDistance) <= 0 || alignZero(t2 - maxDistance) <= 0))) { //and at least one intersection is closer then max distance
            List<GeoPoint> result = new LinkedList<>(); // only ad the ones where t>0
            if (alignZero(t1) > 0 && alignZero(t1 - maxDistance) <= 0)
                result.add(new GeoPoint(this, ray.getPoint(t1)));
            if (alignZero(t2) > 0 && alignZero(t2 - maxDistance) <= 0)
                result.add(new GeoPoint(this, ray.getPoint(t2)));
            return result;
        }
        return null;
    }

    @Override
    public void setBox() {
        // min value is the value of the coordinate mines the radius
        // max value is the value of the coordinate plus the radius
        boundaryBox = new Box(
                new Point3D(_center.get_x().get() - _radius,
                        _center.get_y().get() - _radius,
                        _center.get_z().get() - _radius),
                new Point3D(_center.get_x().get() + _radius,
                        _center.get_y().get() + _radius,
                        _center.get_z().get() + _radius));
    }

    //******************** Admin ****************

    @Override
    public String toString() {
        return "point: " + _center + ", radius: " + _radius;

    }
}
