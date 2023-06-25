package jedi.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.function.Predicate;

public class DiscoveryFilterAction
    extends CustomDiscoveryAction
{
    public DiscoveryFilterAction(Predicate<AbstractCard> filter, int displayNumber, int copiesNumber, boolean allowSkip)
    {
        super(compileGroup(filter), displayNumber, copiesNumber, allowSkip);
    }



    private static CardGroup compileGroup(Predicate<AbstractCard> f)
    {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardLibrary.getAllCards().stream().filter(f).forEach(c -> retVal.addToTop(c.makeCopy()));
        return retVal;
    }

}
