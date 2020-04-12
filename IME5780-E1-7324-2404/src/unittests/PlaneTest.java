package unittests;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)}.
     */
    @Test
    public void testConstructor(){

        // =============== Boundary Values Tests ==================

        // TC11: check that constructor throws Exception for all points on the same line
        try
        {
            new Plane(new Point3D(1,1,1), new Point3D(1,2,2), new Point3D(1,3,3));
            fail("Plane constructor does not throw Exception for all points on the same line");
        }
        catch (IllegalArgumentException e){}
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {

        Plane p = new Plane(new Point3D(1, 1, 1), new Point3D(1, 2, 2), new Point3D(3, 3, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Plane.getNormal(Point3D) returns the right vector
        assertEquals("Plane incorrect normal vector",
                new Vector(-1 / Math.sqrt(3), 1 / Math.sqrt(3), -1 / Math.sqrt(3)),
                p.getNormal(new Point3D(1, 1, 1)));

        // TC02: check that Plane.getNormal(Point3D) returns unit vector
        assertTrue("Plane incorrect normal vector length",
                isZero(p.getNormal(new Point3D(1, 1, 1)).length() - 1));
    }
}