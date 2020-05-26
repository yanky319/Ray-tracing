package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import parser.XMLtoRenderParser;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static org.junit.Assert.assertEquals;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * function that creates a Render by getting its parameters from XMLtoRenderParser.
     *
     * @param fileName the name of the XML document to be passed to the Parser
     * @return Render including the Scene and image writer fro the XML document
     */
    private Render renderFromXml(String fileName) {
        XMLtoRenderParser parser;
        try {
            parser = new XMLtoRenderParser(fileName); // create instance of XML Parser
        } catch (Exception e) { // if it fails return null
            return null;
        }
        // create instance of Scene and get its parameters from the XML parser
        Scene scene = new Scene("XML Test scene");
        scene.set_camera(parser.cameraFromXML());
        scene.set_distance(parser.distanceFromXml());
        scene.set_background(parser.backgroundFromXML());
        scene.set_ambientLight(parser.AmbientLightFromXML());

        for (Intersectable i : parser.geometriesFromXml())
            scene.addGeometries(i);

        // create instance of imageWriter and get its parameters from the XML parser
        ImageWriter imageWriter = new ImageWriter("XML base render test",
                parser.imageWidthFromXML(), parser.imageHeightFromXML(), parser.nXFromXML(), parser.nyFromXML());
        // create and return instance of Render that includes the imageWriter and Scene from the XML parser
        return new Render(imageWriter, scene);
    }

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a grid
     * and do so also to a scene with basic 3D model given from an XML document.
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(100);
        scene.set_background(new Color(75, 127, 90));
        scene.set_ambientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();

        Render XML_render = renderFromXml("basicRenderTestTwoColors.xml");
        if (XML_render != null) {
            XML_render.renderImage();
            XML_render.printGrid(50, java.awt.Color.YELLOW);
            XML_render.writeToImage();
        }
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(100);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Color(java.awt.Color.BLUE),
                        new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),      // lower right
                new Triangle(
                        new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),    // upper right
                new Triangle(new Color(java.awt.Color.RED),
                        new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),    // lower left
                new Triangle(new Color(java.awt.Color.GREEN),
                        new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100))); // upper left

        ImageWriter imageWriter = new ImageWriter("color render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);
        render.writeToImage();
    }

//    /**
//     * test method for{@link renderer.Render#getClosestPoint(List)}
//     */
//    @Test
//    public void getClosestPoint() {
//        Scene scene = new Scene("Test scene");
//        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
//        scene.set_distance(100);
//        scene.set_background(new Color(75, 127, 90));
//        scene.set_ambientLight(new AmbientLight(new Color(255, 191, 191), 1));
//        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
//        Render render = new Render(imageWriter, scene);
//
//
//        // =============== Boundary Values Tests ==================
//
//        //TC11 list is null
//        List<GeoPoint> points = null;
//        assertEquals("does not return null when the list is null", null, render.getClosestPoint(points));
//
//        //TC12 empty list of Points
//        points = new LinkedList<>();
//        assertEquals("does not return null when the list is empty", null, render.getClosestPoint(points));
//
//        // ============ Equivalence Partitions Tests ==============
//
//        // 01 check if returns the right point
//        points.add(new GeoPoint(null, new Point3D(4, 6, 9)));
//        points.add(new GeoPoint(null, new Point3D(10, 3, 0.8)));
//        points.add(new GeoPoint(null, new Point3D(1, 2, 3)));
//        points.add(new GeoPoint(null, new Point3D(5, 3, 56.8)));
//        points.add(new GeoPoint(null, new Point3D(10, 6, 0.8)));
//        assertEquals("does not return the correct point", new Point3D(1, 2, 3), render.getClosestPoint(points).point);
//    }

}
