package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.awt.*;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * class for Creating the color matrix from the scene for the image.
 */
public class Render {

    private ImageWriter _imageWriter; // Object of image Writer
    private Scene _scene; // the scene to be Rendered

    // ------------------- constructor -----------

    /**
     * constructor for class Render.
     *
     * @param _imageWriter // instance of image Writer
     * @param _scene       // the scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
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
        Color background = _scene.get_background().getColor();
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        double height = _imageWriter.getHeight();
        double width = _imageWriter.getWidth();
        double distance = _scene.get_distance();

        for (int i = 0; i < ny; i++) {                        // go over all of the pixels
            for (int j = 0; j < nx; j++) {
                Ray ray = camera.constructRayThroughPixel(nx, ny, j, i, distance, width, height);   // construct a ray from tha camera through it
                List<Point3D> intersectionPoints = geometries.findIntersections(ray); // find intersections between the ray and any geometries
                if (intersectionPoints == null)                          // if no intersections
                    _imageWriter.writePixel(j, i, background);          //then color pixel with background color
                else {                                                  // if there are intersections
                    Point3D p = getClosestPoint(intersectionPoints);    // find the closest intersection point
                    Color color = calcColor(p);                         // calculate the color of that point
                    _imageWriter.writePixel(j, i, color);               // color the pixel
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
    //public Point3D getClosestPoint(List<Point3D> points) {
    private Point3D getClosestPoint(List<Point3D> points) {
        if(points == null || points.size() ==0 )
            return null;
        Point3D ClosestPoint = null;
        double minimum = Double.MAX_VALUE;
        Point3D p0 = _scene.get_camera().get_p0();
        for (Point3D p : points) {              //go over all points in list
            double distance = p0.distance(p);   // calculate its distance from camera
            if (distance < minimum) {           // save the one with shortest distance
                minimum = distance;
                ClosestPoint = p;
            }
        }
        return ClosestPoint;                // return the point
    }

    /**
     * calculates the color of a given point taking in count all light sources in the scene.
     *
     * @param point the given point
     * @return the color at that point
     */
    private Color calcColor(Point3D point) {
        return _scene.get_ambientLight().GetIntensity().getColor();
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
     */
    public void writeToImage() {
        _imageWriter.writeToImage(); // pass on the job to the imageWriter
    }
}
