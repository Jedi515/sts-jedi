package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import jedi.interfaces.onForcedTurnEndInterface;

@SpirePatch2(clz = GameActionManager.class, method = "callEndTurnEarlySequence")
public class ForcedTurnEndPatch {
    public static SpireReturn Prefix()
    {
        for (AbstractRelic r : AbstractDungeon.player.relics)
        {
            if ((r instanceof onForcedTurnEndInterface) && (!(((onForcedTurnEndInterface) r).onForcedTurnEnd())))
                return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
