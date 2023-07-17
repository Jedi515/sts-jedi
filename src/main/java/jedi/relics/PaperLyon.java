package jedi.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.OddMushroom;

public class PaperLyon
    extends AbstractJediRelic
{
    public static final String ID = "jedi:paperlyon";
    public static final OddMushroom shroom = new OddMushroom();

    public PaperLyon() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(shroom.name, "y");
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2] + FontHelper.colorString(shroom.name, "y") + DESCRIPTIONS[3]));
        this.initializeTips();

    }

    //Make a wild guess. It's a patch. patches/PaperLyonPatch

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}
