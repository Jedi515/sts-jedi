package jedi.cards.green;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import jedi.cards.CustomJediCard;

public class UnstableFumes
    extends CustomJediCard
{
    public static final String ID = makeCardId("unstablefumes");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public UnstableFumes()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondMN = this.baseSecondMN = 4;
    }

    @Override
    public void triggerOnManualDiscard()
    {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.secondMN), this.secondMN));
        }
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
            this.upgradeMagicNumber(2);
            this.upgradeSecondMN(2);
            this.initializeDescription();
        }
    }
}
