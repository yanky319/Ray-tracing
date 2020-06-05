package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

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
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        _position = new Point3D(position);
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
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
}
