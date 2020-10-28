package mod.jedi.potions;

import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import mod.jedi.actions.CustomDiscoveryAction;
import mod.jedi.jedi;

public class StrikingPotion
        extends AbstractPotion
{
    public static final String ID = "jedi:StrikingPotion";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public StrikingPotion() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.CARD, PotionColor.ATTACK);

        potency = getPotency();

        if (potency > 1)
        {
            description = String.format(potionStrings.DESCRIPTIONS[1], potency);
        }
        else
        {
            description = potionStrings.DESCRIPTIONS[0];
        }
        isThrown = false;
        targetRequired = false;

        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        addToBot(new CustomDiscoveryAction(jedi.StrikeGroup, 3, potency, false));
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new StrikingPotion();
    }
}