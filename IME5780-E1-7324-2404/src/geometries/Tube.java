package geometries;

import primitives.*;


/**
 * class Tube representing a Tube in 3D.
 */
public class Tube extends Cylinder {
    /**
     * the height of the tube the distance between the top and bottom plane
     */
    private float _height;

    //*********************************** constructor ***************

    /**
     * Tube constructor receiving Tube radius value and the Tube height.
     * calls {@link geometries.Tube#Tube(Color, double, Ray, float)}.
     *
     * @param radius Tube radius
     * @param height Tube height
     * @param ray    Tube ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Tube(double radius, Ray ray, float height) {
        this(Color.BLACK, radius, ray, height);
    }

    /**
     * Tube constructor receiving Tube radius value and the Tube height and emission light.
     * calls {@link geometries.Tube#Tube(Material, Color, double, Ray, float)}.
     *
     * @param emission Tubes emission light
     * @param radius   Tube radius
     * @param height   Tube height
     * @param ray      Tube ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Tube(Color emission, double radius, Ray ray, float height) throws IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, ray, height);
    }

    /**
     * Tube constructor receiving Tube radius value and the Tube height emission light and material.
     * calls {@link geometries.Cylinder#Cylinder(Material, Color, double, Ray)}.
     *
     * @param material Tubes material
     * @param emission Tubes emission light
     * @param radius   Tube radius
     * @param height   Tube height
     * @param ray      Tube ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Tube(Material material, Color emission, double radius, Ray ray, float height) throws IllegalArgumentException {
        super(material, emission, radius, ray);
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
        if (t == 0)
            return new Vector(this._axisRay.get_direction()).scale(-1).normalize();

        if (t == this._height)
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
