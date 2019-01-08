package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import mod.jedi.util.TextureLoader;

public class Endoplasm
    extends CustomRelic
    implements BetterOnLoseHpRelic
{
    public static final String ID = "jedi:endoplasm";
    public static final String IMG_PATH = "resources/jedi/images/relics/endoplasm.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    public static boolean canTrigger = true;


    public Endoplasm() {
        super(ID, IMG, RelicTier.BOSS,LandingSound.FLAT);
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public int betterOnLoseHp(DamageInfo info, int damageAmount)
    {
        if (canTrigger) {
            if (!AbstractDungeon.player.hasPower(BufferPower.POWER_ID)) {
                AbstractDungeon.player.loseGold(50);
                canTrigger = false;
                this.flash();
            }
        }
        return damageAmount;
    }


    public void atTurnStart() {
        canTrigger = true;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new Endoplasm();
    }
}
