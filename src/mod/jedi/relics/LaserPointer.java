package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.util.TextureLoader;

public class LaserPointer
    extends CustomRelic
{
    public static final String ID = "jedi:laserpointer";
    public static final String IMG_PATH = "resources/jedi/images/relics/laserpointer.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);


    public LaserPointer() {
        super(ID, IMG, RelicTier.COMMON,LandingSound.FLAT);
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
