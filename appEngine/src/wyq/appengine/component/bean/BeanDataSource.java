package wyq.appengine.component.bean;

import wyq.appengine.Component;

public interface BeanDataSource extends Component {

	Object getValue(String fieldName);

}
