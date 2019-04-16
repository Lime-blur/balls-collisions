package ActionScript.by.gsu;

public class Physics {

    public static double getBodyKineticEnergy(Bodies body) {
        double mass = body.mass;
        double vx = body.vectorX;
        double vy = body.vectorY;

        return 0.5 * mass * (vx * vx + vy * vy);
    }

    public static double getBodyImpulse(Bodies body) {
        double mass = body.mass;
        double vx = body.vectorX;
        double vy = body.vectorY;

        return mass * (Math.abs(vx) + Math.abs(vy));
    }
}
