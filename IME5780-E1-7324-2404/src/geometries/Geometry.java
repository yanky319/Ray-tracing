package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * abstract class Geometry for Geometry objects
 */
public abstract class Geometry extends Intersectable {

    /**
     * the color/light from the object
     */
    protected Color _emission;
    /**
     * the material of the object
     */
    protected Material _material;

    //-------------- constructors -------------

    /**
     * default constructor that calls parameter constructor with black.
     * calls {@link geometries.Geometry#Geometry(Material, Color)}.
     */
    public Geometry() {
        this(new Material(0, 0, 0), Color.BLACK);
    }


    /**
     * constructor for Geometry that sets the emission color of the object.
     * calls {@link geometries.Geometry#Geometry(Material, Color)}.
     *
     * @param emission the emission light of the geometry
     */
    public Geometry(Color emission) {
        this(new Material(0, 0, 0), emission);
    }

    /**
     * constructor for Geometry that sets the emission light and material of the object.
     *
     * @param material the material of the geometry
     * @param emission the emission light of the geometry
     */
    public Geometry(Material material, Color emission) {
        _emission = new Color(emission);
        _material = material;
    }

    // ------------------ getters ---------------------------

    /**
     * calculates the unit vector that is orthogonal to the Tube in a given point.
     *
     * @param point a given point
     * @return orthogonal unit vector
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * getter for emission light of the geometry.
     *
     * @return emission light
     */
    public Color get_emission() {
        return new Color(_emission);
    }

    /**
     * getter for Material of the geometry.
     *
     * @return Material
     */
    public Material get_material() {
        return _material;
    }
}
