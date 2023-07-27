package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
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
    public void atBattleStart() {
        addToBot(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new RegenPower(Wiz.adp(), potency)));
    }

    public String getUpdatedDescription()
    {
        return String.format(DESCRIPTIONS[0], potency);
    }
}
