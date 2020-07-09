package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import mod.jedi.cards.green.CollectorVenom;
import mod.jedi.cards.red.CollectorStrike;

@SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
public class RunHistoryCollectorPatch
{
    public static class bruh
    {
        public static AbstractCard Postfix(AbstractCard __result, String key, int upgradeTime, int misc)
        {
            if (misc != 0)
            {
                if (__result.cardID.equals(CollectorStrike.ID))
                {
                    __result.baseDamage = __result.damage = misc;
                    __result.initializeDescription();
                }
                if (__result.cardID.equals(CollectorVenom.ID))
                {
                    __result.magicNumber = __result.baseMagicNumber = misc;
                    __result.initializeDescription();
                }
            }
            return __result;
        }
    }
}