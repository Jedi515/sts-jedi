package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class Sprinkler
    extends AbstractJediRelic
{
    public static final String ID = "jedi:sprinkler";
    private static final int powerCurve = 3;

    public Sprinkler()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, RelicType.GREEN);
        this.description = DESCRIPTIONS[0] + powerCurve + DESCRIPTIONS[1];
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + powerCurve + DESCRIPTIONS[1];
    }

    public void onShuffle()
    {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, powerCurve), powerCurve));
        }
    }
}
