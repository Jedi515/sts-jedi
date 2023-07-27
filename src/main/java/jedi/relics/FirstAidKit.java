package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import jedi.util.Wiz;

public class FirstAidKit
    extends AbstractJediRelic
{

    public static final String ID = "jedi:firstaidkit";
    public static final int potency = 4;

    public FirstAidKit()
    {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        usedUp = false;
    }

    @Override
    public void onVictory() {
        usedUp = false;
    }

    @Override
    public void wasHPLost(int damageAmount) {
        if (damageAmount <= 0) return;
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;
        if (usedUp) return;

        usedUp = true;
        addToBot(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new RegenPower(Wiz.adp(), potency)));
    }

    public String getUpdatedDescription()
    {
        return String.format(DESCRIPTIONS[0], potency);
    }
}
