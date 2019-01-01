package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class EvokeWithoutRemovingSpecificOrbAction
    extends AbstractGameAction
{
    private int orbCount;
    private int position;

    public EvokeWithoutRemovingSpecificOrbAction(int position, int amount) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_FAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        }

        this.position = position;
        this.duration = this.startDuration;
        this.orbCount = amount;
        this.actionType = ActionType.DAMAGE;
    }


    @Override
    public void update()
    {
        if (this.duration == this.startDuration) {
            for(int i = 0; i < this.orbCount; ++i) {

                if (!AbstractDungeon.player.orbs.isEmpty() && !(AbstractDungeon.player.orbs.get(position) instanceof EmptyOrbSlot)) {
                    ((AbstractOrb)AbstractDungeon.player.orbs.get(position)).onEvoke();
                }

            }
        }

        this.tickDuration();
    }
}
