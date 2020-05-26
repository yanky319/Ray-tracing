package unittests;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphere class
 */
public class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#Sphere(double, Point3D)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================

        // TC11: check that constructor does not except negative radius
        try {
            new Sphere(-1, new Point3D(1, 1, 1));
            fail("Sphere constructor does not throw exception for negative radius");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: check that Sphere.getNormal(Point3D) returns the right vector
        assertEquals("Sphere incorrect normal vector",
                new Vector(0.5, 0.5, 1 / Math.sqrt(2)),
                new Sphere(2, new Point3D(1, 1, 1)).getNormal(new Point3D(2, 2, (Math.sqrt(2) + 1))));

        // TC02: check that Sphere.getNormal(Point3D) returns unit vector
        assertTrue("Sphere incorrect normal vector length"
                , isZero(new Sphere(2, new Point3D(1, 1, 1)).getNormal(new Point3D(2, 2, (Math.sqrt(2) + 1))).length() - 1));
    }


    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Wrong Sphere findIntersections when Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points when Ray crosses sphere", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Wrong Sphere findIntersections when Ray crosses sphere", List.of(new GeoPoint(sphere, p1),new GeoPoint(sphere, p2)), result);
        // TC03: Ray starts inside the sphere (1 point)
        assertEquals("Wrong Sphere findIntersections when Ray starts inside sphere",
                List.of(new GeoPoint(sphere, new Point3D(0.5 + ((Math.sqrt(390) + 3 * Math.sqrt(10)) / 20) * 3 / Math.sqrt(10), ((Math.sqrt(390) + 3 * Math.sqrt(10)) / 20) * 1 / Math.sqrt(10), 0))),
                sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(3, 1, 0))));
        //TC04: Ray starts after the sphere (0 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts after the sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-3, -1, 0))));
        // =============== Boundary Values Tests ================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts at sphere and goes inside",
                List.of(new GeoPoint(sphere, new Point3D(0.4, -0.8, 0))),
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-2, -1, 0))));
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(2, 1, 0))));
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point3D(-2, -Math.sqrt(3), 0), new Vector(Math.sqrt(3), 1, 0)));
        p1 = new Point3D(1 - Math.sqrt(3) / 2, -0.5, 0);
        p2 = new Point3D(1 + Math.sqrt(3) / 2, 0.5, 0);
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Wrong Sphere findIntersections when Ray starts before the sphere and goes through the center", List.of(new GeoPoint(sphere, p1),new GeoPoint(sphere, p2)), result);
        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts at sphere and goes through the center",
                List.of(new GeoPoint(sphere, p2)),
                sphere.findIntersections(new Ray(p1, new Vector(Math.sqrt(3), 1, 0))));
        // TC15: Ray starts inside (1 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts inside sphere and goes through the center",
                List.of(new GeoPoint(sphere, p2)),
                sphere.findIntersections(new Ray(new Point3D(2.5 - 0.5 * Math.sqrt(3), 0.5 * Math.sqrt(3) - 0.5, 0), new Vector(Math.sqrt(3), 1, 0))));
        // TC16: Ray starts at the center (1 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts at sphere center", List.of(new GeoPoint(sphere, p2)),
                sphere.findIntersections(new Ray(sphere.get_center(), new Vector(Math.sqrt(3), 1, 0))));
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(p2, new Vector(Math.sqrt(3), 1, 0))));
        // TC18: Ray starts after sphere (0 points)
        assertEquals("Wrong Sphere findIntersections when Ray starts after sphere", null,
                sphere.findIntersections(new Ray(new Point3D(1 + 1.5 * Math.sqrt(3), 1.5, 0), new Vector(Math.sqrt(3), 1, 0))));
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertEquals("Wrong Sphere findIntersections when Ray starts before the tangent point", null,
                sphere.findIntersections(new Ray(new Point3D(0.25 - Math.sqrt(7), 0, 0.25 * Math.sqrt(7) - 3), new Vector(Math.sqrt(7), 0, 3))));
        // TC20: Ray starts at the tangent point
        assertEquals("Wrong Sphere findIntersections when Ray starts at the tangent point", null,
                sphere.findIntersections(new Ray(new Point3D(0.25, 0, 0.25 * Math.sqrt(7)), new Vector(Math.sqrt(7), 0, 3))));
        // TC21: Ray starts after the tangent point
        assertEquals("Wrong Sphere findIntersections when Ray starts after the tangent point", null,
                sphere.findIntersections(new Ray(new Point3D(0.25 + Math.sqrt(7), 0, 0.25 * Math.sqrt(7) + 3), new Vector(Math.sqrt(7), 0, 3))));
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Wrong Sphere findIntersections when Ray starts after the tangent point", null,
                sphere.findIntersections(new Ray(new Point3D(-0.5, 0, 0.5 * Math.sqrt(7)), new Vector(Math.sqrt(7), 0, 3))));

    }

}


