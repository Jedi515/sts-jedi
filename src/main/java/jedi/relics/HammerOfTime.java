package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.interfaces.onForcedTurnEndInterface;
import jedi.jedi;

public class HammerOfTime
    extends AbstractJediRelic
    implements onForcedTurnEndInterface
{
    public static final String ID = jedi.makeID(HammerOfTime.class.getSimpleName());
    private static final int potency = 2;

    public HammerOfTime() {
        super(ID, RelicTier.SHOP, LandingSound.HEAVY, RelicType.PURPLE);
        cardPool = RelicType.PURPLE;
    }

    public boolean onForcedTurnEnd()
    {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, potency)));
        return false;
    }

    public String getUpdatedDescription()
    {
        return String.format(DESCRIPTIONS[0], potency);
    }
}
