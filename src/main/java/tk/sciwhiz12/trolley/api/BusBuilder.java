package tk.sciwhiz12.trolley.api;

import java.util.function.Supplier;

/**
 * A builder for an {@linkplain EventBus event buses}. For convenience, the various configuration methods return the
 * same instance on which the method was invoked, for a fluent interface API through method chaining.
 */
public interface BusBuilder {
    /**
     * Sets whether the event bus will dispatch events in parallel.
     *
     * @param parallel whether to dispatch events in parallel
     * @return this builder, for method chaining
     * @see EventBus#parallel()
     */
    BusBuilder parallel(boolean parallel);

    /**
     * Sets whether this event bus, when {@linkplain EventBus#fire(Supplier) dispatching events in bulk}, fires each
     * event individually.
     *
     * @param dispatchesIndividualEvents whether to fire events individually during bulk dispatch
     * @return this builder, for method chaining
     * @see EventBus#dispatchesIndividualEvents()
     */
    BusBuilder dispatchesIndividualEvents(boolean dispatchesIndividualEvents);

    /**
     * Builds an event bus based on the configuration of this builder. This method can be invoked only once for each
     * builder.
     *
     * @return an event bus
     * @throws IllegalStateException if this builder has built a bus before
     */
    EventBus build();
}
