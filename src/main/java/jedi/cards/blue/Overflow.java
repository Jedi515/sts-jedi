package jedi.cards.blue;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.OverflowAction;
import jedi.cards.CustomJediCard;

public class Overflow
        extends CustomJediCard
{
    public static final String ID = "jedi:overflow";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 1;

    public Overflow()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;

        if (this.magicNumber != 1)
        {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new OverflowAction(this.upgraded, this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            if (this.magicNumber == 1)
            {
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
            else
            {
                this.rawDescription = EXTENDED_DESCRIPTION[1];
            }
            initializeDescription();
        }
    }

    @Override
    public void applyPowers()
    {
        if (this.magicNumber != 1)
        {
            if (!this.upgraded)
            {
                this.rawDescription = EXTENDED_DESCRIPTION[0];
            }
            else
            {
                this.rawDescription = EXTENDED_DESCRIPTION[1];
            }
        }
        else
        {
            if (!this.upgraded)
            {
                this.rawDescription = DESCRIPTION;
            }
            else
            {
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
        }
        initializeDescription();
    }
}
