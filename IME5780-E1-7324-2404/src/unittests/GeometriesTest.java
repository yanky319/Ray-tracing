package unittests;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Geometries class
 */
public class GeometriesTest {

    /**
     * Test method for {@link Geometries#Geometries()}.
     */
    @Test
    public void testConstructor1() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: empty constructor
        try{
            new Geometries().getListSize();
        }catch (NullPointerException e){
            fail("empty constructor for class Geometries dose not make a list");
        }

    }

    /**
     * Test method for {@link Geometries#Geometries(Intersectable...)}.
     */
    @Test
    public void testConstructor2() {
        Triangle triangle1 = new Triangle(new Point3D(4.1, -0.7, 0.8), new Point3D(1, 2, 2), new Point3D(3, 3, 1));
        Sphere sphere2 =new Sphere(3, new Point3D(1, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: more then one parameter constructor
        Geometries geometries1 = new Geometries(sphere2,triangle1);
        try{
            geometries1.getListSize();
        }catch (NullPointerException e){
            fail("parameter constructor for class Geometries dose not make a list");
        }
        assertEquals("wrong list size from parameter constructor",2,geometries1.getListSize());

        // =============== Boundary Values Tests ================
        // TC11: one parameter constructor
        Geometries geometries2 = new Geometries(triangle1);
        try{
            geometries2.getListSize();
        }catch (NullPointerException e){
            fail("parameter constructor for class Geometries dose not make a list when given one parameter");
        }
        assertEquals("wrong list size from parameter constructor",1,geometries2.getListSize());
    }

    /**
     * Test method for {@link geometries.Geometries#add(Intersectable...)}.
     */
    @Test
    public void testAdd() {
        Triangle triangle1 = new Triangle(new Point3D(4.1, -0.7, 0.8), new Point3D(1, 2, 2), new Point3D(3, 3, 1));
        Triangle triangle2 = new Triangle(new Point3D(1, 1, 1), new Point3D(1, 2, 2), new Point3D(3, 3, 1));
        Sphere sphere1 =new Sphere(1, new Point3D(1, 1, 1));
        Sphere sphere2 =new Sphere(3, new Point3D(1, 1, 1));
        Geometries geometries1 = new Geometries();
        Geometries geometries2 = new Geometries();

        // =============== Boundary Values Tests ================
        // TC11: add one to empty group of geometries
        geometries1.add(sphere1);
        assertEquals("Wrong Geometries add when adding one to empty group of geometries", 1,geometries1.getListSize());
        // TC12: add more then one to empty group of geometries
        geometries2.add(sphere1,triangle1);
        assertEquals("Wrong Geometries add when adding more then one to empty group of geometries", 2,geometries2.getListSize());

        // ============ Equivalence Partitions Tests ==============
        // TC01: add one to not empty group of geometries
        geometries1.add(sphere2);
        assertEquals("Wrong Geometries add when adding one to not empty group of geometries", 2,geometries1.getListSize());
        // TC02: add more then one to not empty group of geometries
        geometries2.add(sphere2,triangle2);
        assertEquals("Wrong Geometries add when adding more then one to not empty group of geometries", 4,geometries2.getListSize());


    }

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
        Triangle triangle = new Triangle(new Point3D(4.1, -0.7, 0.8), new Point3D(1, 2, 2), new Point3D(3, 3, 1));
        Polygon polygon = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0)
                , new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        Plane plane = new Plane(new Point3D(1, 1, 1), new Vector(1, -1, 1));
        Geometries geometries = new Geometries(sphere, triangle, polygon, plane);
        // ============ Equivalence Partitions Tests ==============

        // TC01: some of the elements in the group of geometries gets intersected
        List<Point3D> result = geometries.findIntersections(new Ray(new Point3D(1.375, 0.7, 0.5), new Vector(-1.875, -0.2, -0.5)));
        assertEquals("Wrong Geometries findIntersections when Ray crosses some of the elements in the group (case 1)", 3, result.size());
        List<Point3D> result2 = geometries.findIntersections(new Ray(new Point3D(1.375, 0.7, 0.5), new Vector(1.875, 0.2, 0.5)));
        assertEquals("Wrong Geometries findIntersections when Ray crosses some of the elements in the group (case 2)", 2, result2.size());

        // =============== Boundary Values Tests ================

        // TC11: empty group of geometries (0 points)
        assertEquals("Wrong Geometries findIntersections when empty group of geometries", null,
                new Geometries().findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0))));
        // TC12: none of the elements in the group of geometries gets intersected (0 points)
        assertEquals("Wrong Geometries findIntersections when Ray crosses none of the elements in the group", null,
                geometries.findIntersections(new Ray(new Point3D(3.4, -8, 0), new Vector(1, 1, 0))));
        // TC13: one of the elements in the group of geometries gets intersected (1 point)
        assertEquals("Wrong Geometries findIntersections when Ray crosses one of the elements in the group", 1,
                geometries.findIntersections(new Ray(new Point3D(3.4, -8, 0), new Vector(1, 15, 0))).size());
        // TC14: all of the elements in the group of geometries gets intersected
        assertEquals("Wrong Geometries findIntersections when Ray crosses all of the elements in the group", 5,
                geometries.findIntersections(new Ray(new Point3D(7, 1.3, 2), new Vector(-7.5, -0.8, -2))).size());
    }
}