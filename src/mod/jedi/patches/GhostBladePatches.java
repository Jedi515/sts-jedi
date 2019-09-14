package mod.jedi.patches;

import com.evacipated.cardcrawl.mod.hubris.patches.cards.AbstractCard.PiercingTag;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.jedi;
import mod.jedi.relics.GhostBlades;

import static mod.jedi.patches.JediEnums.JEDI_PIERCING_CARD;
import static mod.jedi.patches.JediEnums.JEDI_PIERCING_DAMAGE;

public class GhostBladePatches
{


    @SpirePatch(clz = Shiv.class, method = SpirePatch.CONSTRUCTOR)
    public static class BetterShivs
    {
        public static void Postfix(Shiv __instance)
        {
            if (AbstractDungeon.player != null)
            {
                if (AbstractDungeon.player.hasRelic(GhostBlades.ID))
                {
                    if (jedi.isHubrisLoaded)
                    {
                        __instance.tags.add(PiercingTag.HUBRIS_PIERCING);
                    }
                    else
                    {
                        __instance.tags.add(JEDI_PIERCING_CARD);
                    }
                }
            }
        }
    }

    //And now I just copy paste all hubris piercing right?
    //Right.
    //Pls don't boop me kio.

    @SpirePatch(
            clz= DamageInfo.class,
            method=SpirePatch.CLASS
    )
    public static class PiercingField
    {
        public static SpireField<Boolean> isJediPiercing = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="resetAttributes"
    )
    public static class DamageTypeReset
    {
        public static void Postfix(AbstractCard __instance)
        {
            if (__instance.hasTag(JEDI_PIERCING_CARD)) {
                __instance.damageTypeForTurn = JEDI_PIERCING_DAMAGE;
            }
        }
    }

    @SpirePatch(
            clz=DamageInfo.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez={
                    AbstractCreature.class,
                    int.class,
                    DamageInfo.DamageType.class
            }
    )
    public static class MakeDamageInfoPiercing
    {
        public static void Prefix(DamageInfo __instance, AbstractCreature damageSource, int base, @ByRef DamageInfo.DamageType[] type)
        {
            if (type[0] == JEDI_PIERCING_DAMAGE) {
                type[0] = DamageInfo.DamageType.NORMAL;
                PiercingField.isJediPiercing.set(__instance, true);
            }
        }
    }


    @SpirePatch(
            clz=AbstractCard.class,
            method="applyPowers"
    )
    public static class ApplyPowersFakeNormalDamage
    {
        public static void Prefix(AbstractCard __instance)
        {
            if (__instance.hasTag(JEDI_PIERCING_CARD)) {
                __instance.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
            }
        }

        public static void Postfix(AbstractCard __instance) {
            if (__instance.hasTag(JEDI_PIERCING_CARD)) {
                __instance.damageTypeForTurn = JEDI_PIERCING_DAMAGE;
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class CalculateCardDamageFakeNormalDamage
    {
        public static void Prefix(AbstractCard __instance, AbstractMonster mo)
        {
            if (__instance.hasTag(JEDI_PIERCING_CARD)) {
                __instance.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
            }
        }

        public static void Postfix(AbstractCard __instance, AbstractMonster mo) {
            if (__instance.hasTag(JEDI_PIERCING_CARD)) {
                __instance.damageTypeForTurn = JEDI_PIERCING_DAMAGE;
            }
        }
    }

    @SpirePatch(
            clz=AbstractCreature.class,
            method="decrementBlock"
    )
    public static class PassThroughBlock
    {
        public static SpireReturn<Integer> Prefix(AbstractCreature __instance, DamageInfo info, int damageAmount)
        {
            if (PiercingField.isJediPiercing.get(info)) {
                return SpireReturn.Return(damageAmount);
            }
            return SpireReturn.Continue();
        }
    }
}
