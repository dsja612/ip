package enums;

/**
 * Types of commands that can be used
 */
public enum CommandType {
    BYE("bye"),
    LIST("list"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete");
    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
