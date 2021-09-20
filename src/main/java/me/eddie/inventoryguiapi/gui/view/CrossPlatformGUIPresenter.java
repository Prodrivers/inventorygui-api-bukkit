package me.eddie.inventoryguiapi.gui.view;

import me.eddie.inventoryguiapi.gui.session.GUISession;
import me.eddie.inventoryguiapi.util.BedrockUtil;
import org.bukkit.entity.Player;

/**
 * Class that takes the generated contents of an InventoryGUI and shows it to a player, with different presentation
 * implementation depending on the player's platform (Java or Bedrock).
 * This will show an inventory for a Java player and a native Bedrock form for a Bedrock player.
 */
public class CrossPlatformGUIPresenter implements GUIPresenter {
    private final InventoryGUIPresenter javaPresenter = new InventoryGUIPresenter();
    private final BedrockGUIPresenter bedrockPresenter = new BedrockGUIPresenter();

    @Override
    public void updateView(Player viewer, GUISession session) {
        if(BedrockUtil.hasBedrockSession(viewer)) {
            bedrockPresenter.updateView(viewer, session);
        } else {
            javaPresenter.updateView(viewer, session);
        }
    }
}
