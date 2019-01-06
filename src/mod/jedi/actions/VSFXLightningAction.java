package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class VSFXLightningAction
    extends AbstractGameAction {
    private AbstractCreature c;

    public VSFXLightningAction(AbstractCreature creature)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.c = creature;
    }

    public VSFXLightningAction(boolean isMultihit)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.c = null;
    }

    @Override
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (c != null)
            {
                c.damageFlash = true;
                c.damageFlashFrames = 4;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(c.hb.cX, c.hb.cY, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.effectList.add(new LightningEffect(c.drawX, c.drawY));
            }
            else
            {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                {
                    mo.damageFlash = true;
                    mo.damageFlashFrames = 4;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(mo.hb.cX, mo.hb.cY, AbstractGameAction.AttackEffect.NONE));
                    AbstractDungeon.effectList.add(new LightningEffect(mo.drawX, mo.drawY));
                }
            }
            CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE");
        }
        this.isDone = true;
    }
}