package com.totemrefill;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TotemRefillClient implements ClientModInitializer {

    public static void refillTotem() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        // Already holding a Totem in offhand
        if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        // Search the main inventory
        for (int slot = 0; slot < client.player.getInventory().size(); slot++) {
            ItemStack stack = client.player.getInventory().getStack(slot);

            if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                ItemStack offhand = client.player.getOffHandStack();

                // Swap the inventory slot with the offhand
                client.player.getInventory().setStack(slot, offhand);
                client.player.setStackInHand(
                        net.minecraft.util.Hand.OFF_HAND,
                        stack
                );

                return;
            }
        }
    }

    @Override
    public void onInitializeClient() {
        System.out.println("Totem Refill loaded!");
    }
             }
