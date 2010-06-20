/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoadDictJPanel.java
 *
 * Created on 2010-6-20, 14:42:06
 */
package backwordgui;

import dewafer.backword.core.Paper;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author dewafer
 */
public class LoadDictJPanel extends javax.swing.JPanel implements PropertyChangeListener {

    /** Creates new form LoadDictJPanel */
    public LoadDictJPanel() {
        initComponents();
        BackWordGUIApp.getApplication().addPropertyChangeListener(BackWordGUIApp.PROP_PAPER_PROPERTY, this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loadDictButton = new javax.swing.JButton();
        start = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_dictName = new javax.swing.JLabel();
        jLabel_dictAuthor = new javax.swing.JLabel();
        jLabel_dictDesc = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class).getContext().getActionMap(LoadDictJPanel.class, this);
        loadDictButton.setAction(actionMap.get("loadDict")); // NOI18N
        loadDictButton.setName("loadDictButton"); // NOI18N

        start.setAction(actionMap.get("StartGame")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class).getContext().getResourceMap(LoadDictJPanel.class);
        start.setText(resourceMap.getString("start.text")); // NOI18N
        start.setEnabled(hasPaper());
        start.setName("start"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel_dictName.setText(getDictName());
        jLabel_dictName.setName("jLabel_dictName"); // NOI18N

        jLabel_dictAuthor.setText(getDictAuthor());
        jLabel_dictAuthor.setName("jLabel_dictAuthor"); // NOI18N

        jLabel_dictDesc.setText(getDictDesc());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(start, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadDictButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_dictAuthor)
                            .addComponent(jLabel_dictName)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_dictDesc)))
                .addContainerGap(323, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loadDictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel_dictName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel_dictAuthor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel_dictDesc))
                .addContainerGap(116, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_dictAuthor;
    private javax.swing.JLabel jLabel_dictDesc;
    private javax.swing.JLabel jLabel_dictName;
    private javax.swing.JButton loadDictButton;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables

    private String getDictName() {
        Paper p = BackWordGUIApp.getApplication().getPaper();
        if (p != null) {
            return p.getName();
        }
        return "null";
    }

    private String getDictAuthor() {
        Paper p = BackWordGUIApp.getApplication().getPaper();
        if (p != null) {
            return p.getAuthor();
        }
        return "null";
    }

    private String getDictDesc() {
        Paper p = BackWordGUIApp.getApplication().getPaper();
        if (p != null) {
            return p.getDescription();
        }
        return "null";
    }

    private void appPaperPropertyChangeHandler() {
        jLabel_dictName.setText(getDictName());
        jLabel_dictAuthor.setText(getDictAuthor());
        jLabel_dictDesc.setText(getDictDesc());
        start.setEnabled(hasPaper());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        appPaperPropertyChangeHandler();
    }

    private boolean hasPaper() {
        return BackWordGUIApp.getApplication().getPaper() != null;
    }
}
