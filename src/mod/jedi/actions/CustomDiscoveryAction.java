package mod.jedi.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomDiscoveryAction
    extends AbstractGameAction
{
    private CardGroup group;
    private int numberOfCards;
    private boolean retrieveCard;


    public CustomDiscoveryAction(CardGroup group, int number)
    {
        this.group = group;
        this.numberOfCards = number;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            try {
                CardGroup groupToShow = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                while (groupToShow.size() < numberOfCards)
                {
                    boolean dupe = false;
                    AbstractCard tmp = group.getRandomCard(true);
                    for (AbstractCard c : groupToShow.group)
                    {
                        if (c.cardID.equals(tmp.cardID)) dupe = true;
                    }

                    if (!dupe)
                    {
                        UnlockTracker.markCardAsSeen(tmp.cardID);
                        groupToShow.addToBottom(tmp);
                    }
                }
                Method discovery = CardRewardScreen.class.getDeclaredMethod("customDiscovery", CardGroup.class);
                discovery.setAccessible(true);
                discovery.invoke(AbstractDungeon.cardRewardScreen, groupToShow);
                this.tickDuration();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                this.isDone = true;
                e.printStackTrace();
            }
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    disCard.current_x = -1000.0F * Settings.scale;
                    if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    disCard.setCostForTurn(0);
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }
}
