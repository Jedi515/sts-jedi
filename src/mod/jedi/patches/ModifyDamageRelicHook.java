package mod.jedi.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.interfaces.ModifyDamageRelic;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class ModifyDamageRelicHook
{
    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class ApplyPowersPatch
    {
        @SpireInsertPatch(locator = SingleDamageLocator.class, localvars = {"tmp"})
        public static void SingleInsert(AbstractCard __instance, @ByRef float[] tmp)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[0] = ((ModifyDamageRelic)r).applyPowersRelic(__instance, (int)tmp[0]);
                    if (__instance.baseDamage != (int)tmp[0])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        @SpireInsertPatch(locator = SingleDamageFinalLocator.class, localvars = {"tmp"})
        public static void SingleFinalInsert(AbstractCard __instance, @ByRef float[] tmp)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[0] = ((ModifyDamageRelic)r).applyPowersFinalRelic(__instance, (int)tmp[0]);
                    if (__instance.baseDamage != (int)tmp[0])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        @SpireInsertPatch(locator = MultiDamageLocator.class, localvars = {"tmp", "i"})
        public static void MultiInsert(AbstractCard __instance, float[] tmp, @ByRef int[] i)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[i[0]] = ((ModifyDamageRelic)r).applyPowersRelic(__instance, (int)tmp[i[0]]);
                    if (__instance.baseDamage != (int)tmp[i[0]])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        @SpireInsertPatch(locator = MultiDamageFinalLocator.class, localvars = {"tmp", "i"})
        public static void MultiFinalInsert(AbstractCard __instance, float[] tmp, @ByRef int[] i)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[i[0]] = ((ModifyDamageRelic)r).applyPowersFinalRelic(__instance, (int)tmp[i[0]]);
                    if (__instance.baseDamage != (int)tmp[i[0]])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class CalculateCardDamagePatch
    {
        @SpireInsertPatch(locator = SingleDamageLocator.class, localvars = {"tmp"})
        public static void SingleInsert(AbstractCard __instance, AbstractMonster target, @ByRef float[] tmp)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[0] = ((ModifyDamageRelic)r).calculateCardDamageRelic(__instance, target, (int)tmp[0]);
                    if (__instance.baseDamage != (int)tmp[0])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        @SpireInsertPatch(locator = SingleDamageFinalLocator.class, localvars = {"tmp"})
        public static void SingleFinalInsert(AbstractCard __instance, AbstractMonster target, @ByRef float[] tmp)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[0] = ((ModifyDamageRelic)r).calculateCardDamageFinalRelic(__instance, target, (int)tmp[0]);
                    if (__instance.baseDamage != (int)tmp[0])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        @SpireInsertPatch(locator = MultiDamageLocator.class, localvars = {"tmp", "i"})
        public static void MultiInsert(AbstractCard __instance, AbstractMonster target, float[] tmp, @ByRef int[] i)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[i[0]] = ((ModifyDamageRelic)r).calculateCardDamageRelic(__instance, AbstractDungeon.getCurrRoom().monsters.monsters.get(i[0]), (int)tmp[i[0]]);
                    if (__instance.baseDamage != (int)tmp[i[0]])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        @SpireInsertPatch(locator = MultiDamageFinalLocator.class, localvars = {"tmp", "i"})
        public static void MultiFinalInsert(AbstractCard __instance, AbstractMonster target, float[] tmp, @ByRef int[] i)
        {
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r instanceof ModifyDamageRelic)
                {
                    tmp[i[0]] = ((ModifyDamageRelic)r).calculateCardDamageFinalRelic(__instance, AbstractDungeon.getCurrRoom().monsters.monsters.get(i[0]), (int)tmp[i[0]]);
                    if (__instance.baseDamage != (int)tmp[i[0]])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }
    }

//    @SpirePatch(clz = DamageInfo.class, method = "applyPowers")
//    public static class DamageInfoPatch
//    {
//        @SpireInsertPatch(locator = DamageInfoLocator.class, localvars = {"tmp"})
//        public static void PreInsert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp)
//        {
//            AbstractCard c = null;
//            for (AbstractGameAction GA : AbstractDungeon.actionManager.actions)
//            {
//                if (GA instanceof UseCardAction)
//                {
//                    c = (AbstractCard) ReflectionHacks.getPrivate(GA, UseCardAction.class, "targetCard");
//                    break;
//                }
//            }
//            if (c != null)
//            {
//                for (AbstractRelic r : AbstractDungeon.player.relics)
//                {
//                    if (r instanceof ModifyDamageRelic)
//                    {
//                        tmp[0] = ((ModifyDamageRelic)r).calculateCardDamageRelic(c, (AbstractMonster)target, (int)tmp[0]);
//                    }
//                }
//                if (__instance.base != (int)tmp[0])
//                {
//                    __instance.isModified = true;
//                }
//            }
//        }
//
//        @SpireInsertPatch(locator = DamageInfoFinalLocator.class, localvars = {"tmp"})
//        public static void PostInsert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp)
//        {
//            AbstractCard c = null;
//            for (AbstractGameAction GA : AbstractDungeon.actionManager.actions)
//            {
//                if (GA instanceof UseCardAction)
//                {
//                    c = (AbstractCard) ReflectionHacks.getPrivate(GA, UseCardAction.class, "targetCard");
//                    break;
//                }
//            }
//            if (c != null)
//            {
//                System.out.println("JEDI MOD: Card in queue: " + c.name);
//                for (AbstractRelic r : AbstractDungeon.player.relics)
//                {
//                    if (r instanceof ModifyDamageRelic)
//                    {
//                        tmp[0] = ((ModifyDamageRelic)r).calculateCardDamageFinalRelic(c, (AbstractMonster) target, (int)tmp[0]);
//                    }
//                }
//                if (__instance.base != (int)tmp[0])
//                {
//                    __instance.isModified = true;
//                }
//            }
//        }
//    }

    private static class SingleDamageLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    private static class MultiDamageLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
            return LineFinder.findInOrder(ctMethodToPatch, Collections.singletonList(finalMatcher), finalMatcher);
        }
    }

    private static class SingleDamageFinalLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "damage");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    private static class MultiDamageFinalLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "multiDamage");
            return LineFinder.findInOrder(ctMethodToPatch, Collections.singletonList(finalMatcher), finalMatcher);
        }
    }
//
//    private static class DamageInfoLocator extends SpireInsertLocator
//    {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
//        {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
//            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//        }
//    }
//
//    private static class DamageInfoFinalLocator extends SpireInsertLocator
//    {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
//        {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
//            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
//        }
//    }
}
