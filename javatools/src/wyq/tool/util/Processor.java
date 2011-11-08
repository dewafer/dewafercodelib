/**
 * 
 */
package wyq.tool.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wyq
 * 
 */
public interface Processor {

    /**
     * @author wyq
     * 
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface InjectProperty {
	String value() default "";
    }

    public abstract void process(String[] args) throws Exception;

}
