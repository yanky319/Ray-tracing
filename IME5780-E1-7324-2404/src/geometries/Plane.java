package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    Point3D _po;
    Vector _normal;

    public Plane(Point3D _po, Vector _normal)throws NullPointerException {
        if (_po == null  || _normal == null)
            throw new NullPointerException();
        this._po = new Point3D(_po);
        this._normal = new Vector(_normal);
    }

    public Plane(Point3D _po1,Point3D _po2, Point3D _po3)throws NullPointerException {
        if (_po1 == null  || _po2 == null || _po3 == null)
            throw new NullPointerException();
        this._po = new Point3D(_po1);
        Vector U = new Vector (_po1.subtract(_po2));
        Vector V = new Vector (_po1.subtract(_po3));
        Vector N = U.crossProduct(V);
        N.normalize();
        this._normal = N.scale(-1);
        this._normal = new Vector(_normal);
    }

    public Point3D get_po() {
        return new Point3D(_po);
    }

    public Vector get_normal() {
        return new Vector(_normal);
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_po=" + _po +
                ", _normal=" + _normal +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return new Vector(_normal);
    }

    public Vector getNormal() {
        return this.getNormal(_po);
    }
}
