package com.Adathan;

import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import Interactions.SimpleCommandInteraction;

import javax.annotation.Nonnull;

public class SteamwroughtTomb extends JavaPlugin {

    public SteamwroughtTomb(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        (this.getLogger().atInfo()).log("[Steamwrought Tomb] Plugin Started!");

        Interaction.CODEC.register("SimpleCommand", SimpleCommandInteraction.class, SimpleCommandInteraction.CODEC);
    }
}