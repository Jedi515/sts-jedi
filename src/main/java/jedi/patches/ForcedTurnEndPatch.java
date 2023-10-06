package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import jedi.interfaces.onForcedTurnEndInterface;

import java.util.ArrayList;

public class ForcedTurnEndPatch {

    public static boolean shouldEndTurn()
    {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if ((r instanceof onForcedTurnEndInterface) && (!(((onForcedTurnEndInterface) r).onForcedTurnEnd())))
                    return false;
            }
        return true;
    }

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
    @SpirePatch2(cls = "com.megacrit.cardcrawl.mod.replay.powers.TimeCollectorPower", method = "onAfterUseCard", optional = true)
    public static class InfiniteSpire {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(EndTurnButton.class.getName()) && m.getMethodName().equals("disable")) {
                        m.replace("if (" + ForcedTurnEndPatch.class.getName() + ".shouldEndTurn()) {$proceed($$);}");
                    }
                }
            };
        }
    }
}
