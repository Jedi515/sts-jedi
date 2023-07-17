package jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import jedi.util.Wiz;

public class EvokeWithoutRemovingSpecificOrbAction
    extends AbstractGameAction
{
    private int orbCount;
    private AbstractOrb orb;

    public EvokeWithoutRemovingSpecificOrbAction(AbstractOrb orb, int amount) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_FAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        }

        this.duration = this.startDuration;
        this.actionType = ActionType.DAMAGE;
        this.orb = orb;
        this.orbCount = amount;
    }

    public static AbstractOrb getRightmostOrb(String orbID)
    {
        for (AbstractOrb o : Wiz.p().orbs)
        {
            if (orbID.equals(o.ID))
            {
                return o;
            }
        }
        return null;
    }


    @Override
    public void update()
    {
        if (this.duration == this.startDuration)
        {
            for(int i = 0; i < this.orbCount; ++i)
            {
                orb.onEvoke();
            }
        }

        this.tickDuration();
    }
}
