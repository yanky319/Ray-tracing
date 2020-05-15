package elements;

import primitives.Color;

/**
 * class for Ambient Light in a scene.
 */
public class AmbientLight {
    private Color _intensity; // the color of the light

//----------------- constructor -------------
    /**
     * constructor for class AmbientLight receiving color and Attenuation coefficientץ
     *
     * @param ia the color
     * @param ka the Attenuation coefficient
     */
    public AmbientLight(Color ia, double ka ){
        this._intensity = ia.scale(ka);
    }
//----------------- getter ------------

    /**
     * getter for the color of Ambient Light.
     *
     * @return the intensity of the color
     */
    public Color GetIntensity() {
        return new Color(_intensity);
    }
}
