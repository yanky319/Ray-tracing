package geometries;

import primitives.*;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Tube representing a Tube in 3D.
 */
public class Tube extends RadialGeometry {
    /**
     * the axis of the Tube.
     */
    protected final Ray _axisRay;

    //*********************************** constructor ***************

    /**
     * Tube constructor receiving the radius and the axis ray.
     * calls {@link Tube#Tube(Color, double, Ray)}.
     *
     * @param radius Tubes radius
     * @param ray    Tubes axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(double radius, Ray ray) {
        this(Color.BLACK, radius, ray);
    }

    /**
     * Tube constructor receiving the radius and the axis ray and emission light.
     * calls {@link Tube#Tube(Material, Color, double, Ray)}.
     *
     * @param emission Tubes emission light
     * @param radius   Tubes radius
     * @param ray      Tubes axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(Color emission, double radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        this(new Material(0, 0, 0), emission, radius, ray);
    }

    /**
     * Tube constructor receiving the radius and the axis rays emission light and material.
     * calls {@link Tube#Tube(Color, double, Ray)}.
     *
     * @param material Tubes material
     * @param emission Tubes emission light
     * @param radius   Tube radius
     * @param ray      Tube axis ray
     * @throws NullPointerException     In case of the Ray argument is null
     * @throws IllegalArgumentException In case of the radius argument is not greater than zero
     */
    public Tube(Material material, Color emission, double radius, Ray ray) throws NullPointerException, IllegalArgumentException {
        super(material, emission, radius);
        if (ray == null)
            throw new NullPointerException("ERROR Ray arguments is NULL");
        this._axisRay = new Ray(ray);
        // check that not a Cylinder to avoid run time exception
        if (!(this instanceof Cylinder))
            setBox();
    }

    //*********************************** Getters ***************

    /**
     * axis ray getter.
     *
     * @return Tube axis ray
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }


    @Override
    public Vector getNormal(Point3D point) {
        double t = this._axisRay.get_direction().dotProduct(point.subtract(this._axisRay.get_p0()));
        Point3D o = new Point3D(this._axisRay.get_p0().add(this._axisRay.get_direction().scale(t)));
        Vector n = new Vector(point.subtract(o));
        return n.normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if (!boundaryBox.intersectBox(ray)) // if no intersection with the box return null
            return null;
        Point3D p = ray.get_p0();
        Point3D p0 = _axisRay.get_p0();
        Vector deltaP = p.subtract(p0);
        Vector v = ray.get_direction();
        Vector va = _axisRay.get_direction();
        double VxVa = v.dotProduct(va);
        double deltaPxVa = deltaP.dotProduct(va);
        double A, B, C;
        if (isZero(VxVa) && !isZero(deltaPxVa)) {
            A = v.lengthSquared();
            B = 2 * (v.dotProduct(deltaP.subtract(va.scale(deltaPxVa))));
            C = (deltaP.subtract(va.scale(deltaPxVa))).lengthSquared() - _radius * _radius;
        } else if (isZero(VxVa) && isZero(deltaPxVa)) {
            A = v.lengthSquared();
            B = 2 * (v.dotProduct(deltaP));
            C = (deltaP).lengthSquared() - _radius * _radius;
        } else if (!isZero(VxVa) && isZero(deltaPxVa)) {
            A = v.subtract(va.scale(VxVa)).lengthSquared();
            B = 2 * ((v.subtract(va.scale(VxVa))).dotProduct(deltaP));
            C = (deltaP).lengthSquared() - _radius * _radius;
        } else {
            A = (v.subtract(va.scale(VxVa))).lengthSquared();
            B = 2 * ((v.subtract(va.scale(VxVa))).dotProduct(deltaP.subtract(va.scale(deltaPxVa))));
            C = (deltaP.subtract(va.scale(deltaP.dotProduct(va)))).lengthSquared() - _radius * _radius;
        }
        double calc_d = B * B - 4 * A * C;
        if (calc_d < 0) return null;
        double t1 = (-1 * B + sqrt(calc_d)) / (2d * A);
        double t2 = (-1 * B - sqrt(calc_d)) / (2d * A);

        // takes only positive and non zero results, also in the distance:
        if (isZero(calc_d)) return List.of(new GeoPoint(this, ray.getPoint(t1)));
        if (alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0 && t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        if (alignZero(t1 - maxDistance) <= 0 && t1 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        if (alignZero(t2 - maxDistance) <= 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        return null;
    }

    @Override
    public void setBox() {  // set initial values as plus/minus max value
        double min_x = -Double.MAX_VALUE;
        double max_x = Double.MAX_VALUE;
        double min_y = -Double.MAX_VALUE;
        double max_y = Double.MAX_VALUE;
        double min_z = -Double.MAX_VALUE;
        double max_z = Double.MAX_VALUE;

        double x = this._axisRay.get_direction().get_head().get_x().get();
        double y = this._axisRay.get_direction().get_head().get_y().get();
        double z = this._axisRay.get_direction().get_head().get_z().get();

        if (isZero(y) && isZero(z)) { // if tube is parallel to x axes
            min_x = _axisRay.get_p0().get_x().get() - _radius;
            max_x = _axisRay.get_p0().get_x().get() + _radius;
        }
        if (isZero(x) && isZero(z)) { // if tube is parallel to y axes
            min_y = _axisRay.get_p0().get_y().get() - _radius;
            max_y = _axisRay.get_p0().get_y().get() + _radius;
        }
        if (isZero(y) && isZero(x)) { // if tube is parallel to z axes
            min_z = _axisRay.get_p0().get_z().get() - _radius;
            max_z = _axisRay.get_p0().get_z().get() + _radius;
        }
        // create box with min and max values found
        boundaryBox = new Box(
                new Point3D(min_x, min_y, min_z),
                new Point3D(max_x, max_y, max_z));
    }

    //******************** Admin ****************

    @Override
    public String
    toString() {
        return "ray: " + _axisRay +
                ", radius: " + _radius;
    }
}
