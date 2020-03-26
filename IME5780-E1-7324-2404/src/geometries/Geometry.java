package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry for Geometry objects.
 */
public interface Geometry {
    /**
     * Returns a vector perpendicular to the plane at the given point.
     *
     * @param point a given point
     * @return vector perpendicular to the plane
     */
    public Vector getNormal(Point3D point);
}
