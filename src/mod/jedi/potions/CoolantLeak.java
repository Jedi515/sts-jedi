package mod.jedi.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class CoolantLeak
    extends AbstractPotion
{

    public static final String ID = "jedi:coolantleak";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public CoolantLeak() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.BLUE);

        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.isThrown = true;
        this.targetRequired = true;
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractCreature, AbstractDungeon.player, new LockOnPower(abstractCreature, this.potency), this.potency));
    }

    @Override
    public int getPotency(int i) {
        return 3;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CoolantLeak();
    }
}
