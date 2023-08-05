package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Catwich
    extends AbstractJediRelic
{
    public static final String ID = "jedi:catwich";
    private boolean triggered;

    public Catwich()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, RelicType.GREEN);
        this.description = DESCRIPTIONS[0];
    }

    public void onManualDiscard()
    {
        if (!this.triggered && AbstractDungeon.player.discardPile.getTopCard().canUpgrade())
        {
            this.triggered = true;
            this.flash();
            AbstractDungeon.player.discardPile.getTopCard().upgrade();
        }
    }

    public void atTurnStart()
    {
        this.triggered = false;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

}
