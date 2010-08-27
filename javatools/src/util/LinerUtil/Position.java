package util.LinerUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Position annotation for fields sorting.
 * @author wangyinqiu
 * @version 2010/08/09 v0.1
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Position {
    /** position of field */
    int value();

}
