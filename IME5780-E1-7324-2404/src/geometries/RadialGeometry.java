package geometries;


/**
 * abstract Class RadialGeometry representing a Radial Geometry objects.
 */
public abstract class RadialGeometry implements Geometry {
    protected double _radius;

    //*********************************** constructors ***************


    /**
     * RadialGeometry constructor receiving radius value.
     *
     * @param _radius radius value
     * @throws IllegalArgumentException In case of the height argument is not greater than zero
     */
    public RadialGeometry(double _radius) throws IllegalArgumentException {
        if (_radius <= 0)
            throw new IllegalArgumentException("The radius must be greater than zero");
        this._radius = _radius;
    }

    /**
     * RadialGeometry copy constructor.
     *
     * @param radialGeometry object to be copied
     * @throws NullPointerException In case of one of the argument is null
     */
    public RadialGeometry(RadialGeometry radialGeometry) throws NullPointerException {
        if (radialGeometry == null)
            throw new NullPointerException("ERROR arguments is NULL");
        this._radius = radialGeometry._radius;
    }

    //*********************************** getter ***************

    /**
     * radius getter.
     *
     * @return the radius
     */
    public double get_radius() {
        return _radius;
    }
}
