/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backwordgui;

import dewafer.backword.core.Paper;
import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author dewafer
 */
public class PaperBean implements Serializable {

    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    public static final String PROP_PAPER_PROPERTY = "paperProperty";
    private String sampleProperty;
    private Paper paper;
    private PropertyChangeSupport propertySupport;

    public PaperBean() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public String getSampleProperty() {
        return sampleProperty;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper p) {
        Paper oldPaper = paper;
        this.paper = p;
        propertySupport.firePropertyChange(PROP_PAPER_PROPERTY, oldPaper, paper);
    }

    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
