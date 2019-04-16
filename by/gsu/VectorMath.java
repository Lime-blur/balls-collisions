package ActionScript.by.gsu;

public class VectorMath {

    /* Vectors operations */

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double getDistanceSquared(double x1, double y1, double x2, double y2) {
        return Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
    }

    public static double getLength(double x, double y) { return Math.sqrt(x * x + y * y); }

    public static double getLengthSquared(double x, double y) { return x * x + y * y; }

    public static double dotProduct(double x1, double y1, double x2, double y2) { return x1 * x2 + y1 * y2; }

    public static double[] vectorNormalize(double x, double y)
    {
        double locLength = getLength(x, y);
        double inv_length = (1 / locLength);

        x *= inv_length;
        y *= inv_length;

        return new double[]{x, y};
    }

    /* Operations */

    public static double[] getVectorBetweenCenters(double cX1, double cY1, double cX2, double cY2,
                                                   double vX1, double vY1, double vX2, double vY2,
                                                   double dotPr) {
        double pX = cX1 - cX2 + (vX1 - vX2) * dotPr;
        double pY = cY1 - cY2 + (vY1 - vY2) * dotPr;

        return new double[]{pX, pY};
    }

    public static double[] getBodiesSpeed(double vX1, double vY1, double vX2, double vY2,
                                           double mass1, double mass2) {
        double sumMass = mass1 + mass2;

        double rvX1 = ((mass1 - mass2) * vX1 + 2 * mass2 * vX2) / sumMass;
        double rvY1 = ((mass1 - mass2) * vY1 + 2 * mass2 * vY2) / sumMass;

        double rvX2 = ((mass2 - mass1) * vX2 + 2 * mass1 * vX1) / sumMass;
        double rvY2 = ((mass2 - mass1) * vY2 + 2 * mass1 * vY1) / sumMass;

        return new double[]{rvX1, rvY1, rvX2, rvY2};
    }

}
