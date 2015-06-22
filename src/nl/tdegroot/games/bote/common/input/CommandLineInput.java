package nl.tdegroot.games.bote.common.input;

import nl.tdegroot.games.bote.common.event.EventDispatcher;
import nl.tdegroot.games.bote.common.input.commands.Commands;
import nl.tdegroot.games.bote.common.input.event.CommandEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CommandLineInput {
    Scanner scanner;

    private static ArrayList<Command> commands = new ArrayList<Command>();

    public CommandLineInput() {
        scanner = new Scanner(System.in);

        registerCommand(Commands.playersCommand);
    }

    public static void registerCommand(Command command) {
        commands.add(command);
    }

    public void start() {
        while(scanner.hasNext()) {
            String input = scanner.next();
            String[] splitInput = input.split(" ");
            for(Command command : commands) {
                if (splitInput[0].matches(command.getMatch())) {
                    String[] args = Arrays.copyOfRange(splitInput, 1, splitInput.length);
                    if (args.length > 0)
                        command.setArguments(args);

                    EventDispatcher.call(new CommandEvent("players"));
                }
            }
        }
    }
}
