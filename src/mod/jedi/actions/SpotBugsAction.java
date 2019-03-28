package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class SpotBugsAction
    extends AbstractGameAction
{
    private AbstractMonster target;
    private boolean upgraded;
    private float startingDuration;
    private int magicNumber;

    public SpotBugsAction(AbstractMonster target, boolean upgraded, int magicNumber)
    {
        this.magicNumber = magicNumber;
        this.target = target;
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
            if (target.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                target.intent == AbstractMonster.Intent.BUFF ||
                target.intent == AbstractMonster.Intent.DEFEND_BUFF)
            {
                AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.magicNumber));
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
