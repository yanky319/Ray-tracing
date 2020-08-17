package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * abstract class Intersectable for Intersectable objects.
 */
public abstract class Intersectable {
    /**
     * box of boundary values
     */
    protected Box boundaryBox;

    /**
     * internal class
     */
    public static class Box {
        /**
         * min values of x,y,z of the object
         */
        Point3D _min;
        /**
         * max values of x,y,z of the object
         */
        Point3D _max;
        /**
         * mid values of x,y,z of the object
         */
        Point3D _mid;
        /**
         * boolean flag is true if object is infinite
         */
        boolean isInfinite;
 //------------------ constructor ------------------
        /**
         * constructor receives min and max values and calculates the mid point
         * @param min Point of min values
         * @param max Point of max values
         */
        public Box(Point3D min, Point3D max) {
            _min = new Point3D(min);
            _max = new Point3D(max);
            // calculate the mid Point
            _mid = new Point3D(
                    (max.get_x().get() + min.get_x().get()) / 2d,
                    (max.get_y().get() + min.get_y().get()) / 2d,
                    (max.get_z().get() + min.get_z().get()) / 2d);

            // mark if the object is infinite
            if (max.get_x().get() == Double.MAX_VALUE ||
                    max.get_y().get() == Double.MAX_VALUE ||
                    max.get_z().get() == Double.MAX_VALUE)
                isInfinite = true;
            else
                isInfinite = false;
        }


        /**
         * AABB algorithm to check intersection of the ray and a boundary box.
         *
         * @param ray the ray that we are checking if it intersects the Box
         * @return true if the ray intersects the box
         */
        boolean intersectBox(Ray ray) {
            double tmin = (_min.get_x().get() - ray.get_p0().get_x().get()) / ray.get_direction().get_head().get_x().get();
            double tmax = (_max.get_x().get() - ray.get_p0().get_x().get()) / ray.get_direction().get_head().get_x().get();
            if (tmin > tmax) {
                tmax = swap(tmin, tmin = tmax);
            }
            double tymin = (_min.get_y().get() - ray.get_p0().get_y().get()) / ray.get_direction().get_head().get_y().get();
            double tymax = (_max.get_y().get() - ray.get_p0().get_y().get()) / ray.get_direction().get_head().get_y().get();
            if (tymin > tymax)
                tymax = swap(tymin, tymin = tymax);
            if (tmin > tymax || tymin > tmax)
                return false;
            if (tymin > tmin)
                tmin = tymin;
            if (tymax < tmax)
                tmax = tymax;
            double tzmin = (_min.get_z().get() - ray.get_p0().get_z().get()) / ray.get_direction().get_head().get_z().get();
            double tzmax = (_max.get_z().get() - ray.get_p0().get_z().get()) / ray.get_direction().get_head().get_z().get();
            if (tzmin > tzmax)
                tzmax = swap(tzmin, tzmin = tzmax);
            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            return true;
        }

        /**
         * fast swap.
         *
         * @param a first param
         * @param b second param
         * @return param a
         */
        double swap(double a, double b) {
            return a;
        }
    }


    /**
     * a class that connects between a point and the geometry it is part of
     * for the purpose of knowing the emission color of the point etc.
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
    public List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * calculates all intersections between a Ray and a intersectable objects
     * from beginning of the ray until a given distance.
     *
     * @param ray         the Ray to find intersections with
     * @param maxDistance the max distance value between the head of the ray and intersection point
     * @return list of GeoPoints that are intersections with the Ray
     */
    public abstract List<GeoPoint> findIntersections(Ray ray, double maxDistance);

    /**
     * set boundary values of the object.
     */
    public abstract void setBox();

}
