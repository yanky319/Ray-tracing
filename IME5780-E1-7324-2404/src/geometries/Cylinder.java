package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


/**
 * class Cylinder representing a Cylinder in 3D.
 */
public class Cylinder extends RadialGeometry {
    protected final Ray _axisRay;

    //*********************************** constructor ***************

    /**
     * Cylinder constructor receiving the radius and the axis ray.
     *
     * @param _radius Cylinder radius
     * @param ray     Cylinder axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Cylinder(double _radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        super(_radius);
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
    public List<Point3D> findIntersections(Ray ray) {
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
