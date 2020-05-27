package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import primitives.Color;

import static java.lang.System.out;
import static primitives.Util.*;

import java.awt.*;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * class for Creating the color matrix from the scene for the image.
 */
public class Render {
    /**
     * Object of image Writer for writing the image
     */
    private ImageWriter _imageWriter;
    /**
     * the scene to be Rendered
     */
    private Scene _scene;

    // ------------------- constructor -----------

    /**
     * constructor for class Render.
     *
     * @param imageWriter  instance of image Writer
     * @param scene        the scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    //--------------- functions -----------------

    /**
     * construct Rays from the camera Through every Pixel and calculates the color of that pixel
     * by checking if the ray intersects with any geometries
     * and which is in front ant what is its color.
     */
    public void renderImage() {
        Camera camera = _scene.get_camera();
        Intersectable geometries = _scene.get_geometries();
        Color background = _scene.get_background();
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        double height = _imageWriter.getHeight();
        double width = _imageWriter.getWidth();
        double distance = _scene.get_distance();

        for (int i = 0; i < ny; i++) {                        // go over all of the pixels
            for (int j = 0; j < nx; j++) {
                Ray ray = camera.constructRayThroughPixel(nx, ny, j, i, distance, width, height);   // construct a ray from tha camera through it
                List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(ray); // find intersections between the ray and any geometries
                if (intersectionPoints == null)                          // if no intersections
                    _imageWriter.writePixel(j, i, background.getColor());          //then color pixel with background color
                else {                                                  // if there are intersections
                    GeoPoint p = getClosestPoint(intersectionPoints);    // find the closest intersection point
                    Color color = calcColor(p);                         // calculate the color of that point
                    _imageWriter.writePixel(j, i, color.getColor());               // color the pixel
                }
            }
        }
    }

    /**
     * finds the point on the ray closest to its beginning.
     *
     * @param points a list of points on the ray
     * @return the closest point to beginning of ray
     */
    //public GeoPoint getClosestPoint(List<GeoPoint> points) {
    private GeoPoint getClosestPoint(List<GeoPoint> points) {
        if (points == null || points.size() == 0)
            return null;
        GeoPoint ClosestPoint = null;
        double minimum = Double.MAX_VALUE;
        Point3D p0 = _scene.get_camera().get_p0();
        for (GeoPoint p : points) {              //go over all points in list
            double distance = p0.distance(p.point);   // calculate its distance from camera
            if (distance < minimum) {           // save the one with shortest distance
                minimum = distance;
                ClosestPoint = p;
            }
        }
        return ClosestPoint;                // return the point
    }

    /**
     * calculates the color of a given geoPoint taking in count all light sources in the scene.
     *
     * @param geoPoint the given geoPoint
     * @return the color at that geoPoint
     */
    private Color calcColor(GeoPoint geoPoint) {
        Color color = _scene.get_ambientLight().get_intensity();
        color = color.add(geoPoint.geometry.get_emission());
        List<LightSource> lightSources = _scene.get_lights();
        if (lightSources.size() == 0)
            return color;
        Point3D p = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(p); //normal vector from geometry at the point
        Vector v = p.subtract(_scene.get_camera().get_p0()).normalize(); // vector from camera to the point
        double kd = geoPoint.geometry.get_material().get_kD();
        double ks = geoPoint.geometry.get_material().get_kS();
        int nShininess = geoPoint.geometry.get_material().get_nShininess();
        for (LightSource light : lightSources) {
            Vector l = light.getL(p); //vector from light source to the point
            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));
            if (Math.signum(nl) == Math.signum(nv)) {
                Color li = light.getIntensity(p);
                color = color.add(
                        calcDiffusive(kd, nl, li),
                        calcSpecular(ks, l, n, nl, v, nShininess, li));
            }
        }
        return color;
    }

    /**
     * calculates the specular part of the reflection of light.
     *
     * @param ks         specular coefficient
     * @param l          vector from light source to the point
     * @param n          normal vector from geometry at the point
     * @param nl         dot product between the 2 vectors
     * @param v          vector from camera to the point
     * @param nShininess strength of Shininess
     * @param li         the light that hits the geometry at the point
     * @return specular reflection of light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color li) {
        Vector r = new Vector(l);
        if (!isZero(nl))
            r = r.subtract(n.scale(2 * nl));
        double vr = v.scale(-1).dotProduct(r);
        return li.scale(ks * Math.pow(Math.max(0, vr), nShininess));
    }

    /**
     * calculates the diffuse part of the reflection of light.
     *
     * @param kd diffuse coefficient
     * @param nl dot product between vector from light source to the point
     *           and normal vector from geometry at the point
     * @param li the light that hits the geometry at the point
     * @return diffuse reflection of light
     */
    private Color calcDiffusive(double kd, double nl, Color li) {
        return li.scale(Math.abs(kd * nl));
    }


    /**
     * prints a grid on top of the image.
     *
     * @param interval the amount of pixels between the grid lines
     * @param color    the color of the grid lines
     */
    public void printGrid(int interval, java.awt.Color color) {
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();

        for (int i = 0; i < ny; i++)
            for (int j = 0; j < nx; j++)
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
    }

    /**
     * writes the pixels to the image file and saves it.
     * calls {@link ImageWriter#writeToImage()}.
     */
    public void writeToImage() {
        _imageWriter.writeToImage(); // pass on the job to the imageWriter
    }
}
