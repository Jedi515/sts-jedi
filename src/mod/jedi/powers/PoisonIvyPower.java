package mod.jedi.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class PoisonIvyPower
    extends AbstractPower
{
    public static final String POWER_ID = "jedi:poisonivy";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME =  powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean notShiv;

    public PoisonIvyPower(AbstractCreature owner, int setAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = setAmount;
        this.updateDescription();
//        this.region128 = new TextureAtlas.AtlasRegion(new Texture("resources/jedi/images/powers/poisonivy power 84.png"), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(new Texture("resources/jedi/images/powers/poisonivy power 32.png"), 0, 0, 32, 32);
        this.loadRegion("poison");
        //change that.

        this.type = PowerType.BUFF;
    }

    public void updateDescription()
    {
        if (this.amount == 1)
        {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void onUseCard(AbstractCard c, UseCardAction action)
    {
        notShiv = (c.type == AbstractCard.CardType.ATTACK) && !(c instanceof Shiv);
    }


    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if (target != this.owner && info.type == DamageInfo.DamageType.NORMAL && notShiv)
        {
            if (target.hasPower(PoisonPower.POWER_ID))
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), this.amount));
            }
        }
    }
}
