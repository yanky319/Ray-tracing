package unittests;


import org.junit.Test;
import primitives.Vector;
import primitives.Point3D;

import static primitives.Util.isZero;
import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Point3D class
 */
public class Point3DTests {

    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)}.
     */
    @Test
    public void testSubtract() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Subtract returns the right vector
        assertEquals("Wrong result vector from Point3D subtraction",
                new Vector(2, 1, 0),
                new Point3D(3, 3, 3).subtract(new Point3D(1, 2, 3)));
        // =============== Boundary Values Tests ==================

        // TC11: check Subtract of p - p
        try {
            new Point3D(3, 3, 3).subtract(new Point3D(3, 3, 3));
            fail("subtraction of p - p does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Point3D#add(Vector)}.
     */
    @Test
    public void testAdd() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that add returns the right point
        assertEquals("Wrong result Point3D from Point3D addition",
                new Point3D(4, 5, 6),
                new Point3D(3, 3, 3).add(new Vector(1, 2, 3)));
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D)}.
     */
    @Test
    public void testDistanceSquared() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that distanceSquared returns the right value
        assertTrue("Wrong result from Point3D distanceSquared",
                isZero(new Point3D(3, 3, 3).distanceSquared(new Point3D(1, 2, 3)) - 5));

        // =============== Boundary Values Tests ==================

        // TC11: check that distanceSquared from point to itself returns the right value
        assertTrue("Wrong result for distanceSquared from point to itself",
                isZero(new Point3D(1, 2, 3).distanceSquared(new Point3D(1, 2, 3))));

        // TC12: check that distanceSquared from point to Point3D.ZERO returns the right value
        assertTrue("Wrong result for distanceSquared from point to Point3D.ZERO",
                isZero(new Point3D(1, 2, 3).distanceSquared(Point3D.ZERO) - 14));

    }

    /**
     * Test method for {@link primitives.Point3D#distance(Point3D)}.
     */
    @Test
    public void testDistance() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that distance returns the right value
        assertTrue("Wrong result from Point3D distance",
                isZero(new Point3D(3, 3, 3).distance(new Point3D(1, 2, 3)) - Math.sqrt(5)));

        // =============== Boundary Values Tests ==================

        // TC11: check that distance from point to itself returns the right value
        assertTrue("Wrong result for distance from point to itself",
                isZero(new Point3D(1, 2, 3).distance(new Point3D(1, 2, 3))));

        // TC12: check that distance from point to Point3D.ZERO returns the right value
        assertTrue("Wrong result for distance from point to Point3D.ZERO",
                isZero(new Point3D(1, 2, 3).distance(Point3D.ZERO) - Math.sqrt(14)));

    }
}