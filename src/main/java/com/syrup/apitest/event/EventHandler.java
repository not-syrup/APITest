package com.syrup.apitest.event;

import me.lucko.helper.Events;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventHandler {

    public void init() {

        //simple join handler
        Events.subscribe(PlayerJoinEvent.class).handler((e) -> Bukkit.broadcastMessage(e.getPlayer().getName() + " 님이 서버에 참여했습니다!"));

        //only one calls (Timeunit 붙이면 해당 시간 이후 만료 가능)
        Events.subscribe(PlayerJoinEvent.class).expireAfter(1).handler((e) -> Bukkit.broadcastMessage("이 메시지는 다음부터 호출되지 않습니다."));

        //비슷한 이벤트끼리 merge 가능.
        Events.merge(PlayerEvent.class, PlayerQuitEvent.class, PlayerKickEvent.class).handler(e -> Bukkit.broadcastMessage("!")); //구와왁 비슷한 이벤트끼리 묶어서 되는데 왜 EntityDamageEvent는 안될까

        Events.merge(Player.class) //해당 타입만 리턴하면 다른 이벤트더라도 융합가능.
                .bindEvent(PlayerDeathEvent.class, PlayerDeathEvent::getEntity) //Player return
                .bindEvent(PlayerQuitEvent.class, PlayerQuitEvent::getPlayer) //Player return
                .filter(Player::isOp) // 체킹 과정에서 메시지 전송가능
                .handler(e -> Bukkit.broadcastMessage(e.getName() + "이(가) 죽거나 나갔습니다."));


    }
}
