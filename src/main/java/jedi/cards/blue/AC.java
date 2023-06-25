package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import jedi.cards.CustomJediCard;
import jedi.jedi;

public class AC
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(AC.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public AC()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.NONE);
        setMN(1);
        cardsToPreview = new DC();
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            cardsToPreview.upgrade();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        for (int i = 0; i < magicNumber; ++i)
            addToBot(new ChannelAction(new Lightning()));
        addToBot(new MakeTempCardInHandAction(cardsToPreview.makeStatEquivalentCopy()));
    }
}
