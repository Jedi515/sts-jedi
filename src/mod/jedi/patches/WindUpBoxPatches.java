package mod.jedi.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.hubris.relics.CrackedHourglass;
import com.evacipated.cardcrawl.mod.hubris.relics.Metronome;
import com.evacipated.cardcrawl.mod.hubris.relics.Stopwatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.relics.WindUpBox;

import java.util.ArrayList;
import java.util.Collections;

public class WindUpBoxPatches
{
    @SpirePatch(clz = MercuryHourglass.class, method = "atTurnStart")
    public static class MercuryWindup
    {
        public static SpireReturn Prefix(MercuryHourglass __instance)
        {
            if (AbstractDungeon.player.hasRelic(WindUpBox.ID))
            {
                __instance.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, __instance));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(4, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Pocketwatch.class, method = "atTurnStartPostDraw")
    public static class PocketwatchWindup
    {
        public static void Prefix(Pocketwatch __instance)
        {
            if (AbstractDungeon.player.hasRelic(WindUpBox.ID) && __instance.counter == 4)
            {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 3));
            }
        }
    }

    @SpirePatch(clz = StoneCalendar.class, method = "atBattleStart")
    public static class StoneWindupHow
    {
        public static void Postfix(StoneCalendar __instance)
        {
            if (AbstractDungeon.player.hasRelic(WindUpBox.ID) && __instance.counter == 4)
            {
                __instance.counter++;
            }
        }
    }

    @SpirePatch(clz = Sundial.class, method = "onShuffle")
    public static class SundialWindup
    {
        @SpireInsertPatch(locator = SundialLocator.class)
        public static void Insert(Sundial __instance)
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }

    private static class SundialLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    @SpirePatch(cls = "com.evacipated.cardcrawl.mod.hubris.relics.CrackedHourglass", method = "atBattleStart", optional = true)
    public static class CrackedWindup
    {
        @SpireInsertPatch(locator = CrackedLocator.class)
        public static void Insert(CrackedHourglass __instance)
        {
            if (AbstractDungeon.player.hasRelic(WindUpBox.ID))
            {
                ReflectionHacks.setPrivate(__instance, CrackedHourglass.class, "timeCounter", 210.0F);
            }
        }
    }

    private static class CrackedLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CrackedHourglass.class, "setCounter");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    @SpirePatch(cls = "com.evacipated.cardcrawl.mod.hubris.relics.Stopwatch", method = "onEquip", optional = true)
    public static class StopwatchWindup
    {
        public static void Postfix(Stopwatch __instance)
        {
            if(AbstractDungeon.player.hasRelic(WindUpBox.ID))
            {
                __instance.counter += 2;
            }
        }
    }

    @SpirePatch(cls = "com.evacipated.cardcrawl.mod.hubris.relics.Metronome", method = "doMetronome", optional = true)
    public static class MetronomeWindup
    {
        public static void Prefix(Metronome __instance, @ByRef int[] newCounter)
        {
            if (AbstractDungeon.player.hasRelic(WindUpBox.ID))
            {
               if (newCounter[0] != 0 && newCounter[0] < 10)
               {
                   newCounter[0]++;
               }
            }
        }
    }

    private static class MetronomeLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(Metronome.class, "counter");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
