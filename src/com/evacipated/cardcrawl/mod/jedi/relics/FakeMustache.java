package com.evacipated.cardcrawl.mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FakeMustache
    extends CustomRelic
{
    public static final String ID = "jedi:fakemustache";
    public static final String IMG_PATH = "resources/images/relics/beta_rock.png";


    public FakeMustache() {
        super(ID, new Texture(IMG_PATH), RelicTier.SHOP,LandingSound.FLAT);
    }


    public void onSpendGold()
    {
        AbstractDungeon.player.heal(5);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new FakeMustache();
    }

}
