package Object;

public enum ToolUnit {
    UNIT("Unit"), 
    PAIRS("Pairs"),
    BOX("Box"),
    SET("Set"),
    PACK("Pack"),
    PACKAGE("Package"),

    ;

    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here.
    private final String name;
    ToolUnit(String name) { this.name = name; }
    ToolUnit(Object name) { this.name = (String) name; }
    
    public String GetName() { return this.name; }
    public Object GetNameAsObject() { return (Object) this.GetName(); }

    // ----------------------------------------------------------
    // Magic here: Declare the enum constant as a static final field using the prefix.
    public static ToolUnit GetEnumByName(String name) {
        for (ToolUnit p : ToolUnit.values()) {
            if (p.name.equals(name) || p.GetNameAsObject().equals(name)) { return p; } 
            if (p.name.equals(name.toLowerCase())) { return p; }
        }
        return null;
    }
    public static ToolUnit GetEnumByName(Object name) { return ToolUnit.GetEnumByName(name.toString());  }

    public static boolean FindEnumByName(String name) { return ToolUnit.GetEnumByName(name) != null; }
    public static boolean FindEnumByName(Object name) { return ToolUnit.GetEnumByName(name) != null; }

}
