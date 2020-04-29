package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
/**
 * interface Intersectable for Intersectable objects.
 */
public interface Intersectable {

    /**
     * calculates intersections between a Ral and a intersectable object.
     *
     * @param ray the Ray to find intersections with
     * @return list of points that are intersections with the Ray
     */
    List<Point3D> findIntersections(Ray ray);
}
