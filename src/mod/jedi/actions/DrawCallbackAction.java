package mod.jedi.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DrawCallbackAction
    extends AbstractGameAction
{
    private int count;
    private Predicate<AbstractCard> filter;
    private Consumer<ArrayList<AbstractCard>> callback;

    /**
     * @param count - how many cards to draw
     * @param filter - what cards to draw. For example, if you want to only draw attacks, it would look like {card -> card.type == CardType.ATTACK}
     * @param callback - what to do with cards drawn.
     *                 For example, if you want to lose 1 hp and upgrade each card drawn, it would look like
     *                 list -> {
     *                            addToBot(
     *                            new LoseHPAction(player, player, list.size());
     *                            list.forEach(c -> c.upgrade());
     *                         )}
    */

    public DrawCallbackAction(int count, Predicate<AbstractCard> filter, Consumer<ArrayList<AbstractCard>> callback)
    {
        this.count = count;
        this.filter = filter;
        this.callback = callback;
    }

    public DrawCallbackAction(int count, Predicate<AbstractCard> filter)
    {
        this(count, filter, null);
    }

    public DrawCallbackAction(Predicate<AbstractCard> filter)
    {
        this(1, filter, null);
    }

    public DrawCallbackAction(Predicate<AbstractCard> filter, Consumer<ArrayList<AbstractCard>> callback)
    {
        this(1, filter, callback);
    }

    @Override
    public void update()
    {
        CardGroup draw = AbstractDungeon.player.drawPile;
        count = Math.min(count, BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size());
        if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID) || draw.group.stream().noneMatch(filter) || count == 0)
        {
            isDone = true;
            return;
        }

        int actualCount = Math.min((int) draw.group.stream().filter(filter).count(), count);

        if (!draw.group.stream().allMatch(filter))
        {
            ArrayList<AbstractCard> blacklist = new ArrayList<>();
            for (int i = 0; i < actualCount; ++i)
            {
                for (AbstractCard c : draw.group)
                {
                    if (filter.test(c) && !blacklist.contains(c))
                    {
                        blacklist.add(c);
                        draw.removeCard(c);
                        draw.addToTop(c);
                        break;
                    }
                }
            }
        }

        addToTop(new DrawCardAction(actualCount, callback != null ? (new AbstractGameAction()
        {
            @Override
            public void update()
            {
                callback.accept(DrawCardAction.drawnCards);
                isDone = true;
            }
        }) : null));
        isDone = true;
    }
}
