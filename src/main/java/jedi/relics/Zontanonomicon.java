package jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import jedi.util.TextureLoader;

public class Zontanonomicon
    extends AbstractJediRelic
{
    public static final String ID = "jedi:zontanonomicon";
    private boolean activated = true;

    public Zontanonomicon() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL && (card.costForTurn >= 2 || card.cost == -1 && card.energyOnUse >= 2) && this.activated) {
            activated = false;
            flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }
            GameActionManager.queueExtraCard(card, m);
        }

    }

    public void atTurnStart() {
        activated = true;
    }

    public boolean checkTrigger() {
        return activated;
    }

    public AbstractRelic makeCopy()
    {
        return new Zontanonomicon();
    }

}
