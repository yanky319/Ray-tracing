package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

public class SuperSamplingTest {
    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
     */
    @Test
    public void trianglesSphereWithSuperSampling() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(new Material(0, 0.8, 60), Color.BLACK, //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(new Material(0, 0.8, 60), Color.BLACK, //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Material(0.5, 0.5, 30), new Color(java.awt.Color.BLUE),// )
                        30, new Point3D(0, 0, 115)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(40, -40, -115), new Vector(-1, 1, 4), 1, 4E-4, 2E-5));

        ImageWriter imageWriter = new ImageWriter("trianglesSphereWithSuperSampling", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.setSuperSampling(true);
        render.setNumOfRays(150);
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture
     */
    @Test
    public void pictureE7WithSuperSampling() {
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
        ImageWriter imageWriter = new ImageWriter("pictureE7WithSuperSampling", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.setSuperSampling(true);
        render.setNumOfRays(35);
        render.renderImage();
        render.writeToImage();
    }
}