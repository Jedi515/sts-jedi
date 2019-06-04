package mod.jedi.patches;


import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.cards.curses.TheDog;
import mod.jedi.cards.red.UnlimitedPower;

public class RetainUseCardPatch
{
    public static boolean RetainCard = false;

    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class ActualUsePatch
    {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn Insert(UseCardAction __instance)
        {
            AbstractCard usedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard");
            if  (    RetainCard ||
                    (usedCard.cardID.equals(UnlimitedPower.ID)) ||
                    (usedCard.cardID.equals(TheDog.ID))
                ) //might aswell
            {

                if (usedCard.cardID.equals(TheDog.ID)) ((TheDog) usedCard).theDogStays();

                RetainCard = false;
                AbstractDungeon.player.hand.moveToHand(usedCard, AbstractDungeon.player.hand);
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
