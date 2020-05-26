package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.*;

/**
 * a light source that lightens in 180 degrees
 */
public class SpotLight extends PointLight {
    private Vector _direction; // the center light beam from the source

    //------------- constructor --------------------

    /**
     * constructor for point light class.
     *
     * @param intensity color of the light
     * @param position  position of the light source
     * @param direction the center light beam from the source
     * @param kC        constant attenuation coefficient
     * @param kL        Linear attenuation coefficient
     * @param kQ        quadrant attenuation coefficient
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        _direction = new Vector(direction).normalize();
    }

    //-------------- Override functions ----------------
    @Override
    public Color getIntensity(Point3D p) {
        double dotProduct = _direction.dotProduct(getL(p));
        if (alignZero(dotProduct) <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(dotProduct);
    }
}
