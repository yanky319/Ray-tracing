package elements;

import primitives.Color;

/**
 * abstract class representing Light's.
 */
abstract class Light {
    /**
     * the Light intensity
     */
    protected Color _intensity;

    //---------------- constructor -------------------

    /**
     * constructor for class Light that sets its intensity.
     *
     * @param intensity the Light intensity
     */
    public Light(Color intensity) {
        this._intensity = new Color(intensity);
    }

    //------------------- getter ------------------

    /**
     * getter for the Light intensity.
     *
     * @return Light intensity
     */
    public Color get_intensity() {
        return new Color(this._intensity);
    }
}
