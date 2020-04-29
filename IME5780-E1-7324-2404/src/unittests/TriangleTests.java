package unittests;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#Triangle(Point3D, Point3D, Point3D)}.
     */
    @Test
    public void testConstructor(){

        // =============== Boundary Values Tests ==================

        // TC11: check that constructor throws Exception for all points on the same line
        try
        {
            new Triangle(new Point3D(1,1,1), new Point3D(1,2,2), new Point3D(1,3,3));
            fail("Triangle constructor does not throw Exception for all points on the same line");
        }
        catch (IllegalArgumentException e){}
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Triangle t = new Triangle(new Point3D(1, 1, 1), new Point3D(1, 2, 2), new Point3D(3, 3, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Triangle.getNormal(Point3D) returns the right vector
        assertEquals("Triangle incorrect normal vector",
                new Vector(-1 / Math.sqrt(3), 1 / Math.sqrt(3), -1 / Math.sqrt(3)),
                t.getNormal(new Point3D(1, 1, 1)));

        // TC02: check that Triangle.getNormal(Point3D) returns unit vector
        assertTrue("Triangle incorrect normal vector length",
                isZero(t.getNormal(new Point3D(1, 1, 1)).length() - 1));
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point3D(1, 1, 1), new Point3D(1, 2, 2), new Point3D(3, 3, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside Triangle (1 point)
        assertEquals("Wrong Triangle findIntersections when Ray's line is inside the Triangle",
                    List.of(new Point3D(1.4,1.6,1.2)),
                triangle.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3,2,1.5))));

        // TC02: Ray's line is Outside against the edge of Triangle (0 points)
        assertEquals("Wrong Triangle findIntersections when Ray is Outside against the edge of Triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-1, 1, 0), new Vector(1, 1, 1))));

        // TC03: Ray's line is Outside against the vertex of Triangle (0 points)
        assertEquals("Wrong Triangle findIntersections when Ray is Outside against the vertex of Triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-1, 1, 0), new Vector(1.8, -0.5, 0.7))));

        // =============== Boundary Values Tests ================

        // TC11: Ray starts before the plane and goes through the edge of the Triangle (0 points)
        assertEquals("Wrong Triangle findIntersections when Ray goes through the edge of the Triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-1, 1, 0), new Vector(2, 1, 1.5))));
        // TC12: Ray starts before the plane and goes through vertex (0 points)
        assertEquals("Wrong Triangle findIntersections when Ray goes through the vertex of the Triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 1))));
        // TC13: Ray starts before the plane and goes through vertex (0 points)
        assertEquals("Wrong Triangle findIntersections when Ray goes through continuation of the Triangle edge's", null,
                triangle.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -0.5, 0.5))));

    }
}