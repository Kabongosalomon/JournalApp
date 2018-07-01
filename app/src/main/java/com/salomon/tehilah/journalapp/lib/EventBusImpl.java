package com.salomon.tehilah.journalapp.lib;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public class EventBusImpl implements EventBus{
    org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder {
        private static final EventBusImpl INSTANCE = new EventBusImpl();
    }

    public static EventBusImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public EventBusImpl(){
        eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }


    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }


    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public void post(Object event) {
        eventBus.post(event);
    }
}
