package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import jedi.relics.IronBlood;

@SpirePatch(clz = DamageInfo.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, int.class, DamageInfo.DamageType.class})
public class IronBloodPatch
{
    public static void Postfix(DamageInfo __instance, AbstractCreature damageSource, int base, DamageInfo.DamageType type)
    {
        if (CardCrawlGame.isInARun())
        {
            if (AbstractDungeon.player.hasRelic(IronBlood.ID) && type == DamageInfo.DamageType.HP_LOSS && damageSource == AbstractDungeon.player)
            {
                __instance.type = DamageInfo.DamageType.THORNS;
            }
        }
    }
}
