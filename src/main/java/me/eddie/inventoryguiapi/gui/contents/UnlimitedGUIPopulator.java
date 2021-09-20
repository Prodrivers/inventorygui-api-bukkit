package me.eddie.inventoryguiapi.gui.contents;

import me.eddie.inventoryguiapi.gui.elements.ActionItem;
import me.eddie.inventoryguiapi.gui.elements.GUIElement;
import me.eddie.inventoryguiapi.gui.guis.GUI;
import me.eddie.inventoryguiapi.gui.guis.InventoryGUI;
import me.eddie.inventoryguiapi.gui.session.GUISession;
import me.eddie.inventoryguiapi.gui.session.InventoryState;
import me.eddie.inventoryguiapi.plugin.InventoryGUIAPI;
import me.eddie.inventoryguiapi.util.Callback;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible for Calculating the positions that GUIElements need to go into in the displayed, size-unlimited GUI and
 * then updating the current inventory state.
 */
public class UnlimitedGUIPopulator implements GUIPopulator {
    @Override
    public void populateGUI(GUISession session, Player viewer, Callback<Void> callback) {
        if(session == null || viewer == null) {
            throw new IllegalArgumentException();
        }
        InventoryGUI iGui = session.getInventoryGUI();
        if(!(iGui instanceof GUI)) {
            throw new IllegalArgumentException("The default GUIPopulator can only be used with the default GUI implementation");
        }
        final GUI gui = (GUI) iGui;
        final int page = session.getPage();
        final GUIContentsProvider contentsProvider = gui.getContentsProvider();
        contentsProvider.genContents(viewer, page, session, contents -> {
            Map<Integer, GUIElement> positions = new HashMap<>();
            List<GUIElement> elements = new ArrayList<>(contents.getElements()); //Clone as we will be removing items from this list as we go

            //Place first the GUIElements that has desired positions
            for(GUIElement elem : new ArrayList<>(elements)) { //Iterate over cloned list since we are modifying it during the iteration (And not doing this would cause a concurrent modification exception)
                if(elem.hasDesiredDisplayPosition()
                        && !positions.containsKey(elem.getDesiredDisplayPosition())) { //If this element has a desired position and it isn't taken
                    positions.put(elem.getDesiredDisplayPosition(), elem); //Place it into it's desired position
                    elements.remove(elem); //Remove element from list so that list only contains unplaced elements
                }
            }

            //Place all the other GUIElements
            int vacantSlot = 0;
            for(GUIElement element : new ArrayList<>(elements)) { //Iterate over cloned list since we are modifying it during the iteration (And not doing this would cause a concurrent modification exception)
                while(positions.containsKey(vacantSlot)) {
                    vacantSlot++;
                }
                positions.put(vacantSlot, element); //Place this element in the vacant slot
                vacantSlot++; //Not strictly necessary but skips the containsKey check next iteration
                elements.remove(element);
            }

            if(elements.size() > 0) {
                InventoryGUIAPI.getInstance().getLogger().warning(elements.size() + " GUIElements were unable to be placed into a GUI. The following stack trace should help you find what went wrong");
                new Exception().printStackTrace();
            }

            final InventoryState inventoryState = session.getGUIState().getOrCreateInventoryState(page);
            inventoryState.setComputedContentsBySlot(positions);
            inventoryState.setHasNextPage(contents.hasNextPage());
            contentsProvider.genTitle(viewer, page, session, title -> {
                inventoryState.setTitle(title);
                session.getGUIState().updateInventoryState(page, inventoryState);
                callback.call(null);
            });
        });
    }
}
