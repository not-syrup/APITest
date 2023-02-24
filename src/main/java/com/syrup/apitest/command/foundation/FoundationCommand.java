package com.syrup.apitest.command.foundation;

import com.syrup.apitest.gui.FoundationGUI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.Arrays;

@AutoRegister // annotation 방식 커맨드 추가? [ 사용할려면 클래스 확장 불가능하게 final로 선언? 근데 생성자는 public or 싱글톤 instance 필드 ]
public final class FoundationCommand extends SimpleCommand {
    public FoundationCommand() {
        super("foundation"); // /foundation
        /*
        setUsage("<x> <y> <z>"); //x y z 좌표에 있는 블럭 가져오기
        setDescription("get block from location");
        setPermission("foundation");
        setMinArguments(3); //x, y, z
        //setPermission(null); 아무나 사용가능

         */
    }

    //multiline usage override시 설명 수정 가능

    @Override
    protected void onCommand() {
        this.checkConsole();
        /*
        //checkArgs(); min argument 설정함
        //Remain = nms랑 가까운듯 / Common = Bukkit이랑 가까움
        try {

            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            World world = ((Player)sender).getWorld();
            tell(world.getBlockAt(x,y,z).getType() + "!");
        }
        catch (NumberFormatException e) {
            returnInvalidArgs();
        }
         */
        FoundationGUI.showTo((Player) sender);
    }

}
