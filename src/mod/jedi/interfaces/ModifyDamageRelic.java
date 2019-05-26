package mod.jedi.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface ModifyDamageRelic
{
    default float calculateCardDamageRelic(AbstractCard card, AbstractMonster target, float damage)
    {
        return damage;
    }

    default float calculateCardDamageFinalRelic(AbstractCard card, AbstractMonster target, float damage)
    {
        return damage;
    }

    default float applyPowersRelic(AbstractCard card, float damage)
    {
        return damage;
    }

    default float applyPowersFinalRelic(AbstractCard card, float damage)
    {
        return damage;
    }
}
