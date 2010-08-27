package wyq.mousePositionWatcherCore;

public class MouseMoveEvent {

    private int x;

    private int y;

    private int pre_x;

    private int pre_y;

    /**
     * #x#を取得します。
     * @return #x#
     */
    public int getX() {
        return this.x;
    }

    /**
     * #x#を設定します。
     * @param pX 設定する#x#
     */
    public void setX(int pX) {
        this.x = pX;
    }

    /**
     * #y#を取得します。
     * @return #y#
     */
    public int getY() {
        return this.y;
    }

    /**
     * #y#を設定します。
     * @param pY 設定する#y#
     */
    public void setY(int pY) {
        this.y = pY;
    }

    /**
     * #pre_x#を取得します。
     * @return #pre_x#
     */
    public int getPre_x() {
        return this.pre_x;
    }

    /**
     * #pre_x#を設定します。
     * @param pPre_x 設定する#pre_x#
     */
    public void setPre_x(int pPre_x) {
        this.pre_x = pPre_x;
    }

    /**
     * #pre_y#を取得します。
     * @return #pre_y#
     */
    public int getPre_y() {
        return this.pre_y;
    }

    /**
     * #pre_y#を設定します。
     * @param pPre_y 設定する#pre_y#
     */
    public void setPre_y(int pPre_y) {
        this.pre_y = pPre_y;
    }

}
