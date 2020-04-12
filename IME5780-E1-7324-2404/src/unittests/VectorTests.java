package unittests;

import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;
import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Vector class
 */
public class VectorTests {

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor1() {

        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not create vector (0,0,0)
        try {
            new Vector(0, 0, 0);
            fail("constructor Vector(double, double, double) dose not throw exception for zero vector");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link primitives.Vector#Vector(Point3D)}.
     */
    @Test
    public void testConstructor2() {
        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not create vector (0,0,0)
        try {
            new Vector(Point3D.ZERO);
            fail("constructor Vector(Point3D) dose not throw exception for zero vector");
        } catch (IllegalArgumentException e) { }
    }

    /**
     * Test method for {@link primitives.Vector#Vector(Coordinate, Coordinate, Coordinate)}.
     */
    @Test
    public void testConstructor3() {
        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not create vector (0,0,0)

        try {
            new Vector(new Coordinate(0), new Coordinate(0), new Coordinate(0));
            fail("constructor Vector(Coordinate,Coordinate,Coordinate) dose not throw exception for zero vector");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check add returns the right vector
        assertEquals("Wrong result vector from vector addition",
                new Vector(4, 5, 6),
                new Vector(3, 3, 3).add(new Vector(1, 2, 3)));

        // =============== Boundary Values Tests ==================

        // TC11: check add V + (-V)
        try {
            new Vector(3, 3, 3).add(new Vector(-3, -3, -3));
            fail("addition of V + (-V) does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}.
     */
    @Test
    public void testSubtract() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check Subtract returns the right vector
        assertEquals("Wrong result vector from vector Subtraction",
                new Vector(2, 1, 0),
                new Vector(3, 3, 3).subtract(new Vector(1, 2, 3)));

        // =============== Boundary Values Tests ==================

        // TC11: check Subtract of V - V
        try {
            new Vector(3, 3, 3).subtract(new Vector(3, 3, 3));
            fail("Subtraction of V - V does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check scale returns the right vector
        assertEquals("Wrong result vector from vector scaleition",
                new Vector(7.5, 7.5, 5),
                new Vector(3, 3, 2).scale(2.5));
        // =============== Boundary Values Tests ==================

        // TC11: check scale by zero
        try {
            new Vector(3, 3, 3).scale(0);
            fail("multiplication of V by zero does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    public void testDotProduct() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check dotProduct returns the right value
        assertTrue("Wrong result from vector dotProduct",
                isZero(new Vector(3, 3, 3).dotProduct(new Vector(1, 2, 3)) - 18));

        // =============== Boundary Values Tests ==================

        // TC11: check dotProduct for orthogonal vectors
        assertTrue("dotProduct for orthogonal vectors is not zero",
                isZero(new Vector(3, 0, 0).dotProduct(new Vector(0, 2, 0))));
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(3, 3, 3);
        Vector v2 = new Vector(1, 2, 3);
        Vector v3 = new Vector(3, -6, 3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: check CrossProduct returns the right vector
        assertEquals("Wrong result vector from vector CrossProduct",
                v3,
                v1.crossProduct(v2));

        // TC02: check CrossProduct returns vector that is orthogonal to its operands
        assertTrue("CrossProduct result is not orthogonal to its operands",
                isZero(v3.dotProduct(v1)) && isZero(v3.dotProduct(v2)));

        // =============== Boundary Values Tests ==================

        // TC11: check CrossProduct for parallel vectors
        try {
            new Vector(v1).crossProduct(new Vector(v1.scale(-2)));
            fail("crossProduct for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check vector lengthSquared
        assertTrue("Wrong result from vector lengthSquared",
                isZero(new Vector(3, 2, 1).lengthSquared() - 14));

    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check vector length
        assertTrue("Wrong result from vector length",
                isZero(new Vector(9, 4, 1).length() - Math.sqrt(98)));
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        Vector v1 = new Vector(0, 3, -4);
        Vector v2 = v1.normalize();

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that normalize does not creates new vector
        assertTrue("normalize function creates a new vector", v2 == v1);

        // TC02: check that normalize result is unit vector
        assertTrue("normalize result is not a unit vector", isZero(v2.length() - 1));

        // TC03: check that normalize result is the right vector
        assertTrue("normalize result is not the right vector", v2.equals(new Vector(0, 0.6, -0.8)));
    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    public void testNormalized() {
        Vector v1 = new Vector(0, 3, -4);
        Vector v2 = v1.normalized();

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that normalized creates new vector
        assertTrue("normalized function does not create a new vector", v2 != v1);

        // TC02: check that normalized result is unit vector
        assertTrue("normalized result is not a unit vector", isZero(v2.length() - 1));

        // TC03: check that normalized result is the right vector
        assertTrue("normalized result is not the right vector", v2.equals(new Vector(0, 0.6, -0.8)));
    }
}