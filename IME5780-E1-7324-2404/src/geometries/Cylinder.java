package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;


/**
 * class Cylinder representing a Cylinder in 3D.
 */
public class Cylinder extends Tube {
    /**
     * the height of the Cylinder the distance between the top and bottom plane
     */
    private double _height;

    //*********************************** constructor ***************

    /**
     * Cylinder constructor receiving Cylinder radius value and the Cylinder height.
     * calls {@link Cylinder#Cylinder(Color, double, Ray, double)}.
     *
     * @param radius Cylinders radius
     * @param height Cylinders height
     * @param ray    Cylinders ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(double radius, Ray ray, double height) {
        this(Color.BLACK, radius, ray, height);
    }

    /**
     * Cylinders constructor receiving Cylinders radius value and the Cylinders height and emission light.
     * calls {@link Cylinder#Cylinder(Material, Color, double, Ray, double)}.
     *
     * @param emission Cylinders emission light
     * @param radius   Cylinders radius
     * @param height   Cylinders height
     * @param ray      Cylinders ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(Color emission, double radius, Ray ray, double height) throws IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, ray, height);
    }

    /**
     * Cylinders constructor receiving Cylinders radius value and the Cylinders height emission light and material.
     * calls {@link Tube#Tube(Material, Color, double, Ray)}.
     *
     * @param material Cylinders material
     * @param emission Cylinders emission light
     * @param radius   Cylinders radius
     * @param ray      Cylinders ray
     * @param height   Cylinders height
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(Material material, Color emission, double radius, Ray ray, double height) throws IllegalArgumentException {
        super(material, emission, radius, ray);
        if (height <= 0)
            throw new IllegalArgumentException("The Cylinder height must be greater than zero");
        _height = height;
    }

    //*********************************** Getters ***************

    /**
     * height getter.
     *
     * @return the Cylinder height
     */
    public double get_height() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        double t = this._axisRay.get_direction().dotProduct(point.subtract(this._axisRay.get_p0()));
        if (t == 0)
            return new Vector(this._axisRay.get_direction()).scale(-1).normalize();

        if (t == this._height)
            return new Vector(this._axisRay.get_direction()).normalize();

        Point3D o = new Point3D(this._axisRay.get_p0().add(this._axisRay.get_direction().scale(t)));
        Vector n = new Vector(point.subtract(o));
        return n.normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        // Step 1: intersect with tube
        List<GeoPoint> intersections = super.findIntersections(ray, maxDistance);
        Vector va = _axisRay.get_direction();
        Point3D A = _axisRay.get_p0();
        Point3D B = _axisRay.getPoint(_height);
        List<GeoPoint> intersectionsCylinder = null;
        if (intersections != null) {
            // Step 2: intersect is between caps
            double lowerBound, upperBound;
            for (GeoPoint gPoint : intersections) {
                lowerBound = va.dotProduct(gPoint.point.subtract(A));
                upperBound = va.dotProduct(gPoint.point.subtract(B));
                if (lowerBound > 0 && upperBound < 0) {
                    // the check for distance, if the intersection point is beyond the distance
                    if (alignZero(gPoint.point.distance(ray.get_p0()) - maxDistance) <= 0)
                        if (intersectionsCylinder == null)
                            intersectionsCylinder = new LinkedList<>();
                    intersectionsCylinder.add(gPoint);
                }
            }
        }

        // Step 3: intersect with each plane which belongs to the caps
        Plane planeA = new Plane(_material, _emission, A, va);
        Plane planeB = new Plane(_material, _emission, B, va);
        List<GeoPoint> intersectionPlaneA = planeA.findIntersections(ray, maxDistance);
        List<GeoPoint> intersectionPlaneB = planeB.findIntersections(ray, maxDistance);
        if (intersectionPlaneA == null && intersectionPlaneB == null)
            return intersectionsCylinder;
        // Step 4: intersect inside caps
        Point3D q3, q4;
        if (intersectionPlaneA != null) {
            q3 = intersectionPlaneA.get(0).point;
            if (q3.subtract(A).lengthSquared() < _radius * _radius) {
                if (intersectionsCylinder == null)
                    intersectionsCylinder = new LinkedList<>();
                intersectionsCylinder.add(intersectionPlaneA.get(0));
            }
        }
        if (intersectionPlaneB != null) {
            q4 = intersectionPlaneB.get(0).point;
            if (q4.subtract(B).lengthSquared() < _radius * _radius) {
                if (intersectionsCylinder == null)
                    intersectionsCylinder = new LinkedList<>();
                intersectionsCylinder.add(intersectionPlaneB.get(0));
            }
        }
        return intersectionsCylinder;
    }

    //******************** Admin ****************


    @Override
    public String
    toString() {
        return "height=" + _height +
                ", radius=" + _radius;

    }
}
