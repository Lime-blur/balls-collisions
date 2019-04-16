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

    public static double[] getNewBodiesSpeed(double r1, double r2,
                                             double cX1, double cY1, double cX2, double cY2,
                                             double vX1, double vY1, double vX2, double vY2,
                                           double mass1, double mass2, double recoveryFactor) {
        double sumMass = mass1 + mass2;
        double fi = Math.asin(Math.abs(cY2 - cY1) / (r1 + r2));
        double beta = Math.acos(dotProduct(cX1, cY1, cX2, cY2) / (getLength(cX1, cY1) * getLength(cX2, cY2)));

        double rvX1 = ((mass1 - recoveryFactor * mass2) * vX1 + (1 + recoveryFactor) * mass2 * vX2) / sumMass;
        double rvY1 = ((mass1 - recoveryFactor * mass2) * vY1 + (1 + recoveryFactor) * mass2 * vY2) / sumMass;

        double rvX2 = ((mass2 - recoveryFactor * mass1) * vX2 + (1 + recoveryFactor) * mass1 * vX1) / sumMass;
        double rvY2 = ((mass2 - recoveryFactor * mass1) * vY2 + (1 + recoveryFactor) * mass1 * vY1) / sumMass;

        return new double[]{rvX1, rvY1, rvX2, rvY2};
    }

}
