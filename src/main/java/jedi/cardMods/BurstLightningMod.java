package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;
import jedi.util.Wiz;
import jedi.jedi;

public class BurstLightningMod extends AbstractCardModifier
{
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(jedi.makeID(BurstLightningMod.class.getSimpleName())).TEXT;
    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + Wiz.getLogicalPowerAmount(AbstractDungeon.player, FocusPower.POWER_ID);
    }
    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (target == null) return damage;
        LockOnPower pow = (LockOnPower) target.getPower(LockOnPower.POWER_ID);
        if (pow == null)    return damage;

        int retVal = AbstractOrb.applyLockOn(target, (int)damage);

        float leftover = (damage - (int) damage) * ((float)retVal/damage);
        return retVal + leftover;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(TEXT[0], rawDescription);
    }
    @Override
    public AbstractCardModifier makeCopy()
    {
        return new BurstLightningMod();
    }
}
