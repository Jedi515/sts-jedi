package mod.jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

public class BurstLightningMod extends AbstractCardModifier
{
    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target)
    {
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) return damage + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount * card.magicNumber;
        return damage;
    }

    @Override
    public boolean isInherent(AbstractCard card)
    {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new BurstLightningMod();
    }
}
