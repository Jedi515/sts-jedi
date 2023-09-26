package jedi.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import jedi.util.Wiz;

public class OminousLoanNote
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ominousloannote";
    public static final int loan = 400;

    public OminousLoanNote()
    {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL);
        description = getUpdatedDescription();
    }

    public void onEquip()
    {
        AbstractDungeon.effectList.add(new RainingGoldEffect(loan));
        AbstractDungeon.player.gainGold(loan);
        usedUp = false;
        counter = (int)(loan * 1.25F);
    }

    public String getUpdatedDescription()
    {
        if (usedUp) return DESCRIPTIONS[2];
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null)
        {
            OminousLoanNote oln = (OminousLoanNote) p.getRelic(ID);
            if (oln != null) return String.format(DESCRIPTIONS[1], counter);
        }
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return !Wiz.adp().hasRelic(Ectoplasm.ID);
    }

    public int getPrice()
    {
        return 0;
    }
}
