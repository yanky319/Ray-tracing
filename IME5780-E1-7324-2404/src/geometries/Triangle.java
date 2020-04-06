package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * class Triangle representing a Triangle in 3D
 */
public class Triangle extends Polygon  {

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

    @Override
    public Vector getNormal(Point3D point) {
        return this._plane.getNormal();
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
