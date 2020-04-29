package unittests;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

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

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
       Plane plane = new Plane(new Point3D(1,1,1),new Vector(1,1,1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 point)
        assertEquals("Wrong plane findIntersections when Ray does intersect the plane", List.of(new Point3D(1,2,0)),
                plane.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));
        // TC02: Ray does not intersect the plane (0 points)
        assertEquals("Wrong plane findIntersections when Ray does not intersect the plane", null,
                plane.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-1, -1, 0))));


        // =============== Boundary Values Tests ================
        // **** Group: Ray is parallel to the plane
        // TC11: Ray is included in the plane (0 points)
        assertEquals("Wrong plane findIntersections when Ray is parallel to the plane and included in the plane", null,
                plane.findIntersections(new Ray(new Point3D(1,1,1), new Vector(1,0,-1))));
        // TC12: Ray is not included in the plane (0 points)
        assertEquals("Wrong plane findIntersections when Ray is parallel to the plane but not included in the plane", null,
                plane.findIntersections(new Ray(new Point3D(2, 2,2), new Vector(1,0,-1))));
        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane (1 point)
        assertEquals("Wrong plane findIntersections when Ray is orthogonal to the plane and starts after the plane",
                List.of(new Point3D(4d/3,4d/3,1d/3)),
                plane.findIntersections(new Ray(new Point3D(-1, -1,-2), new Vector(1,1,1))));
        // TC14: Ray starts in the plane (0 points)
        assertEquals("Wrong plane findIntersections when Ray is orthogonal to the plane and starts in the plane", null,
                plane.findIntersections(new Ray(new Point3D(1d/3, 4d/3,4d/3), new Vector(1,1,1))));
        // TC15: Ray starts after the plane (0 points)
        assertEquals("Wrong plane findIntersections when Ray is orthogonal to the plane and starts after the plane", null,
                plane.findIntersections(new Ray(new Point3D(1, 2,2), new Vector(1,1,1))));
        // **** Group: Ray is neither orthogonal nor parallel to and begins at the plane
        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        assertEquals("Wrong plane findIntersections when Ray is neither orthogonal nor parallel to the plane and begins in the plane",null,
                plane.findIntersections(new Ray(new Point3D(3.5,0.25,-0.75), new Vector(2,1,1))));
        // **** Group: Ray is neither orthogonal nor parallel to and begins at the plane
        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in the planes reference point (0 points)
        assertEquals("Wrong plane findIntersections when Ray is neither orthogonal nor parallel to the plane and begins in the planes reference point",null,
                plane.findIntersections(new Ray(new Point3D(1,1,1), new Vector(2,1,1))));

    }

}