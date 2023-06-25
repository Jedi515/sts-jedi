/* Hey guess what
* yeah right, also copied from bottled yo-yo.
* */

package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz= AbstractCard.class, method= SpirePatch.CLASS)
public class JediBottleFields
{
    public static SpireField<Boolean> inLeadLinedBottle = new SpireField<>(() -> false);
    public static SpireField<Boolean> inBottledFury = new SpireField<>(() -> false);
//    public static SpireField<Boolean> inFireproofBottle = new SpireField<>(() -> false);

    @SpirePatch(clz=AbstractCard.class, method="makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            inLeadLinedBottle.set(result, inLeadLinedBottle.get(self));
            inBottledFury.set(result, inBottledFury.get(self));
//            inFireproofBottle.set(result, inFireproofBottle.get(self));
            return result;
        }
    }
}
