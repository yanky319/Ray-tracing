package unittests;

import org.junit.Test;
import primitives.Vector;

import static primitives.Util.isZero;
import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Vector class
 */
public class VectorTests {

    /**
     * Test method for {@link Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("Wrong vector add",
                new Vector(4, 5, 6),
                new Vector(3, 3, 3).add(new Vector(1, 2, 3)));
        // =============== Boundary Values Tests ==================
        try {
            new Vector(3, 3, 3).add(new Vector(-3, -3, -3));
            fail("addition of V + (-V) does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link Vector#subtract(Vector)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("Wrong vector Subtract",
                new Vector(2, 1, 0),
                new Vector(3, 3, 3).subtract(new Vector(1, 2, 3)));
        // =============== Boundary Values Tests ==================
        try {
            new Vector(3, 3, 3).subtract(new Vector(3, 3, 3));
            fail("Subtraction of V - V does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("Wrong vector scale",
                new Vector(7.5, 7.5, 5),
                new Vector(3, 3, 2).scale(2.5));
        // =============== Boundary Values Tests ==================
        try {
            new Vector(3, 3, 3).scale(0);
            fail("multiplication of V by zero does not throw Exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     */
    @Test
    public void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("Wrong vector dotProduct",
                18,
                new Vector(3, 3, 3).dotProduct(new Vector(1, 2, 3)),
                0.00001);
        // =============== Boundary Values Tests ==================
        assertTrue("dotProduct for orthogonal vectors is not zero",
                isZero(new Vector(3, 0, 0).dotProduct(new Vector(0, 2, 0))));
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(3, 3, 3);
        Vector v2 = new Vector(1, 2, 3);
        Vector v3 = new Vector(3, -6, 3);

        // ============ Equivalence Partitions Tests ==============
        assertEquals("Wrong vector CrossProduct",
                v3,
                v1.crossProduct(v2));
        assertTrue("CrossProduct result is not orthogonal to its operands",
                isZero(v3.dotProduct(v1)) && isZero(v3.dotProduct(v2)));
        // =============== Boundary Values Tests ==================
        try {
            new Vector(v1).crossProduct(new Vector(v1.scale(-2)));
            fail("crossProduct for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        assertEquals("Wrong vector lengthSquared",
                14,
                new Vector(3, 2, 1).lengthSquared(),
                0.00001);
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void testLength() {
        assertEquals("Wrong vector length",
                Math.sqrt(98),
                new Vector(9, 4, 1).length(),
                0.00001);
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        Vector v1 = new Vector(0, 3, -4);
        Vector v2 = v1.normalize();
        assertTrue("normalize function creates a new vector", v2 == v1);
        assertTrue("normalize result is not a unit vector", isZero(v2.length() - 1));
        assertTrue("normalize result is not the right vector", v2.equals(new Vector(0, 0.6, -0.8)));
    }

    /**
     * Test method for {@link Vector#normalized()}.
     */
    @Test
    public void testNormalized() {
        Vector v1 = new Vector(0, 3, -4);
        Vector v2 = v1.normalized();
        assertTrue("normalized function does not creates a new vector", v2 != v1);
        assertTrue("normalized result is not a unit vector", isZero(v2.length() - 1));
        assertTrue("normalized result is not the right vector", v2.equals(new Vector(0, 0.6, -0.8)));
    }
}