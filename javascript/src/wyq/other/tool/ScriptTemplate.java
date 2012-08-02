package wyq.other.tool;

import wyq.appengine.component.template.TemplateKeyWord;

public interface ScriptTemplate {

	String defaultTemplate(@TemplateKeyWord("MainScript") String script);
}
