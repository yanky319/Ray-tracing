package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;


/**
 * Class Camera representing a Camera sending Ray's through a view plane.
 */
public class Camera {
    private Point3D _p0;
    private Vector _Vup;
    private Vector _Vto;
    private Vector _Vright;


    //*********************************** constructor ***************

    /**
     * constructor for class Camera gets location of camera and two Vectors and calculates the 3'rd.
     *
     * @param p0  the location of the camera
     * @param Vto the vector from the camera towards the view plane
     * @param Vup the vector from the camera upwards
     * @throws NullPointerException     if one of the arguments is null
     * @throws IllegalArgumentException if the Vectors given are not orthogonal
     */
    public Camera(Point3D p0, Vector Vto, Vector Vup) throws NullPointerException, IllegalArgumentException {
        if (p0 == null || Vto == null || Vup == null)
            throw new NullPointerException("one or more of the arguments is null");
        if (!isZero(Vto.dotProduct(Vup)))
            throw new IllegalArgumentException("VTo and Vup are not orthogonal");
        _p0 = new Point3D(p0);
        _Vup = Vup.normalized();
        _Vto = Vto.normalized();
        _Vright = _Vto.crossProduct(_Vup);
    }

    //*********************************** Getters ***************

    /**
     * getter for camera location.
     *
     * @return camera location
     */
    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    /**
     * getter for vector from camera upwards.
     *
     * @return vector from camera upwards
     */
    public Vector get_Vup() {
        return new Vector(_Vup);
    }

    /**
     * getter for vector from camera towards the view plane.
     *
     * @return vector from camera towards the view plane
     */
    public Vector get_Vto() {
        return new Vector(_Vto);
    }

    /**
     * getter for vector from camera to the right.
     *
     * @return vector from camera to the right
     */
    public Vector get_Vright() {
        return new Vector(_Vright);
    }

    //************** functions ******************

    /**
     * gets view plane parameters and indexes of a pixel and generates a Ray from the camera through that pixel.
     *
     * @param nX             number of pixels in X axis
     * @param nY             number of pixels in Y axis
     * @param j              column index of pixels
     * @param i              row index of pixels
     * @param screenDistance the distance between the camera and the view plane
     * @param screenWidth    the width of the view plane
     * @param screenHeight   the height of the view plane
     * @return a ray from the camera through the given pixel on the view plane
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        Point3D Pc = _p0.add(_Vto.scale(screenDistance));

        double Ry = screenHeight / (double) nY;
        double Rx = screenWidth / (double) nX;

        double Yi = (i - nY / 2d) * Ry + Ry / 2d;
        double Xj = (j - nX / 2d) * Rx + Rx / 2d;

        Point3D PIJ = new Point3D(Pc);
        if (Xj != 0)
            PIJ = PIJ.add(_Vright.scale(Xj));
        if (Yi != 0)
            PIJ = PIJ.add(_Vup.scale(-Yi));

        return new Ray(_p0, PIJ.subtract(_p0));
    }
}
