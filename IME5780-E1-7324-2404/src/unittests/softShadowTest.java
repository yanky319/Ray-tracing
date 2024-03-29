package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

public class softShadowTest {
    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing Soft shading
     */
    @Test
    public void trianglesSphereWithSoftShadow() {
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
                new Point3D(100, -40, -115), new Vector(-1, 1, 4), 1, 4E-4, 2E-5,10));

        ImageWriter imageWriter = new ImageWriter("trianglesSphereWithSoftShadowing", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.setSoftShadowing(true);
        render.setSuperSampling(true);
        render.setNumOfRays(50);
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
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7,5));

        ImageWriter imageWriter = new ImageWriter("trianglesTransparentSphereWithSoftShadowing", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.setSoftShadowing(true);
        render.setSuperSampling(true);
        render.setNumOfRays(50);
        render.renderImage();
        render.writeToImage();
    }
}