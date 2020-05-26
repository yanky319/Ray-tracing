package primitives;

import java.util.Objects;

/**
 * class Material represents the material of a geometry
 * using parameters of light reflection types from the surface
 */
public class Material {
    double _kD;      // diffuse coefficient
    double _kS;      // specular coefficient
    int _nShininess; // strength of Shininess

    //------------ constructor ----------------

    /**
     * constructor for class Material.
     *
     * @param _kD  diffuse coefficient
     * @param _kS  specular coefficient
     * @param _nShininess strength of Shininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    // ---------------- getters ---------

    /**
     * getter for diffuse coefficient.
     *
     * @return diffuse coefficient
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * getter for specular coefficient.
     *
     * @return specular coefficient
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * getter for strength of Shininess.
     *
     * @return strength of Shininess
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
