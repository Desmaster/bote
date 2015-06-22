package nl.tdegroot.games.bote.common.input;

import nl.tdegroot.games.bote.common.event.Event;
import nl.tdegroot.games.bote.common.event.EventDispatcher;
import nl.tdegroot.games.bote.common.input.event.CommandEvent;

import java.lang.annotation.Annotation;

public class Command {
    private final String match;
    private String[] arguments;

    public Command(String match) {
        this.match = match;
    }

    /*
    ** @return An instance of Command with set arguments
     */
    public Command getInstance(String[] args) {
        Command command = new Command(match);
        command.setArguments(args);
        return command;
    }

    /*
    ** @return Array containing the given arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    /*
    ** Sets the arguments of this command, can only be set once
     */
    public void setArguments(String[] args) {
        if (arguments.length == 0) arguments = args;
    }


    /*
    ** @return Matched string for this Command
     */
    public String getMatch() {
        return match;
    }
}
