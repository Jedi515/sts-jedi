package com.evacipated.cardcrawl.mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.ChaosPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class OtherSneckoEye
    extends CustomRelic
{
    public static final String ID = "jedi:othersneckoeye";
    public static final String IMG_PATH = "resources/images/relics/beta_rock.png";


    public OtherSneckoEye() {
        super(ID, new Texture(IMG_PATH), RelicTier.BOSS,LandingSound.FLAT);
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChaosPower(AbstractDungeon.player, 1), 1));
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

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OtherSneckoEye();
    }
}
