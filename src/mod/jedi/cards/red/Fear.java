package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fear
    extends CustomCard
{
    public static final String ID = "jedi:fear";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/images/cards/jedi_beta.png";


    public Fear()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Anger(), this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public CustomCard makeCopy()
    {
        return new Fear();
    }
}
