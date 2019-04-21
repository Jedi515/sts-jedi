package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.ChaosPower;
import com.megacrit.cardcrawl.mod.replay.relics.RingOfChaos;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SneckoEye;
import mod.jedi.util.TextureLoader;

public class OtherSneckoEye
    extends CustomRelic
{
    public static final String ID = "jedi:othersneckoeye";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);


    public OtherSneckoEye() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void onEquip()
    {
        AbstractDungeon.player.masterHandSize += 1;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.masterHandSize -= 1;
    }

    public void atPreBattle()
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChaosPower(AbstractDungeon.player, 2), 2));
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public void onPlayerEndTurn()
    {
        ++this.counter;
        if (this.counter == 3)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChaosPower(AbstractDungeon.player, 1), 1));
            this.counter = 0;
        }
    }

    public void onVictory() {
        this.counter = -1;
        this.stopPulse();
    }

    public boolean canSpawn()
    {
        return  (AbstractDungeon.player.hasRelic(SneckoEye.ID) ||
                (AbstractDungeon.player.hasRelic("Ring of Chaos")));
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OtherSneckoEye();
    }
}
