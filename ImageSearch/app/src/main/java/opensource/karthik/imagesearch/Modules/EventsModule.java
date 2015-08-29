package opensource.karthik.imagesearch.Modules;

import opensource.karthik.imagesearch.Interfaces.IEventsModule;

public class EventsModule implements IEventsModule {
    private static EventsModule instance = null;
    private EventsModule() {
    }
    public static EventsModule getInstance() {
        if(instance == null) {
            instance = new EventsModule();
        }
        return instance;
    }

    @Override
    public void Post(Object event) {
        EventBus.post(event);
    }

    @Override
    public void Subscribe(Object subscriber) {
        EventBus.register(subscriber);
    }

    @Override
    public void Unsubscribe(Object subscriber) {
        EventBus.unregister(subscriber);
    }
}
