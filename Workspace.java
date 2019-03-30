package ActionScript;

public class Workspace {

    /* параметры формы */
    private static int windowWidth = 750, windowHeight = 500; // ширина, высота формы

    /* построение пола */
    private static int floorProperties[] = {
            0, // X координата
            450, // Y координата
            windowWidth, // ширина пола
            windowHeight - 450 // высота пола
    };

    /* построение стен рабочей области */
    public static int fieldWalls[] = {
            0, // потолок
            0, // левая стена
            windowHeight - floorProperties[3], // пол
            windowWidth - 150 // правая стена
    };

    public static int getInstrumentWindowSize(String value) {
        if (value.equals("width"))
            return 160;
        if (value.equals("height"))
            return 500;
        return 0;
    }

    public static int getGeneratorWindowSize(String value) {
        if (value.equals("width"))
            return 200;
        if (value.equals("height"))
            return 600;
        return 0;
    }

    public static int getWindowSize(String value) {
        if (value.equals("width"))
            return windowWidth;
        if (value.equals("height"))
            return windowHeight;
        return 0;
    }

    public static int getFieldWalls(String value) {
        if (value.equals("top"))
            return fieldWalls[0];
        if (value.equals("left"))
            return fieldWalls[1];
        if (value.equals("bottom"))
            return fieldWalls[2];
        if (value.equals("right"))
            return fieldWalls[3];
        return 0;
    }

    public static int getFloorPosition(String value) {
        if (value.equals("x"))
            return floorProperties[0];
        if (value.equals("y"))
            return floorProperties[1];
        return 0;
    }

    public static int getFloorSize(String value) {
        if (value.equals("width"))
            return floorProperties[2];
        if (value.equals("height"))
            return floorProperties[3];
        return 0;
    }

}
