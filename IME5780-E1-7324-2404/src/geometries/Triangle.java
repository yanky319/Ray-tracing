package geometries;

import primitives.*;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class Triangle representing a Triangle in 3D
 */
public class Triangle extends Polygon {

    //*********************************** constructor ***************

    /**
     * Triangle constructor receiving 3 points3D representing the Triangles vertices.
     * calls {@link geometries.Triangle#Triangle(Color, Point3D, Point3D, Point3D)}.
     *
     * @param po1 point3D
     * @param po2 point3D
     * @param po3 point3D
     */
    public Triangle(Point3D po1, Point3D po2, Point3D po3) {
        this(Color.BLACK, po1, po2, po3);
    }

    /**
     * Triangle constructor receiving 3 points3D representing the Triangles vertices and emission light.
     * calls {@link geometries.Triangle#Triangle(Material, Color, Point3D, Point3D, Point3D)}.
     *
     * @param emission Triangles emission light
     * @param po1      point3D
     * @param po2      point3D
     * @param po3      point3D
     */
    public Triangle(Color emission, Point3D po1, Point3D po2, Point3D po3) {
        this(new Material(0, 0, 0), emission, po1, po2, po3);
    }

    /**
     * Triangle constructor receiving 3 points3D representing the Triangles vertices emission light and material.
     * calls {@link geometries.Polygon#Polygon(Material, Color, Point3D...)}.
     *
     * @param material Triangles material
     * @param emission Triangles emission light
     * @param po1      point3D
     * @param po2      point3D
     * @param po3      point3D
     */
    public Triangle(Material material, Color emission, Point3D po1, Point3D po2, Point3D po3) {
        super(material, emission, po1, po2, po3);
    }
    //*********************************** Getters ***************

    /**
     * List of Point3D getter.
     *
     * @return a copy list of the triangle vertices
     */
    public List<Point3D> get_vertices() {
        List<Point3D> copy_list = new ArrayList<>();
        copy_list.addAll(_vertices);
        return copy_list;
    }
    //*********************************** functions ***************

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> planeResult = _plane.findIntersections(ray, maxDistance);
        if (planeResult == null)
            return null;
        Vector v1 = _vertices.get(0).subtract(ray.get_p0()).normalize();
        Vector v2 = _vertices.get(1).subtract(ray.get_p0()).normalize();
        Vector v3 = _vertices.get(2).subtract(ray.get_p0()).normalize();

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double t1 = alignZero(ray.get_direction().dotProduct(n1));
        double t2 = alignZero(ray.get_direction().dotProduct(n2));
        double t3 = alignZero(ray.get_direction().dotProduct(n3));

        if (Math.signum(t1) == Math.signum(t2) && Math.signum(t2) == Math.signum(t3)) {
            planeResult.get(0).geometry = this;
            return planeResult;
        }
        return null;
    }


    //******************** Admin ****************

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices) {
            result += p.toString();
        }
        return result;
    }
}
