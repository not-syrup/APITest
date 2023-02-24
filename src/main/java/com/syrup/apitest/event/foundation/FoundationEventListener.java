package com.syrup.apitest.event.foundation;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.event.SimpleListener;

@AutoRegister
public final class FoundationEventListener extends SimpleListener<AsyncPlayerChatEvent> {

    private static final FoundationEventListener instance = new FoundationEventListener();

    private FoundationEventListener() {
        super(AsyncPlayerChatEvent.class, EventPriority.NORMAL, false);
        //eventhandler annotation이랑 비슷
    }

    @Override
    protected void execute(AsyncPlayerChatEvent event) {
        //근데 이거보다 helper가 더 쓰기 좋은듯?
        event.setCancelled(true);
        Messenger.broadcastSuccess(event.getMessage());
    }
}
