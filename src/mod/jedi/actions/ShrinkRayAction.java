package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShrinkRayAction
    extends AbstractGameAction
{
    public float efficiency;

    public ShrinkRayAction(float efficiency)
    {
        this.duration = this.startDuration = 0.1F;
        this.actionType = ActionType.SPECIAL;
        this.efficiency = efficiency;

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startDuration = 0.0F;
            this.isDone = true;
        }
    }

    @Override
    public void update()
    {

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!(m.type == AbstractMonster.EnemyType.BOSS)) {
                if (m.currentHealth > (int) ((float) m.maxHealth * (this.efficiency)))
                {
                    m.currentHealth -= (int) ((float) m.maxHealth * (this.efficiency));
                    m.healthBarUpdatedEvent();
                }
            }
        }
        this.isDone = true;
    }
}
