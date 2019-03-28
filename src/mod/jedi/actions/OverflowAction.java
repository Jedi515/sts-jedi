package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class OverflowAction
    extends AbstractGameAction
{
    private boolean upgraded;
    private float startingDuration;
    private int magicNumber;

    public OverflowAction(boolean upgraded, int magicNumber)
    {
        this.magicNumber = magicNumber;
        this.actionType = ActionType.SPECIAL;
        this.upgraded = upgraded;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    @Override
    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            boolean triggered = true;
            for (AbstractOrb o : AbstractDungeon.player.orbs)
            {
                if (o instanceof EmptyOrbSlot)
                {
                    triggered = false;
                }
            }

            if (triggered)
            {
                AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.magicNumber));
            }
            else
            {
                if (this.upgraded)
                {
                    for (int i = 0; i < this.magicNumber; i++)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(AbstractOrb.getRandomOrb(true)));
                    }
                }
            }

            this.isDone = true;
        }
    }
}
