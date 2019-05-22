package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.interfaces.ModifyBlockRelicInterface;

import java.util.ArrayList;


public class ModifyBlockRelicHook
{
    @SpirePatch(clz = AbstractCard.class, method = "applyPowersToBlock")
    public static class ActualBlockClass
    {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard __instance, @ByRef float[] tmp)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyBlockRelicInterface)
                {
                    tmp[0] = ((ModifyBlockRelicInterface)r).modifyBlock(__instance, tmp[0]);
                }
            }
            __instance.isBlockModified = (__instance.baseBlock != (int)tmp[0]);
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
