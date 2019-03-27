package ActionScript;

import java.util.ArrayList;

public class BodiesGenerator {

    public static ArrayList<Bodies> body = new ArrayList<>();

    public static int getBodiesArraySize() {
        if (body.isEmpty()) {
            return -1;
        } else {
            return body.size();
        }
    }

    public static void deleteBody(int indexToDelete) { body.remove(indexToDelete); }
    public static void clearArray() { body.clear(); }

    public static void updateBodiesID() {
        for (int i = 0; i < body.size(); i++) {
            Bodies currentBody = body.get(i);
            currentBody.id = i;
        }
    }

    public static Bodies getBodyByID(int id) {
        if (body.isEmpty()) {
            return null;
        } else {
            if (id > -1 && id < body.size()) {
                return body.get(id);
            }
            return null;
        }
    }

    public static void fillArrayManually(String type, double x, double y, double width, double height, double angle) {
        int arraySize = getBodiesArraySize();

        if (arraySize == -1) {
            body.add(new Bodies(0, type, x, y, width, height, angle));
        } else {
            body.add(new Bodies(getBodiesArraySize(), type, x, y, width, height, angle));
        }
    }

    public static void fillArrayAutomatically(int amount, String type, double width, double height, double angle) {
        for (int i = 0; i < amount; i++) {
            body.add(new Bodies(i, type, width, height, angle));
        }
    }

    public static double findMaxBodySize(String value) {
        double maxSize = 0;
        int arraySize = getBodiesArraySize();

        if (arraySize != -1) {
            for (int i = 0; i < arraySize; i++) {
                Bodies body1 = getBodyByID(i);
                if (value.equals("width")) {
                    if (body1.width > maxSize) {
                        maxSize = body1.width;
                    }
                }
                if (value.equals("height")) {
                    if (body1.height > maxSize) {
                        maxSize = body1.height;
                    }
                }
            }
        } else {
            System.out.println("ERROR: BodiesGenerator.java: Return @ 'findMaxBodySize' [Array elements amount is less than zero, got -1 value]");
            return -1;
        }
        return maxSize;
    }

    public static int[] calculateSpaceSize(boolean isCustom, int sX, int sY) {
        int spaceWidth = Workspace.getFieldWalls("right") - Workspace.getFieldWalls("left");
        int spaceHeight = Workspace.getFieldWalls("bottom") - Workspace.getFieldWalls("top");
        double maxWidth;
        double maxHeight;

        if (isCustom) {
            maxWidth = sX;
            maxHeight = sY;
        } else {
            maxWidth = findMaxBodySize("width");
            maxHeight = findMaxBodySize("height");
        }

        if (maxHeight != -1 && maxWidth != -1) {
            if (maxHeight > maxWidth || maxWidth > maxHeight) {
                System.out.print("Тела имеют разные стороны, невозможно сгенерировать сетку!");
            } else {
                int amountX = (int) (spaceWidth / maxWidth);
                int amountY = (int) (spaceHeight / maxHeight);
                return new int[]{amountX, amountY};
            }
        }
        return new int[]{-1, -1};
    }

    public static void fillSpace(boolean isRandom) {
        int spaceSizeX = calculateSpaceSize(false, 0, 0)[0];
        int spaceSizeY = calculateSpaceSize(false, 0, 0)[1];
        int factorX = 0, factorY = 1;

        if (spaceSizeX > 0 && spaceSizeY > 0) {
            if (isRandom) {
                int i = 0;
                boolean alone;
                do {
                    Bodies body1 = getBodyByID(i);
                    alone = true;
                    double maxWidth = findMaxBodySize("width");
                    double maxHeight = findMaxBodySize("height");
                    int x = (int) (maxWidth * (int) (Math.random() * spaceSizeX));
                    int y = (int) (maxHeight * (int) (Math.random() * spaceSizeY));
                    if (i != 0) {
                        for (int j = i - 1; j > -1; j--) {
                            Bodies body2 = getBodyByID(j);
                            if (x == body2.x && y == body2.y) {
                                alone = false;
                                break;
                            }
                        }
                    }
                    if (alone) {
                        body1.x = x;
                        body1.i_x = x;
                        body1.y = y;
                        body1.i_y = y;
                        i++;
                    }
                } while (i < getBodiesArraySize());
            } else {
                for (int i = 0; i < getBodiesArraySize(); i++) {
                    Bodies body1 = getBodyByID(i);
                    if (i / factorY == spaceSizeX) {
                        factorX = 0;
                        factorY = factorY + 1;
                    }
                    int x = (int) (body1.width * factorX);
                    int y = (int) (body1.height * (factorY - 1));
                    body1.x = x;
                    body1.i_x = x;
                    body1.y = y;
                    body1.i_y = y;
                    factorX++;
                }
            }
        }
    }

}
