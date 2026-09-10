package com.totemrefill;

import net.fabricmc.api.ClientModInitializer;

public class TotemRefillClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Totem Refill loaded!");
    }
}
