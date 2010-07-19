package util.EventModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {

    private static final String             DEFAULT_EVENTTYPE = "999999999999";

    private Map<String, List<EventHandler>> eventHandlers     = new HashMap<String, List<EventHandler>>();

    public void addEventListener(String eventType, EventHandler handler) {
        List<EventHandler> lists = eventHandlers.get(eventType);
        if (lists == null) {
            lists = new ArrayList<EventHandler>();
            eventHandlers.put(eventType, lists);
        }
        lists.add(handler);
    }

    public void addEventListener(EventHandler handler) {
        addEventListener(DEFAULT_EVENTTYPE, handler);
    }

    public void dispatchEvent(String eventType, Event e) {
        List<EventHandler> lists = eventHandlers.get(eventType);
        if (lists != null) {
            for (EventHandler handler : lists) {
                handler.eventHandler(e);
            }
        }
    }

    public void dispatchEvent() {
        dispatchEvent(DEFAULT_EVENTTYPE, new Event() {});
    }

    public boolean removeEventListener(String eventType, EventHandler handler) {
        List<EventHandler> lists = eventHandlers.get(eventType);
        if (lists != null) {
            return lists.remove(handler);
        }
        return false;
    }

    public boolean removeEventListener(EventHandler handler) {
        return removeEventListener(DEFAULT_EVENTTYPE, handler);
    }

}
