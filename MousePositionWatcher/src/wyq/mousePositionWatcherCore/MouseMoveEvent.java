package wyq.mousePositionWatcherCore;

public class MouseMoveEvent {

    private int x;

    private int y;

    private int pre_x;

    private int pre_y;

    /**
     * #x#���擾���܂��B
     * @return #x#
     */
    public int getX() {
        return this.x;
    }

    /**
     * #x#��ݒ肵�܂��B
     * @param pX �ݒ肷��#x#
     */
    public void setX(int pX) {
        this.x = pX;
    }

    /**
     * #y#���擾���܂��B
     * @return #y#
     */
    public int getY() {
        return this.y;
    }

    /**
     * #y#��ݒ肵�܂��B
     * @param pY �ݒ肷��#y#
     */
    public void setY(int pY) {
        this.y = pY;
    }

    /**
     * #pre_x#���擾���܂��B
     * @return #pre_x#
     */
    public int getPre_x() {
        return this.pre_x;
    }

    /**
     * #pre_x#��ݒ肵�܂��B
     * @param pPre_x �ݒ肷��#pre_x#
     */
    public void setPre_x(int pPre_x) {
        this.pre_x = pPre_x;
    }

    /**
     * #pre_y#���擾���܂��B
     * @return #pre_y#
     */
    public int getPre_y() {
        return this.pre_y;
    }

    /**
     * #pre_y#��ݒ肵�܂��B
     * @param pPre_y �ݒ肷��#pre_y#
     */
    public void setPre_y(int pPre_y) {
        this.pre_y = pPre_y;
    }

}
