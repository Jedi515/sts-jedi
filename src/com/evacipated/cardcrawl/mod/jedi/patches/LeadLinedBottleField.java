/* Hey guess what
* yeah right, also copied from bottled yo-yo.
* */

package com.evacipated.cardcrawl.mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz= AbstractCard.class, method= SpirePatch.CLASS)
public class LeadLinedBottleField
{
    public static SpireField<Boolean> inLeadLinedBottle = new SpireField<>(() -> false);

    @SpirePatch(clz=AbstractCard.class, method="makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            inLeadLinedBottle.set(result, inLeadLinedBottle.get(self));
            return result;
        }
    }
}
