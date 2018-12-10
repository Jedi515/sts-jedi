package com.evacipated.cardcrawl.mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class BlockOn
    extends CustomCard
{

    public static final String ID = "jedi:blockon";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/images/cards/jedi_beta.png";

    public BlockOn()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if ((mo.intent == AbstractMonster.Intent.ATTACK ||
                mo.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                mo.intent == AbstractMonster.Intent.ATTACK_DEFEND))
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new LockOnPower(mo, magicNumber), magicNumber));
            }
        }
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBlock(3);
            this.upgradeMagicNumber(1);
        }
    }

}
