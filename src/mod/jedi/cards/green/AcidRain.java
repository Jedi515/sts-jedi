package mod.jedi.cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import mod.jedi.cards.CustomJediCard;

import static mod.jedi.cards.CustomJediCard.makeCardId;

public class AcidRain
    extends CustomJediCard
{
    public static final String ID = makeCardId("acidrain");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public AcidRain()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void didDiscard()
    {
        updateDescription();
    }

    public void triggerWhenDrawn()
    {
        super.triggerWhenDrawn();
        updateDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    private void updateDescription()
    {
        int DiscardedThisTurn = GameActionManager.totalDiscardedThisTurn;
        if (DiscardedThisTurn == 1)
        {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + DiscardedThisTurn + EXTENDED_DESCRIPTION[1];
        }
        else
        {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + DiscardedThisTurn + EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (int i = 0; i < GameActionManager.totalDiscardedThisTurn; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}
