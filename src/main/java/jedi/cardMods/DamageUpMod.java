package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageUpMod
        extends AbstractCardModifier
{
    int flatMod = 0;
    float percentMod = 1f;

    public DamageUpMod(int flat, float percent)
    {
        flatMod = flat;
        percentMod = percent;
    }
    public DamageUpMod(int flat)
    {
        flatMod = flat;
    }
    public DamageUpMod(float percent)
    {
        percentMod = percent;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage+flatMod;
    }

    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage*percentMod;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamageUpMod(flatMod, percentMod);
    }
}
