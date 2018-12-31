/*Ok so here's the thing, this card has been BASICALLY created by JohnnyDevo (MysticMod creator) by one way or another.
* I kinda got his help on the majority of this code or took parts from the way he did it in his cards (Fireball) so
* There's that. https://github.com/JohnnyDevo/The-Mystic-Project/releases for the Mystic mod.
*
*
* Ok so here's another thing. if you're reading this - something went horribly wrong. This card should have never made to actual release.
* */

package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.ElectroPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class C_Light_test
        extends CustomCard
{
    public static final String ID = "jedi:C_Light_test";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_attack.png";
    public static final float LockOnMult = 1.5F;

    public C_Light_test()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (p.hasPower(ElectroPower.POWER_ID))
        {
            int DamagePlaceholder = this.damage;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo.hasPower(LockOnPower.POWER_ID))
                {
                    this.damage *= LockOnMult;
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                this.damage = DamagePlaceholder;
            }
        }
        else
        {
            int DamagePlaceholder = this.damage;
            if (m.hasPower(LockOnPower.POWER_ID))
            {
                this.damage *= LockOnMult;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            this.damage = DamagePlaceholder;
        }
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Lightning());
    }

    @Override
    public void applyPowers() {
        int baseDamagePlaceholder = this.baseDamage;
        int currentFocus = 0;
        int currentStrength = 0;
        boolean hasFocus = false;
        boolean hasStrength = false;

        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            currentFocus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            hasFocus = true;
        }
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            currentStrength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            hasStrength = true;
        }

        if (hasFocus)
        {
            if (!hasStrength) {
                this.baseDamage += currentFocus * magicNumber;
                super.applyPowers();
                this.baseDamage = baseDamagePlaceholder;
                this.isDamageModified = baseDamage != damage;
            } else {
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
                super.applyPowers();
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
            }
        }
        else if (hasStrength)
        {
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
            super.applyPowers();
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
        }
        else
        {
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int baseDamagePlaceholder = this.baseDamage;
        int currentFocus = 0;
        int currentStrength = 0;
        boolean hasFocus = false;
        boolean hasStrength = false;

        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            currentFocus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            hasFocus = true;
        }
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            currentStrength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            hasStrength = true;
        }

        if (hasFocus) {
            if (!hasStrength) {
                this.baseDamage += currentFocus * magicNumber;
                super.calculateCardDamage(m);
                this.baseDamage = baseDamagePlaceholder;
                this.isDamageModified = baseDamage != damage;
            } else {
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
                super.calculateCardDamage(m);
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
            }
        } else if (hasStrength) {
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentFocus * magicNumber;
            super.calculateCardDamage(m);
            AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount = currentStrength;
        } else {
            super.calculateCardDamage(m);
        }

    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new C_Light_test();
    }
}
