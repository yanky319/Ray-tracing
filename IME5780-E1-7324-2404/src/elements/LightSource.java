package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all light sources.
 */
public interface LightSource {
    /**
     * calculates the light from the source at a given point.
     *
     * @param p the point we want the light at
     * @return the color of the light at p
     */
    public Color getIntensity(Point3D p);

    /**
     * returns a normalized vector from the light sources towards the given point.
     *
     * @param p the point we need a vector to
     * @return vector from light sources to p
     */
    public Vector getL(Point3D p);

}
