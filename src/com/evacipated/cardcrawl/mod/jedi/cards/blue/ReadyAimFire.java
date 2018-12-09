package com.evacipated.cardcrawl.mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.LockOnPower;

import java.util.Iterator;

public class ReadyAimFire
    extends CustomCard
{
    public static final String ID = "jedi:readyaimfire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public ReadyAimFire()
    {
        super(ID, NAME, "resources/images/cards/jedi_beta_attack.png", COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var3.hasNext())
        {
            AbstractMonster mo = (AbstractMonster) var3.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new LockOnPower(mo, magicNumber), magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());

    }

    @Override
    public void upgrade()
    {
        this.upgradeName();
        this.upgradeBaseCost(0);
        this.upgradeMagicNumber(1);
    }
    public AbstractCard makeCopy()
    {
        return new ReadyAimFire();
    }

}
