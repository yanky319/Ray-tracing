package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * class Geometries for groups of geometries implementing Composite pattern.
 */
public class Geometries extends Intersectable {

    /**
     * a list of intersectable geometries
     */
    private List<Intersectable> _intersectables;

    //*********************************** constructor ***************

    /**
     * empty constructor for class Geometries.
     */
    public Geometries() {
        this._intersectables = new LinkedList<>();
    }

    /**
     * constructor for class Geometries.
     *
     * @param intersectables one or more intersectable geometries
     */
    public Geometries(Intersectable... intersectables) {
        this._intersectables = List.of(intersectables);
        setBox();
    }

    //*********************************** functions ***************

    /**
     * function for adding geometries to the list.
     *
     * @param intersectables one or more intersectable geometries to be added
     */
    public void add(Intersectable... intersectables) {
        this._intersectables.addAll(List.of(intersectables));
        setBox(List.of(intersectables));
    }

    /**
     * function for getting the size of the list for tests.
     *
     * @return the size of the intersectable geometries list
     */
    public int getListSize() {
        return _intersectables.size();
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if (!boundaryBox.intersectBox(ray)) // if no intersection with the box return null
            return null;

        List<GeoPoint> intersections = null;
        // go over all intersectables and look for intersections
        for (Intersectable i : this._intersectables) {
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

    @Override
    public void setBox() {

        if (_intersectables.size() > 0) {
            // set min and max values to be min and max values of the first intersectables
            double min_x = _intersectables.get(0).boundaryBox._min.get_x().get();
            double max_x = _intersectables.get(0).boundaryBox._max.get_x().get();
            double min_y = _intersectables.get(0).boundaryBox._min.get_y().get();
            double max_y = _intersectables.get(0).boundaryBox._max.get_y().get();
            double min_z = _intersectables.get(0).boundaryBox._min.get_z().get();
            double max_z = _intersectables.get(0).boundaryBox._max.get_z().get();

            // go over all intersectables and check for grater or smaller values
            for (Intersectable intersectable : _intersectables) {
                if (intersectable.boundaryBox._min.get_x().get() < min_x)
                    min_x = intersectable.boundaryBox._min.get_x().get();
                if (intersectable.boundaryBox._max.get_x().get() > max_x)
                    max_x = intersectable.boundaryBox._max.get_x().get();
                if (intersectable.boundaryBox._min.get_y().get() < min_y)
                    min_y = intersectable.boundaryBox._min.get_y().get();
                if (intersectable.boundaryBox._max.get_y().get() > max_y)
                    max_y = intersectable.boundaryBox._max.get_y().get();
                if (intersectable.boundaryBox._min.get_z().get() < min_z)
                    min_z = intersectable.boundaryBox._min.get_z().get();
                if (intersectable.boundaryBox._max.get_z().get() > max_z)
                    max_z = intersectable.boundaryBox._max.get_z().get();
            }
            // create box with min and max values found
            boundaryBox = new Box(
                    new Point3D(min_x, min_y, min_z),
                    new Point3D(max_x, max_y, max_z));
        }

    }

    /**
     * fixes the box when intersectables are added.
     *
     * @param newIntersectables the intersectables added to the list
     */
    public void setBox(List<Intersectable> newIntersectables) {
        double min_x;
        double max_x;
        double min_y;
        double max_y;
        double min_z;
        double max_z;

        if(this.boundaryBox != null)
        { // if there is a box set initial values to be of the box
            min_x = this.boundaryBox._min.get_x().get();
            max_x = this.boundaryBox._max.get_x().get();
            min_y = this.boundaryBox._min.get_y().get();
            max_y = this.boundaryBox._max.get_y().get();
            min_z = this.boundaryBox._min.get_z().get();
            max_z = this.boundaryBox._max.get_z().get();
        }
        else
        {   // other ways set them to plus/minus max value
            min_x = -Double.MAX_VALUE;
            max_x = Double.MAX_VALUE;
            min_y = -Double.MAX_VALUE;
            max_y = Double.MAX_VALUE;
            min_z = -Double.MAX_VALUE;
            max_z = Double.MAX_VALUE;
        }

        // go over all new intersectables and check for grater or smaller values
        for (Intersectable intersectable : newIntersectables) {
            if (intersectable.boundaryBox._min.get_x().get() < min_x)
                min_x = intersectable.boundaryBox._min.get_x().get();
            if (intersectable.boundaryBox._max.get_x().get() > max_x)
                max_x = intersectable.boundaryBox._max.get_x().get();
            if (intersectable.boundaryBox._min.get_y().get() < min_y)
                min_y = intersectable.boundaryBox._min.get_y().get();
            if (intersectable.boundaryBox._max.get_y().get() > max_y)
                max_y = intersectable.boundaryBox._max.get_y().get();
            if (intersectable.boundaryBox._min.get_z().get() < min_z)
                min_z = intersectable.boundaryBox._min.get_z().get();
            if (intersectable.boundaryBox._max.get_z().get() > max_z)
                max_z = intersectable.boundaryBox._max.get_z().get();
        }
        // create box with min and max values found
        boundaryBox = new Box(
                new Point3D(min_x, min_y, min_z),
                new Point3D(max_x, max_y, max_z));
    }

    /**
     * creates hierarchical tree of intersectables using the composite principle.
     */
    public void bvhTree() {

        // remove all infinite geometries from the list into a new object of Geometries
        Geometries infiniteGeometries = new Geometries();
        for (int k = 0; k < _intersectables.size(); k++) {
            if (_intersectables.get(k).boundaryBox.isInfinite) {
                infiniteGeometries.add(_intersectables.get(k));
                _intersectables.remove(k);
            }
        }

        // as long as there is more then 1 intersectable in the list
        while (_intersectables.size() > 1) {
            // initial a and b to be tue first 2 intersectables in the list
            Intersectable a = _intersectables.get(0);
            Intersectable b = _intersectables.get(1);
            // initial distance to be the distance between a and b
            double bestDistance = a.boundaryBox._mid.distance(b.boundaryBox._mid);
            // go over all intersectables
            for (Intersectable j : _intersectables) {
                // look for intersectable closer to a then b
                if (a != j && a.boundaryBox._mid.distance(j.boundaryBox._mid) < bestDistance) {
                    bestDistance = a.boundaryBox._mid.distance(j.boundaryBox._mid);
                    b = j;
                }
            }
            // remove a and b from list of intersectables
            _intersectables.remove(a);
            _intersectables.remove(b);
            // add them in new Geometries
            _intersectables.add(new Geometries(a, b));
        }
        // take out object of all finite intersectables
        Intersectable temp = _intersectables.get(0);
        _intersectables.clear();
        // add new Geometries with finite and infinite intersectables
        _intersectables.add(new Geometries(temp, infiniteGeometries));
        setBox();
    }
}
