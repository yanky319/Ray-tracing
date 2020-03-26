package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class Sphere representing a Sphere in 3D
 */
public class Sphere extends RadialGeometry {
    protected Point3D _center;

    /**
     * Sphere constructor
     * @param _radius Sphere radius value
     * @param po Sphere point
     */
    public Sphere(double _radius,Point3D po) {
        super(_radius);
        _center = po;
    }

    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     * point3D getter
     * @return point
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
