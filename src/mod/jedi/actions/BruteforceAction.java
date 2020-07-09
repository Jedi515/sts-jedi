package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class BruteforceAction
    extends AbstractGameAction
{
    private AbstractMonster m;
    private int timesToEvoke;

    public BruteforceAction(AbstractMonster target, int timesToEvoke)
    {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_FAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        }

        this.duration = this.startDuration;
        this.actionType = ActionType.DAMAGE;

        this.m = target;
        this.timesToEvoke = timesToEvoke;
    }

    @Override
    public void update()
    {
        if (this.duration == this.startDuration)
        {
            if (AbstractDungeon.player.orbs.stream().noneMatch(o -> o instanceof Lightning))
            {
                isDone = true;
                return;
            }
            DamageInfo info = evokeInfo();
            if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() && !m.isDeadOrEscaped() && !m.isDead)
            {
                AbstractCreature mo = AbstractDungeon.getRandomMonster();

                if (this.m == mo)
                {
                    this.timesToEvoke--;
                }

                if (mo != null && !mo.isDeadOrEscaped() && !mo.isDead)
                {
                    float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
                    if (Settings.FAST_MODE)
                    {
                        speedTime = 0.0F;
                    }

                    info.output = AbstractOrb.applyLockOn(mo, info.base);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, info, AbstractGameAction.AttackEffect.NONE, true));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), speedTime));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
                }
            }
            else
            {
                this.timesToEvoke = 0;
            }

            if (this.timesToEvoke == 0)
            {
                for (AbstractOrb o : AbstractDungeon.player.orbs)
                {
                    if (o instanceof Lightning)
                    {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbAction(o));
                        this.isDone = true;
                        break;
                    }
                }
                this.isDone = true;
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new BruteforceAction(this.m, this.timesToEvoke));
            }
        }
        this.isDone = true;
    }


    private DamageInfo evokeInfo()
    {
        return new DamageInfo(AbstractDungeon.player, evokeAmt(), DamageInfo.DamageType.THORNS);
    }

    private int evokeAmt()
    {
        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof Lightning)
            {
                return o.evokeAmount;
            }
        }
        return 0;
    }
}
