package mod.jedi.cards.green;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import mod.jedi.cards.AbstractCollectorCard;

public class CollectorVenom
    extends AbstractCollectorCard
{
    public static final String ID = "jedi:collectorvenom";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public CollectorVenom()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        this.secondMN = this.baseSecondMN = 2; //Increment
        this.magicNumber = this.baseMagicNumber = this.misc = 2; //Application
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeSecondMN(1);
            this.upgradeName();
        }
    }

    public void applyPowers() {
        this.baseMagicNumber = this.magicNumber = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    public boolean onAddedToMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (this.cardID.equals(card.cardID) && card != this)
            {
                CollectorVenom venomCard = (CollectorVenom) card;
                card.misc += ((this.baseMagicNumber >= card.magicNumber && this.magicNumber >= venomCard.secondMN)  ? this.baseMagicNumber : venomCard.secondMN);
                card.magicNumber = card.baseMagicNumber = card.misc;
                card.initializeDescription();
                AbstractCollectorCard showCard = (AbstractCollectorCard) card.makeSameInstanceOf();
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(showCard));
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect(Settings.WIDTH/2, Settings.HEIGHT/2));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ReplaceCardWithCollector() {
        {
            if (this.misc < 5)
            {
                threshold = 20;
            }

            if (this.misc >= 5)
            {
                threshold = 35;
            }

            if (this.misc >= 12)
            {
                threshold = 40;
            }

            if (this.misc >= 18)
            {
                threshold = 60;
            }

            return AbstractDungeon.cardRng.random(0,99) < threshold;
        }
    }
}
