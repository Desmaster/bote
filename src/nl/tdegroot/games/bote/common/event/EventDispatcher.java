package nl.tdegroot.games.bote.common.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class EventDispatcher {
    private static Collection<EventHandler> eventHandlers = new ArrayList<EventHandler>();

    public static void register(EventHandler handler) {
        eventHandlers.add(handler);
    }

    public static void call(Event event) {
        for(EventHandler eventHandler : eventHandlers) {
            for (Method method : eventHandler.getClass().getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    EventListener listener = (EventListener) annotation;

                    if (listener.value().equals(event.event)) {
                        try {
                            method.invoke(eventHandler, event);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
