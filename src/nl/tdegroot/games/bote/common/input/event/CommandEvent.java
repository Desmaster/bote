package nl.tdegroot.games.bote.common.input.event;

import nl.tdegroot.games.bote.common.event.Event;

public class CommandEvent extends Event {
    String command;
    String[] arguments;

    public CommandEvent(String command, String[] arguments) {
        super("command:" + command);

        this.command = command;
        this.arguments = arguments;
    }

    public CommandEvent(String command) {
        this(command, new String[0]);
    }
}
