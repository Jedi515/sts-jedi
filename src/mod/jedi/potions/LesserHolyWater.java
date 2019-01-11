package mod.jedi.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
        this.description = DESCRIPTIONS[0];
        this.isThrown = false;
        this.targetRequired = false;
        this.tips.add(new PowerTip(this.name, this.description));

        liquidColor = Color.YELLOW;
        hybridColor = Color.WHITE;
        spotsColor = null;
        GathererMod.setLesserPotionColors(liquidColor, hybridColor, spotsColor);
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int getBasePotency() {
        return 0;
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
            AbstractPower po = (AbstractPower)debuffs.get(AbstractDungeon.miscRng.random(0, debuffs.size() - 1));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, po));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LesserHolyWater();
    }
}
