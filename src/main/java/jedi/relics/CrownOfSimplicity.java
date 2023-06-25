package jedi.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrownOfSimplicity
    extends AbstractJediRelic
{
    public static final String ID = "jedi:crownofsimplicity";
    private boolean oncePerTurn;

    public CrownOfSimplicity()
    {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    public boolean canSpawn()
    {
        return CardCrawlGame.isInARun() &&
                AbstractDungeon.player.masterDeck.group.stream().anyMatch(c->c.rarity == AbstractCard.CardRarity.BASIC);
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ((   oncePerTurn &&
                (
                    card.rarity == AbstractCard.CardRarity.BASIC ||
                    card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) ||
                    card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) &&
                    !card.purgeOnUse &&
                    !card.freeToPlayOnce)
                )
        {
            flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }
            GameActionManager.queueExtraCard(card, m);
            oncePerTurn = false;
        }
    }

    public void atTurnStart()
    {
        oncePerTurn = true;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}
