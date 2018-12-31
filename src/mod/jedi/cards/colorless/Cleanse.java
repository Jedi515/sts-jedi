package mod.jedi.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cleanse
extends CustomCard
{

    public static final String ID = "jedi:cleanse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public Cleanse()
    {
        super(ID, NAME, "resources/jedi/images/cards/jedi_beta.png", 1, DESCRIPTION, CardType.SKILL, AbstractCard.CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new Cleanse();
    }
}
