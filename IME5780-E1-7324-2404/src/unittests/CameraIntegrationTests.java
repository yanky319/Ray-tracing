package unittests;

import elements.Camera;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
import static geometries.Intersectable.GeoPoint;

/**
 * Testing integration with Camera Class
 */
public class CameraIntegrationTests {


    /**
     * Test method for integration of {@link geometries.Sphere#findIntersections(Ray)}
     * with {@link elements.Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithSphere() {
        Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sphere;
        Ray ray;
        List<GeoPoint> intersections;
        int count;

        //TC01: sphere is in front of center of view plane
        sphere = new Sphere(1, new Point3D(0, 0, 3));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam1.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = sphere.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when sphere is in front of center of view plane", 2, count);


        //TC02: sphere is in front of the whole view plane
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam2.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = sphere.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when sphere is in front of the whole view plane", 18, count);


        //TC03: sphere is in front of the view plane but not in front of the corners
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam2.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = sphere.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }

        assertEquals("wrong amount of intersections wen sphere is in front of the view plane but not in front of the corners", 10, count);

        //TC04: camera and view plane are inside the sphere
        sphere = new Sphere(4, new Point3D(0, 0, 2.5));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam2.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = sphere.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }

        assertEquals("wrong amount of intersections when camera and view plane are inside the sphere", 9, count);

        //TC05: camera and view plane are behind the sphere
        sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam1.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = sphere.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when camera and view plane are behind the sphere", 0, count);
    }

    /**
     * Test method for integration of {@link geometries.Triangle#findIntersections(Ray)}
     * with {@link elements.Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithTriangle() {
        Camera cam = new Camera(new Point3D(0, 0, -1), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Triangle triangle;
        Ray ray;
        List<GeoPoint> intersections;
        int count;

        //TC01: triangle only in front of the center of the view plane
        triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = triangle.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when triangle only in front of the center of the view plane", 1, count);

        //TC01: triangle only in front of the center and top middle of the view plane
        triangle = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = triangle.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when triangle only in front of the center and top middle of the view plane", 2, count);
    }

    /**
     * Test method for integration of {@link geometries.Plane#findIntersections(Ray)}
     * with {@link elements.Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithPlane() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Plane plane;
        Ray ray;
        List<GeoPoint> intersections;
        int count;


        //TC01: plane is in front of the view plane and parallel to it
        plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 0, 1));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = plane.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when plane is in front of the view plane", 9, count);

        //TC02: plane is in front of the view plane and tilted a little
        plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 0.5, 1));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = plane.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when plane is in front of the view plane and tilted a little ", 9, count);

        //TC03: plane is in front of the view plane and tilted more then 45 degrees
        plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 1, 1));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                intersections = plane.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("wrong amount of intersections when plane is in front of the view plane and tilted more then 45 degrees", 6, count);
    }
}