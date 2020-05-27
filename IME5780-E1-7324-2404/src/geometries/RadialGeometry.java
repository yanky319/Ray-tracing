package geometries;


import primitives.Color;
import primitives.Material;

/**
 * abstract Class RadialGeometry representing a Radial Geometry objects.
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * the radius of the geometry.
     */
    protected double _radius;

    //*********************************** constructors ***************

    /**
     * RadialGeometry constructor receiving radius value.
     * calls {@link geometries.RadialGeometry#RadialGeometry(Color, double)}.
     *
     * @param radius radius value
     * @throws IllegalArgumentException In case of the height argument is not greater than zero
     */
    public RadialGeometry(double radius){
        this(Color.BLACK, radius);
    }

    /**
     * RadialGeometry constructor receiving radius value.
     * calls {@link geometries.RadialGeometry#RadialGeometry(Material, Color, double)}.
     *
     *  @param emission RadialGeometry's emission light
     * @param radius radius value
     * @throws IllegalArgumentException In case of the height argument is not greater than zero
     */
    public RadialGeometry(Color emission, double radius) throws IllegalArgumentException {
      this(new Material(0,0,0), emission, radius);
    }

    /**
     * RadialGeometry constructor receiving radius value.
     * calls {@link geometries.Geometry#Geometry(Material, Color)}.
     *
     * @param material RadialGeometry's material
     * @param emission RadialGeometry's emission light
     * @param radius radius value
     * @throws IllegalArgumentException In case of the height argument is not greater than zero
     */
    public RadialGeometry(Material material, Color emission, double radius) throws IllegalArgumentException {
        super(material, emission);
        if (radius <= 0)
            throw new IllegalArgumentException("The radius must be greater than zero");
        this._radius = radius;
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
