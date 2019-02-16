package mod.jedi.actions;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import mod.jedi.cards.red.UnlimitedPower;

import java.util.ArrayList;

public class SmithInCombatAction
    extends AbstractGameAction
{
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList();
    public static String ID = "jedi:SmithInCombatAction";
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public SmithInCombatAction()
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        //Checks on if cards in hand can have their master deck equivalent upgraded

        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : p.hand.group) {
                AbstractCard masterC = StSLib.getMasterDeckEquivalent(c);
                if ((masterC == null) || (!masterC.canUpgrade()) || (c instanceof UnlimitedPower)) {
                    this.cannotUpgrade.add(c);
                }
            }
//      if no cards in hand can have their master deck version upgraded
            if (this.cannotUpgrade.size() == p.hand.group.size()) {
                this.isDone = true;
                return;
            }
//      if only one can...
            if (p.hand.group.size() - this.cannotUpgrade.size() == 1) {
                for (AbstractCard c : p.hand.group) {
                    AbstractCard masterC = StSLib.getMasterDeckEquivalent(c);
                    if (masterC != null && masterC.canUpgrade()) {
                        masterC.upgrade();
                        if (c.canUpgrade()) {
                            c.upgrade();
                        }
                        c.superFlash();

                        this.isDone = true;
                        return;
                    }
                }
            }
//      and now if there's more than one
            p.hand.group.removeAll(this.cannotUpgrade);
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, true);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                if (c.canUpgrade())
                {
                    c.upgrade();
                }
                p.hand.addToTop(c);
                c.superFlash();
                AbstractCard cardToSmith = StSLib.getMasterDeckEquivalent(c);
                if (cardToSmith != null && cardToSmith.canUpgrade())
                {
                    p.masterDeck.getSpecificCard(cardToSmith).upgrade();
                }
            }
            for (AbstractCard c : this.cannotUpgrade)
            {
                p.hand.addToTop(c);
            }
            p.hand.refreshHandLayout();
        }

        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        this.isDone = true;
    }
}
