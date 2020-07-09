package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mod.jedi.cards.CustomJediCard;

public class GatheringStorm
        extends CustomJediCard
{
    public static final String ID = "jedi:gatheringstorm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 3;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public GatheringStorm()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCard.CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat)
        {
            if (!(o instanceof Lightning))
            {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Lightning()));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.magicNumber = baseMagicNumber = (int)AbstractDungeon.actionManager.orbsChanneledThisCombat.stream().filter(o -> !(o instanceof Lightning)).count();

        if (this.baseMagicNumber > 0) {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    public AbstractCard makeCopy()
    {
        return new GatheringStorm();
    }
}
