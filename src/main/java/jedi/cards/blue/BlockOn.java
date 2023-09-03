package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import jedi.cards.CustomJediCard;

public class BlockOn
    extends CustomJediCard
{

    public static final String ID = "jedi:blockon";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public BlockOn()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if ((mo.intent == AbstractMonster.Intent.ATTACK ||
                mo.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                mo.intent == AbstractMonster.Intent.ATTACK_DEFEND))
            {
                addToBot(new GainBlockAction(p, p, block));
                addToBot(new ApplyPowerAction(mo, p, new LockOnPower(mo, magicNumber)));
            }
        }
    }

    @Override
    protected void upp()
    {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }

}
