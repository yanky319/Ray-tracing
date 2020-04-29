package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
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
     *
     * @param _po1 point3D
     * @param _po2 point3D
     * @param _po3 point3D
     */
    public Triangle(Point3D _po1, Point3D _po2, Point3D _po3) {
        super(_po1, _po2, _po3);
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
    public List<Point3D> findIntersections(Ray ray) {
      List<Point3D> planeResult =  _plane.findIntersections(ray);
      if(planeResult == null)
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

        if(Math.signum(t1)==Math.signum(t2)&&Math.signum(t2)==Math.signum(t3))
            return planeResult;
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
