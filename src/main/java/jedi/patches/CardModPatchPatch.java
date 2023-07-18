package jedi.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cardMods.ModifyDamageOtherInterface;
import jedi.util.Wiz;

public class CardModPatchPatch
{
    @SpirePatch2(clz = CardModifierManager.class, method = "onModifyDamage")
    public static class FlatPatch
    {
        public static float Postfix(float __result, AbstractCard card, AbstractMonster mo)
        {
            for (AbstractCard c : Wiz.adp().hand.group)
            {
                for (AbstractCardModifier mod : CardModifierManager.modifiers(c))
                {
                    if (mod instanceof ModifyDamageOtherInterface) __result = ((ModifyDamageOtherInterface) mod).modifyDamageOther(__result, card, mo);
                }
            }

            return __result;
        }
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onModifyDamageFinal")
    public static class PercentPatch
    {
        public static float Postfix(float __result, AbstractCard card, AbstractMonster mo)
        {
            for (AbstractCard c : Wiz.adp().hand.group)
            {
                for (AbstractCardModifier mod : CardModifierManager.modifiers(c))
                {
                    if (mod instanceof ModifyDamageOtherInterface) __result = ((ModifyDamageOtherInterface) mod).modifyDamageOtherFinal(__result, card, mo);
                }
            }

            return __result;
        }
    }
}
