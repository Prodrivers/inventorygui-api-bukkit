package me.eddie.inventoryguiapi.gui.events;

import me.eddie.inventoryguiapi.gui.elements.GUIElement;
import me.eddie.inventoryguiapi.gui.session.GUISession;
import me.eddie.inventoryguiapi.util.MockInventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Cancellable event that is fired when a GUIElement has been clicked on in a Bedrock form but has not yet handled the
 * click
 */
public class GUIBedrockClickEvent extends GUIClickEvent implements Cancellable {
    public GUIBedrockClickEvent(GUISession session, Player player, GUIElement interactedElement, int formButtonIndex){
        super(
                session,
                player,
                formButtonIndex,
                interactedElement,
                new MockInventoryClickEvent(formButtonIndex)
        );
    }

    private static final HandlerList handlers = new HandlerList(); //Required for Bukkit Events, can't just inherit

    @Override
    public HandlerList getHandlers() { //Required for Bukkit Events, can't just inherit
        return handlers;
    }

    public static HandlerList getHandlerList() { //Required for Bukkit Events, can't just inherit
        return handlers;
    }
}
