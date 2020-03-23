package primitives;

import java.util.Objects;

public class Ray {
    Point3D _p0;
    Vector _direction;


    // ***************** Constructors ********************** //

    /**
     * public Ray(Point3D poo, Vector direction){
     *         this._POO = new Point3D(poo);
     *         this._direction = new Vector (direction);
     *         this._direction.normalize();
     *     }
     * @param _p0
     * @param _dir
     */
    public Ray(Point3D _p0, Vector _dir) {
        if (Math.sqrt((_dir.get_head().get_x()._coord - _p0.get_x()._coord)*(_dir.get_head().get_x()._coord - _p0.get_x()._coord)
                + (_dir.get_head().get_y()._coord - _p0.get_y()._coord)*(_dir.get_head().get_y()._coord - _p0.get_y()._coord)
                + (_dir.get_head().get_z()._coord - _p0.get_z()._coord)*(_dir.get_head().get_z()._coord - _p0.get_z()._coord)) == 1)
            throw new IllegalArgumentException("Illegal input");
        this._p0 = _p0;
        this._direction = _dir;
    }


    public Ray(Ray ray)throws NullPointerException{
        if (ray == null)
            throw new NullPointerException();
        this._p0 = new Point3D(ray.get_p0());
        this._direction = new Vector(ray.get_direction());
    }

    // ***************** Getters ********************** //

    public Vector get_direction() {
        return new Vector(_direction);
    }

    public Point3D get_p0() {
        return new Point3D(_p0);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return this._p0.equals(ray.get_p0())
                && this._direction.equals(ray.get_direction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_p0(), get_direction());
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _direction=" + _direction +
                '}';
    }

}











