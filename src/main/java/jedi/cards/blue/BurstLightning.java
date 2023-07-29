/*Ok so here's the thing, this card has been BASICALLY created by JohnnyDevo (MysticMod creator) by one way or another.
 * I kinda got his help on the majority of this code or took parts from the way he did it in his cards (Fireball) so
 * There's that. https://github.com/JohnnyDevo/The-Mystic-Project/releases for the Mystic mod.
 * */

package jedi.cards.blue;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Electrodynamics;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.ElectroPower;
import jedi.actions.VSFXLightningAction;
import jedi.cardMods.BurstLightningMod;
import jedi.cards.CustomJediCard;


public class BurstLightning
        extends CustomJediCard
{
    public static final String ID = jedi.jedi.makeID(BurstLightning.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public BurstLightning()
    {
        super(ID, NAME, 1, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        CardModifierManager.addModifier(this, new BurstLightningMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VSFXLightningAction(m, true));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Lightning());
    }
    public void upp()
    {
        upgradeDamage(3);
    }
}
