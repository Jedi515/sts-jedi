package jedi.cards.purple;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.WrathStance;
import jedi.actions.DrawCallbackShuffleAction;
import jedi.cards.CustomJediCard;
import jedi.jedi;

public class Furiosity
    extends CustomJediCard
{
    public static String ID = jedi.makeID(Furiosity.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 2;

    public Furiosity()
    {
        super(ID, NAME, null, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
        setMN(2);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription += UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
        addToBot(new DrawCallbackShuffleAction(magicNumber, c -> c.type == CardType.ATTACK, upgraded ? list ->
        {
            addToBot(new SelectCardsInHandAction(1, EXTENDED_DESCRIPTION[0], list::contains, callbackList -> {callbackList.forEach(c ->
            {
                c.setCostForTurn(0);
            });}));
        }
        : null));
    }
}
