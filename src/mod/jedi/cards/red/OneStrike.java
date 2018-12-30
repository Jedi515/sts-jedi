package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import conspire.powers.GhostlyPower;
import conspire.powers.HolyPower;
import sts_jedi.jedi;

public class OneStrike
    extends CustomCard
{

    public static final String ID = "jedi:onestrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 3;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_attack.png";

    public String Speech = "ONE STRIKE!";
    public String SpeechUp = "ONE STRIKE PLUS ULTRA!";

    public OneStrike()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        this.cantUseMessage = "Cannot be used on bosses.";
        FleetingField.fleeting.set(this, true);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new RemoveAllTemporaryHPAction(m, p));
//        AbstractDungeon.actionManager.addToBottom(new ReplayLoseAllShieldingAction(m, p));
//        There's no monster that uses temp.hp or shielding, so why bother.

//        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, BufferPower.POWER_ID));
//        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, IntangiblePower.POWER_ID));
//        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, IntangiblePlayerPower.POWER_ID));
//        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, InvinciblePower.POWER_ID));
//        if (jedi.isConspireLoaded)
//        {
//            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, GhostlyPower.POWER_ID));
//            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, HolyPower.POWER_ID));
//        }

        for (AbstractPower pow : m.powers)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, pow.ID));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, m.maxHealth * 2, DamageInfo.DamageType.HP_LOSS)));
        if (this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Speech, 1.0F, 2.0F));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, SpeechUp, 1.0F, 2.0F));
        }
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.name += " Plus Ultra";
            this.upgraded = true;
            initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = false;
        if (m != null) {
            if (m.type != AbstractMonster.EnemyType.BOSS) {
                canUse = true;
            }
        }

        return canUse;
    }

    public AbstractCard makeCopy()
    {
        return new OneStrike();
    }

}
