/*
 * BackWordGUIApp.java
 */
package backwordgui;

import dewafer.backword.core.DictionaryReader;
import dewafer.backword.core.DictionaryReaderImpl;
import dewafer.backword.core.Paper;
import dewafer.backword.core.PaperFactory;
import javax.swing.JFileChooser;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;

/**
 * The main class of the application.
 */
public class BackWordGUIApp extends SingleFrameApplication {

    public static final String PROP_PAPER_PROPERTY = "paperProperty";
    private Paper paper;

    public Paper getPaper() {
        return this.paper;
    }

    public void setPaper(Paper p) {
        Paper oldPaper = paper;
        this.paper = p;
        this.firePropertyChange(PROP_PAPER_PROPERTY, oldPaper, paper);
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new BackWordGUIView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of BackWordGUIApp
     */
    public static BackWordGUIApp getApplication() {
        return Application.getInstance(BackWordGUIApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(BackWordGUIApp.class, args);
    }

    @Action
    public Task loadDict() {
        return new LoadDictTask(org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class));
    }

    private class LoadDictTask extends org.jdesktop.application.Task<Object, Void> {

        LoadDictTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to LoadDictTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                DictionaryReader reader = new DictionaryReaderImpl(chooser.getSelectedFile());
                return PaperFactory.getPaper(reader);
            } else {
                return null;
            }
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
            if (result != null && result instanceof Paper) {
                BackWordGUIApp.getApplication().setPaper((Paper) result);
            }
        }
    }

    @Action
    public Task unloadDict() {
        return new UnloadDictTask(org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class));
    }

    private class UnloadDictTask extends org.jdesktop.application.Task<Object, Void> {

        UnloadDictTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to UnloadDictTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            BackWordGUIApp.getApplication().setPaper(null);
            System.gc();
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
            BackWordGUIApp.getApplication().FinishGame().execute();
        }
    }

    @Action
    public Task StartGame() {
        return new StartGameTask(org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class));
    }

    private class StartGameTask extends org.jdesktop.application.Task<Object, Void> {
        StartGameTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to StartGameTask fields, here.
            super(app);
        }
        @Override protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
            BackWordGUIView view = (BackWordGUIView) BackWordGUIApp.getApplication().getMainView();
            view.startGame();
        }
    }

    @Action
    public Task FinishGame() {
        return new FinishGameTask(org.jdesktop.application.Application.getInstance(backwordgui.BackWordGUIApp.class));
    }

    private class FinishGameTask extends org.jdesktop.application.Task<Object, Void> {
        FinishGameTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to StartGameTask fields, here.
            super(app);
        }
        @Override protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
            BackWordGUIView view = (BackWordGUIView) BackWordGUIApp.getApplication().getMainView();
            view.finishGame();
        }
    }
}
