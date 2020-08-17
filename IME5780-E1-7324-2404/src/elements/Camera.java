package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.*;


/**
 * Class Camera representing a Camera sending Ray's through a view plane.
 */
public class Camera {
    /**
     * location of tha camera
     */
    private Point3D _p0;
    /**
     * vector from center of the camera upwards
     */
    private Vector _Vup;
    /**
     * vector from center of the camera towards the view plane
     */
    private Vector _Vto;
    /**
     * vector from center of the camera t the right
     */
    private Vector _Vright;


    //*********************************** constructor **************

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
        // center point of the view plane
        Point3D Pc = _p0.add(_Vto.scale(screenDistance));
        // size of the pixel
        double Ry = screenHeight / (double) nY;
        double Rx = screenWidth / (double) nX;

        // distance to the center of the pixel
        double Yi = (i - nY / 2d) * Ry + Ry / 2d;
        double Xj = (j - nX / 2d) * Rx + Rx / 2d;

        // get point of the center of the pixel
        Point3D PIJ = new Point3D(Pc);
        if (Xj != 0)
            PIJ = PIJ.add(_Vright.scale(Xj));
        if (Yi != 0)
            PIJ = PIJ.add(_Vup.scale(-Yi));
        // create ray from camera through center of the pixel
        return new Ray(_p0, PIJ.subtract(_p0));
    }

    /**
     * gets view plane parameters and indexes of a pixel and generates a Beam Of Rays from the camera through that pixel.
     *
     * @param nX             number of pixels in X axis
     * @param nY             number of pixels in Y axis
     * @param j              column index of pixels
     * @param i              row index of pixels
     * @param screenDistance the distance between the camera and the view plane
     * @param screenWidth    the width of the view plane
     * @param screenHeight   the height of the view plane
     * @param numOfRays      the amount of rays to construct
     * @return a Beam Of Rays from the camera through the given pixel on the view plane
     */
    public List<Ray> constructBeamOfRaysThroughPixel(int nX, int nY,
                                                     int j, int i, double screenDistance,
                                                     double screenWidth, double screenHeight, int numOfRays) {
        // create list to hold the rays
        List<Ray> rays = new LinkedList<>();
        // center point of the view plane
        Point3D planeCenter = _p0.add(_Vto.scale(screenDistance));
        // size of each pixel
        double Ry = screenHeight / (double) nY;
        double Rx = screenWidth / (double) nX;
        // center of the pixel to construct the beam through
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        //calculate ray through pixel(i,j) center
        Point3D Pij = new Point3D(planeCenter);
        if (!isZero(xj)) {
            Pij = Pij.add(_Vright.scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.add(_Vup.scale((-yi)));
        }
        rays.add(new Ray(_p0, Pij.subtract(_p0)));

        Random r = new Random();

        // the parameter to calculate the coefficient of the _vRight and _vUp vectors
        double dX, dY;
        //the coefficient to calculate in which quadrant is random point on this pixel
        int k, h;
        Point3D randomPoint;
        // divide the random points evenly within the four quarters
        for (int t = 0; t < 4; t++) {
            // decide which quarter we are in
            k = t != 1 && t != 2 ? 1 : -1;
            h = t != 2 && t != 3 ? 1 : -1;
            for (int u = 0; u < numOfRays / 4; u++) {
                dX = r.nextDouble() * Rx / 2d;
                dY = r.nextDouble() * Ry / 2d;
                // find random point on this pixel to create new ray from camera
                randomPoint = Pij;
                if (dY != 0)
                    randomPoint = randomPoint.add(_Vup.scale(-1 * h * dY));
                if (dX != 0)
                    randomPoint = randomPoint.add(_Vright.scale(k * dX));
                // add the ray to the list
                rays.add(new Ray(_p0, randomPoint.subtract(_p0)));
            }
        }
        return rays;
    }
}
