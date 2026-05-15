package cn.hwoxu.teamspirit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/5/15 22:01
 */
public class TeamCommand implements CommandExecutor {

    public static TeamCommand TeamCommandInstance = new TeamCommand();

    private TeamCommand(){}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            Component next = Component.text("");

            // judge
            if (strings.length < 2){
                player.sendMessage(next);
                player.sendMessage(Component.text("Wrong usage, correct usage be like: teamspirit [TeamA Number] [TeamB Number]"));
                player.sendMessage(next);
                return true;
            }

            // parse
            int teamA_number;
            int teamB_number;
            try{
                teamA_number = Integer.parseInt(strings[0]);
                teamB_number = Integer.parseInt(strings[1]);
            }catch (NumberFormatException exception){
                player.sendMessage(next);
                player.sendMessage(Component.text("Wrong usage, correct usage be like: teamspirit [TeamA Number] [TeamB Number]"));
                player.sendMessage(next);
                return true;
            }


            Server server = commandSender.getServer();
            List<Player> players = Arrays.asList(server.getOnlinePlayers().toArray(Player[]::new));
            int playerNumber = players.size();

            if (teamA_number + teamB_number > playerNumber){
                player.sendMessage(next);
                player.sendMessage(Component.text("Wrong arguments, 你连数数都能输错啊小妹妹你是真有点疯魔了"));
                player.sendMessage(next);
                return true;
            }

            // shuffle
            for (int i = 0; i < playerNumber; i++) {
                Collections.shuffle(players);
            }

            // construct message return
            Component sender = (Component.text("Executor ").color(NamedTextColor.WHITE))
                                .append(Component.text(player.getName()).color(NamedTextColor.GOLD))
                                .append(Component.text(", the result is as below: ").color(NamedTextColor.WHITE));
            Component TeamA = Component.text("Team 1: ").color(NamedTextColor.WHITE);
            Component TeamB = Component.text("Team 2: ").color(NamedTextColor.WHITE);
            Component TeamC = Component.text("Team Extra: ").color(NamedTextColor.WHITE);
            int index = 0;
            while (index != teamA_number){
                TeamA = TeamA.append(Component.text(players.get(index).getName() + " ").color(NamedTextColor.AQUA));
                index++;
            }
            while (index != teamA_number + teamB_number){
                TeamB = TeamB.append(Component.text(players.get(index).getName() + " ").color(NamedTextColor.YELLOW));
                index++;
            }
            while (index != playerNumber){
                TeamC = TeamC.append(Component.text(players.get(index).getName() + " ").color(NamedTextColor.GREEN));
                index++;
            }
            TeamC = TeamC.append(Component.text(", you can select one team to join or be a 旁观者(单词不会不搜了)").color(NamedTextColor.WHITE));

            // send message to global
            for (Player p : players) {
                player.sendMessage(next);
                p.sendMessage(sender);
                p.sendMessage(TeamA);
                p.sendMessage(TeamB);
                if (teamA_number + teamB_number != playerNumber){
                    p.sendMessage(TeamC);
                }
                player.sendMessage(next);
            }
        }
        return true; // 表示命令执行成功
    }
}
