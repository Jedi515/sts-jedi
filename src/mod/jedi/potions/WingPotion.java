package mod.jedi.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class WingPotion
    extends CustomPotion
{
    public static final String ID = "jedi:wingpotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public WingPotion()
    {
        super(NAME, ID, PotionRarity.UNCOMMON, PotionSize.GHOST, PotionColor.BLUE);
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

    }

    public boolean canUse()
    {
        return false;
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new WingPotion();
    }
}
