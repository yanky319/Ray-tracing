package geometries;

import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;


    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * calls {@link geometries.Polygon#Polygon(Color emission, Point3D... vertices)}.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, vertices);
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * calls {@link geometries.Polygon#Polygon(Material, Color emission, Point3D... vertices)}.
     *
     * @param emission Polygons emission light
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex</li>
     *                                  </ul>
     */
    public Polygon(Color emission, Point3D... vertices) {
        this(new Material(0, 0, 0), emission, vertices);
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * calls {@link geometries.Geometry#Geometry(Material, Color)}.
     *
     * @param material Polygons material
     * @param emission Polygons emission light
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex</li>
     *                                  </ul>
     */
    public Polygon(Material material, Color emission, Point3D... vertices) {
        super(material, emission);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        setBox();
    }

    // ------------------------ Override functions ---------------------

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if(!boundaryBox.intersectBox(ray)) // if no intersection with the box return null
            return null;
        GeoPoint p;
        try { // check if intersects with the plane its in
            p = _plane.findIntersections(ray, maxDistance).get(0);
            if (p == null) return null;
        } catch (Exception e) {
            return null;
        }
        // changing to an array so it will be faster to get one of them, like vertices[1] and vertices[3].
        Point3D[] vertices = new Point3D[_vertices.size()];
        vertices = _vertices.toArray(vertices);
        Vector rn = ray.get_direction();
        Point3D rp = ray.get_p0();
        // getting the first and second vectors
        try {

            Vector vec1 = rp.subtract(vertices[vertices.length - 1]);
            Vector vec2 = rp.subtract(vertices[0]);

            if (isZero(vec1.crossProduct(vec2).normalized().dotProduct(rn)))
                return null;
            boolean flag = vec1.crossProduct(vec2).normalized().dotProduct(rn) > 0;

            for (int i = 1; i < vertices.length; ++i) {
                vec1 = vec2;
                vec2 = rp.subtract(vertices[i]);
                if ((isZero(vec1.crossProduct(vec2).normalized().dotProduct(rn))) ||
                        (flag != vec1.crossProduct(vec2).normalized().dotProduct(rn) > 0)) {
                    return null;
                }
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
        return List.of(new GeoPoint(this, p.point));
    }

    @Override
    public void setBox() {
        // set min and max values to ba coordinates of first vertices
        double min_x = _vertices.get(0).get_x().get();
        double max_x = _vertices.get(0).get_x().get();
        double min_y = _vertices.get(0).get_y().get();
        double max_y = _vertices.get(0).get_y().get();
        double min_z = _vertices.get(0).get_z().get();
        double max_z = _vertices.get(0).get_z().get();
        // // go over all vertices and check for grater or smaller values
        for (Point3D p : _vertices) {
            if (p.get_x().get() < min_x)
                min_x = p.get_x().get();
            if (p.get_x().get() > max_x)
                max_x = p.get_x().get();
            if (p.get_y().get() < min_y)
                min_y = p.get_y().get();
            if (p.get_y().get() > max_y)
                max_y = p.get_y().get();
            if (p.get_z().get() < min_z)
                min_z = p.get_z().get();
            if (p.get_z().get() > max_z)
                max_z = p.get_z().get();
        }
        // create box with min and max values found
        boundaryBox = new Box(
                new Point3D(min_x, min_y, min_z),
                new Point3D(max_x, max_y, max_z));

    }

}
