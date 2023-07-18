package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ModifyDamageOtherCardmod
    extends AbstractCardModifier
    implements ModifyDamageOtherInterface
{
    int flatMod = 0;
    float percentMod = 1f;

    public ModifyDamageOtherCardmod(int flat, float percent)
    {
        flatMod = flat;
        percentMod = percent;
    }
    public ModifyDamageOtherCardmod(int flat)
    {
        flatMod = flat;
    }
    public ModifyDamageOtherCardmod(float percent)
    {
        percentMod = percent;
    }

    @Override
    public float modifyDamageOther(float damage, AbstractCard card, AbstractMonster mo) {
        return damage+flatMod;
    }


    @Override
    public float modifyDamageOtherFinal(float damage, AbstractCard card, AbstractMonster mo) {
        return damage*percentMod;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ModifyDamageOtherCardmod(flatMod, percentMod);
    }
}
