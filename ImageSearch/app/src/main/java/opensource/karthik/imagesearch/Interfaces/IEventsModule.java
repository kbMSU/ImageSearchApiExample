package opensource.karthik.imagesearch.Interfaces;

import com.squareup.otto.Bus;

public interface IEventsModule {
    Bus EventBus = new Bus();
    void Post(Object event);
    void Subscribe(Object subscriber);
    void Unsubscribe(Object subscriber);
}
