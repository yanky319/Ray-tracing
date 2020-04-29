package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


/**
 * class Tube representing a Tube in 3D.
 */
public class Tube extends Cylinder {
    private float _height;

    //*********************************** constructor ***************

    /**
     * Tube constructor receiving Tube radius value and the Tube height.
     *
     * @param _radius Tube radius
     * @param height  Tube height
     * @param ray     Tube ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Tube(double _radius, Ray ray, float height) throws IllegalArgumentException {
        super(_radius, ray);
        if (height <= 0)
            throw new IllegalArgumentException("The Tube height must be greater than zero");
        _height = height;
    }

    //*********************************** Getters ***************

    /**
     * height getter.
     *
     * @return the Tube height
     */
    public float get_height() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        double t = this._axisRay.get_direction().dotProduct(point.subtract(this._axisRay.get_p0()));
        if(t == 0)
            return new Vector(this._axisRay.get_direction()).scale(-1).normalize();

        if(t == this._height)
            return new Vector(this._axisRay.get_direction()).normalize();

        Point3D o = new Point3D(this._axisRay.get_p0().add(this._axisRay.get_direction().scale(t)));
        Vector n = new Vector(point.subtract(o));
        return n.normalize();
    }

    //******************** Admin ****************


    @Override
    public String
    toString() {
        return "height=" + _height +
                ", radius=" + _radius;

    }
}
