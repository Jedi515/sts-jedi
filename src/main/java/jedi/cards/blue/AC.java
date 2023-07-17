package jedi.cards.blue;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.util.Wiz;

public class AC
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(AC.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public AC() {
        super(ID, NAME, 2, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 10;
        cardsToPreview = new DC();
    }

    @Override
    public void upp() {
        upgradeBlock(4);
        cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.doBlk(block);
        Wiz.makeInHand(cardsToPreview.makeStatEquivalentCopy());
    }
}
