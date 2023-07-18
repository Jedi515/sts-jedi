package jedi.cardMods;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface ModifyDamageOtherInterface
{
    default float modifyDamageOther(float damage, AbstractCard card, AbstractMonster mo){return damage;}
    default float modifyDamageOtherFinal(float damage, AbstractCard card, AbstractMonster mo){return damage;}
}
