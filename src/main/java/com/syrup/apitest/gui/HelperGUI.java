package com.syrup.apitest.gui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.Material;
import org.bukkit.entity.Player;

//helper api gui
public class HelperGUI extends Gui {


    //schema = 구조, Display에 선언한 (000010000)은 첫번째 줄의 5번째에 배치
    // the display book
    private static final MenuScheme DISPLAY = new MenuScheme().mask("000010000");

    // the keyboard buttons
    //나머지 버튼은 1로 표시한곳에만 배치한다는것. 혹시 줄을 지우면 gui도 작아지나? = constructor 참고, schema는 줄만 맞춰주면 될듯
    private static final MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("000000001")
            .mask("111111111")
            .mask("111111111")
            .mask("011111110")
            .mask("000000000");

    // we're limited to 9 keys per line, so add 'P' one line above.
    private static final String KEYS = "PQWERTYUIOASDFGHJKLZXCVBNM";

    private StringBuilder message = new StringBuilder();

    public HelperGUI(Player player) {
        super(player, 4, "&7Typewriter");
    }

    @Override
    public void redraw() {

        // perform initial setup.
        if (isFirstDraw()) {

            // place the buttons
            MenuPopulator populator = BUTTONS.newPopulator(this);
            for (char keyChar : KEYS.toCharArray()) {
                populator.accept(ItemStackBuilder.of(Material.CLAY_BALL)
                        .name("&f&l" + keyChar)
                        .lore("")
                        .lore("&7Click to type this character")
                        .build(() -> {
                            message.append(keyChar);
                            redraw();
                        }));
            }

            // space key
            populator.accept(ItemStackBuilder.of(Material.CLAY_BALL)
                    .name("&f&lSPACE")
                    .lore("")
                    .lore("&7Click to type this character")
                    .build(() -> {
                        message.append(" ");
                        redraw();
                    }));
        }

        // update the display every time the GUI is redrawn. (버튼 클릭시 책만 갱신하는듯)
        DISPLAY.newPopulator(this).accept(ItemStackBuilder.of(Material.BOOK)
                .name("&f" + message.toString() + "&7_")
                .lore("")
                .lore("&f> &7Use the buttons below to type your message.")
                .lore("&f> &7Hit ESC when you're done!")
                .buildItem().build());
    }

}
