package me.eddie.inventoryguiapi.gui.contents;

import me.eddie.inventoryguiapi.gui.session.GUISession;
import me.eddie.inventoryguiapi.util.Callback;
import org.bukkit.entity.Player;

/**
 * Responsible for Calculating the positions that GUIElements need to go into in the displayed GUI and then
 * updating the current inventory state.
 * To customize an inventory layout beyond what this is capable of, simply implements this class and make your GUI use
 * your version of GUIPopulator.
 */
public interface GUIPopulator {
    /**
     * Gets, via GUI's contents provider, the items a player should be viewing; figures out what slots they should be
     * displayed in within the inventory; updates the current InventoryState with this and then calls back to the callback.
     * @param session The GUISession of the GUI being populated
     * @param viewer The viewer of the GUISession
     * @param callback Callback to be called back to on completion
     */
    public void populateGUI(final GUISession session, final Player viewer, final Callback<Void> callback);
}
