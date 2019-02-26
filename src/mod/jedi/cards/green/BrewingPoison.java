package mod.jedi.cards.green;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import mod.jedi.cards.CustomJediCard;

public class BrewingPoison
    extends CustomJediCard
{
    public static final String ID = makeCardId("brewingpoison");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public BrewingPoison()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondMN = this.baseSecondMN = 3;
        this.exhaust = true;
    }

    @Override
    public void triggerOnManualDiscard()
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("POWER_POISON"));
        upgradeMagicNumber(magicNumber);
    }

    @Override
    public void triggerWhenDrawn()
    {
        superFlash();
        upgradeMagicNumber(secondMN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeSecondMN(1);
            this.upgradeBaseCost(1);
            this.initializeDescription();
        }
    }
}
