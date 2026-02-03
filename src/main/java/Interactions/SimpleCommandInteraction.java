package Interactions;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.protocol.Interaction;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.protocol.WaitForDataFrom;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class SimpleCommandInteraction extends SimpleInstantInteraction {
    @Nonnull
    public static final BuilderCodec CODEC;

    protected String command;

    @Nonnull
    public WaitForDataFrom getWaitForDataFrom() {
        return WaitForDataFrom.Server;
    }

    protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        World world = (commandBuffer.getExternalData()).getWorld();
        world.execute(() -> {
            if (command.contains(" @w ")) {
                String worldName = world.getName();
                command = command.replace("@w",worldName);
                CommandManager.get().handleCommand(ConsoleSender.INSTANCE, (command == null || command.isEmpty()) ? "/say Command Not Found" : command);
            } else if (command.contains(" @a ")){
                    world.getPlayerRefs().forEach(playerRef -> {
                        CommandManager.get().handleCommand(ConsoleSender.INSTANCE, (command == null || command.isEmpty()) ? "/say Command Not Found" : command.replace("@a", playerRef.getUsername()));
                    });
            } else {
                CommandManager.get().handleCommand(ConsoleSender.INSTANCE, (command == null || command.isEmpty()) ? "/say Command Not Found" : command);
            }
        });
    }

    @Nonnull
    protected Interaction generatePacket() {
        return new com.hypixel.hytale.protocol.RunRootInteraction();
    }

    protected void configurePacket(Interaction packet) {
        super.configurePacket(packet);
    }

    static {
        CODEC = BuilderCodec.builder(SimpleCommandInteraction.class, SimpleCommandInteraction::new, SimpleInstantInteraction.CODEC)
                .documentation("Executes command when called")
                .append(new KeyedCodec("Command", Codec.STRING),
                        (SimpleCommandInteraction, o) -> SimpleCommandInteraction.command=(String) o,
                        (SimpleCommandInteraction) -> SimpleCommandInteraction.command)
                .documentation("command that will be executed when called").add().build();
    }
}
