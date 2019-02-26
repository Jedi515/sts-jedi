package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class RejectiveToxinAction
    extends AbstractGameAction
{
    private AbstractMonster m;
    private int poisonToApply;
    private boolean upgraded;
    public static String ID = "jedi:RejectiveToxinAction";
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public RejectiveToxinAction(AbstractMonster m, int magicNumber, boolean upgraded)
    {
        this.m = m;
        this.poisonToApply = magicNumber;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (!this.upgraded)
        {
            if (this.duration == Settings.ACTION_DUR_XFAST)
            {
                int handSize = p.hand.size();
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, handSize, false));
                for (int i = 0; i < handSize; i++)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.poisonToApply), this.poisonToApply));
                }
                this.isDone = true;
            }
        }
        else
        {
            if (this.duration == Settings.ACTION_DUR_XFAST)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                this.tickDuration();
            }
            else
            {
                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
                {
                    if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty())
                    {
                        for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                        {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.poisonToApply), this.poisonToApply));
                            AbstractDungeon.player.hand.moveToDiscardPile(c);
                            GameActionManager.incrementDiscard(false);
                            c.triggerOnManualDiscard();
                        }
                    }
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                }
                this.tickDuration();
            }
        }
    }
}
