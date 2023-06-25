package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.jedi;

public class Pinwheel
    extends AbstractJediRelic
{
    public static final String ID = jedi.makeID(Pinwheel.class.getSimpleName());

    public Pinwheel()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onShuffle()
    {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
