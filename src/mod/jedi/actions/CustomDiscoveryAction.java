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
    private boolean allowSkip;
    private boolean retrieveCard;
    private int copiesCount;

    public CustomDiscoveryAction(CardGroup group)
    {
        this(group, 3, false);
    }

    public CustomDiscoveryAction(CardGroup group, int number, int copiesCount, boolean allowSkip)
    {
        this.group = group;
        this.copiesCount = copiesCount;
        this.numberOfCards = number;
        this.allowSkip = allowSkip;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public CustomDiscoveryAction(CardGroup group, int number, boolean allowSkip)
    {
        this(group, number, 1, false);
    }

    public CustomDiscoveryAction(CardGroup group, int number)
    {
        this(group, number, false);
    }

    @Override
    public void update()
    {
        if (numberOfCards < 1 || group.size() == 0)
        {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            try {
                CardGroup groupToShow = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (group.size() > numberOfCards)
                {
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
                            groupToShow.addToBottom(tmp.makeStatEquivalentCopy());
                        }
                    }
                }
                else
                {
                    for (AbstractCard c : group.group)
                    {
                        groupToShow.addToBottom(c.makeStatEquivalentCopy());
                    }
                }
                Method discovery = CardRewardScreen.class.getDeclaredMethod("customJediDiscovery", CardGroup.class, boolean.class);
                discovery.setAccessible(true);
                discovery.invoke(AbstractDungeon.cardRewardScreen, groupToShow, allowSkip);
                this.tickDuration();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                this.isDone = true;
                e.printStackTrace();
            }
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    int handSize = AbstractDungeon.player.hand.size();
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    disCard.current_x = -1000.0F * Settings.scale;
                    if (handSize + copiesCount <= BaseMod.MAX_HAND_SIZE)
                    {
                        for (int i = 0; i < copiesCount; i++)
                        {
                            disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            disCard.setCostForTurn(0);
                        }
                    } else {
                        for (int i = handSize; i < BaseMod.MAX_HAND_SIZE; i++)
                        {
                            disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            disCard.setCostForTurn(0);
                        }
                        for (int i = BaseMod.MAX_HAND_SIZE; i < handSize + copiesCount; i++)
                        {
                            disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            disCard.setCostForTurn(0);
                        }
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }
}
