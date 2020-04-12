package unittests;


import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTests {

    /**
     * Test method for {@link geometries.Tube#Tube(double, Ray)}.
     */
    @Test
    public void testConstructor(){

        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not except negative radius
        try
        {
            new Tube(-1,new Ray(new Point3D(0,0,0), new Vector(1,1,1)));
            fail("Tube constructor does not throw exception for negative radius");
        }
        catch (IllegalArgumentException e){}
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Tube.getNormal(Point3D) returns the right vector
        assertEquals("Tube incorrect normal vector",
                new Vector(1,0,0),
                new Tube(1,new Ray(new Point3D(0,0,0),
                        new Vector(0,0,1))).getNormal(new Point3D(1,0,1.5)));

        // TC02: check that Tube.getNormal(Point3D) returns unit vector
        assertTrue("Tube incorrect normal vector length",isZero(new Tube(1,new Ray(new Point3D(0,0,0),
                new Vector(0,0,1))).getNormal(new Point3D(1,0,1.5)).length()-1));
    }
}