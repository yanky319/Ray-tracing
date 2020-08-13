package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * direction light representing a light that is far
 * and all light beams from it arr close enough as parallel
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * the direction of the light beams from the source
     */
    private Vector _direction;

    //---------------- constructor -----------------------

    /**
     * constructor for DirectionalLight class.
     * calls {@link elements.Light#Light(Color)} with the intensity
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
    public Vector getL(Point3D p) {
        return new Vector(_direction).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Vector> getListOfVectors(Point3D lightedPoint, int numOfVectors) {
        return List.of(this.getL(lightedPoint).scale(-1));
    }
}
