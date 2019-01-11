package mod.jedi.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import the_gatherer.GathererMod;
import the_gatherer.patches.PotionRarityEnum;
import the_gatherer.potions.SackPotion;

public class LesserTentacleJuice
    extends SackPotion
{

    public static final String ID = "jedi:lessertentaclejuice";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public LesserTentacleJuice()
    {
        super(NAME, ID, PotionRarityEnum.LESSER, PotionSize.BOTTLE, PotionColor.NONE);
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.isThrown = true;
        this.targetRequired = true;
        this.tips.add(new PowerTip(this.name, this.description));

        liquidColor = Color.PURPLE;
        hybridColor = null;
        spotsColor = Color.WHITE;
        GathererMod.setLesserPotionColors(liquidColor, hybridColor, spotsColor);
    }

    @Override
    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
    }

    @Override
    public int getBasePotency() {
        return 3;
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new ConstrictedPower(target, AbstractDungeon.player, this.potency), this.potency));

    }

    @Override
    public AbstractPotion makeCopy() {
        return new LesserTentacleJuice();
    }
}
