package jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import jedi.cards.CustomJediCard;

public class Faster
        extends CustomJediCard
{
    public static final String ID = "jedi:faster";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 2;


    public Faster()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.isEthereal = true;
        cardsToPreview = new Stronger();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        Stronger stronger = new Stronger();
        if (this.upgraded)
        {
            stronger.upgrade();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber), this.magicNumber));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new SlowPower(mo, 1), 1));
            }
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, 1), 1));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(stronger));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            cardsToPreview.upgrade();
            this.upgradeName();
            this.name = EXTENDED_DESCRIPTION[0];
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.target = CardTarget.ALL_ENEMY;
        }
    }

    public CustomCard makeCopy()
    {
        return new Faster();
    }


}
