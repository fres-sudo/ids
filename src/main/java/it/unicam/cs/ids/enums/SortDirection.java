package it.unicam.cs.ids.enums;

public class SortDirection {
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public static boolean isValid(String direction) {
        return ASC.equalsIgnoreCase(direction) || DESC.equalsIgnoreCase(direction);
    }
}
