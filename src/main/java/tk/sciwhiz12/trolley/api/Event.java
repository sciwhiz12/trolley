package tk.sciwhiz12.trolley.api;

/**
 * The superclass of all events which can be fired on an {@linkplain EventBus event bus}.
 */
public abstract class Event {
    private boolean cancelled = false;

    /**
     * {@return whether this event is cancelled or not} A cancelled event will not be received by further listeners
     * which do not explicitly opt into receiving cancelled events.
     */
    public final boolean isCancelled() {
        return cancelled;
    }

    /**
     * {@return whether this event can be cancelled}
     *
     * @see #isCancelled()
     */
    public final boolean isCancellable() {
        return false; // TODO: implement
    }

    /**
     * Sets the cancellation state of this event.
     *
     * @param cancelled the new cancellation state of the event
     * @throws UnsupportedOperationException if this event is not {@linkplain #isCancellable() cancellable}
     */
    public final void setCancelled(boolean cancelled) {
        if (!isCancellable()) {
            throw new UnsupportedOperationException("Attempted to set cancellation state on a non-cancellable event of type "
                    + this.getClass().getCanonicalName());
        }
        this.cancelled = cancelled;
    }
}
