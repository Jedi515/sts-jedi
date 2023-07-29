package jedi.cards.curses;

import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.PlayRandomCardInHandAction;
import jedi.cards.CustomJediCard;
import jedi.util.Wiz;

public class Missplay
    extends CustomJediCard
{
    public static final String ID = jedi.jedi.makeID(Missplay.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = -2;

    public Missplay()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }

    @Override
    public void upp()
    {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {

    }

    @Override
    public boolean hasEnoughEnergy() {
        cantUseMessage = CardCrawlGame.languagePack.getCardStrings(Reflex.ID).EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new PlayRandomCardInHandAction(Wiz.getEnemies().get(AbstractDungeon.cardRandomRng.random(Wiz.getEnemies().size()-1))));
    }
}
