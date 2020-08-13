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
    /**
     * transparency coefficient
     */
    double _kT;
    /**
     * reflection coefficient
     */
    double _kR;

    //------------ constructor ----------------

    /**
     * constructor for class Material.
     * calls {@link primitives.Material#Material(double, double, int, double, double)}
     * with default values for the new parameters
     *
     * @param kD         diffuse coefficient
     * @param kS         specular coefficient
     * @param nShininess strength of Shininess
     */
    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    /**
     * constructor for class Material.
     *
     * @param kD         diffuse coefficient
     * @param kS         specular coefficient
     * @param nShininess strength of Shininess
     * @param kT         transparency coefficient
     * @param kR         reflection coefficient
     */
    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
        this._kT = kT;
        this._kR = kR;
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

    /**
     * getter for transparency coefficient.
     *
     * @return transparency coefficient
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * getter for reflection coefficient.
     *
     * @return reflection coefficient
     */
    public double get_kR() {
        return _kR;
    }
}
