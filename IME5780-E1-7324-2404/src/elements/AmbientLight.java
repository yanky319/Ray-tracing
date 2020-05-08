package elements;

import primitives.Color;

public class AmbientLight {
    private Color _intensity;
    public AmbientLight(Color ia, double ka ){
        this._intensity = ia.scale(ka);
    }

    public Color GetIntensity() {
        return new Color(_intensity);
    }
}
