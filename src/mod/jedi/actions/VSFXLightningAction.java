package mod.jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class VSFXLightningAction
    extends AbstractGameAction {
    private AbstractMonster m;

    public VSFXLightningAction(AbstractMonster monster)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.m = monster;
    }

    public VSFXLightningAction(boolean isMultihit)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.m = null;
    }

    @Override
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (m != null)
            {
                m.damageFlash = true;
                m.damageFlashFrames = 4;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.effectList.add(new LightningEffect(m.drawX, m.drawY));
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
            CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        }
        this.isDone = true;
    }
}