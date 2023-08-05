package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import jedi.interfaces.onObtainRelicInterface;

public class HeavyJacket
    extends AbstractJediRelic
    implements onObtainRelicInterface
{

    public static final String ID = "jedi:heavyjacket";

    public HeavyJacket()
    {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void atBattleStart()
    {
        int PA = AbstractDungeon.player.relics.size() / 2;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, PA), PA));
    }

    public void onEquip()
    {
        this.counter = (AbstractDungeon.player.relics.size()) / 2;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onObtainRelic(AbstractRelic r)
    {
        this.counter = (AbstractDungeon.player.relics.size() + 1) / 2;
    }
}
