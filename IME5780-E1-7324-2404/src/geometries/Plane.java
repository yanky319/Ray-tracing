package geometries;

import primitives.*;

import static primitives.Util.*;

import java.util.List;
import java.util.Objects;

/**
 * class Plane representing a Plane in 3D.
 */
public class Plane extends Geometry {
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
 * calls {@link geometries.Plane#Plane(Color emission, Point3D _po1, Point3D _po2, Point3D _po3)}.
 *
 * @param _po1 point1
 * @param _po2 point2
 * @param _po3 point3
 * @throws NullPointerException     In case of one of the arguments is null
 * @throws IllegalArgumentException In case of normal vector is (0,0,0)
 */
public Plane(Point3D _po1, Point3D _po2, Point3D _po3)  {
    this( Color.BLACK, _po1, _po2, _po3);
}

    /**
     * Plane constructor receiving 3 points on the plane and emission light.
     *
     * @param emission Planes emission light
     * @param _po1 point1
     * @param _po2 point2
     * @param _po3 point3
     * @throws NullPointerException     In case of one of the arguments is null
     * @throws IllegalArgumentException In case of normal vector is (0,0,0)
     */
    public Plane(Color emission, Point3D _po1, Point3D _po2, Point3D _po3) throws NullPointerException, IllegalArgumentException {
        this(new Material(0,0,0), emission, _po1, _po2,  _po3);
    }

    /**
     * Plane constructor receiving 3 points on the planes emission light and material.
     *
     * @param material Planes material
     * @param emission Planes emission light
     * @param _po1 point1
     * @param _po2 point2
     * @param _po3 point3
     * @throws NullPointerException     In case of one of the arguments is null
     * @throws IllegalArgumentException In case of normal vector is (0,0,0)
     */
    public Plane(Material material, Color emission, Point3D _po1, Point3D _po2, Point3D _po3) throws NullPointerException, IllegalArgumentException {
        super(material, emission);
        if (_po1 == null || _po2 == null || _po3 == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._po = new Point3D(_po1);
        Vector U = new Vector(_po1.subtract(_po2));
        Vector V = new Vector(_po1.subtract(_po3));
        Vector N = U.crossProduct(V);
        N.normalize();
        this._normal = N;
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
    public Vector getNormal() {
        return new Vector(_normal);
    }


    @Override
    public Vector getNormal(Point3D point) {
        return getNormal();
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector Qp;
        try {
            Qp = this._po.subtract(ray.get_p0());
        } catch (IllegalArgumentException e) { // the ray starts at the planes reference point
            return null;
        }
        double Nv = alignZero(this._normal.dotProduct(ray.get_direction()));
        double NQp = alignZero(this._normal.dotProduct(Qp));
        if(isZero(Nv) // the Ray is parallel to or in the plane
                || isZero(NQp) // the point is in the plane
                ||NQp/Nv<0) // the Ray starts after the plane
            return null;
        return List.of(new GeoPoint(this, ray.getPoint(NQp/Nv)));
    }


    //******************** Admin ****************

    @Override
    public String toString() {
        return "point=" + _po +
                ", normal=" + _normal;

    }


}