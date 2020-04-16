package unittests;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
}