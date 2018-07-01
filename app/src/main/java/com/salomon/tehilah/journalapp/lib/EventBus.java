package com.salomon.tehilah.journalapp.lib;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
