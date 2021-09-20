package me.eddie.inventoryguiapi.gui.contents;

import me.eddie.inventoryguiapi.gui.session.GUISession;
import me.eddie.inventoryguiapi.util.BedrockUtil;
import me.eddie.inventoryguiapi.util.Callback;
import org.bukkit.entity.Player;

/**
 * Determines what contents to show viewers on each page.
 * This class assign each platform its own GUI provider (and by extension, GUI populator).
 *
 * This allows for example to have a paginated view for Java player and an un-paginated view for Bedrock players, as
 * native Bedrock forms have no size limitation.
 */
public class PlatformDifferentiatedGUIContentsProvider implements GUIContentsProvider {
    GUIContentsProvider javaProvider;
    GUIContentsProvider bedrockProvider;

    public void setJavaProvider(GUIContentsProvider javaProvider) {
        this.javaProvider = javaProvider;
    }

    public void setBedrockProvider(GUIContentsProvider bedrockProvider) {
        this.bedrockProvider = bedrockProvider;
    }

    public boolean hasJavaProvider() {
        return this.javaProvider != null;
    }

    public boolean hasBedrockProvider() {
        return this.bedrockProvider != null;
    }

    @Override
    public void genContents(Player viewer, int page, GUISession session, Callback<GUIContentsResponse> callback) {
        if(BedrockUtil.hasBedrockSession(viewer)) {
            this.bedrockProvider.genContents(viewer, page, session, callback);
        } else {
            this.javaProvider.genContents(viewer, page, session, callback);
        }
    }

    @Override
    public void genTitle(Player viewer, int page, GUISession session, Callback<String> callback) {
        if(BedrockUtil.hasBedrockSession(viewer)) {
            this.bedrockProvider.genTitle(viewer, page, session, callback);
        } else {
            this.javaProvider.genTitle(viewer, page, session, callback);
        }
    }
}
