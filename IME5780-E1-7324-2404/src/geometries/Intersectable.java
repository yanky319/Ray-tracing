package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * interface Intersectable for Intersectable objects.
 */
public interface Intersectable {

    /**
     * a class that connects between a point and the geometry it is part of
     * for the purpose of knowing the emission color of the point.
     */
    public static class GeoPoint {
        /**
         * the geometry it is part of
         */
        public Geometry geometry;
        /**
         * the point
         */
        public Point3D point;

        // ------------ constructor -------------

        /**
         * constructor for GeoPoint that gets 2 parameters.
         *
         * @param geo the Geometry that the point is pat of
         * @param p   the point
         */
        public GeoPoint(Geometry geo, Point3D p) {
            this.geometry = geo;
            this.point = p;
        }

        //--------------- admin ------------

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return (this.geometry != null && this.geometry.equals(((GeoPoint) o).geometry)) &&
                    this.point != null && this.point.equals(((GeoPoint) o).point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

//    /**
//     * calculates intersections between a Ral and a intersectable object.
//     *
//     * @param ray the Ray to find intersections with
//     * @return list of GeoPoints that are intersections with the Ray
//     */
//    List<GeoPoint> findIntersections(Ray ray);
//    /**
//     * calculates intersections between a Ral and a intersectable object.
//     *
//     * @param ray the Ray to find intersections with
//     * @return list of GeoPoints that are intersections with the Ray
//     */
//    List<GeoPoint> findIntersections(Ray ray, double maxDistance);

    /**
     * calculates all intersections between a Ray and a intersectable objects
     * from beginning of the ray all a long.
     * calls {@link geometries.Intersectable#findIntersections(Ray, double)}
     * with Double.POSITIVE_INFINITY as the highest distance value
     *
     * @param ray the Ray to find intersections with
     * @return list of GeoPoints that are intersections with the Ray
     */
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * calculates all intersections between a Ray and a intersectable objects
     * from beginning of the ray until a given distance.
     *
     * @param ray the Ray to find intersections with
     * @param maxDistance the max distance value between the head of the ray and intersection point
     * @return list of GeoPoints that are intersections with the Ray
     */
    List<GeoPoint> findIntersections(Ray ray, double maxDistance);

}
