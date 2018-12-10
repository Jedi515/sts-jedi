package com.evacipated.cardcrawl.mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class LockNLoad
    extends CustomCard
{
    public static final String ID = "jedi:locknload";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static int CardLightningChannel = 2;
    public static String DESCRIPTION = cardStrings.DESCRIPTION + CardLightningChannel + cardStrings.UPGRADE_DESCRIPTION;


    public LockNLoad()
    {
        super(ID, NAME, "resources/images/cards/jedi_beta.png", 1, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        CardLightningChannel = this.magicNumber+1;
        this.rawDescription = cardStrings.DESCRIPTION + CardLightningChannel + cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LockOnPower(m, this.magicNumber), this.magicNumber));
        for (int i = 0; i <= magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Lightning()));
        }
    }



    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            CardLightningChannel = this.magicNumber + 1;
            this.rawDescription = cardStrings.DESCRIPTION + CardLightningChannel + cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new LockNLoad();
    }
}
