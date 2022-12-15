package net.kvn.modules;

public enum Category {
    BUILD("Build"),
    CLIENT("Client"),
    QUICKWORLDEDIT("QuickWorldEdit");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
