package jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cards.CustomJediCard;
import jedi.powers.UpgradeOnDraw;

public class Better
    extends CustomJediCard
{
    public static final String ID = "jedi:better";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 1;


    public Better()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.SPECIAL, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
        cardsToPreview = new Faster();
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        Faster faster = new Faster();

        AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
        if (this.upgraded)
        {
            faster.upgrade();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new UpgradeOnDraw(p, 1), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(faster));
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
        }
    }
}
