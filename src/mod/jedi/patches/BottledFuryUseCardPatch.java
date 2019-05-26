package mod.jedi.patches;


import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.cards.red.UnlimitedPower;

public class BottledFuryUseCardPatch
{
    public static boolean RetainCard = false;

    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class ActualUsePatch
    {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn Insert(UseCardAction __instance)
        {
            if (RetainCard || ((AbstractCard) ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard")).cardID.equals(UnlimitedPower.ID)) //might aswell
            {
                RetainCard = false;
                AbstractDungeon.player.hand.moveToHand((AbstractCard) ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard"), AbstractDungeon.player.hand);
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(UseCardAction.class, "exhaustCard");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
