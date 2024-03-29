package jedi.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SpecialDeliveryPotion
    extends CustomPotion
{
    public static final String ID = "jedi:specialdeliverypotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SpecialDeliveryPotion()
    {
        super(NAME, ID, PotionRarity.RARE, PotionSize.HEART, PotionColor.ANCIENT);
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        liquidColor = Color.GOLD.cpy();
        hybridColor = Color.CLEAR.cpy();
        spotsColor = null;
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.maxHealth));
        }
        else
        {
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        }

    }

    @Override
    public int getPotency(int i) {
        return 0;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SpecialDeliveryPotion();
    }
}
