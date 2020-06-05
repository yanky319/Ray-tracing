package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * class Geometries for groups of geometries implementing Composite pattern.
 */
public class Geometries implements Intersectable {

    /**a list of intersectable geometries*/
    private List<Intersectable> intersectables;

    //*********************************** constructor ***************

    /**
     * empty constructor for class Geometries.
     */
    public Geometries() {
        this.intersectables = new LinkedList<>();
    }
    /**
     * constructor for class Geometries.
     *
     * @param intersectables one or more intersectable geometries
     */
    public Geometries(Intersectable... intersectables) {
        this.intersectables = List.of(intersectables);
    }

    //*********************************** functions ***************
    /**
     * function for adding geometries to the list.
     *
     * @param intersectables one or more intersectable geometries to be added
     */
    public void add(Intersectable... intersectables) {
        this.intersectables.addAll(List.of(intersectables));
    }

    /**
     * function for getting the size of the list for tests.
     *
     * @return the size of the intersectable geometries list
     */
    public int getListSize() { return intersectables.size();}


    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
            List<GeoPoint> intersections = null;
        for (Intersectable i : this.intersectables) {
            List<GeoPoint> temp = i.findIntersections(ray, maxDistance);
            if (temp != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(temp);
                else
                    intersections.addAll(temp);
            }
        }
        return intersections;
    }
}
