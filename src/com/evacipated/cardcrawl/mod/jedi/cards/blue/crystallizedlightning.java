package com.evacipated.cardcrawl.mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ElectroPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;


public class crystallizedlightning
        extends CustomCard
{
    public static final String ID = "jedi:crystallizedlightning";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public crystallizedlightning()
    {
        super(ID, NAME, "resources/images/cards/jedi_beta_attack.png", 1, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (p.hasPower(ElectroPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(m, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(ElectroPower.POWER_ID)) {
            this.target = AbstractCard.CardTarget.ALL_ENEMY;
            this.isMultiDamage = true;
        }
        else {
            this.target = AbstractCard.CardTarget.ENEMY;
            this.isMultiDamage = false;
        }
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
                this.baseDamage += currentFocus;
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
        if (AbstractDungeon.player.hasPower(ElectroPower.POWER_ID)) {
            this.target = AbstractCard.CardTarget.ALL_ENEMY;
            this.isMultiDamage = true;
        }
        else {
            this.target = AbstractCard.CardTarget.ENEMY;
            this.isMultiDamage = false;
        }
        int baseDamagePlaceholder = this.baseDamage;
        int currentFocus = 0;
        int currentStrength = 0;
        boolean hasFocus = false;
        boolean hasStrength = false;
//        boolean hasLockon = false;
//        ArrayList<AbstractMonster> monArr = AbstractDungeon.getCurrRoom().monsters.monsters;
//        why? because when it turns into multi-hit, it nulls m from this function and gets NPE if it's multihit and there's anyone with lockon
//        int monArrSize = monArr.size();

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
                this.baseDamage += currentFocus;
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
            this.upgradeMagicNumber(1);
            this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new crystallizedlightning();
    }
}
