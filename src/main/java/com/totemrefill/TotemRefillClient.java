package com.totemrefill;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class TotemRefillClient implements ClientModInitializer {

    private static KeyBinding refillKey;

    @Override
    public void onInitializeClient() {
        refillKey = new KeyBinding(
                "key.totemrefill.refill",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN,
                "category.totemrefill"
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (refillKey.wasPressed()) {
                refillTotem(client);
            }
        });
    }

    private static void refillTotem(MinecraftClient client) {
        if (client.player == null ||
                client.interactionManager == null ||
                client.currentScreen != null) {
            return;
        }

        // Already holding a Totem
        if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        var inventory = client.player.getInventory();

        // Player inventory container slots:
        // 0-8   = hotbar
        // 9-35  = main inventory
        // 36-39 = armor
        // 40    = offhand
        for (int inventorySlot = 0; inventorySlot < 36; inventorySlot++) {
            if (inventory.getStack(inventorySlot).isOf(Items.TOTEM_OF_UNDYING)) {

                int screenSlot = inventorySlot < 9
                        ? 36 + inventorySlot
                        : inventorySlot;

                // Pick up the Totem.
                client.interactionManager.clickSlot(
                        client.player.playerScreenHandler.syncId,
                        screenSlot,
                        0,
                        SlotActionType.PICKUP,
                        client.player
                );

                // Put it into offhand.
                client.interactionManager.clickSlot(
                        client.player.playerScreenHandler.syncId,
                        45,
                        0,
                        SlotActionType.PICKUP,
                        client.player
                );

                return;
            }
        }
    }
    }
