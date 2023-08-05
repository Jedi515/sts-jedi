package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LaserPointer
    extends AbstractJediRelic
{
    public static final String ID = "jedi:laserpointer";


    public LaserPointer() {
        super(ID, RelicTier.COMMON,LandingSound.FLAT, RelicType.BLUE);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(mo, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new LockOnPower(mo, 1), 1));
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new LaserPointer();
    }
}
