package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.OddMushroom;
import jedi.relics.PaperLyon;

public class PaperLyonPatch
{
    @SpirePatch(clz = VulnerablePower.class, method = "atDamageReceive")
    public static class PaperLyonVulnerablePatch
    {
        public static SpireReturn Prefix(VulnerablePower __instance, float damage, DamageInfo.DamageType type)
        {
            AbstractPlayer p = AbstractDungeon.player;
            if (__instance.owner == p && p.hasRelic(PaperLyon.ID))
            {
                if (p.hasRelic(OddMushroom.ID))
                {
                    return SpireReturn.Return(damage * 1.5F);
                }
                return SpireReturn.Return(damage * 1.75F);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = WeakPower.class, method = "atDamageGive")
        public static class PaperLyonWeakPatch
        {
            public static SpireReturn Prefix(WeakPower __instance, float damage, DamageInfo.DamageType type)
            {
                AbstractPlayer p = AbstractDungeon.player;
                if (__instance.owner == p && p.hasRelic(PaperLyon.ID))
                {
                    return SpireReturn.Return(damage * 0.6F);
                }
                return SpireReturn.Continue();
            }
        }
}
