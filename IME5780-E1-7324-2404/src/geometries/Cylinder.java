package geometries;

import primitives.*;


/**
 * class Cylinder representing a Cylinder in 3D.
 */
public class Cylinder extends Tube {
    /**
     * the height of the Cylinder the distance between the top and bottom plane
     */
    private float _height;

    //*********************************** constructor ***************

    /**
     * Cylinder constructor receiving Cylinder radius value and the Cylinder height.
     * calls {@link Cylinder#Cylinder(Color, double, Ray, float)}.
     *
     * @param radius Cylinders radius
     * @param height Cylinders height
     * @param ray    Cylinders ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(double radius, Ray ray, float height) {
        this(Color.BLACK, radius, ray, height);
    }

    /**
     * Cylinders constructor receiving Cylinders radius value and the Cylinders height and emission light.
     * calls {@link Cylinder#Cylinder(Material, Color, double, Ray, float)}.
     *
     * @param emission Cylinders emission light
     * @param radius   Cylinders radius
     * @param height   Cylinders height
     * @param ray      Cylinders ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(Color emission, double radius, Ray ray, float height) throws IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, ray, height);
    }

    /**
     * Cylinders constructor receiving Cylinders radius value and the Cylinders height emission light and material.
     * calls {@link Tube#Tube(Material, Color, double, Ray)}.
     *
     * @param material Cylinders material
     * @param emission Cylinders emission light
     * @param radius   Cylinders radius
     * @param height   Cylinders height
     * @param ray      Cylinders ray
     * @throws IllegalArgumentException In case of the height or radius argument is not greater than zero
     */
    public Cylinder(Material material, Color emission, double radius, Ray ray, float height) throws IllegalArgumentException {
        super(material, emission, radius, ray);
        if (height <= 0)
            throw new IllegalArgumentException("The Cylinder height must be greater than zero");
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
