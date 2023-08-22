package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.patches.JediEnums;

public class Entomb
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(Entomb.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;

    public Entomb()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        tags.add(JediEnums.FROST_CARD);
        setMN(2);
        setSecondMN(1);
        setBlock(15);
        cardsToPreview = new FlashFreeze();
    }

    @Override
    protected void upp()
    {
        upgradeBlock(3);
        upgradeSecondMN(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
        addToBot(new MakeTempCardInHandAction(cardsToPreview.makeSameInstanceOf(), secondMN));
    }
}
