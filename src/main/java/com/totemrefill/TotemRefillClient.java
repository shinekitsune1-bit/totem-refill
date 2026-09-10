package com.totemrefill;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class TotemRefillClient implements ClientModInitializer {

    private static KeyBinding totemRefillKey;

    @Override
    public void onInitializeClient() {

        totemRefillKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.totemrefill.refill",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_UNKNOWN,
                        "category.totemrefill"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (totemRefillKey.wasPressed()) {
                refillTotem(client);
            }
        });

        System.out.println("Totem Refill loaded!");
    }

    private static void refillTotem(net.minecraft.client.MinecraftClient client) {

        if (client.player == null) {
            return;
        }

        // Already have a Totem in offhand
        if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        // Search inventory for Totem
        for (int slot = 0; slot < client.player.getInventory().size(); slot++) {

            ItemStack stack = client.player.getInventory().getStack(slot);

            if (stack.isOf(Items.TOTEM_OF_UNDYING)) {

                ItemStack offhand = client.player.getOffHandStack();

                // Swap Totem with current offhand item
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
