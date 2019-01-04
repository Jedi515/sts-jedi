package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.powers.MeditationPower;

public class Meditation
    extends CustomCard
{
    public static final String ID = "jedi:meditation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_power.png";

    public Meditation()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MeditationPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Meditation();
    }
}
