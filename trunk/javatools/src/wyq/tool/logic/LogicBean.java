package wyq.tool.logic;

import wyq.infrastructure.PropertySupporter;

public class LogicBean extends PropertySupporter {

	private String labelText;

	public String getLabelText() {
		return labelText;
	}

	public LogicBean() {
		super("/test.properties");
	}

}
