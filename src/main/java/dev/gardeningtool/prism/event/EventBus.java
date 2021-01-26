package dev.gardeningtool.prism.event;

import dev.gardeningtool.prism.PrismBot;
import dev.gardeningtool.prism.listener.EventHandler;
import dev.gardeningtool.prism.listener.Listener;
import dev.gardeningtool.prism.listener.impl.CommandListener;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class EventBus {

    private Deque<Event> eventQueue;
    private HashMap<Listener, Class<? extends Listener>> listeners;

    public EventBus(PrismBot prismBot) {
        eventQueue = new LinkedList<>();
        listeners = new HashMap<>();
        listeners.put(new CommandListener(prismBot.getCommandManager()), CommandListener.class);
        new Thread(() -> { //There's dozens of better ways to do this
            while(true) {
                if (!eventQueue.isEmpty()) {
                    Event event = eventQueue.poll();
                    Class<? extends Event> eventClazz = event.getClass();
                    for (Map.Entry<Listener, Class<? extends Listener>> entry : listeners.entrySet()) {
                        Listener listener = entry.getKey();
                        Class<? extends Listener> clazz = entry.getValue();
                        Stream.of(clazz.getMethods()).filter(method -> method.isAnnotationPresent(EventHandler.class) &&
                                method.getParameterCount() == 1 &&
                                method.getParameterTypes()[0].equals(eventClazz)).forEach(method -> {
                            try {
                                method.invoke(listener, event);
                            } catch (InvocationTargetException | IllegalAccessException exc) {
                                exc.printStackTrace();
                            }
                        });
                    }
                }
                try {
                    Thread.sleep(35L);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            }
        }).start();
    }
    public void post(Event event) {
        eventQueue.add(event);
    }

}
