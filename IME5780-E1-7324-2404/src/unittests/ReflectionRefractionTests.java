package unittests;

import geometries.Polygon;
import geometries.Tube;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Material(0.4, 0.3, 100, 0.3, 0), new Color(java.awt.Color.BLUE), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Material(0.5, 0.5, 100), new Color(java.awt.Color.RED), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(10000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Material(0.25, 0.25, 20, 0.5, 0), new Color(0, 0, 100), 400, new Point3D(-950, 900, 1000)),
                new Sphere(new Material(0.25, 0.25, 20), new Color(100, 20, 20), 200, new Point3D(-950, 900, 1000)),
                new Triangle(new Material(0, 0, 0, 0, 1), new Color(20, 20, 20), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Material(0, 0, 0, 0, 0.5), new Color(20, 20, 20), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     * producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(new Material(0.5, 0.5, 60), Color.BLACK, //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(new Material(0.5, 0.5, 60), Color.BLACK, //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Material(0.2, 0.2, 30, 0.6, 0), new Color(java.awt.Color.BLUE), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void pictureE7() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, -220, -800), new Vector(0, 1.7, 10), new Vector(0, -10, 1.7)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        int size = 50;
        for (int z = 0, c = -1; z < 400; z += size, c *= -1) {
            for (int x = -200; x < 200; x += size, c *= -1) {
                if (c < 0)
                    scene.addGeometries(
                            new Polygon(new Material(0.2, 0.2, 30, 0, 0.2), new Color(java.awt.Color.white).scale(0.4),
                                    new Point3D(x, 0, z),
                                    new Point3D(x + size, 0, z),
                                    new Point3D(x + size, 0, z + size),
                                    new Point3D(x, 0, z + size)));
                else
                    scene.addGeometries(
                            new Polygon(new Material(0.2, 0.2, 30, 0, 0.2), new Color(java.awt.Color.BLACK),
                                    new Point3D(x, 0, z),
                                    new Point3D(x + size, 0, z),
                                    new Point3D(x + size, 0, z + size),
                                    new Point3D(x, 0, z + size)));

            }

        }
        Material material = new Material(0.5, 2, 111, 0, 0.8);
        scene.addGeometries(new Polygon(material, new Color(java.awt.Color.RED).scale(0.8),
                        new Point3D(-35 + 40, 0, 270), new Point3D(40, 0, -60.6218 + 270), new Point3D(40, -70, 20.21 + 270)),
                new Polygon(material, new Color(java.awt.Color.RED),
                        new Point3D(40, 0, -60.6218 + 270), new Point3D(35 + 40, 0, 270), new Point3D(40, -70, 20.21 + 270)),
                new Polygon(material, new Color(java.awt.Color.RED),
                        new Point3D(-35 + 40, 0, 270), new Point3D(35 + 40, 0, 270), new Point3D(40, -70, 20.21 + 270)));

        scene.addGeometries(new Tube(new Material(0.2, 0.2, 30, 0, 0.6), new Color(java.awt.Color.BLUE), 50,
                new Ray(new Point3D(-60, -15, 350),new Vector(1,-1,0))));
        scene.addGeometries(
                new Sphere(new Material(0.2, 0.2, 30, 0, 0.6), new Color(java.awt.Color.BLUE), 15, new Point3D(-60, -15, 350)),
                new Sphere(new Material(0.2, 0.4, 30, 0.3, 0.4), new Color(java.awt.Color.BLUE), 15, new Point3D(-10, -15, 20.21 + 250)));


        scene.addLights(new SpotLight(new Color(500,500,500),
                        new Point3D(50, -60, 20.21 + 220), new Polygon(material, new Color(java.awt.Color.RED).scale(0.8),
                        new Point3D(-35 + 40, 0, 270), new Point3D(40, 0, -60.6218 + 270), new Point3D(40, -70, 20.21 + 270)).getNormal(Point3D.ZERO).scale(-1), 1, 4E-5, 2E-7),
                new PointLight(new Color(200,200,200), //
                        new Point3D(30, -200, 200), 1, 4E-5, 2E-7));
        ImageWriter imageWriter = new ImageWriter("pictureE7", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

}