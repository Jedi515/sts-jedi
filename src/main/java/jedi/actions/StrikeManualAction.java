package jedi.actions;

import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class StrikeManualAction extends CodexAction {
    @SpireOverride
    public ArrayList<AbstractCard> generateCardChoices()
    {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        AbstractCard tmp = jedi.jedi.returnTrulyRandomStrike();
        while(retVal.size() < 3)
        {
            if (tmp.rarity != AbstractCard.CardRarity.BASIC && !dupeCheck(retVal, tmp.cardID))
            {
                retVal.add(tmp.makeCopy());
            }
            tmp = jedi.jedi.returnTrulyRandomStrike();
        }
        return retVal;
    }

    private static boolean dupeCheck(ArrayList<AbstractCard> list, String cardID)
    {
        return list.stream().anyMatch(c -> c.cardID.equals(cardID));
    }
}
