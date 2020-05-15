package parser;

import javax.xml.parsers.*;

import elements.AmbientLight;
import elements.Camera;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.*;
import java.io.*;

/**
 * a class for Parse from XML document of a scene and image
 * which together are whats needed to render the scene
 */
public class XMLtoRenderParser {
    private final String PATH = System.getProperty("user.dir") + "/images/"; // path of ware the file is
    Document XMLDocument; // the XML document created from the file

    //----------------------- constructor -----------------------------

    /**
     * constructor given the name of the file it opens the file and creates a XML document.
     *
     * @param fileName the name of the XML file
     */
    public XMLtoRenderParser(String fileName) {
        try {
            //creating a constructor of file class and parsing an XML file
            File file = new File(PATH + fileName);
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            XMLDocument = db.parse(file);
        } catch (Exception e) {
        }
    }

    // ------------ functions for Scene parameters ------------

    /**
     * creates the list of geometries in the Scene from the XML document.
     *
     * @return list of geometries for Scene
     */
    public List<Intersectable> geometriesFromXml() {
        List<Intersectable> intersectables = new LinkedList<>();                     // create empty list
        NodeList geometries = XMLDocument.getElementsByTagName("geometries").item(0).getChildNodes(); // get the list from the file
        for (int i = 0; i < geometries.getLength(); i++) {           // go over the list
            try {
                Element element = (Element) geometries.item(i); // get specific XML geometry and create the geometry
                // add more options of geometries here as deeded
                if (element.getNodeName() == "sphere") {
                    intersectables.add(new Sphere(doubleFromStr(element.getAttribute("radius"), 0),
                            pointFromStr(element.getAttribute("center"))));
                }
                if (element.getNodeName() == "triangle") {
                    intersectables.add(new Triangle(pointFromStr(element.getAttribute("p0")),
                            pointFromStr(element.getAttribute("p1")),
                            pointFromStr(element.getAttribute("p2"))));
                }
            } catch (Exception e) {
            }
        }
        return intersectables;
    }

    /**
     * creates the Camera in the Scene from the XML document.
     *
     * @return the camera for Scene
     */
    public Camera cameraFromXML() {
        Element cam = (Element) XMLDocument.getElementsByTagName("camera").item(0); // get camera XML element
        return new Camera(pointFromStr(cam.getAttribute("P0")),                    //create camera
                new Vector(pointFromStr(cam.getAttribute("Vto"))),
                new Vector(pointFromStr(cam.getAttribute("Vup"))));
    }

    /**
     * creates the Ambient Light in the Scene from the XML document.
     *
     * @return AmbientLight for Scene
     */
    public AmbientLight AmbientLightFromXML() {
        Element element = (Element) XMLDocument.getElementsByTagName("ambient-light").item(0); // get AmbientLight XML element
        return new AmbientLight(colorFromStr(element.getAttribute("color")), 1.0);         // create AmbientLight
    }

    /**
     * creates the background Color in the Scene from the XML document.
     *
     * @return background Color for Scene
     */
    public Color backgroundFromXML() {
        String str = XMLDocument.getDocumentElement().getAttribute("background-color"); // get background XML element
        return new Color(colorFromStr(str));                                                   // create the color
    }

    /**
     * creates the distance between the camera and view plane in the Scene from the XML document.
     *
     * @return that distance for Scene
     */
    public double distanceFromXml() {
        String str = XMLDocument.getDocumentElement().getAttribute("screen-distance"); // get screen-distance XML element
        return doubleFromStr(str, 0);                                                      // create the number
    }


    // ------------ functions for image writer parameters ------------

    /**
     * creates the width of screen for image writer from the XML document.
     *
     * @return width of screen
     */
    public double imageWidthFromXML() {
        Element img = (Element) XMLDocument.getElementsByTagName("image").item(0); // get image XML element
        return doubleFromStr(img.getAttribute("screen-width"), 0);              // get the width parameter
    }

    /**
     * creates the height of screen for image writer from the XML document.
     *
     * @return height of screen
     */
    public double imageHeightFromXML() {
        Element img = (Element) XMLDocument.getElementsByTagName("image").item(0); // get image XML element
        return doubleFromStr(img.getAttribute("screen-height"), 0);             // get the height parameter
    }

    /**
     * creates the number of pixels on X axis of screen for image writer from the XML document.
     *
     * @return number of pixels on X axis of screen
     */
    public int nXFromXML() {
        Element img = (Element) XMLDocument.getElementsByTagName("image").item(0); // get image XML element
        return (int) doubleFromStr(img.getAttribute("Nx"), 0);                  // get number of pixels on X axis of screen
    }

    /**
     * creates the number of pixels on Y axis of screen for image writer from the XML document.
     *
     * @return number of pixels on Y axis of screen
     */
    public int nyFromXML() {
        Element img = (Element) XMLDocument.getElementsByTagName("image").item(0); // get image XML element
        return (int) doubleFromStr(img.getAttribute("Ny"), 0);                  // get number of pixels on Y axis of screen
    }


    //------------- tools for parsing strings -------------

    /**
     * parser from string to double.
     *
     * @param str the string to be pares
     * @return the double
     */
    private double doubleFromStr(String str, int i) {
        return Double.parseDouble(str.split(" ")[i]);
    }

    /**
     * parser from string to Point3D instance.
     *
     * @param str the string to be pares
     * @return point3D instance
     */
    private Point3D pointFromStr(String str) {
        return new Point3D(doubleFromStr(str, 0),
                doubleFromStr(str, 1),
                doubleFromStr(str, 2));
    }

    /**
     * parser from string to Color instance.
     *
     * @param str the string to be pares
     * @return Color instance
     */
    private Color colorFromStr(String str) {
        return new Color(doubleFromStr(str, 0),
                doubleFromStr(str, 1),
                doubleFromStr(str, 2));
    }
}
