package jedi.cards.purple;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.DrawCallbackShuffleAction;
import jedi.cardMods.ExhaustJediMod;
import jedi.cards.CustomJediCard;
import jedi.jedi;

public class PromisingFuture
    extends CustomJediCard
{
    public static String ID = jedi.makeID(PromisingFuture.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public PromisingFuture()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.NONE);
        setMN(3);
        setSecondMN(3);
        exhaust = true;
    }


    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(3, new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                DrawCardAction.drawnCards.forEach(c -> CardModifierManager.addModifier(c, new ExhaustJediMod()));
            }
        }));
//        addToBot(new DrawCallbackShuffleAction(secondMN, c -> true, list -> list.forEach(c -> CardModifierManager.addModifier(c, new ExhaustJediMod()))));
    }
}
