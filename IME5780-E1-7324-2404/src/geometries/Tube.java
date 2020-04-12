package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


/**
 * class Tube representing a Tube in 3D.
 */
public class Tube extends RadialGeometry {
    protected final Ray _axisRay;

    //*********************************** constructor ***************

    /**
     * Tube constructor receiving the radius and the axis ray.
     *
     * @param _radius Tube radius
     * @param ray     Tube axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(double _radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        super(_radius);
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

    //******************** Admin ****************

    @Override
    public String
    toString() {
        return "ray: " + _axisRay +
                ", radius: " + _radius;
    }
}
