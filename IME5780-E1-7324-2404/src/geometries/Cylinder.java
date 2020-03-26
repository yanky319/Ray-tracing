package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class Cylinder representing a Cylinder in 3D
 */
public class Cylinder extends RadialGeometry {
    protected float _height;

    /**
     * Cylinder constructor
     * @param _radius Cylinder radius
     * @param height Cylinder height
     * @throws IllegalArgumentException
     */
    public Cylinder(double _radius,float height)throws IllegalArgumentException {
        super(_radius);
        if (height == 0)
            throw new IllegalArgumentException("The cylinder height must be greater than zero");
        _height = height;
    }

    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     *  height getter
     * @return the Cylinder height
     */
    public float get_height() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
