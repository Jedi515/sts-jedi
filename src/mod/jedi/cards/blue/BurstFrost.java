package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.DexterityPower;


public class BurstFrost
        extends CustomCard
{
    public static final String ID = "jedi:BurstFrost";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public BurstFrost()
    {
        super(ID, NAME, "resources/jedi/images/cards/jedi_beta.png", 1, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Frost());
    }

    @Override
    public void applyPowers() {
        int baseBlockPlaceholder = this.baseBlock;
        int currentFocus = 0;
        int currentDexterity = 0;
        boolean hasFocus = false;
        boolean hasDexterity = false;
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            currentFocus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            hasFocus = true;
        }
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            currentDexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
            hasDexterity = true;
        }
        if (hasFocus) {
            if (!hasDexterity) {
                this.baseBlock += currentFocus * magicNumber;
                super.applyPowers();
                this.baseBlock = baseBlockPlaceholder;
                this.isBlockModified = baseBlock != block;
            } else {
                AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentFocus * magicNumber;
                super.applyPowers();
                AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentDexterity;
            }
        } else if (hasDexterity) {
            AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentFocus * magicNumber;
            super.applyPowers();
            AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentDexterity;
        } else {
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int baseBlockPlaceholder = this.baseBlock;
        int currentFocus = 0;
        int currentDexterity = 0;
        boolean hasFocus = false;
        boolean hasDexterity = false;
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            currentFocus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            hasFocus = true;
        }
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            currentDexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
            hasDexterity = true;
        }
        if (hasFocus) {
            if (!hasDexterity) {
                this.baseBlock += currentFocus * magicNumber;
                super.calculateCardDamage(m);
                this.baseBlock = baseBlockPlaceholder;
                this.isBlockModified = baseBlock != block;
            } else {
                AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentFocus * magicNumber;
                super.calculateCardDamage(m);
                AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentDexterity;
            }
        } else if (hasDexterity) {
            AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentFocus * magicNumber;
            super.calculateCardDamage(m);
            AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount = currentDexterity;
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
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new BurstFrost();
    }
}
