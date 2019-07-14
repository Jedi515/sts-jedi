package mod.jedi.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import conspire.powers.AbstractConspirePower;
import mod.jedi.actions.RemoveSpecificOrbAction;
import the_gatherer.GathererMod;
import the_gatherer.modules.PotionSack;
import the_gatherer.potions.SackPotion;

import java.util.ArrayList;

public class LesserHolyWater
    extends SackPotion
{
    public static final String ID = "jedi:lesserholywater";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public LesserHolyWater() {
        super(NAME, ID, PotionRarity.RARE, PotionSize.SPHERE, PotionColor.NONE);

        this.potency = getPotency();

        if (this.potency == 1)
        {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[2];
        }

        this.isThrown = false;
        this.targetRequired = false;
        this.tips.add(new PowerTip(this.name, this.description));

        liquidColor = Color.YELLOW.cpy();
        hybridColor = Color.WHITE.cpy();
        spotsColor = null;
        GathererMod.setLesserPotionColors(liquidColor, hybridColor, spotsColor);
    }
    @Override
    public void updateDescription() {
        this.upgrade = PotionSack.potionPotency;
        this.potency = getPotency();
        this.tips.clear();
        if (this.potency == 1)
        {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[2];
        }
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public int getBasePotency() {
        return 1;
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        ArrayList<AbstractPower> debuffs = new ArrayList<>();
        for (AbstractPower p : AbstractDungeon.player.powers)
        {
            if (p.type == AbstractPower.PowerType.DEBUFF)
            {
                debuffs.add(p);
            }
        }
        if (!debuffs.isEmpty())
        {
            if (debuffs.size() < this.potency)
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
            }
            else
            {
                ArrayList<AbstractPower> removed = new ArrayList<>();
                for (int i = 0; i < this.potency; i++)
                {
                    AbstractPower po = debuffs.get(AbstractDungeon.miscRng.random(0, debuffs.size() - 1));
                    if (!removed.contains(po))
                    {
                        removed.add(po);
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, po));
                    }
                    else
                    {
                        i--;
                    }
                }
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LesserHolyWater();
    }
}
