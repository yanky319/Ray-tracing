package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class Tube representing a Tube in 3D
 */
public class Tube extends RadialGeometry {
    protected Ray _axisRay;

    /**
     * Tube constructor
     * @param _radius Tube radius
     * @param ray Tube axis ray
     * @throws NullPointerException
     */
    public Tube(double _radius,Ray ray)throws NullPointerException {
        super(_radius);
        if(ray == null)
            throw new NullPointerException();
        this._axisRay = ray;
    }

    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     * _axisray getter
     * @return Tube axis ray
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
