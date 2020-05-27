package elements;

import primitives.Color;

/**
 * class for Ambient Light in a scene.
 */
public class AmbientLight extends Light {

//----------------- constructor -------------

    /**
     * constructor for class AmbientLight receiving color and Attenuation coefficient.
     * calls {@link elements.Light#Light(Color)} with the intensity calculated
     *
     *  @param ia the lights color
     * @param ka the Attenuation coefficient
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }
}
