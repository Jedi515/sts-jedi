package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

public class DeepenNeedlesMod extends AbstractCardModifier
{
    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target)
    {
        if (target == null) return damage;
        AbstractPower marks = target.getPower(MarkPower.POWER_ID);
        if (marks != null) damage += marks.amount;
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
        return new DeepenNeedlesMod();
    }
}
