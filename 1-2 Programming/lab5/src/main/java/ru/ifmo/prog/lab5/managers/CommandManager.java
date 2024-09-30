package ru.ifmo.prog.lab5.managers;

import ru.ifmo.prog.lab5.commands.Command;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Класс для управления коммандами.
 * @author ru6ik
 */

public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> commandsHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     * @param commandName Название команды.
     * @param command Команда.
     */

    public void register(String commandName, Command command){
        commands.put(commandName, command);
    }

    /**
     * @return Словарь команд.
     */

    public Map<String, Command> getCommands(){
        return commands;
    }

    /**
     * @return История команд.
     */

    public List<String> getCommandsHistory(){
        return commandsHistory;
    }

    /**
     * Добавляет команду в историю.
     * @param command Команда.
     */

    public void addToHistory(String command){
        commandsHistory.add(command);
    }

}
