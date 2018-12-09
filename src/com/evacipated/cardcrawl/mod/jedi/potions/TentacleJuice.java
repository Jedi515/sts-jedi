package com.evacipated.cardcrawl.mod.jedi.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

public class TentacleJuice
    extends AbstractPotion
{
    public static final String ID = "jedi:tentaclejuice";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TentacleJuice() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.FRUIT);

        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.isThrown = true;
        this.targetRequired = true;
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new ConstrictedPower(target, AbstractDungeon.player, this.potency), this.potency));

    }

    @Override
    public int getPotency(int i) {
        return 4;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TentacleJuice();
    }
}
