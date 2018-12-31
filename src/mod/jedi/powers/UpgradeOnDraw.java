package mod.jedi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UpgradeOnDraw
    extends AbstractPower
    implements OnCardDrawPower
{
    public static final String POWER_ID = "jedi:upgradeondraw";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);;
    public static final String NAME =  powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UpgradeOnDraw(AbstractCreature owner, int setAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = setAmount;
        this.updateDescription();
//        this.region128 = new TextureAtlas.AtlasRegion(new Texture("resources/jedi/images/powers/meditation power 84.png"), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(new Texture("resources/jedi/images/powers/meditation power 32.png"), 0, 0, 32, 32);
        this.loadRegion("draw");
        //change that.

        this.type = PowerType.BUFF;
    }

    public void atEndOfRound()
    {
        this.amount--;
        if (this.amount == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this.ID));
        }
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard c)
    {
        if (c.canUpgrade())
        {
            c.upgrade();
            c.superFlash();
        }
    }
}
