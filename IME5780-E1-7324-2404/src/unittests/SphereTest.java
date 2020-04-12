package unittests;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphere class
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#Sphere(double, Point3D)}.
     */
    @Test
    public void testConstructor(){
        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not except negative radius
        try
        {
            new Sphere(-1,new Point3D(1,1,1));
            fail("Sphere constructor does not throw exception for negative radius");
        }
        catch (IllegalArgumentException e){}
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Sphere.getNormal(Point3D) returns the right vector
        assertEquals("Sphere incorrect normal vector",
                new Vector(0.5,0.5,1/Math.sqrt(2)),
                new Sphere(2,new Point3D(1,1,1)).getNormal(new Point3D(2,2,(Math.sqrt(2)+1))));

        // TC02: check that Sphere.getNormal(Point3D) returns unit vector
        assertTrue("Sphere incorrect normal vector length"
                ,isZero(new Sphere(2,new Point3D(1,1,1)).getNormal(new Point3D(2,2,(Math.sqrt(2)+1))).length()-1));
    }
}


