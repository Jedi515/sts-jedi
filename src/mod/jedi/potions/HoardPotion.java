package mod.jedi.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import mod.jedi.interfaces.OutOfCombatPotion;

public class HoardPotion
    extends CustomPotion
    implements OutOfCombatPotion
{
    public static final String ID = "jedi:hoardpotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public HoardPotion()
    {
        super(NAME, ID, PotionRarity.RARE, PotionSize.CARD, PotionColor.BLUE);
        this.potency = getPotency();
        if (this.potency == 1)
        {
            this.description = DESCRIPTIONS[0];
        }
        else
        {
            this.description = DESCRIPTIONS[2];
        }
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        liquidColor = Color.GOLD.cpy();
        hybridColor = Color.CLEAR.cpy();
        spotsColor = null;
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.cardRewardScreen.rewardGroup)
        {
            System.out.println("JEDI MOD: " + c.name);
            AbstractCard crd = c.makeStatEquivalentCopy();
            if (this.potency > 1)
            {
                for (int i = 1; i < this.potency; i++)
                {
                    if (crd.canUpgrade())
                    {
                        crd.upgrade();
                    }
                }
            }
            group.addToBottom(crd);
        }
        if (group.size() > 0)
        {
            AbstractDungeon.combatRewardScreen.rewards.remove(AbstractDungeon.cardRewardScreen.rItem);
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
        }
    }

    @Override
    public boolean canUse()
    {
        return AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD;
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HoardPotion();
    }
}
