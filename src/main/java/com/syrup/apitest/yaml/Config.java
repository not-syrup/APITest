package com.syrup.apitest.yaml;

import lombok.Getter;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.settings.YamlConfig;

@AutoRegister
@Getter
public final class Config extends YamlConfig {

    @Getter
    private final static Config instance = new Config();

    private final String key = "test";

    private final String value = "secret";

    @Getter
    private String getValue = "";


    private Config() {
        loadConfiguration(NO_DEFAULT, "custom-config.yml");
    }

    @Override
    protected void onSave() {
        Common.log("File onSave");
        set(key, value);
        //저장은 알아서 되는듯? = save 직접 호출하는편이 더 안전할듯.. 저장 잘 안되네
    }

    @Override
    protected void onLoad() {
        Common.log("File onLoad");
        /**
         * test : secret
         */
        getValue = getString(key);
    }
}
