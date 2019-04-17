package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.relics.Zontanonomicon;

import java.util.ArrayList;

@SpirePatch(clz = CursedTome.class, method = "randomBook")
public class CursedTomePatch
{
    @SpireInsertPatch(locator = Locator.class, localvars = {"possibleBooks"})
    public static void Insert(CursedTome __instance, @ByRef ArrayList<AbstractRelic>[] possibleBooks)
    {
        if (!AbstractDungeon.player.hasRelic(Zontanonomicon.ID)) {
            possibleBooks[0].add(RelicLibrary.getRelic(Zontanonomicon.ID).makeCopy());
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
