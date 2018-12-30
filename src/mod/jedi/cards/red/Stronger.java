package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DemonFormPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Stronger
    extends CustomCard
{
    public static final String ID = "jedi:stronger";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_NAME = "Makes us ";
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_power.png";


    public Stronger()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.SPECIAL, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 4;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DemonFormPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.name = UPGRADE_NAME+NAME;
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public CustomCard makeCopy()
    {
        return new Stronger();
    }

}
