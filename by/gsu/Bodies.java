package ActionScript.by.gsu;

public class Bodies {

    public int id;
    public String type;
    public double i_x,  i_y, i_width, i_height;
    public double x, y, width, height;
    public double vectorX, vectorY;
    public double angle;
    public double bodySpeed;

    public double mass;
    public boolean isCollide = false;

    public Bodies(int id, String type, double width, double height, double angle, double mass, double speed) {
        this.id = id;
        this.type = type;
        this.i_x = 0;
        this.i_y = 0;
        this.i_width = width;
        this.i_height = height;
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.vectorX = 0;
        this.vectorY = 0;
        this.angle = angle;
        this.mass = mass;
        this.bodySpeed = speed;
    }

    public Bodies(int id, String type, double x, double y, double width, double height, double angle, double mass, double speed) {
        this.id = id;
        this.type = type;
        this.i_x = x;
        this.i_y = y;
        this.i_width = width;
        this.i_height = height;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vectorX = 0;
        this.vectorY = 0;
        this.angle = angle;
        this.mass = mass;
        this.bodySpeed = speed;
    }

    public Bodies(int id,
                  String type,
                  double i_x, double i_y, double i_width, double i_height,
                  double x, double y, double width, double height,
                  double vectorX, double vectorY,
                  double angle,
                  double mass,
                  double speed
    ) {
        this.id = id;
        this.type = type;
        this.i_x = i_x;
        this.i_y = i_y;
        this.i_width = i_width;
        this.i_height = i_height;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
        this.angle = angle;
        this.mass = mass;
        this.bodySpeed = speed;
    }

    /* Current properties */

    public void setBodyAngle(double value) {
        angle = value;
        double i_angle = Math.toRadians(angle);
        vectorX = Math.cos(i_angle) * bodySpeed;
        vectorY = -Math.sin(i_angle) * bodySpeed;
    }

    public void setBodyAngle(double value, double speed) {
        angle = value;
        bodySpeed = speed;
        double i_angle = Math.toRadians(angle);
        vectorX = Math.cos(i_angle) * bodySpeed;
        vectorY = -Math.sin(i_angle) * bodySpeed;
    }
}