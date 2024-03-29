package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

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

    /**
     * calculates the distance between the light source and a given point.
     *
     * @param point the given point
     * @return the distance calculated
     */
    double getDistance(Point3D point);

    /**
     * returns list of normalized vectors from the light sources towards the given point.
     *
     * @param lightedPoint the point in which the vectors are directed to
     * @param numOfVectors the amount of vectors to create
     * @return list of vectors from the light towards the point
     */
    List<Vector> getListOfVectors(Point3D lightedPoint,int numOfVectors);
}
