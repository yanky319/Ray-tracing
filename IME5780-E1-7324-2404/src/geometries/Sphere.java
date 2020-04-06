package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class Sphere representing a Sphere in 3D
 */
public class Sphere extends RadialGeometry {
    protected Point3D _center;

    //*********************************** constructor ***************

    /**
     * Sphere constructor receiving Sphere radius value and the Sphere Center point.
     *
     * @param _radius Sphere radius value
     * @param po      Sphere Center point
     * @throws NullPointerException     In case the Point3D arguments is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Sphere(double _radius, Point3D po) throws NullPointerException, IllegalArgumentException {
        super(_radius);
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

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    //******************** Admin ****************


    @Override
    public String toString() {
        return "point: " + _center + ", radius: " + _radius;

    }
}
