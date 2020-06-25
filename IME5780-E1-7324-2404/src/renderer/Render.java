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

import static primitives.Util.*;

import java.util.List;

/**
 * class for Creating the color matrix from the scene for the image.
 */
public class Render {
    /**
     * max level of recursion when calculating reflection and transparency color
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * color that has les weight then this gives very little effect of color and wil not be calculated
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Object of image Writer for writing the image
     */
    private ImageWriter _imageWriter;
    /**
     * the scene to be Rendered
     */
    private Scene _scene;
    /**
     * boolean flag whether to super sample
     */
    private boolean _superSampling;
    /**
     * the size of the superSampling grid
     */
    private int _gridSize;
    // ------------------- constructor -----------

    /**
     * constructor for class Render.
     *
     * @param imageWriter instance of image Writer
     * @param scene       the scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        _superSampling = false;
        _gridSize = 0;
    }
    // -------------- setters --------------------

    /**
     * sets whether we are super sampling or not.
     *
     * @param superSampling
     */
    public void setSuperSampling(boolean superSampling) {
        _superSampling = superSampling;
    }

    /**
     * sets the size of superSampling grid.
     *
     * @param gridSize size of superSampling grid
     */
    public void setGridSize(int gridSize) {
        _gridSize = gridSize;
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
                GeoPoint closestIntersection = findClosestIntersection(ray); // find the closest intersection point on the ray and any geometry
                if (closestIntersection == null)                          // if no intersections
                    _imageWriter.writePixel(j, i, background.getColor());          //then color pixel with background color
                else {                                                  // if there are intersections
                    Color color = calcColor(closestIntersection, ray);                         // calculate the color of that point
                    _imageWriter.writePixel(j, i, color.getColor());               // color the pixel
                }
            }
        }
    }

    /**
     * finds the point from the list closest to the camera.
     * calls {@link renderer.Render#getClosestPoint(List, Point3D)} with camera as p0
     *
     * @param points a list of intersection points on the ray
     * @return the closest point to beginning of ray (the camera)
     */
    //public GeoPoint getClosestPoint(List<GeoPoint> points) {
    private GeoPoint getClosestPoint(List<GeoPoint> points) {
        return getClosestPoint(points, _scene.get_camera().get_p0());
    }

    /**
     * finds the point from the list closest to beginning of the ray.
     *
     * @param points a list of intersection points on the ray
     * @param p0     the head of the ray
     * @return the closest point to beginning of ray (p0)
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points, Point3D p0) {
        if (points == null || points.size() == 0)
            return null;
        GeoPoint ClosestPoint = null;
        double minimum = Double.MAX_VALUE;
        for (GeoPoint p : points) {              //go over all points in list
            double distance = p0.distance(p.point);   // calculate its distance from p0
            if (distance < minimum) {           // save the one with shortest distance
                minimum = distance;
                ClosestPoint = p;
            }
        }
        return ClosestPoint;                // return the point
    }

    /**
     * finds the closest intersection point between a ray and the geometries in the scene.
     * calls {@link geometries.Geometries#findIntersections(Ray)} to get all intersection points
     * and then calls {@link renderer.Render#getClosestPoint(List, Point3D)} to find the one closest to beginning of the ray
     *
     * @param ray the given ray
     * @return closest intersection point or null if there are no intersection points
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        if (ray == null)
            return null;
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray); // find intersections between the ray and any geometries
        GeoPoint ClosestPoint = getClosestPoint(intersections, ray.get_p0()); // find the closest intersection
        return ClosestPoint;
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
                if (unshaded(l, n, geoPoint, light)) {
                    Color li = light.getIntensity(p);
                    color = color.add(
                            calcDiffusive(kd, nl, li),
                            calcSpecular(ks, l, n, nl, v, nShininess, li));
                }
            }
        }
        return color;
    }

    /**
     * calculates the color of a given geoPoint taking in count all light sources in the scene
     * and all reflection and transparency colors.
     * calls recursion function {@link renderer.Render#calcColor(GeoPoint, Ray, int, double)} to add all reflection and transparency colors
     * and adds the ambient Light
     *
     * @param geoPoint the given geoPoint
     * @param inRay    the ray of light that hits at the given geoPoint
     * @return the color at that geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        return calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0)
                .add(_scene.get_ambientLight().get_intensity());
    }

    /**
     * calculates the color of a given geoPoint taking in count all light sources in the scene
     * and all reflection and transparency colors.
     * calls {@link renderer.Render#getLightSourceColor(GeoPoint, Color, Vector, Vector, double, double, int, double)}  to add all light source effects
     * calls itself with recursion to add all reflection and transparency colors
     *
     * @param geoPoint the given geoPoint
     * @param inRay    the ray of light that hits at the given geoPoint
     * @param level    level of recursion
     * @param k        the weight of color calculated at this call
     * @return the color at that geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        if (level == 0)
            return Color.BLACK;
        Color color = geoPoint.geometry.get_emission();

        Point3D point = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point); //normal vector from geometry at the point
        Vector v = point.subtract(_scene.get_camera().get_p0()).normalize(); // vector from camera to the point

        double kd = geoPoint.geometry.get_material().get_kD();
        double ks = geoPoint.geometry.get_material().get_kS();
        int nShininess = geoPoint.geometry.get_material().get_nShininess();
        color = color.add(getLightSourceColor(geoPoint, color, v, n, kd, ks, nShininess, k));

        double kr = geoPoint.geometry.get_material().get_kR();
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectionRay = constructReflectedRay(point, inRay, n);
            GeoPoint reflectionPoint = findClosestIntersection(reflectionRay);
            if (reflectionPoint != null)
                color = color.add(calcColor(reflectionPoint, reflectionRay, level - 1, kkr).scale(kr));
        }


        double kt = geoPoint.geometry.get_material().get_kT();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractionRay = constructRefractedRay(point, inRay, n);
            GeoPoint refractionPoint = findClosestIntersection(refractionRay);
            if (refractionPoint != null)
                color = color.add(calcColor(refractionPoint, refractionRay, level - 1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * calculates the effect of all light sources at a given point.
     *
     * @param geoPoint   the given geoPoint
     * @param color      color at the given geoPoint
     * @param v          vector from camera to the point
     * @param n          normal vector at the given geoPoint
     * @param kd         diffuse coefficient of the material of geometry
     * @param ks         specular coefficient of the material of geometry
     * @param nShininess strength of Shininess of the material of geometry
     * @param k          the weight of color calculated at this call
     * @return the color at that geoPoint
     */
    private Color getLightSourceColor(GeoPoint geoPoint, Color color, Vector v, Vector n, double kd, double ks, int nShininess, double k) {
        Point3D point = geoPoint.point;
        List<LightSource> lightSources = _scene.get_lights();
        for (LightSource light : lightSources) {
            Vector l = light.getL(point); //vector from light source to the point
            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));
            if (nl * nv > 0) {  // check both dotProducts hav the same sign
                //if (unshaded(l, n, geoPoint, light)) {
                double ktr = transparency(l, n, geoPoint, light);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color li = light.getIntensity(point).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, nl, li),
                            calcSpecular(ks, l, n, nl, v, nShininess, li));
                }
            }
        }
        return color;
    }

    /**
     * constructs the ray of refracted light.
     *
     * @param point the point we ned refracted ray from
     * @param inRay the ray from source that huts at the point
     * @param n     normal vector from tha geometry at the point
     * @return ray of refracted light
     */
    private Ray constructRefractedRay(Point3D point, Ray inRay, Vector n) {
        return new Ray(point, inRay.get_direction(), n);
    }

    /**
     * constructs the ray of reflected light.
     *
     * @param point the point we ned reflected ray from
     * @param inRay the ray from source that huts at the point
     * @param n     normal vector from tha geometry at the point
     * @return ray of reflected light
     */
    private Ray constructReflectedRay(Point3D point, Ray inRay, Vector n) {
        // r = v - 2*(v*n)*n
        Vector v = inRay.get_direction();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(point, r, n);
    }

    /**
     * checks whether the light from a source hits a given point or that point is shaded by another object.
     *
     * @param l           vector from light source towards the point
     * @param n           normal vector from the geometry at the given point
     * @param gp          the geo point we are checking if is unshaded
     * @param lightSource the light source we are checking if the light from gets to the point
     * @return boolean value true wen the point is unshaded
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // get vector from point towards light source
        Point3D p = gp.point;
        Ray lightRay = new Ray(p, lightDirection, n); // create ray from the point towards the light source
        // check if the ray intersects objects that are closer then the light source
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay, lightSource.getDistance(p));
        if (intersections != null) {
            for (GeoPoint point : intersections)
                if (point.geometry.get_material().get_kT() == 0)
                    return false;
        }
        return true;
    }

    /**
     * checks whether the light from a source hits a given pointor that point is shaded by another object.
     *
     * @param l           vector from light source towards the point
     * @param n           normal vector from the geometry at the given point
     * @param gp          the geo point we are checking if is unshaded
     * @param lightSource the light source we are checking if the light from gets to the point
     * @return boolean value true wen the point is unshaded
     */
    private double transparency(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // get vector from point towards light source
        Point3D p = gp.point;
        Ray lightRay = new Ray(p, lightDirection, n); // create ray from the point towards the light source
        // check if the ray intersects objects that are closer then the light source
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay, lightSource.getDistance(p));
        if (intersections == null || intersections.size() == 0)
            return 1.0;
        double ktr = 1.0;
        for (GeoPoint point : intersections) {
            ktr *= point.geometry.get_material().get_kT();
            if (ktr < MIN_CALC_COLOR_K)
                return 0.0;
        }
        return ktr;
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
        if (isZero(nl))
            return Color.BLACK;
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
