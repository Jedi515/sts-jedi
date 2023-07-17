package jedi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class MeditationPower
    extends AbstractPower
{
    public static final String POWER_ID = "jedi:meditation";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public MeditationPower(AbstractCreature owner, int setAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = setAmount;
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(new Texture("resources/jedi/images/powers/meditation power 84.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture("resources/jedi/images/powers/meditation power 32.png"), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        this.flash();
        addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, amount), amount));
        addToBot(new RemoveNextOrbAction());
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
