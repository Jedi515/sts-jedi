package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import mod.jedi.actions.BruteforceAction;
import mod.jedi.actions.RemoveSpecificOrbAction;

import java.util.Collections;

public class Bruteforce
    extends CustomCard
{
    public static final String ID = "jedi:bruteforce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public Bruteforce()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BruteforceAction(m, this.magicNumber));
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = false;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof Lightning) {
                canUse = true;
            }
        }
        if (!canUse)
        {
            this.cantUseMessage = UPGRADE_DESCRIPTION;
        }

        return canUse;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}
