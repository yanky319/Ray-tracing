package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * a light source that lightens in less then 180 degrees by concentrating the light beams.
 */
public class ImprovedSpotLight extends SpotLight {

    /**
     * defines the light beam concentration towards the center
     */
    private int _concentration;
    //--------------- constructor ------------

    /**
     * constructor for Improved Spot light class.
     * calls {@link elements.SpotLight#SpotLight(Color, Point3D, Vector, double, double, double)}
     * with all parameters except for concentration.
     *
     * @param intensity     color of the light
     * @param position      position of the light source
     * @param direction     the center light beam from the source
     * @param kC            constant attenuation coefficient
     * @param kL            Linear attenuation coefficient
     * @param kQ            quadrant attenuation coefficient
     * @param concentration the parameter that defines the light beam concentration
     */
    public ImprovedSpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ, int concentration) {
        super(intensity, position, direction, kC, kL, kQ);
        _concentration = concentration;
    }

    //-------------- Override functions ----------------
    @Override
    public Color getIntensity(Point3D p) {
        double dotProduct = _direction.dotProduct(getL(p));
        if (alignZero(dotProduct) <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(Math.pow(dotProduct, _concentration));
    }
}
