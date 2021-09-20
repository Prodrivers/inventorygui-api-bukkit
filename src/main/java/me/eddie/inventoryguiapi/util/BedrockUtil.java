package me.eddie.inventoryguiapi.util;

import me.eddie.inventoryguiapi.gui.session.GUISession;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.Optional;

public class BedrockUtil {
	public static final String SESSION_PROPERTY_KEY = "inventoryguiapi.session";
	public static final String FORM_BUTTON_INDEX_TO_ELEMENT_KEY_PREFIX = "form.button.byinventoryindex.";

	public static String getFormButtonIndexToElementKey(int buttonIndex) {
		return BedrockUtil.FORM_BUTTON_INDEX_TO_ELEMENT_KEY_PREFIX + buttonIndex;
	}

	public static boolean hasBedrockSession(Player player) {
		try {
			// Check presence of FloodgateApi
			Class.forName("org.geysermc.floodgate.api.FloodgateApi");

			FloodgateApi api = FloodgateApi.getInstance();
			return api != null && api.isFloodgatePlayer(player.getUniqueId());
		} catch(ClassNotFoundException e) {
			return false;
		}
	}

	public static Optional<FloodgatePlayer> getBedrockSession(Player player) {
		try {
			// Check presence of FloodgateApi
			Class.forName("org.geysermc.floodgate.api.FloodgateApi");

			FloodgateApi api = FloodgateApi.getInstance();
			if(api != null) {
				return Optional.ofNullable(api.getPlayer(player.getUniqueId()));
			}
		} catch(ClassNotFoundException ignored) {}

		return Optional.empty();
	}

	public static Optional<GUISession> getGUISessionOfBedrockPlayer(Player player) {
		try {
			// Check presence of FloodgateApi
			Class.forName("org.geysermc.floodgate.api.FloodgateApi");

			Optional<FloodgatePlayer> bedrockPlayer = getBedrockSession(player);
			if(bedrockPlayer.isPresent()) {
				return getGUISessionOfBedrockPlayer(bedrockPlayer.get());
			}
		} catch(ClassNotFoundException ignored) {}

		return Optional.empty();
	}

	public static Optional<GUISession> getGUISessionOfBedrockPlayer(FloodgatePlayer bedrockPlayer) {
		return Optional.ofNullable(bedrockPlayer.getProperty(BedrockUtil.SESSION_PROPERTY_KEY));
	}

	public static void addGUISessionOfBedrockPlayer(Player player, GUISession guiSession) {
		try {
			// Check presence of FloodgateApi
			Class.forName("org.geysermc.floodgate.api.FloodgateApi");

			Optional<FloodgatePlayer> bedrockPlayer = getBedrockSession(player);
			bedrockPlayer.ifPresent(floodgatePlayer -> addGUISessionOfBedrockPlayer(floodgatePlayer, guiSession));
		} catch(ClassNotFoundException ignored) {}
	}

	public static void addGUISessionOfBedrockPlayer(FloodgatePlayer bedrockPlayer, GUISession guiSession) {
		bedrockPlayer.addProperty(BedrockUtil.SESSION_PROPERTY_KEY, guiSession);
	}

	public static void removeGUISessionOfBedrockPlayer(Player player) {
		try {
			// Check presence of FloodgateApi
			Class.forName("org.geysermc.floodgate.api.FloodgateApi");

			Optional<FloodgatePlayer> bedrockPlayer = getBedrockSession(player);
			bedrockPlayer.ifPresent(floodgatePlayer -> floodgatePlayer.removeProperty(BedrockUtil.SESSION_PROPERTY_KEY));
		} catch(ClassNotFoundException ignored) {}
	}
}
