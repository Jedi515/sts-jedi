package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class AngryMask
    extends AbstractJediRelic
{
    public static final String ID = "jedi:angrymask";
    private static final int strGain = 2;

    public AngryMask()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void onEquip()
    {
        this.counter = -1;
    }

    public void onSpendGold()
    {
        this.counter = -2;
        this.beginLongPulse();
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public void atBattleStart()
    {
        if (this.counter == -2)
        {
            AbstractPlayer p = AbstractDungeon.player;
            this.stopPulse();
            this.counter = -1;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, strGain), strGain));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + strGain + DESCRIPTIONS[1];
    }
}
