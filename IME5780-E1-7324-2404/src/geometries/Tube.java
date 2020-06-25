package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Tube representing a Tube in 3D.
 */
public class Tube extends RadialGeometry {
    /**
     * the axis of the Tube.
     */
    protected final Ray _axisRay;

    //*********************************** constructor ***************

    /**
     * Tube constructor receiving the radius and the axis ray.
     * calls {@link Tube#Tube(Color, double, Ray)}.
     *
     * @param radius Tubes radius
     * @param ray    Tubes axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(double radius, Ray ray) {
        this(Color.BLACK, radius, ray);
    }

    /**
     * Tube constructor receiving the radius and the axis ray and emission light.
     * calls {@link Tube#Tube(Material, Color, double, Ray)}.
     *
     * @param emission Tubes emission light
     * @param radius   Tubes radius
     * @param ray      Tubes axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(Color emission, double radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, ray);
    }

    /**
     * Tube constructor receiving the radius and the axis rays emission light and material.
     * calls {@link Tube#Tube(Color, double, Ray)}.
     *
     * @param material Tubes material
     * @param emission Tubes emission light
     * @param radius   Tube radius
     * @param ray      Tube axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(Material material, Color emission, double radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        super(material, emission, radius);
        if (ray == null)
            throw new NullPointerException("ERROR Ray arguments is NULL");
        this._axisRay = new Ray(ray);
    }

    //*********************************** Getters ***************

    /**
     * axis ray getter.
     *
     * @return Tube axis ray
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
