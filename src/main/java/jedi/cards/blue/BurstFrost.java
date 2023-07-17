package jedi.cards.blue;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import jedi.cardMods.BurstFrostMod;
import jedi.cards.CustomJediCard;


public class BurstFrost
        extends CustomJediCard
{
    public static final String ID = "jedi:BurstFrost";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public BurstFrost()
    {
        super(ID, NAME, 1, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        CardModifierManager.addModifier(this, new BurstFrostMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Frost());
    }

    public void upp()
    {
        upgradeMagicNumber(1);
        upgradeBlock(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public AbstractCard makeCopy()
    {
        return new BurstFrost();
    }
}
