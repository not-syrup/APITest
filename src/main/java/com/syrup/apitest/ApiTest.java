package com.syrup.apitest;

import com.syrup.apitest.command.HelperCommand;
import com.syrup.apitest.event.EventHandler;
import com.syrup.apitest.yaml.Config;
import me.lucko.helper.Schedulers;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class ApiTest extends SimplePlugin {

    //Helper API Test Plugin.
    //02.24 Added Foundation API Test
    @Override
    protected void onPluginStart() {
        getLogger().info("API Enabled");
        Schedulers.async().runLater(() -> getLogger().info("ASYNC LOGGING"), 60L).thenRunAsync(() -> {
            getLogger().info("THEN ASYNC");
        }).thenRunAsync(() -> {
            getLogger().info(Config.getInstance().getGetValue() + "!");
        });
        //command
        new HelperCommand().init();
        new EventHandler().init();
    }

    // rl confirm에서 동작하지 않는듯..? 그냥 Disable 에서 처리하는게 깔끔할 것 같음. 아니면 Foundation 메인 API 자체에서 리로드를 호출하면, Foundation에 의해 로드된 플러그인들 모두가 이걸 호출하는걸수도?
    @Override
    protected void onPluginReload() {
        getLogger().info("Detect Reloading..");
    }

    @Override
    protected void onPluginPreReload() {
        getLogger().info("Detect pre Reload");
    }
}
