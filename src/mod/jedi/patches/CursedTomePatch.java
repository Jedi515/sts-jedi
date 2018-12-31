package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.relics.Zontanonomicon;

import java.util.ArrayList;

@SpirePatch(clz = CursedTome.class, method = "randomBook")
public class CursedTomePatch
{
    @SpireInsertPatch(rloc = 1, localvars = {"possibleBooks"})
    public static void Insert(CursedTome __instance, @ByRef ArrayList<AbstractRelic>[] possibleBooks)
    {
        if (!AbstractDungeon.player.hasRelic(Zontanonomicon.ID)) {
            possibleBooks[0].add(RelicLibrary.getRelic(Zontanonomicon.ID).makeCopy());
        }
    }
}
