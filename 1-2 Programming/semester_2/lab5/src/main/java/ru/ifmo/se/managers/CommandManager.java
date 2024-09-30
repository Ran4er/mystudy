package ru.ifmo.se.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.ifmo.se.commands.Command;

/**
 * This class realization manipulation with commands
 * @author Ra4el
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Add command
     * @param commandName Name of the command
     * @param command command
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Return dictionary of the command
     * @return Dictionary of the command
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Return history of the command
     * @return list of the command history
     */
    public List<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Add command to the history
     * @param command command
     */
    public void addToHistory(String command) {
        commandHistory.add(command);
    }

}
