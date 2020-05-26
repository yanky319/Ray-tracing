package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * direction light representing a light that is far
 * and all light beams from it arr close enough as parallel
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction; // the direction of light beams from the source

    //---------------- constructor -----------------------

    /**
     * constructor for DirectionalLight class.
     *
     * @param intensity the color of the light
     * @param direction the direction of the light beams
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = new Vector(direction).normalized();

    }

    //-------------- Override functions ----------------
    @Override
    public Color getIntensity(Point3D p) {
        return get_intensity();
    }

    @Override
    public Vector getL(Point3D p) { return new Vector(_direction); }
}
