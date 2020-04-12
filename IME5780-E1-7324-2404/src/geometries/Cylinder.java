package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


/**
 * class Cylinder representing a Cylinder in 3D.
 */
public class Cylinder extends Tube {
    private float _height;

    //*********************************** constructor ***************

    /**
     * Cylinder constructor receiving Cylinder radius value and the Cylinder height.
     *
     * @param _radius Cylinder radius
     * @param height  Cylinder height
     * @param ray     Cylinder ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(double _radius, Ray ray, float height) throws IllegalArgumentException {
        super(_radius, ray);
        if (height <= 0)
            throw new IllegalArgumentException("The cylinder height must be greater than zero");
        _height = height;
    }

    //*********************************** Getters ***************

    /**
     * height getter.
     *
     * @return the Cylinder height
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
