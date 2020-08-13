package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

/**
 * a light source that lightens in 360 degrees
 */
public class PointLight extends Light implements LightSource {
    /**
     * the position of the light source
     */
    protected Point3D _position;
    /**
     * constant attenuation coefficient
     */
    protected double _kC;
    /**
     * Linear attenuation coefficient
     */
    protected double _kL;
    /**
     * quadrant attenuation coefficient
     */
    protected double _kQ;
    /**
     * radius of the light
     */
    protected double _radius;

    //------------- constructor --------------------

    /**
     * constructor for point light class.
     * calls {@link elements.Light#Light(Color)} with the intensity
     *
     * @param intensity color of the light
     * @param position  position of the light source
     * @param kC        constant attenuation coefficient
     * @param kL        Linear attenuation coefficient
     * @param kQ        quadrant attenuation coefficient
     * @param radius    radius of the light
     * @throws IllegalArgumentException if radius is negative
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ, double radius) throws IllegalArgumentException {
        super(intensity);
        if (radius < 0)
            throw new IllegalArgumentException("radius can not be negative");
        _position = new Point3D(position);
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
        _radius = radius;
    }

    /**
     * constructor for point light class.
     * calls {@link elements.PointLight#PointLight(Color, Point3D, double, double, double, double)}
     *
     * @param intensity color of the light
     * @param position  position of the light source
     * @param kC        constant attenuation coefficient
     * @param kL        Linear attenuation coefficient
     * @param kQ        quadrant attenuation coefficient
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        this(intensity, position, kC, kL, kQ, 0);
    }

    //-------------- Override functions ----------------
    @Override
    public Color getIntensity(Point3D p) {
        double distance = _position.distance(p);
        double distanceSquared = _position.distanceSquared(p);
        return (get_intensity().reduce(_kC + _kL * distance + _kQ * distanceSquared));
    }

    @Override
    public Vector getL(Point3D p) {
        try {
            return p.subtract(_position).normalize();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
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
        List<Double> list = List.of(l.get_head().get_x().get(),
                l.get_head().get_y().get(),
                l.get_head().get_z().get());
        int i = list.indexOf(Collections.min(list));

        Vector vectorV;
        switch (i) { // create orthogonal vector to main vector from light to the lighted point
            case 0:
                vectorV = new Vector(0, -1 * l.get_head().get_z().get(), l.get_head().get_y().get()).normalize();
                break;
            case 1:
                vectorV = new Vector(-1 * l.get_head().get_z().get(), 0, l.get_head().get_x().get()).normalize();
                break;
            default:
                vectorV = new Vector(-1 * l.get_head().get_y().get(), l.get_head().get_x().get(), 0).normalize();
                break;
        }
        Vector vectorU = l.crossProduct(vectorV); // get second orthogonal vector
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
                // find random point on this pixel to create new ray from camera
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
