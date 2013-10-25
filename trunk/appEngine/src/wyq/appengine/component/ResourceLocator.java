/**
 * 
 */
package wyq.appengine.component;

import java.net.URL;

import wyq.appengine.Component;

/**
 * @author dewafer
 * 
 */
public class ResourceLocator implements Component {

	public URL locate(Class<?> targetClass, String name, String surfix) {
		URL resource = null;

		if (targetClass != null) {
			if (name == null || name.length() == 0) {
				name = targetClass.getSimpleName();
			}

			if (surfix != null && surfix.length() > 0) {
				name += surfix;
			}

			resource = targetClass.getResource(name);

			if (resource == null) {
				resource = targetClass.getResource("/" + name);
			}
		}
		
		// TODO complete
		return resource;

	}
}
