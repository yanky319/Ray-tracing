package unittests;

import geometries.Cylinder;
import org.junit.Test;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Cylinder class
 */
public class CylinderTests {

    /**
     * Test method for {@link Cylinder#Cylinder(double, Ray, float)}.
     */
    @Test
    public void testConstructor() {

        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not except negative radius
        try {
            new Cylinder(-1, new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 1)), 3);
            fail("Cylinder constructor does not throw exception for negative radius");
        } catch (IllegalArgumentException e) {
        }

        // TC12: check that constructor does not except negative height
        try {
            new Cylinder(1, new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 1)), -3);
            fail("Cylinder constructor does not throw exception for negative height");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link Cylinder#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {

        Cylinder c = new Cylinder(1, new Ray(new Point3D(0, 0, 0),
                new Vector(0, 0, 1)), 4);

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Cylinder.getNormal(Point3D) returns the right vector for point on middle
        assertEquals("Cylinder incorrect normal vector for point on middle",
                new Vector(1, 0, 0),
                c.getNormal(new Point3D(1, 0, 1.5)));

        // TC02: check that Cylinder.getNormal(Point3D) returns unit vector for point on middle
        assertTrue("Cylinder incorrect normal vector length for point on middle",
                isZero(c.getNormal(new Point3D(1, 0, 1.5)).length() - 1));


        // TC03: check that Cylinder.getNormal(Point3D) returns the right vector for point on bottom base
        assertEquals("Cylinder incorrect normal vector for point on bottom base",
                new Vector(0, 0, -1),
                c.getNormal(new Point3D(0.5, 0, 0)));

        // TC04: check that Cylinder.getNormal(Point3D) returns unit vector for point on bottom base
        assertTrue("Cylinder incorrect normal vector length for point on bottom base",
                isZero(c.getNormal(new Point3D(0.5, 0, 0)).length() - 1));


        // TC05: check that Cylinder.getNormal(Point3D) returns the right vector for point on top base
        assertEquals("Cylinder incorrect normal vector for point on top base",
                new Vector(0, 0, 1),
                c.getNormal(new Point3D(0.5, 0, 4)));

        // TC06: check that Cylinder.getNormal(Point3D) returns unit vector for point on top base
        assertTrue("Cylinder incorrect normal vector length for point on top base",
                isZero(c.getNormal(new Point3D(0.5, 0, 0)).length() - 1));


        // =============== Boundary Values Tests ==================

        // TC11: check that Cylinder.getNormal(Point3D) returns the right vector for point on bottom Boundary
        assertEquals("Cylinder incorrect normal vector for point on bottom Boundary",
                new Vector(0, 0, -1),
                c.getNormal(new Point3D(1, 0, 0)));

        // TC12: check that Cylinder.getNormal(Point3D) returns unit vector for point on bottom Boundary
        assertTrue("Cylinder incorrect normal vector length for point on bottom Boundary",
                isZero(c.getNormal(new Point3D(0.5, 0, 0)).length() - 1));


        // TC13: check that Cylinder.getNormal(Point3D) returns the right vector for point on top Boundary
        assertEquals("Cylinder incorrect normal vector for point on top Boundary",
                new Vector(0, 0, 1),
                c.getNormal(new Point3D(1, 0, 4)));

        // TC14: check that Cylinder.getNormal(Point3D) returns unit vector for point on top Boundary
        assertTrue("Cylinder incorrect normal vector length for point on top Boundary",
                isZero(c.getNormal(new Point3D(0.5, 0, 0)).length() - 1));
    }


    /**
     * Test method for {@link Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {


    }
}