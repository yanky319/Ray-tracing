package unittests;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * system test testing all functionality of the system
 */
public class SystemTest {

    @Test
    public void finalTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(150, -400, -1100), new Vector(-0.7, 1.4, 4), new Vector(0, -1, 0.35d)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));


        //The plane
        scene.addGeometries(
                new Plane(new Material(0.2, 0.2, 30, 0, 0.2), new Color(10, 10, 10),
                        new Point3D(1, 0, 1), new Vector(0, -1, 0)));

        Point3D p1 = new Point3D(100, 8, -100);
        Point3D p2 = new Point3D(-100, 8, -100);
        Point3D p3 = new Point3D(0, 8, 200);
        Point3D ph = new Point3D(0, -200, 100);
        Point3D pc = new Point3D(0, -73, 100);
        Material material = new Material(0.3, 0.3, 30, 0, 0);
        scene.addGeometries(
                new Cylinder(material, new Color(210, 81, 242).reduce(3), 10, new Ray(p1, ph.subtract(p1)), ph.distance(p1)),
                new Cylinder(material, new Color(210, 81, 242).reduce(3), 10, new Ray(p2, ph.subtract(p2)), ph.distance(p2)),
                new Cylinder(material, new Color(210, 81, 242).reduce(3), 10, new Ray(p3, ph.subtract(p3)), ph.distance(p3)),
                new Cylinder(material, new Color(117, 78, 111).reduce(5), 1, new Ray(ph, new Vector(0, 1, 0)), ph.distance(pc)),
                new Sphere(new Material(0.5, 0.5, 100), new Color(0, 0, 255), 15, pc));

        Color color = new Color(0, 0, 102).reduce(5);
        Material material1 = new Material(0.5, 0.5, 100, 0.5, 0);
        Point3D p4 = new Point3D(150, 0, -250);
        Point3D p5 = new Point3D(250, 0, -250);
        Point3D p6 = new Point3D(200, 0, -400);
        double len = p4.distance(p5);
        Vector vector = new Vector(0, -1, 0).scale(len);
        scene.addGeometries(
                new Triangle(material1, color, p4, p5, p6),
                new Polygon(material1, color, p4, p5, p5.add(vector), p4.add(vector)),
                new Polygon(material1, color, p5, p6, p6.add(vector), p5.add(vector)),
                new Polygon(material1, color, p6, p4, p4.add(vector), p6.add(vector)),
                new Triangle(material1, color, p4.add(vector), p5.add(vector), p6.add(vector)));

        scene.addLights(new PointLight(new Color(255, 255, 255), new Point3D(350, -450, 350), 1, 0.00001, 0.000001, 5),
                new SpotLight(new Color(255, 255, 255), new Point3D(-400, -400, 400), Point3D.ZERO.subtract(new Point3D(-350, -400, 350)), 1, 0.00001, 0.000001, 5),
                new DirectionalLight(new Color(255, 255, 255).reduce(6), new Vector(0.7, -1.4, -4)));

        List<Sphere> spheres = new LinkedList() {{
            add(new Sphere(250, new Point3D(0, 0, 100)));
            add(new Sphere(len * 0.8, new Point3D(200, -len / 2d, -325)));
        }};
        Random r = new Random();
        double x, y, z;
        List<Color> colors = new ArrayList<>() {{
            add(new Color(0, 255, 255));
            add(new Color(255, 0, 255));
            add(new Color(255, 255, 0));
            add(new Color(0, 0, 255));
        }};
        int i = 0;
        while (i < 50) {
            boolean flag = true;
            x = r.nextDouble() * 1100 - 550;
            y = r.nextDouble() * 15 + 10;
            z = r.nextDouble() * 1300 - 500;
            for (Sphere sphere : spheres) {
                double dist = Math.sqrt((x - sphere.get_center().get_x().get()) * (x - sphere.get_center().get_x().get())
                        + (z - sphere.get_center().get_z().get()) * (z - sphere.get_center().get_z().get()));
                if (dist < y + sphere.get_radius()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Sphere sphere2 = new Sphere(new Material(0.5, 0.5, 100), colors.get(i % 4).reduce(4)
                        , y, new Point3D(x, -y, z));
                spheres.add(sphere2);
                scene.addGeometries(sphere2);
                i++;
            }
        }
        scene.build_bvhTree();
        ImageWriter imageWriter = new ImageWriter("E1_off", 648, 432, 1080, 720);
        Render render = new Render(imageWriter, scene);
        render.setMultithreading(1)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();


        ImageWriter imageWriter2 = new ImageWriter("E1_on", 648, 432, 1080, 720);
        render = new Render(imageWriter2, scene);
        render.setSoftShadowing(true);
        render.setSuperSampling(true);
        render.setNumOfRays(89);
        render.setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();

    }


}