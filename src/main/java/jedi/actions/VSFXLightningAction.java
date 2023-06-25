package jedi.actions;

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
    private boolean playSound;

    public VSFXLightningAction(AbstractCreature creature)
    {
        this(creature, true);
    }

    public VSFXLightningAction(AbstractCreature creature, boolean playSound)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.c = creature;
        this.playSound = playSound;
    }

    @Override
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.c != null)
            {
//                c.damageFlash = true;
//                c.damageFlashFrames = 4;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(c.hb.cX, c.hb.cY, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.effectList.add(new LightningEffect(c.drawX, c.drawY));
            }
            else
            {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                {
                    if (!mo.isDeadOrEscaped())
                    {
//                        mo.damageFlash = true;
//                        mo.damageFlashFrames = 4;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(mo.hb.cX, mo.hb.cY, AbstractGameAction.AttackEffect.NONE));
                        AbstractDungeon.effectList.add(new LightningEffect(mo.drawX, mo.drawY));
                    }
                }
            }
            if (this.playSound)
            {
                CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE");
            }
        }
        this.isDone = true;
    }
}