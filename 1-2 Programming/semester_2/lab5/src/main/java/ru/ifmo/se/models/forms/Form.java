package ru.ifmo.se.models.forms;

import ru.ifmo.se.SpecialException.IncorrectInputInScriptException;
import ru.ifmo.se.SpecialException.InvalidFormException;

public abstract class Form<T> {

    public abstract T build() throws InvalidFormException, IncorrectInputInScriptException;

}
