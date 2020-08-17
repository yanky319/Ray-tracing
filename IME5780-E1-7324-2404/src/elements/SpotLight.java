package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.*;

/**
 * a light source that lightens in 180 degrees
 */
public class SpotLight extends PointLight {
    /**
     * the center light beam from the source
     */
    protected Vector _direction;

    //------------- constructor --------------------

    /**
     * constructor for spot light class.
     * calls {@link elements.PointLight#PointLight(Color, Point3D, double, double, double, double)}
     * with all parameters except for direction.
     *
     * @param intensity color of the light
     * @param position  position of the light source
     * @param direction the center light beam from the source
     * @param kC        constant attenuation coefficient
     * @param kL        Linear attenuation coefficient
     * @param kQ        quadrant attenuation coefficient
     * @param radius    radius of the light source
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ, double radius) {
        super(intensity, position, kC, kL, kQ, radius);
        _direction = new Vector(direction).normalize();
    }

    /**
     * constructor for spot light class.
     * calls {@link elements.SpotLight#SpotLight(Color, Point3D, Vector, double, double, double, double)}
     * with all parameters except for direction.
     *
     * @param intensity color of the light
     * @param position  position of the light source
     * @param direction the center light beam from the source
     * @param kC        constant attenuation coefficient
     * @param kL        Linear attenuation coefficient
     * @param kQ        quadrant attenuation coefficient
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        this(intensity, position, direction, kC, kL, kQ, 0);
    }

    //-------------- Override functions ----------------
    @Override
    public Color getIntensity(Point3D p) {
        double dotProduct = _direction.dotProduct(getL(p));
        if (alignZero(dotProduct) <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(dotProduct);
    }

    @Override
    public List<Vector> getListOfVectors(Point3D lightedPoint, int numOfVectors) {
        // create list for the vectors
        List<Vector> vectors = new LinkedList<>();
        // add main vector
        Vector l = this.getL(lightedPoint);
        vectors.add(l);
        if (_radius == 0)
            return vectors;
        // get coordinate with min value
        List<Double> list = List.of(_direction.get_head().get_x().get(),
                _direction.get_head().get_y().get(),
                _direction.get_head().get_z().get());
        int i = list.indexOf(Collections.min(list));

        Vector vectorV;
        switch (i) { // create orthogonal vector to direction vector of the light
            case 0:
                vectorV = new Vector(0, -1 * _direction.get_head().get_z().get(), _direction.get_head().get_y().get()).normalize();
                break;
            case 1:
                vectorV = new Vector(-1 * _direction.get_head().get_z().get(), 0, _direction.get_head().get_x().get()).normalize();
                break;
            default:
                vectorV = new Vector(-1 * _direction.get_head().get_y().get(), _direction.get_head().get_x().get(), 0).normalize();
                break;
        }
        Vector vectorU = _direction.crossProduct(vectorV); // get second orthogonal vector
        Random r = new Random();
        // the parameter to calculate the coefficient of the 2 vectors
        double cos, sin, d;
        //the coefficient to calculate in which quarter in the radius is random point
        int k, h;

        Point3D randomPoint;
        // divide the random points evenly within the four quarters
        for (int t = 0; t < 4; t++) {
            // decide which quarter we are in
            k = t != 1 && t != 2 ? 1 : -1;
            h = t != 2 && t != 3 ? 1 : -1;
            for (int u = 0; u < numOfVectors / 4; u++) { // create vectors in the quarter
                cos = r.nextDouble(); // give value for cosine
                sin = Math.sqrt(1 - cos * cos); // calculate sine
                d = r.nextDouble() * _radius; // 0 < d < radius
                // find random point on this pixel to create new vector from the light source to the lighted point
                randomPoint = _position;
                if (sin * d * h != 0)
                    randomPoint = randomPoint.add(vectorV.scale(sin * d * h));
                if (cos * d * k != 0)
                    randomPoint = randomPoint.add(vectorU.scale(cos * d * k));
                // add the vector to the list
                vectors.add(lightedPoint.subtract(randomPoint).normalize());
            }
        }
        return vectors;
    }
}
