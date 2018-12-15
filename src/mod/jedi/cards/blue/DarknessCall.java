package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class DarknessCall
    extends CustomCard
{
    public static final String ID = "jedi:darknesscall";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/images/cards/jedi_beta.png";

    public DarknessCall()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractMonster weakestMonster = null;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
        {
            if (!mo.isDeadOrEscaped()) {
                if (weakestMonster == null) {
                    weakestMonster = mo;
                } else if (mo.currentHealth < weakestMonster.currentHealth) {
                    weakestMonster = mo;
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(weakestMonster, p, new LockOnPower(weakestMonster, 1), 1));
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof Dark) {
                AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o));
                return;
            }
        }
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = false;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof Dark) {
                canUse = true;
            }
        }
        if (!canUse)
        {
            this.cantUseMessage = UPGRADE_DESCRIPTION;
        }

        return canUse;
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new DarknessCall();
    }
}
