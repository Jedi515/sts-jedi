package com.evacipated.cardcrawl.mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sharpshooter
    extends CustomCard
{

    public static final String ID = "jedi:sharpshooter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public Sharpshooter()
    {
        super(ID, NAME, "resources/images/cards/jedi_beta_attack.png", COST,DESCRIPTION,CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Sharpshooter();
    }
}
