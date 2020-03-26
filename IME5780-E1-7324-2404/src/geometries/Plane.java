package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

/**
 * class Plane representing a Plane in 3D.
 */
public class Plane implements Geometry {
    protected Point3D _po;
    protected Vector _normal;

    //*********************************** constructors ***************

    /**
     * Plane constructor receiving a point on the plane and a vector perpendicular to the plane.
     *
     * @param _po     a point on the plane
     * @param _normal a vector perpendicular to the plane
     * @throws NullPointerException In case of one of the arguments is null
     */
    public Plane(Point3D _po, Vector _normal) throws NullPointerException {
        if (_po == null || _normal == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._po = new Point3D(_po);
        this._normal = new Vector(_normal);
    }

    /**
     * Plane constructor receiving 3 points on the plane.
     *
     * @param _po1 point1
     * @param _po2 point2
     * @param _po3 point3
     * @throws NullPointerException In case of one of the arguments is null
     */
    public Plane(Point3D _po1, Point3D _po2, Point3D _po3) throws NullPointerException {
        if (_po1 == null || _po2 == null || _po3 == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._po = new Point3D(_po1);
        Vector U = new Vector(_po1.subtract(_po2));
        Vector V = new Vector(_po1.subtract(_po3));
        Vector N = U.crossProduct(V);
        N.normalize();
        this._normal = N.scale(-1);
        this._normal = new Vector(_normal);
    }

    //*********************************** getters ***************

    /**
     * point getter
     *
     * @return a point on the plane
     */
    public Point3D get_po() {
        return new Point3D(_po);
    }

    /**
     * normal vector getter
     *
     * @return a vector perpendicular to the plane
     */
    public Vector get_normal() {
        return new Vector(_normal);
    }


    @Override
    public Vector getNormal(Point3D point) {
        return get_normal();
    }

    /**
     * Returns a vector perpendicular to the plane.
     *
     * @return vector perpendicular to the plane
     */
    public Vector getNormal() {
        return get_normal();
    }


    //******************** Admin ****************

    @Override
    public String toString() {
        return "Plane{" +
                "_po=" + _po +
                ", _normal=" + _normal +
                '}';
    }

}