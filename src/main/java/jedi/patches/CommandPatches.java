package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import jedi.modifiers.CommandCustomRun;
import jedi.relics.MainCommand;

@SpirePatch(clz = AbstractDungeon.class, method = "returnEndRandomRelicKey")
@SpirePatch(clz = AbstractDungeon.class, method = "returnRandomRelicKey")
public class CommandPatches
{
    public static String Postfix(String __result, AbstractRelic.RelicTier tier)
    {
        if (AbstractDungeon.player.hasRelic(MainCommand.ID) || ((CardCrawlGame.trial != null) && (CardCrawlGame.trial.dailyModIDs().contains(CommandCustomRun.ID))))
        {
            return "jedi:command_" + tier.toString().toLowerCase();
        }
        return __result;
    }
}
