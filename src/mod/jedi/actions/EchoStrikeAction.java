package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.cards.red.EchoStrike;

public class EchoStrikeAction
    extends AbstractGameAction
{
    AbstractMonster actionTarget;
    AbstractCard cardToSkip;

    public EchoStrikeAction(AbstractMonster target, AbstractCard skipCard)
    {
        this.duration = Settings.ACTION_DUR_XFAST;
        actionTarget = target;
        cardToSkip = skipCard;
    }

    @Override
    public void update()
    {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat)
        {
            if ((c instanceof EchoStrike) && !(c == cardToSkip) && (!c.purgeOnUse))
            {
                AbstractCard echoStrike = c.makeSameInstanceOf();

                echoStrike.freeToPlayOnce = true;
                echoStrike.applyPowers();
                echoStrike.calculateCardDamage(actionTarget);
                echoStrike.purgeOnUse = true;
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(echoStrike, actionTarget));
            }
        }
        this.isDone = true;
    }
}
