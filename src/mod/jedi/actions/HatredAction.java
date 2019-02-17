package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HatredAction
    extends AbstractGameAction
{
    private DamageInfo info;
    private float startingDuration;

    public HatredAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.BLUNT_HEAVY;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration)
        {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if (c instanceof Anger)
                {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, this.info, AttackEffect.BLUNT_HEAVY));
                }
            }
        }

        this.tickDuration();
    }
}
