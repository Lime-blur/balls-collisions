package ActionScript.by.gsu;

public class Animations {

    private static boolean isRunning = false;
    private static boolean isReloading = false;
    private static int delayValue = 5;

    public static boolean isBodyRunning() { return isRunning; }
    public static void setBodyRunning(boolean value) { isRunning = value; }
    public static boolean isBodyReloading() { return isReloading; }
    public static void setBodyReloading(boolean value) { isReloading = value; }
    public static int getBodyDelayValue() { return delayValue; }
    public static void setBodyDelayValue(int value) { delayValue = value; }

    public static double getBodyCenter(double a, double width) { return a + (width / 2); }

    private static void chengeVectorOnHit(Bodies body, double left, double right, double top, double bottom) {
        if (body.x + body.vectorX < left) {
            body.x = left;
            body.vectorX = -body.vectorX;
        }
        if (body.x + body.vectorX > right - body.width) {
            body.x = right - body.width;
            body.vectorX = -body.vectorX;
        }
        if (body.y + body.vectorY < top) {
            body.y = top;
            body.vectorY = -body.vectorY;
        }
        if (body.y + body.vectorY > bottom - body.height) {
            body.y = bottom - body.height;
            body.vectorY = -body.vectorY;
        }
    }

    private static void checkBodyRebound(Bodies body, double left, double right, double top, double bottom) {
        if (body.vectorX * -1 == body.vectorX && body.vectorY * -1 == -body.vectorY) {
            chengeVectorOnHit(body, left, right, top, bottom);
        }
        if (body.vectorX * -1 == -body.vectorX && body.vectorY * -1 == -body.vectorY) {
            chengeVectorOnHit(body, left, right, top, bottom);
        }
        if (body.vectorX * -1 == -body.vectorX && body.vectorY * -1 == body.vectorY) {
            chengeVectorOnHit(body, left, right, top, bottom);
        }
        if (body.vectorX * -1 == body.vectorX && body.vectorY * -1 == body.vectorY) {
            chengeVectorOnHit(body, left, right, top, bottom);
        }
    }

    public static void checkBodyPosition(Bodies body) {
        checkBodyRebound(
                body,
                Workspace.getFieldWalls("left"),
                Workspace.getFieldWalls("right"),
                Workspace.getFieldWalls("top"),
                Workspace.getFieldWalls("bottom")
        );
    }

    /* CORE */

    public static void checkBodiesPositions(Bodies body1, Bodies body2) {
        double centerX1 = getBodyCenter(body1.x, body1.width);
        double centerY1 = getBodyCenter(body1.y, body1.height);
        double centerX2 = getBodyCenter(body2.x, body2.width);
        double centerY2 = getBodyCenter(body2.y, body2.height);

        if (body1.type.equals("circle") && body2.type.equals("circle")) {
            double radius1 = body1.width / 2;
            double radius2 = body2.width / 2;
            double distSquared = VectorMath.getDistanceSquared(centerX1, centerY1, centerX2, centerY2);

            if (distSquared <= Math.pow(radius1 + radius2, 2)) {
                double dotProduct = VectorMath.dotProduct(
                        centerX1 - centerX2, centerY1 - centerY2,
                        body2.vectorX - body1.vectorX, body2.vectorY - body1.vectorY
                );

                if (dotProduct > 0) {

                    /*
                    double collisionScale = dotProduct / distSquared;
                    double xCollision = (centerX1 - centerX2) * collisionScale;
                    double yCollision = (centerY1 - centerY2) * collisionScale;

                    double combinedMass = body1.mass + body2.mass;
                    double collisionWeightA = 2 * body2.mass / combinedMass;
                    double collisionWeightB = 2 * body1.mass / combinedMass;

                    body1.vectorX += collisionWeightA * xCollision;
                    body1.vectorY += collisionWeightA * yCollision;
                    body2.vectorX -= collisionWeightB * xCollision;
                    body2.vectorY -= collisionWeightB * yCollision;
                    */

                    double speed[] = VectorMath.getNewBodiesSpeed(
                            radius1, radius2,
                            centerX1, centerY1, centerX2, centerY2,
                            body1.vectorX, body1.vectorY, body2.vectorX, body2.vectorY,
                            body1.mass, body2.mass, 1);

                    body1.vectorX = speed[0];
                    body1.vectorY = speed[1];
                    body2.vectorX = speed[2];
                    body2.vectorY = speed[3];

                    if (body1.isCollide) {
                        body1.isCollide = false;
                    } else {
                        body1.isCollide = true;
                    }
                    if (body2.isCollide) {
                        body2.isCollide = false;
                    } else {
                        body2.isCollide = true;
                    }
                }
            }
        }
    }

}
