package ru.croc.demo.service.method;

/**
 *
 * @author AGumenyuk
 * @since 25.10.2017 18:53
 */
public enum Methods {

    /**
     *
     */
    ADD("add", AddEntityMethod.class),
    /**
     *
     */
    LIST("list", ListEntitesMethod.class),
    /**
     *
     */
    DELETE("delete", DeleteEntityMethod.class);

    private final String name;

    Methods(String name, Class methodClass) {
        this.name = name;
        this.methodClass = methodClass;
    }
}
