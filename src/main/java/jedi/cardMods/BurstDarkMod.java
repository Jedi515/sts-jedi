package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurstDarkMod
    extends AbstractCardModifier
{
    public int amount;

    public BurstDarkMod(int amt)
    {
        amount = amt;
    }

    @Override
    public boolean isInherent(AbstractCard card)
    {
        return true;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card)
    {
        if (!card.purgeOnUse) amount = 0;
        return false;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target)
    {
        return damage + amount;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new BurstDarkMod(amount);
    }
}
