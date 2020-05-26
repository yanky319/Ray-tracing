package unittests;

import static geometries.Intersectable.GeoPoint;
import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {

    /**
     * Test method for{@link geometries.Polygon#Polygon(Point3D... vertices)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {
        }

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {
        }

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {
        }

        // =============== Boundary Values Tests ==================

        // TC10: vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to Polygon", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }


    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Polygon polygon = new Polygon(new Point3D(2, 1, 0), new Point3D(1, 1, 1), new Point3D(1, 2, 2), new Point3D(3, 3, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside Polygon (1 point)
        assertEquals("Wrong Polygon findIntersections when Ray's line is inside the Polygon",
                List.of(new GeoPoint(polygon, new Point3D(1.7, 1.5, 0.8))),
                polygon.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(1.75, -1.25, 2))));

        // TC02: Ray's line is Outside against the edge of Polygon (0 points)
        assertEquals("Wrong Polygon findIntersections when Ray is Outside against the edge of Polygon", null,
                polygon.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0.7, -2.3, 1))));

        // TC03: Ray's line is Outside against the vertex of Polygon (0 points)
        assertEquals("Wrong Polygon findIntersections when Ray is Outside against the vertex of Polygon", null,
                polygon.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(-0.5, -1.4, 1))));

        // =============== Boundary Values Tests ================

        // TC11: Ray starts before the plane and goes through the edge of the Polygon (0 points)
        assertEquals("Wrong Polygon findIntersections when Ray goes through the edge of the Polygon", null,
                polygon.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0.5, -1, 0.5))));
        // TC12: Ray starts before the plane and goes through vertex (0 points)
        assertEquals("Wrong Polygon findIntersections when Ray goes through the vertex of the Polygon", null,
                polygon.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(1, 2, 0))));
        // TC13: Ray starts before the plane and goes through vertex (0 points)
        assertEquals("Wrong Polygon findIntersections when Ray goes through continuation of the Polygon edge's", null,
                polygon.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(-1, -1, 2))));

    }
}
