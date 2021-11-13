package me.eddie.inventoryguiapi.gui.view;

import fr.prodrivers.bukkit.bedrocktexturelib.BedrockTextureLib;
import me.eddie.inventoryguiapi.gui.elements.GUIElement;
import me.eddie.inventoryguiapi.gui.guis.InventoryGUI;
import me.eddie.inventoryguiapi.gui.guis.SharedInventoryGUI;
import me.eddie.inventoryguiapi.gui.session.GUISession;
import me.eddie.inventoryguiapi.gui.session.GUIState;
import me.eddie.inventoryguiapi.gui.session.InventoryState;
import me.eddie.inventoryguiapi.util.BedrockUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.*;

/**
 * Class that takes the generated contents of a InventoryGUI and shows it to a Bedrock player as a native Bedrock form.
 */
public class BedrockGUIPresenter implements GUIPresenter {
    /**
     * Will update the viewer's view of the given GUISession. This will not recalculate what should be displayed though,
     * instead use {@link InventoryGUI#updateContentsAndView(Player)} if this is what you require.
     * This method will also only do it for the provided viewer, in the case of shared inventories use
     * {@link SharedInventoryGUI#updateContentsAndView()} to update the view for all viewers
     * @param viewer The viewer of the GUI. Or if it's a GUI with multiple viewers, any viewer of the GUI.
     * @param session The GUISession that determines what should be displayed
     */
    public synchronized void updateView(final Player viewer, GUISession session) { //Synchronized so doesn't happen concurrently
        if(viewer == null || session == null){
            throw new IllegalArgumentException();
        }

        GUIState guiState = session.getGUIState();
        int page = session.getPage();

        InventoryState inventoryState = guiState.getExistingInventoryState(page); //Get the state to display
        while(inventoryState == null) { //Attempt to find a page that does exist nearby, since the state to display isn't there.
            page--;
            if(page < 0) {
                throw new RuntimeException("Attempted to update the view of a GUI that doesn't have any state!"); //Output what went wrong
            }
            inventoryState = guiState.getExistingInventoryState(page);
        }

        Optional<FloodgatePlayer> bedrockPlayer = BedrockUtil.getBedrockSession(viewer);
        if(bedrockPlayer.isPresent()) {
            if(BedrockUtil.getGUISessionOfBedrockPlayer(bedrockPlayer.get()).isPresent()) {
                throw new IllegalStateException("Blocked attempt to open multiple forms, as Bedrock clients will ignore all subsequent form requests when a form is still open.");
            }

            Map<Integer, GUIElement> guiElements = inventoryState.getComputedContentsBySlot();
            SortedSet<Integer> keys = new TreeSet<>(guiElements.keySet());

            SimpleForm.Builder builder = SimpleForm.builder()
                    .title(inventoryState.getTitle())
                    .content(""); // No content

            int buttonIndex = 0;
            for(int i = 0; i < keys.last(); i++) {
                GUIElement element = inventoryState.getElementInSlot(i);
                ItemStack display = element == null ? null : element.getDisplay(viewer, session);
                if(display != null && !display.getType().equals(Material.AIR)) {
                    String name = null;
                    if(display.getItemMeta() != null) {
                        if(display.getItemMeta().hasDisplayName()) {
                            name = display.getItemMeta().getDisplayName();
                        } else if(display.getItemMeta().hasLocalizedName()) {
                            name = display.getItemMeta().getLocalizedName();
                        }
                        if(name != null && display.getItemMeta().getLore() != null) {
                            name += "\n";
                            name += String.join("\n", display.getItemMeta().getLore());
                        }
                    }
                    if(name == null) {
                        name = display.getType().name();
                    }
                    me.eddie.inventoryguiapi.gui.elements.FormImage image = element.getFormImage(viewer, session);
                    if(image == null || image.getType() == null || image.getPath() == null) {
                        image = me.eddie.inventoryguiapi.gui.elements.FormImage.DEFAULT;
                    }
                    if(image == me.eddie.inventoryguiapi.gui.elements.FormImage.DEFAULT) {
                        if(BedrockTextureLib.getInstance().getTextureMapper() != null) {
                            String texturePath = BedrockTextureLib.getInstance().getTextureMapper().getTexturePath(display.getType());
                            if(texturePath != null) {
                                image = new me.eddie.inventoryguiapi.gui.elements.FormImage(me.eddie.inventoryguiapi.gui.elements.FormImage.Type.PATH, texturePath);
                            } else {
                                image = me.eddie.inventoryguiapi.gui.elements.FormImage.NONE;
                            }
                        } else {
                            image = me.eddie.inventoryguiapi.gui.elements.FormImage.NONE;
                        }
                    }
                    if(image.getType().getName() == null) {
                        builder.button(name);
                    } else {
                        builder.button(name, FormImage.Type.getByName(image.getType().getName()), image.getPath());
                    }
                }
                inventoryState.putAttribute(BedrockUtil.getFormButtonIndexToElementKey(buttonIndex), element);
                buttonIndex++;
            }

            BedrockUtil.addGUISessionOfBedrockPlayer(bedrockPlayer.get(), session);

            builder.responseHandler((form, responseData) -> {
                SimpleFormResponse response = form.parseResponse(responseData);
                int clickedButtonId = -1;
                if(response.isCorrect()) {
                    // Player did not close form
                    clickedButtonId = response.getClickedButtonId();
                }

                session.getInventoryGUI().handleBedrockResponse(session, viewer, clickedButtonId);
            });

            bedrockPlayer.get().sendForm(builder.build());
        }
    }
}
