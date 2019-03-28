package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import mod.jedi.relics.BattleStandard;

public class BattleStandartPatch
{
    @SpirePatch(
            clz=MapRoomNode.class,
            method="isConnectedTo"
    )
    public static class IsConnectedTo
    {
        public static boolean Postfix(boolean __result, MapRoomNode __instance, MapRoomNode node)
        {
            if (__result && __instance == AbstractDungeon.getCurrMapNode())
            {
                if (AbstractDungeon.player.hasRelic(BattleStandard.ID))
                {
                    for (MapEdge edge : __instance.getEdges())
                    {
                        MapRoomNode nextNode = getNode(edge.dstX, edge.dstY);
                        if (nextNode != null && nextNode.room instanceof MonsterRoom)
                        {
                            return (node.room instanceof MonsterRoom);
                        }
                    }
                }
            }
            return __result;
        }
    }

    private static MapRoomNode getNode(int x, int y)
    {
        try {
            return CardCrawlGame.dungeon.getMap().get(y).get(x);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
