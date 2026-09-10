package com.totemrefill;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        if (client.player == null) {
            return;
        }

        if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        for (int slot = 0; slot < client.player.getInventory().size(); slot++) {
            ItemStack stack = client.player.getInventory().getStack(slot);

            if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                ItemStack offhand = client.player.getOffHandStack();

                client.player.getInventory().setStack(slot, offhand);
                client.player.setStackInHand(
                        net.minecraft.util.Hand.OFF_HAND,
                        stack
                );

                return;
            }
        }
    }
            }
