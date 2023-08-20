package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
        counter = -1;
    }

    public void onSpendGold()
    {
        counter = -2;
        beginLongPulse();
    }

    public boolean canSpawn() {
        return f48();
    }

    public void atBattleStart()
    {
        if (counter == -2)
        {
            AbstractPlayer p = AbstractDungeon.player;
            stopPulse();
            counter = -1;
            flash();
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, strGain), strGain));
            addToBot(new RelicAboveCreatureAction(p, this));
        }
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + strGain + DESCRIPTIONS[1];
    }
}
