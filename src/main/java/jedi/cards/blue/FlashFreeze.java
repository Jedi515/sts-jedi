package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.util.Wiz;

public class FlashFreeze
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(FlashFreeze.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;

    public FlashFreeze()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.SPECIAL, CardTarget.NONE);
        setMN(1);
        exhaust = true;
        selfRetain = true;
    }
    @Override
    protected void upp()
    {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        for (int i=0;i<magicNumber;i++) addToBot(new ChannelAction(new Frost()));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                Wiz.adp().orbs.stream().filter(o -> o instanceof Frost).forEach(AbstractOrb::onEndOfTurn);
            }
        });
    }
}
