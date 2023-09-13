package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import jedi.interfaces.onForcedTurnEndInterface;

import java.util.ArrayList;

public class ForcedTurnEndPatch {

    @SpirePatch2(clz = GameActionManager.class, method = "callEndTurnEarlySequence")
    public static class BaseGame {
        public static SpireReturn Prefix() {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if ((r instanceof onForcedTurnEndInterface) && (!(((onForcedTurnEndInterface) r).onForcedTurnEnd())))
                    return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(cls = "infinitespire.monsters.Nightmare", method = "onEnoughDamageTakenAlpha", optional = true)
    @SpirePatch2(cls = "infinitespire.monsters.Nightmare", method = "onEnoughDamageTakenBeta", optional = true)
    public static class InfiniteSpire {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn Insert() {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if ((r instanceof onForcedTurnEndInterface) && (!(((onForcedTurnEndInterface) r).onForcedTurnEnd())))
                    return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }


    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "clear");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
