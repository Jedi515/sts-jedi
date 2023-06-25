package jedi.powers;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jedi.interfaces.onGenerateCardMidcombatInterface;
import jedi.jedi;

public class FirewallPower
    extends AbstractPower
    implements onGenerateCardMidcombatInterface
{
    public static final String POWER_ID = jedi.makeID(FirewallPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FirewallPower(AbstractCreature owner)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.loadRegion("buffer");
        this.type = PowerType.BUFF;
    }

    public void onCreateCard(AbstractCard card)
    {
        if (card.type == AbstractCard.CardType.STATUS)
        {
            CardGroup grp = null;
            AbstractPlayer p = AbstractDungeon.player;
            if (p.discardPile.contains(card)) grp = p.discardPile;
            else if (p.drawPile.contains(card)) grp = p.drawPile;
            else if (p.hand.contains(card)) grp = p.hand;

            if (grp != null) addToBot(new ExhaustSpecificCardAction(card, grp));
        }
    }
}
