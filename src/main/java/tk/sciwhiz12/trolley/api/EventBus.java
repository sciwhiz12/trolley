package tk.sciwhiz12.trolley.api;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A bus on which events are fired and received by event listeners.
 *
 * <p>Unless otherwise noted, a {@code null} argument as a method parameter will cause a {@link NullPointerException}.</p>
 *
 * @see BusBuilder
 * @see #builder()
 */
public interface EventBus {

    /**
     * {@return a builder for creating a new bus}
     */
    static BusBuilder builder() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: implement builders
    }

    // Bus configuration methods

    /**
     * {@return whether this event bus dispatches events in parallel}
     */
    boolean parallel();

    /**
     * {@return whether this event bus, when {@linkplain #fire(Supplier) dispatching events in bulk}, fires each event
     * individually}
     *
     * @see #fire(Supplier)
     */
    boolean dispatchesIndividualEvents();

    // Firing methods

    /**
     * Fires a single event instance to all listeners on this bus.
     *
     * <p>The dispatch of the event may be done serially or {@linkplain #parallel() in parallel}. In parallel dispatch,
     * all event listeners receive the same event instance; therefore it is critical that <strong>callers ensure that
     * the event instance passed to a parallel-dispatching event bus is thread-safe</strong>.</p>
     *
     * @param event the event instance
     */
    void fire(Event event);

    /**
     * Fires an event, which may have multiple instances, to all listeners on this bus, and returns all fired instances.
     *
     * <p>If this bus is configured to {@linkplain #dispatchesIndividualEvents() dispatch individual events}, then all
     * event listeners will receive their own instance as supplied by the event factory.</p>
     *
     * <p>If the bus is not configured to dispatch individual events, then the amount of instances constructed through
     * the factory depends on the dispatch strategy of this bus. For serial dispatch, a single instance is constructed
     * and passed to all event listeners. For parallel dispatch, the amount of instances is an implementation detail and
     * may vary, but will never surpass the amount of event listeners on this bus.</p>
     *
     * @param eventFactory a factory for event instances
     * @param <E>          the type of the event
     * @return the collection of all fired event instance
     */
    <E extends Event> Collection<E> fire(Supplier<E> eventFactory);

    // Event listener de/registration methods

    /**
     * Registers a listener for a specific event type.
     *
     * @param type     the event class
     * @param listener the event listener
     * @param <E>      type of the event receivable by the listener
     */
    <E extends Event> void registerListener(Class<E> type, Consumer<E> listener);

    /**
     * De-registers a previously registered listener for a specific event type. If the listener has not been previously
     * registered for that specific event type, nothing happens.
     *
     * @param type     the event class
     * @param listener the event listener
     * @param <E>      type of the event receivable by the listener
     */
    <E extends Event> void deregisterListener(Class<E> type, Consumer<E> listener);

    /**
     * A handler which is invoked when an event bus receives an uncaught exception.
     */
    @FunctionalInterface
    interface UncaughtExceptionHandler {
        /**
         * Called to handle an uncaught exception from an event listener of an event bus. After this method is called
         * (and does not throw its own exception), the original exception is propagated upwards.
         *
         * @param bus       the bus which received the uncaught exception
         * @param instance  the instance of the event
         * @param exception the uncaught exception
         * @param context   a context object. The exact type of this object is an implementation detail, but it can be
         *                  generally assumed that calling {@link Object#toString()} on this object will provide a
         *                  textual representation of information which may be helpful in debugging the cause of the
         *                  uncaught exception.
         */
        void handleUncaughtException(EventBus bus, Event instance, Throwable exception, Object context);
    }
}
