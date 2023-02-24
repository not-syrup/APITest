package com.syrup.apitest.command;

import com.syrup.apitest.gui.HelperGUI;
import me.lucko.helper.Commands;
import me.lucko.helper.Schedulers;
import me.lucko.helper.random.VariableAmount;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class HelperCommand {

    public void init() {
        //simple-help
        Commands
                .create() //Builder Start
                .assertPlayer() //Only Execute for Player (String 입력시 메시지 전송 가능)
                .handler((c) -> c.sender().sendMessage("This is Simple help message")) //lambda handler
                .register("simple-help"); //command register (wiki 상 플러그인 인스턴스 받았던거 같은데..?

        //simple random
        Commands
                .create()
                .assertPlayer()
                .handler(c -> c.sender().sendMessage(VariableAmount.range(0, 10).getFlooredAmount() + "값이 나왔습니다."))
                .register("simple-random");

        //simple-teleport x-y-z teleport
        Commands.create().assertPlayer()
                .assertUsage("<x> <y> <z>") //그 뒤에 나와야 하는 파라미터들
                .assertPermission("teleport")
                .handler((c) -> {
                    Player target = c.sender();
                    int x = c.arg(0).parseOrFail(Integer.class);
                    int y = c.arg(1).parseOrFail(Integer.class);
                    int z = c.arg(2).parseOrFail(Integer.class);
                    target.teleport(new Location(target.getWorld(), x, y, z));
                }).register("simple-teleport");

        //simple-countdown <time>
        Commands.create().assertPlayer("It's for Player.")
                .assertOp("Only op execute this command.")
                .assertUsage("<time>")
                .handler((c) -> {
                    Player sender = c.sender();
                    int time = c.arg(0).parseOrFail(Integer.class);
                    AtomicInteger atomicInteger = new AtomicInteger(time);
                    /*
                    Builder 써서 build() 호출 안하나 보네..? 그냥 써도 되는듯
                    Schedulers.builder().async().after(time * 20L).run(() -> {sender.sendMessage("CountDown End!");});
                    */
                    Schedulers.async().runRepeating((task) -> {
                        sender.sendMessage("counting in .." + atomicInteger.getAndDecrement() + " seconds..");
                        if (atomicInteger.get() <= 0) {
                            task.stop();
                            sender.sendMessage("countdown ends.");
                        }
                    }, 0L, 20L); //1초 주기 스케줄링

                }).register("simple-countdown");

        //simple-say <hello/hi/bye/good> - tabcomplete
        Commands.create().assertPlayer()
                .assertOp().assertUsage("<hello/hi/bye/good>").tabHandler((e) -> {
                    List<String> tab = new ArrayList<>(Arrays.asList("Hello", "Hi", "Bye", "Good"));
                    String arg = e.arg(0).parseOrFail(String.class);
                    return tab.stream().filter((it) -> it.contains(arg)).collect(Collectors.toList());
                }).handler((e) -> {
                    Bukkit.broadcastMessage(e.arg(0).parseOrFail(String.class));
                }).register("simple-say");
        Commands.create().assertPlayer().handler((e) -> {
            new HelperGUI(e.sender()).open();
        }).register("helper-gui");
    }
}
