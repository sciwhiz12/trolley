package tk.sciwhiz12.trolley.api;

/**
 * Priority for event listeners. An event listener with a higher priority will receive an event earlier than an
 * event listener with a lower priority. The default priority is {@link #NORMAL}.
 */
public enum EventPriority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}
