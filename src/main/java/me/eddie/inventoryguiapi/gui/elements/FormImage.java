package me.eddie.inventoryguiapi.gui.elements;

import java.io.Serializable;

/**
 * Describes a Bedrock Form image.
 */
public class FormImage implements Serializable {
    public static final FormImage NONE = new FormImage(Type.NONE, null);
    public static final FormImage DEFAULT = new FormImage(Type.PATH, null);

    private final Type type;
    private final String path;

    /**
     * Creates a new NONE Bedrock image.
     */
    public FormImage() {
        this(Type.NONE, null);
    }

    /**
     * Creates a new Bedrock image.
     * @param type Image type
     * @param path Image path
     */
    public FormImage(Type type, String path) {
        this.type = type;
        this.path = path;
    }

    public Type getType() {
        return this.type;
    }

    public String getPath() {
        return this.path;
    }

    public enum Type {
        NONE(null),
        PATH("path"),
        URL("url");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}
