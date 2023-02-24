package com.syrup.apitest.gui;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.MenuTools;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompMonsterEgg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FoundationGUI extends Menu {

    /**
     * Sample buttons to demostrate different Foundation features.
     */
    private final Button sampleButton;
    private final Button sampleSecondButton;
    private final Button sampleMenuButton;

    private final Button sampleToolsButton;

    /*
     * Create a new menu, called using a static method below
     */
    private FoundationGUI() {

        this.setTitle("Sample Menu");
        this.setSize(9 * 4);

        // Create a new button with click handler
        this.sampleButton = Button.makeSimple(ItemCreator.of(CompMaterial.APPLE), player -> {
            this.animateTitle("Received item with custom enchant");

            ItemCreator.of(CompMaterial.DIAMOND_SWORD).give(player);
        });
        //뒤에는 콜백인듯? animateTitle은 타이틀 애니메이션 호출

        // Create a new button with anonymous class, showing how to connect it with settings
        //이렇게도 만들 수 있다.
        this.sampleSecondButton = new Button() {

            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                restartMenu("demo value");

            }

            @Override
            public ItemStack getItem() {

                final boolean hasValue = false;

                return ItemCreator
                        .of(CompMaterial.DIAMOND,
                                "Diamond",
                                "",
                                "Demo value: " + hasValue)
                        .glow(hasValue)
                        .make();
            }
        };

        // ButtonMenu를 만들어서 Pagged넣어주면 페이지 메뉴 가능.
        // Create a new button that will open another menu
        this.sampleMenuButton = new ButtonMenu(new SamplePaggedMenu(this), ItemCreator.of(
                CompMaterial.COAL,
                "Open menu",
                "",
                "Click to open",
                "another menu."));

        // Create a new button that will open a premade menu of tools
        this.sampleToolsButton = new ButtonMenu(new MenuTools() {

            @Override
            protected Object[] compileTools() {
                return new Object[] {

                };
            }
        }, ItemCreator.of(CompMaterial.GOLDEN_AXE, "Tools Menu"));

    }

    //특정 인덱스의 아이템 설정하는곳.
    @Override
    public ItemStack getItemAt(int slot) {

        if (slot == 9 + 1)
            return this.sampleButton.getItem();

        if (slot == 9 + 3)
            return this.sampleSecondButton.getItem();

        if (slot == 9 + 5)
            return this.sampleMenuButton.getItem();
        if (slot == 9 + 7)
            return this.sampleToolsButton.getItem();
        return null;
    }

    //왼쪽 맨 아래에 gui 정보 알려주는곳.
    @Override
    protected String[] getInfo() {

        return new String[] {
                "This is a sample menu which has",
                "a sample button and a submenu."
        };
    }

    /**
     * Show this menu to the given player
     *
     * @param player
     */
    public static void showTo(Player player) {
        new FoundationGUI().displayTo(player);
    }

    /* ------------------------------------------------------------------------------- */
    /* Subclasses */
    /* ------------------------------------------------------------------------------- */

    // pageable gui
    private final class SamplePaggedMenu extends MenuPagged<String> {

        /*
         * Create a new instance of this menu that will automatically add "Return Back"
         * button pointing to the parent menu.
         */
        private SamplePaggedMenu(Menu parent) {
            super(parent, Arrays.asList("Hello", "World", "Hi"));
            //parent 메뉴, 리스트 아이템들.
        }

        /**
         * @see org.mineacademy.fo.menu.MenuPagged#convertToItemStack(java.lang.Object)
         */
        @Override
        protected ItemStack convertToItemStack(String item) {
            return ItemCreator.of(CompMaterial.DIAMOND, item, "hello!").make();
            //약간 viewholder 랑 비슷한거 같기도 하고?
        }

        /**
         * @see org.mineacademy.fo.menu.MenuPagged#onPageClick(org.bukkit.entity.Player, java.lang.Object, org.bukkit.event.inventory.ClickType)
         */
        // 아이템 클릭 했을때라고 보는게 좋을듯.
        @Override
        protected void onPageClick(Player player, String item, ClickType click) {
            player.getInventory().addItem(convertToItemStack(item));

            this.animateTitle("Added " + ItemUtil.bountifyCapitalized(item) + " to inventory!");
        }

        /**
         * Return null to hide the info icon.
         *
         * @see org.mineacademy.fo.menu.Menu#getInfo()
         */
        @Override
        protected String[] getInfo() {
            return null;
        }
    }

    /*
     * Compile a list of valid items here
     */
    }

