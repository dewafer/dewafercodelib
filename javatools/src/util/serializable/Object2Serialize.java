package util.serializable;

import java.io.Serializable;
import java.util.Date;

public class Object2Serialize implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5476876548015023984L;

    private int    count;

    private String name;

    private Date   now = new Date();

    public int getCount() {
        return this.count;
    }

    public void setCount(int pCount) {
        this.count = pCount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public Date getNow() {
        return this.now;
    }

    public void setNow(Date pNow) {
        this.now = pNow;
    }

    @Override
    public String toString() {
        return "Object2Serialize[count:" + count + ", name:" + name + ", date:" + now + "]";
    }

}
