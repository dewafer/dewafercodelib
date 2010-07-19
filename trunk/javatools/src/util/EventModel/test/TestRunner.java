package util.EventModel.test;

import java.util.Timer;
import java.util.TimerTask;

import util.EventModel.Event;
import util.EventModel.EventDispatcher;
import util.EventModel.EventHandler;

public class TestRunner {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        final EventDispatcher eve = new EventDispatcher();
        eve.addEventListener(new EventHandler() {

            @Override
            public void eventHandler(Event pE) {
                System.out.println("GO GO STUDY");
            }
        });

        eve.addEventListener("MyEventType", new MyHandler());
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            private Integer integer = 0;

            @Override
            public void run() {
                integer++;
                eve.dispatchEvent();
                eve.dispatchEvent("MyEventType", new MyEvent(this));
                if (integer == 5) {
                    eve.removeEventListener(new MyHandler());
                }
            }
        }, 5000, 5000);
    }
}


class MyEvent implements Event {
    public Object invoker;

    public MyEvent(Object parent) {
        this.invoker = parent;
    }
}


class MyHandler implements EventHandler {

    @Override
    public void eventHandler(Event pE) {
        MyEvent me = (MyEvent)pE;
        System.out.println(me.invoker);
    }

}
