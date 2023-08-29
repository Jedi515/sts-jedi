package jedi.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import jedi.actions.ScryCallbackAction;
import jedi.actions.ScrySeenCallbackAction;
import jedi.interfaces.CardSeenScriedInterface;
import jedi.interfaces.OnBeingScriedInterface;

import java.util.ArrayList;

@SpirePatch(clz = ScryAction.class, method = "update")
public class ScryPatches
{
    public static boolean wasEmpty;
    public static ExprEditor Instrument()
    {
        return new ExprEditor()
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException
            {

                if (m.getMethodName().equals("moveToDiscardPile"))
                {
                    m.replace("$proceed($$);" +
                            "if ($1 instanceof "+ OnBeingScriedInterface.class.getName() + ") ((" + OnBeingScriedInterface.class.getName() + ")$1).onBeingScried();");
                }
            }
        };
    }

    @SpireInsertPatch(locator =  LocatorOpen.class, localvars = {"tmpGroup"})
    public static void Insert(ScryAction __instance, CardGroup tmpGroup)
    {
        wasEmpty = true;
        if (__instance instanceof ScrySeenCallbackAction) ((ScrySeenCallbackAction)__instance).callback.accept(tmpGroup.group);
        for (AbstractCard c : tmpGroup.group)
        {
            if (c instanceof CardSeenScriedInterface)
            {
                ((CardSeenScriedInterface) c).onSeenScried();
            }
        }
    }
    @SpireInsertPatch(locator = LocatorClear.class)
    public static void InsertCallback(ScryAction __instance)
    {
        wasEmpty = false;
        if (__instance instanceof ScryCallbackAction)
        {
            ((ScryCallbackAction)__instance).callback.accept(AbstractDungeon.gridSelectScreen.selectedCards);
        }
    }
//    @SpireInsertPatch(locator = LocatorAccess.class)
    @SpirePostfixPatch
    public static void InsertEmptyCallback(ScryAction __instance)
    {
        if (!wasEmpty || !__instance.isDone) return;
        if (__instance instanceof ScryCallbackAction)
        {
            ((ScryCallbackAction)__instance).callback.accept(AbstractDungeon.gridSelectScreen.selectedCards);
        }
    }

    private static class LocatorOpen extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "open");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    private static class LocatorClear extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "clear");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
    private static class LocatorAccess extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "discardPile");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    public static void scry(CardGroup grp)
    {
        for (AbstractCard c : grp.group)
        {
            if (c instanceof CardSeenScriedInterface)
            {
                ((CardSeenScriedInterface) c).onSeenScried();
            }
        }
    }
}
