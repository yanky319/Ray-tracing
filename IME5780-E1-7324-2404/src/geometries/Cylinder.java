package geometries;

import primitives.*;
import primitives.Color;

import java.awt.*;
import java.util.List;


/**
 * class Cylinder representing a Cylinder in 3D.
 */
public class Cylinder extends RadialGeometry {
    /**
     * the axis of the Cylinder.
     */
    protected final Ray _axisRay;

    //*********************************** constructor ***************

    /**
     * Cylinder constructor receiving the radius and the axis ray.
     * calls {@link geometries.Cylinder#Cylinder(Color, double, Ray)}.
     *
     * @param radius Cylinder radius
     * @param ray    Cylinder axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Cylinder(double radius, Ray ray) {
        this(Color.BLACK, radius, ray);
    }

    /**
     * Cylinder constructor receiving the radius and the axis ray and emission light.
     * calls {@link geometries.Cylinder#Cylinder(Material, Color, double, Ray)}.
     *
     * @param emission Cylinders emission light
     * @param radius   Cylinder radius
     * @param ray      Cylinder axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Cylinder(Color emission, double radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, ray);
    }

    /**
     * Cylinder constructor receiving the radius and the axis rays emission light and material.
     * calls {@link geometries.Cylinder#Cylinder(Color, double, Ray)}.
     *
     * @param material Cylinders material
     * @param emission Cylinders emission light
     * @param radius   Cylinder radius
     * @param ray      Cylinder axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Cylinder(Material material, Color emission, double radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        super(material, emission, radius);
        if (ray == null)
            throw new NullPointerException("ERROR Ray arguments is NULL");
        this._axisRay = new Ray(ray);
    }

    //*********************************** Getters ***************

    /**
     * axis ray getter.
     *
     * @return Cylinder axis ray
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }


    @Override
    public Vector getNormal(Point3D point) {
        double t = this._axisRay.get_direction().dotProduct(point.subtract(this._axisRay.get_p0()));
        Point3D o = new Point3D(this._axisRay.get_p0().add(this._axisRay.get_direction().scale(t)));
        Vector n = new Vector(point.subtract(o));
        return n.normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        return null;
    }

    //******************** Admin ****************

    @Override
    public String
    toString() {
        return "ray: " + _axisRay +
                ", radius: " + _radius;
    }
}
