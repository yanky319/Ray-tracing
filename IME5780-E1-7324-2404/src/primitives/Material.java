package primitives;

import java.util.Objects;

/**
 * class Material represents the material of a geometry
 * using parameters of light reflection types from the surface
 */
public class Material {
    /**
     * diffuse coefficient
     */
    double _kD;
    /**
     * specular coefficient
     */
    double _kS;
    /**
     * strength of Shininess
     */
    int _nShininess;


    //------------ constructor ----------------

    /**
     * constructor for class Material.
     *
     * @param kD         diffuse coefficient
     * @param kS         specular coefficient
     * @param nShininess strength of Shininess
     */
    public Material(double kD, double kS, int nShininess) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
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
