package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;

public class Endoplasm
    extends CustomRelic
    implements BetterOnLoseHpRelic
{
    public static final String ID = "jedi:endoplasm";
    public static final String IMG_PATH = "resources/images/relics/beta_rock.png";


    public Endoplasm() {
        super(ID, new Texture(IMG_PATH), RelicTier.BOSS,LandingSound.FLAT);
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public int betterOnLoseHp(DamageInfo info, int damageAmount)
    {
        if (!AbstractDungeon.player.hasPower(BufferPower.POWER_ID)) {
            AbstractDungeon.player.loseGold(50);
        }
        return damageAmount;
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
