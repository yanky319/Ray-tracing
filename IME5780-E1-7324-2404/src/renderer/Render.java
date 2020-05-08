package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.awt.*;
import java.io.PipedOutputStream;
import java.util.List;


public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;

    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
    }

    public void renderImage() {
        Camera camera = _scene.get_camera();
        Intersectable geometries = _scene.get_geometries();
        Color background = _scene.get_background().getColor();
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        double height = _imageWriter.getHeight();
        double width = _imageWriter.getWidth();
        double distance = _scene.get_distance();

        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                Ray ray = camera.constructRayThroughPixel(nx, ny, j, i, distance, width, height);
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background);
                else {
                    Point3D p = getClosestPoint(intersectionPoints);
                    Color color = calcColor(p);
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    private Point3D getClosestPoint(List<Point3D> points) {
        Point3D ClosestPoint = null;
        double minimum = Double.MAX_VALUE;
        Point3D p0 = _scene.get_camera().get_p0();
        for (Point3D p : points) {
            double distance = p0.distance(p);
            if (distance < minimum) {
                minimum = distance;
                ClosestPoint = p;
            }
        }
        return ClosestPoint;
    }

    private Color calcColor(Point3D p) {
        return _scene.get_ambientLight().GetIntensity().getColor();
    }

    public void printGrid(int interval, java.awt.Color color) {
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();

        for (int i = 0; i < ny; i++)
            for (int j = 0; j < nx; j++)
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
