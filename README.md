# InventoryGUIAPI
Advanced Minecraft GUI Library/API, built on top of Bukkit.

This is forked version with substantial API changes and transparent support for native Bedrock forms through Geyser's Floodgate 2.0.

A backward-compatible fork, with upgrades for latest versions of Spigot (post-flattening), Java and Gradle, is available in the `legacy` branch.

# Usage
This API is available on a Maven repository.

```
<repositories>
	<repository>
    	<id>prodrivers-repo</id>
    	<url>https://repo.prodrivers.fr/</url>
    </repository>
</repositories>
<dependencies>
	<dependency>
		<groupId>fr.prodrivers.bukkit</groupId>
		<artifactId>inventorygui-api</artifactId>
		<version>2.0.0-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

# Examples
View examples of how to use the API at [src/main/java/me/eddie/inventoryguiapi/examples](https://github.com/Prodrivers/InventoryGUIAPI/tree/master/src/main/java/me/eddie/inventoryguiapi/examples)

# Documentation
Documentation is available on Prodrivers Sources for:

* [latest release](http://inventoryguiapi.sources.prodrivers.fr)
* [latest commit](http://inventoryguiapi.sources.prodrivers.fr/snapshot)
* [latest legacy fork](http://inventoryguiapi.sources.prodrivers.fr/legacy)
