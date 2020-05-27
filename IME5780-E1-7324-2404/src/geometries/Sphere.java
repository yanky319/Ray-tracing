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
     * @param po      Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(double radius, Point3D po){
        this(Color.BLACK, radius, po);
    }

    /**
     * Sphere constructor receiving Sphere radius value and the Sphere Center point and emission light.
     * calls {@link geometries.Sphere#Sphere(Material, Color, double, Point3D)}.
     *
     * @param emission Spheres emission light
     * @param radius Sphere radius value
     * @param po      Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(Color emission, double radius, Point3D po) throws NullPointerException, IllegalArgumentException {
        this(new Material(0,0,0), emission, radius, po);
    }

    /**
     * Sphere constructor receiving Sphere radius value and the Sphere Center point and emission light.
     * calls {@link geometries.RadialGeometry#RadialGeometry(Material, Color, double)}.
     *
     * @param material Spheres material
     * @param emission Spheres emission light
     * @param radius Sphere radius value
     * @param po      Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(Material material, Color emission, double radius, Point3D po) throws NullPointerException, IllegalArgumentException {
        super(material, emission, radius);
        if (po == null)
            throw new NullPointerException("ERROR Point arguments is NULL");
        _center = new Point3D(po);
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
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector u;
        try {
            u = this._center.subtract(ray.get_p0());
        } catch (IllegalArgumentException e) {  // Ray starts at the center of the sphere
            return List.of(new GeoPoint(this, ray.getPoint(this._radius))); // the point on the Ray and the sphere
        }
        double tm = alignZero(ray.get_direction().dotProduct(u));
        double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));
        if (alignZero(d - this._radius) >= 0) // if(d>r) the ray does not intersect with the sphere
            return null;
        double th = alignZero(Math.sqrt(this._radius * this._radius - d * d));
        if (alignZero(tm + th) > 0 || alignZero(tm - th) > 0) { //if there are at least one intersection
            List<GeoPoint> result = new LinkedList<>(); // only ad the ones where t>0
            if (alignZero(tm + th) > 0)
                result.add(new GeoPoint(this, ray.getPoint(tm + th)));
            if (alignZero(tm - th) > 0)
                result.add(new GeoPoint(this, ray.getPoint(tm - th)));
            return result;
        }
        return null;
    }

    //******************** Admin ****************

    @Override
    public String toString() {
        return "point: " + _center + ", radius: " + _radius;

    }
}
