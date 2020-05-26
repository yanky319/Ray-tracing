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
        public Geometry geometry; // the geometry it is part of
        public Point3D point;   //the point

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
    }

    /**
     * calculates intersections between a Ral and a intersectable object.
     *
     * @param ray the Ray to find intersections with
     * @return list of GeoPoints that are intersections with the Ray
     */
    List<GeoPoint> findIntersections(Ray ray);
}
