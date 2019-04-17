package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.LockOnPower;
import mod.jedi.relics.PaperFaux;

public class PaperLockOnPatch
{

    @SpirePatch(clz= AbstractOrb.class, method = "applyLockOn")
    public static class SingleTarget {
        public static SpireReturn<Integer> Prefix(AbstractCreature target, int dmg) {
            if (AbstractDungeon.player.hasRelic(PaperFaux.ID) && target.hasPower(LockOnPower.POWER_ID)) {
                return SpireReturn.Return((int) (dmg * 1.75F));
            } else {
                return SpireReturn.Continue();
            }
        }
    }
    @SpirePatch(
            clz = DamageInfo.class,
            method = "createDamageMatrix",
            paramtypez = {int.class, boolean.class, boolean.class   })
    public static class ElectroTarget
    {
        @SpirePostfixPatch
        public static int[] Postfix(int[] __result, int baseDamage, boolean isPureDamage, boolean isOrbDamage)
        {
            if (!AbstractDungeon.player.hasRelic(PaperFaux.ID))
            {
                return __result;
            }
            int[] retValMultiPatch = new int[AbstractDungeon.getMonsters().monsters.size()];

            for(int i = 0; i < retValMultiPatch.length; ++i) {
                DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);
                if (isOrbDamage && ((AbstractMonster)AbstractDungeon.getMonsters().monsters.get(i)).hasPower(LockOnPower.POWER_ID))
                {
                    if (AbstractDungeon.player.hasRelic(PaperFaux.ID))
                    {
                        info.output = (int)((float)info.base * 1.75F);
                    }
                    else
                    {
                        info.output = (int) ((float) info.base * 1.5F);
                    }
                }

                retValMultiPatch[i] = info.output;
            }

            return retValMultiPatch;
        }
    }
}
