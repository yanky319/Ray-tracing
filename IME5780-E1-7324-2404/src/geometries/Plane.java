package geometries;

import primitives.*;

import static primitives.Util.*;

import java.util.List;

/**
 * class Plane representing a Plane in 3D with a point and normal vector.
 */
public class Plane extends Geometry {
    /**
     * a point on the Plane.
     */
    protected Point3D _po;
    /**
     * a vector normal to the Plane.
     */
    protected Vector _normal;

    //*********************************** constructors ***************

    /**
     * Plane constructor receiving a point on the plane and a vector perpendicular to the plane.
     * calls {@link geometries.Plane#Plane(Color, Point3D, Vector)}.
     *
     * @param po     a point on the plane
     * @param normal a vector perpendicular to the plane
     * @throws NullPointerException In case of one of the arguments is null
     */
    public Plane(Point3D po, Vector normal) throws NullPointerException {
        this(Color.BLACK, po, normal);
    }

    /**
     * Plane constructor receiving a point on the plane and a vector perpendicular to the plane.
     * calls {@link geometries.Plane#Plane(Material, Color, Point3D, Vector)}.
     *
     * @param emission Planes emission light
     * @param po       a point on the plane
     * @param normal   a vector perpendicular to the plane
     * @throws NullPointerException In case of one of the arguments is null
     */
    public Plane(Color emission, Point3D po, Vector normal) throws NullPointerException {
        this(new Material(0, 0, 0), emission, po, normal);
    }

    /**
     * Plane constructor receiving a point on the plane and a vector perpendicular to the plane.
     * calls {@link geometries.Geometry#Geometry(Material, Color)}.
     *
     * @param material Planes material
     * @param emission Planes emission light
     * @param po       a point on the plane
     * @param normal   a vector perpendicular to the plane
     * @throws NullPointerException In case of one of the arguments is null
     */
    public Plane(Material material, Color emission, Point3D po, Vector normal) throws NullPointerException {
        super(material, emission);
        if (po == null || normal == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._po = new Point3D(po);
        this._normal = new Vector(normal).normalize();
        setBox();
    }


    /**
     * Plane constructor receiving 3 points on the plane.
     * calls {@link geometries.Plane#Plane(Color emission, Point3D _po1, Point3D _po2, Point3D _po3)}.
     *
     * @param po1 point1
     * @param po2 point2
     * @param po3 point3
     * @throws NullPointerException     In case of one of the arguments is null
     * @throws IllegalArgumentException In case of normal vector is (0,0,0)
     */
    public Plane(Point3D po1, Point3D po2, Point3D po3) {
        this(Color.BLACK, po1, po2, po3);
    }

    /**
     * Plane constructor receiving 3 points on the plane and emission light.
     * calls {@link geometries.Plane#Plane(Material, Color emission, Point3D _po1, Point3D _po2, Point3D _po3)}.
     *
     * @param emission Planes emission light
     * @param po1      point1
     * @param po2      point2
     * @param po3      point3
     * @throws NullPointerException     In case of one of the arguments is null
     * @throws IllegalArgumentException In case of normal vector is (0,0,0)
     */
    public Plane(Color emission, Point3D po1, Point3D po2, Point3D po3) throws NullPointerException, IllegalArgumentException {
        this(new Material(0, 0, 0), emission, po1, po2, po3);
    }

    /**
     * Plane constructor receiving 3 points on the planes emission light and material.
     * calls {@link geometries.Geometry#Geometry(Material, Color)}.
     *
     * @param material Planes material
     * @param emission Planes emission light
     * @param po1      point1
     * @param po2      point2
     * @param po3      point3
     * @throws NullPointerException     In case of one of the arguments is null
     * @throws IllegalArgumentException In case of normal vector is (0,0,0)
     */
    public Plane(Material material, Color emission, Point3D po1, Point3D po2, Point3D po3) throws NullPointerException, IllegalArgumentException {
        super(material, emission);
        if (po1 == null || po2 == null || po3 == null)
            throw new NullPointerException("ERROR One or more of the arguments is NULL");
        this._po = new Point3D(po1);
        Vector U = new Vector(po1.subtract(po2));
        Vector V = new Vector(po1.subtract(po3));
        Vector N = U.crossProduct(V);
        N.normalize();
        this._normal = N;
        setBox();
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
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if (!boundaryBox.intersectBox(ray)) // if no intersection with the box return null
            return null;
        Vector Qp;
        try {
            Qp = this._po.subtract(ray.get_p0());
        } catch (IllegalArgumentException e) { // the ray starts at the planes reference point
            return null;
        }
        double Nv = alignZero(this._normal.dotProduct(ray.get_direction()));
        double NQp = alignZero(this._normal.dotProduct(Qp));
        if (!isZero(Nv) // the Ray is not parallel to or in the plane
                && !isZero(NQp)) // the point is not in the plane
        {
            double t = alignZero(NQp / Nv);
            if (!(t < 0)  // the Ray does not start after the plane
                    && (alignZero(t - maxDistance) <= 0)) // the distance to the point point is not more then the max distance
                return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        return null;
    }

    @Override
    public void setBox() {

        // set initial values as plus/minus max value
        double min_x = -Double.MAX_VALUE;
        double max_x = Double.MAX_VALUE;
        double min_y = -Double.MAX_VALUE;
        double max_y = Double.MAX_VALUE;
        double min_z = -Double.MAX_VALUE;
        double max_z = Double.MAX_VALUE;

        double x = this._normal.get_head().get_x().get();
        double y = this._normal.get_head().get_y().get();
        double z = this._normal.get_head().get_z().get();

        if (isZero(y) && isZero(z)) { // if plane is orthogonal to x axes
            min_x = x - 0.1;
            max_x = x + 0.1;
        }
        if (isZero(x) && isZero(z)) { // if plane is orthogonal to y axes
            min_y = y - 0.1;
            max_y = y + 0.1;
        }
        if (isZero(y) && isZero(x)) { // if plane is orthogonal to z axes
            min_z = z - 0.1;
            max_z = z + 0.1;
        }
        // create box with min and max values found
        boundaryBox = new Box(
                new Point3D(min_x, min_y, min_z),
                new Point3D(max_x, max_y, max_z));
    }


    //******************** Admin ****************

    @Override
    public String toString() {
        return "point=" + _po +
                ", normal=" + _normal;

    }


}