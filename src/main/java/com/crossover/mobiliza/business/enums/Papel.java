package com.crossover.mobiliza.business.enums;

public enum Papel {
    ADMIN("ADMIN"),
    USER("USER");

    public static final String AUTHORITY_PREFIX = "ROLE_";

    private final String name;

    private Papel(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return name;
    }

    public String getAuthorityName() {
        return AUTHORITY_PREFIX + name;
    }
}
