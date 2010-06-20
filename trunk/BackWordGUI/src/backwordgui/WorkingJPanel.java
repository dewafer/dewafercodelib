/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WorkingJPanel.java
 *
 * Created on 2010-6-20, 14:42:32
 */
package backwordgui;

import dewafer.backword.core.Paper;
import dewafer.backword.core.Quiz;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;

/**
 *
 * @author dewafer
 */
public class WorkingJPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private static final String QUIZ_PROPERTY = "QUIZ";
    private Paper paper = BackWordGUIApp.getApplication().getPaper();
    private Quiz current;

    private void setCurrent(Quiz c) {
        Quiz oldValue = current;
        current = c;
        this.firePropertyChange(QUIZ_PROPERTY, oldValue, c);
    }

    /** Creates new form WorkingJPanel */
    public WorkingJPanel() {
        initComponents();
        reload();
    }

    public void reload() {
        paper = BackWordGUIApp.getApplication().getPaper();
        if (paper != null) {
            this.addPropertyChangeListener(QUIZ_PROPERTY, this);
            setCurrent(paper.getCurrentQuiz());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_answers = new javax.swing.ButtonGroup();
        jLabel_question = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton_answer = new javax.swing.JButton();
        jButton_abandon = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jLabel_question.setText(getQuestion());
        jLabel_question.setName("jLabel_question"); // NOI18N

        buttonGroup_answers.add(jRadioButton1);
        jRadioButton1.setText(getAnswer(0));
        jRadioButton1.setActionCommand("0");
        jRadioButton1.setName("jRadioButton1"); // NOI18N
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup_answers.add(jRadioButton2);
        jRadioButton2.setText(getAnswer(1));
        jRadioButton2.setActionCommand("1");
        jRadioButton2.setName("jRadioButton2"); // NOI18N

        buttonGroup_answers.add(jRadioButton3);
        jRadioButton3.setText(getAnswer(2));
        jRadioButton3.setActionCommand("2");
        jRadioButton3.setName("jRadioButton3"); // NOI18N

        buttonGroup_answers.add(jRadioButton4);
        jRadioButton4.setText(getAnswer(3));
        jRadioButton4.setActionCommand("3");
        jRadioButton4.setName("jRadioButton4"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class).getContext().getResourceMap(WorkingJPanel.class);
        jButton_answer.setText(resourceMap.getString("jButton_answer.text")); // NOI18N
        jButton_answer.setName("jButton_answer"); // NOI18N
        jButton_answer.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton_answer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_answerActionPerformed(evt);
            }
        });

        jButton_abandon.setText(resourceMap.getString("jButton_abandon.text")); // NOI18N
        jButton_abandon.setName("jButton_abandon"); // NOI18N
        jButton_abandon.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton_abandon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_abandonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_question))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton_answer)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_abandon)))
                .addContainerGap(224, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_question)
                .addGap(38, 38, 38)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_answer)
                    .addComponent(jButton_abandon))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_answerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_answerActionPerformed
        // TODO add your handling code here:
        answer(getSelectIndex());
    }//GEN-LAST:event_jButton_answerActionPerformed

    private void jButton_abandonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_abandonActionPerformed
        // TODO add your handling code here:
        abandon();
    }//GEN-LAST:event_jButton_abandonActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void selectAction() {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_answers;
    private javax.swing.JButton jButton_abandon;
    private javax.swing.JButton jButton_answer;
    private javax.swing.JLabel jLabel_question;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    // End of variables declaration//GEN-END:variables

    private String getQuestion() {
        if (current != null) {
            return current.getQuestion();
        }
        return "null";
    }

    private String getAnswer(int i) {
        if (current != null) {
            return current.getAnswersList()[i];
        }
        return "null";
    }

    private void answer(int i) {
        if (current != null) {
            current.answer(i);

            if (isFinished()) {
                finishedGo();
            } else {
                setCurrent(paper.getCurrentQuiz());
            }
        }
    }

    private void abandon() {
        if (current != null) {
            current.abandon();

            if (isFinished()) {
                finishedGo();
            } else {
                setCurrent(paper.getCurrentQuiz());
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        quziPropertyChangeHandler();
    }

    private void quziPropertyChangeHandler() {
        this.jLabel_question.setText(getQuestion());
        this.jRadioButton1.setText(getAnswer(0));
        this.jRadioButton2.setText(getAnswer(1));
        this.jRadioButton3.setText(getAnswer(2));
        this.jRadioButton4.setText(getAnswer(3));
        this.jButton_abandon.setEnabled(paper == null ? false : !isFinished());
        this.jButton_answer.setEnabled(paper == null ? false : !isFinished());
        this.buttonGroup_answers.clearSelection();
    }

    private int getSelectIndex() {
        String indexStr = buttonGroup_answers.getSelection().getActionCommand();
        int index = Integer.parseInt(indexStr);
        return index;
    }

    private boolean isFinished() {
        return paper.isFinished();
    }

    private void finishedGo() {
        String result = "correct:" + paper.getFinishedCorrectQuizCount() + " wrong:" + paper.getFinishedWrongQuizCount();
//                + "\r\n again?";

//        int dialogResult = JOptionPane.showConfirmDialog(this, result);
        JOptionPane.showMessageDialog(this, result);
        BackWordGUIApp.getApplication().FinishGame();
//        if(dialogResult == JOptionPane.YES_OPTION){
//            paper.reset();
//        }

    }
}
