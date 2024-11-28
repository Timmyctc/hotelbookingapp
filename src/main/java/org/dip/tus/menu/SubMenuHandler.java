package org.dip.tus.menu;

/**
 * A functional interface for handling submenu operations in the application.
 * Each implementation defines logic for processing a specific user choice.
 */
@FunctionalInterface
public interface SubMenuHandler {

    /**
     * Processes the given choice from the submenu.
     *
     * @param choice The user-selected option in the submenu.
     */
    void handle(int choice);
}
