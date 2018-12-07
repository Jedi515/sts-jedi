package com.evacipated.cardcrawl.mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class crystallizeddark
        extends CustomCard
{
    public static final String ID = "jedi:crystallizeddark";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public crystallizeddark()
    {
        super(ID, NAME, "resources/images/cards/jedi_beta_attack.png", 2, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void applyPowers() {
        int baseDamagePlaceholder = this.baseDamage;
        int currentFocus = 0;
        int currentStrength = 0;
        boolean hasFocus = false;
        boolean hasStrength = false;
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            currentFocus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            hasFocus = true;
        }
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            currentStrength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            hasStrength = true;
        }
        if (hasFocus) {
            if (!hasStrength) {
                this.baseDamage += currentFocus * magicNumber;
                super.applyPowers();
                this.baseDamage = baseDamagePlaceholder;
                this.isDamageModified = baseDamage != damage;
            } else {
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
                super.applyPowers();
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
            }
        } else if (hasStrength) {
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
            super.applyPowers();
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
        } else {
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int baseDamagePlaceholder = this.baseDamage;
        int currentFocus = 0;
        int currentStrength = 0;
        boolean hasFocus = false;
        boolean hasStrength = false;
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            currentFocus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            hasFocus = true;
        }
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            currentStrength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            hasStrength = true;
        }
        if (hasFocus) {
            if (!hasStrength) {
                this.baseDamage += currentFocus * magicNumber;
                super.calculateCardDamage(m);
                this.baseDamage = baseDamagePlaceholder;
                this.isDamageModified = baseDamage != damage;
            } else {
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
                super.calculateCardDamage(m);
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
            }
        } else if (hasStrength) {
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
            super.calculateCardDamage(m);
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
        } else {
            super.calculateCardDamage(m);
        }
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeMagicNumber(2);
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new crystallizeddark();
    }
}
