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
        return null;
    }

    //******************** Admin ****************


    @Override
    public String
    toString() {
        return "height=" + _height +
                ", radius=" + _radius;

    }
}
