package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class representing a scene that includes
 * a camera and a list of geometries,the background color and ambient lighting.
 */
public class Scene {

    /**
     * tha name of the scene
     */
    private final String _name;
    /**
     * list of Geometries in the scene
     */
    private final Geometries _geometries;
    /**
     * list of Light Sources in the scene
     */
    private final List<LightSource> _lights;

    /**
     * the background Color in the scene
     */
    private Color _background;
    /**
     * the ambient light in the scene
     */
    private AmbientLight _ambientLight;
    /**
     * the camera of the scene
     */
    private Camera _camera;
    /**
     * the distance from camera to simulated screen
     */
    private double _distance;

    //--------------- constructor ------------

    /**
     * constructor for Scene
     * sets the name of the Scene a empty list of geometries
     * and an empty list of Light Sources.
     *
     * @param name the name of the Scene
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
        _lights = new LinkedList<>();
    }

//--------------- getters -----------------

    /**
     * getter for the name of the Scene.
     *
     * @return the name of the Scene
     */
    public String get_name() {
        return _name;
    }

    /**
     * getter for the list of geometries in the Scene.
     *
     * @return the geometries
     */
    public Geometries get_geometries() {
        return _geometries;
    }

    /**
     * getter for the list of Light Sources in the Scene.
     *
     * @return the Light Sources
     */
    public List<LightSource> get_lights() {
        return _lights;
    }

    /**
     * getter for the background of the Scene.
     *
     * @return the background color of the Scene
     */
    public Color get_background() {
        return new Color(_background);
    }

    /**
     * getter for the ambient Light of the Scene.
     *
     * @return the ambient Light of the Scene
     */
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    /**
     * getter for the Camera of the Scene.
     *
     * @return the Camera of the Scene
     */
    public Camera get_camera() {
        return _camera;
    }

    /**
     * getter for the distance from camera to simulated screen in Scene.
     *
     * @return the distance
     */
    public double get_distance() {
        return _distance;
    }

//---------------- setters ------------------

    /**
     * setter for the background of the Scene.
     *
     * @param _background the background color
     */
    public void set_background(Color _background) {
        this._background = _background;
    }

    /**
     * setter for the ambient Light of the Scene.
     *
     * @param _ambientLight the ambient Light
     */
    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * setter for the Camera of the Scene.
     *
     * @param _camera the Camera
     */
    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * setter for the distance from camera to simulated screen in Scene.
     *
     * @param _distance the distance
     */
    public void set_distance(double _distance) {
        this._distance = _distance;
    }
// ----------- functions -----------

    /**
     * function for adding one or more geometries to the scene.
     *
     * @param geometries some geometries
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    /**
     * function for adding one or more Light Sources to the scene.
     *
     * @param lights some Light Sources
     */
    public void addLights(LightSource... lights) {
        for (LightSource l : lights) {
            _lights.add(l);
        }
    }
}

