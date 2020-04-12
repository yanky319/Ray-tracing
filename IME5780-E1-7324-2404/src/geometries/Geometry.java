package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry for Geometry objects.
 */
public interface Geometry {
    /**
     * calculates the unit vector that is orthogonal to the Tube in a given point
     *
     * @param point a given point
     * @return orthogonal unit vector
     */
     Vector getNormal(Point3D point);
}
