package jedi.relics;

import basemod.helpers.RelicType;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.actions.defect.ImpulseAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class Superconductor
    extends AbstractJediRelic
    implements OnChannelRelic
{
    public static final String ID = "jedi:superconductor";

    public Superconductor() {
        super(ID, RelicTier.UNCOMMON,LandingSound.FLAT, RelicType.BLUE);
        counter = 0;
    }

    public void onChannel(AbstractOrb orb)
    {
        ++counter;
        if (counter == 10) {
            counter = 0;
            flash();
            pulse = false;
            addToBot(new ImpulseAction());
        }
        else if (counter == 9) {
            beginPulse();
            pulse = true;
        }
    }

    public void atBattleStart() {
        if (counter == 9) {
            beginPulse();
            pulse = true;
        }
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
