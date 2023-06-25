package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import jedi.cards.AbstractCollectorCard;
import jedi.interfaces.CardAddedToDeck;

import java.util.ArrayList;

public class CollectorCardPatch
{
    @SpirePatch(clz = SoulGroup.class, method = "obtain")
    public static class ShowCardAndObtainEffectPatch
    {
        public static SpireReturn Prefix(SoulGroup __instance, AbstractCard card, boolean obtainCard)
        {
            if (card instanceof CardAddedToDeck)
            {
                boolean skipAddingToDeck = ((CardAddedToDeck) card).onAddedToMasterDeck();
                if (skipAddingToDeck)
                {
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=AbstractDungeon.class,
            method="getRewardCards"
    )
    public static class AddCardReward {
        public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result)
        {
            boolean AlreadyReplaced;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            {
                if (c instanceof AbstractCollectorCard)
                {
                    AlreadyReplaced = false;
                    for (int i=0; i<__result.size(); ++i)
                    {
                        if (!AlreadyReplaced && ((AbstractCollectorCard) c).ReplaceCardWithCollector())
                        {
                            try
                            {
                                AbstractCollectorCard cardToReplace = (AbstractCollectorCard) c.getClass().newInstance();
                                if (c.upgraded)
                                {
                                    cardToReplace.upgrade();
                                }
                                __result.set(i, cardToReplace);
                                AlreadyReplaced = true;
                            }
                            catch (InstantiationException | IllegalAccessException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return __result;
        }
    }
}
