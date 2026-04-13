public class Player extends Sprite {
    private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed;
    private double speed;
    private double currentSpeed;
    private final double NORMALIZE = 1 / Math.sqrt(2);

    public Player(int x, int y, String img_filepath) {
        super(x, y, img_filepath);
        isUpPressed = false;
        isDownPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        speed = 10;
        currentSpeed = speed;
    }

    @Override
    public void update() {
        currentSpeed = speed;
        int directionX = 0;
        int directionY = 0;

        if (isUpPressed) { directionY = -1; }
        if (isDownPressed) { directionY = 1; }
        if (isLeftPressed) { directionX = -1; }
        if (isRightPressed) { directionX = 1; }

        if (
            (isUpPressed || isDownPressed) &&
            (isLeftPressed || isRightPressed)
        ) {
            currentSpeed *= NORMALIZE;
            currentSpeed *= NORMALIZE;
        }

        setX(getX() + directionX * currentSpeed);
        setY(getY() + directionY * currentSpeed);
    }

    public void setIsUpPressed(boolean pressed) { isUpPressed = pressed; }
    public void setIsDownPressed(boolean pressed) { isDownPressed = pressed; }
    public void setIsLeftPressed(boolean pressed) { isLeftPressed = pressed; }
    public void setIsRightPressed(boolean pressed) { isRightPressed = pressed; }
}
