package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.relics.MainCommand;
import sts_jedi.jedi;

@SpirePatch(clz = AbstractDungeon.class, method = "returnEndRandomRelicKey")
@SpirePatch(clz = AbstractDungeon.class, method = "returnRandomRelicKey")
public class CommandPatches
{
    public static String Postfix(String __result, AbstractRelic.RelicTier tier)
    {
        if (jedi.isHubrisLoaded)
        {
            if (AbstractDungeon.player.hasRelic(MainCommand.ID))
            {
                return "jedi:command_" + tier.toString().toLowerCase();
            }
        }
        return __result;
    }
}
