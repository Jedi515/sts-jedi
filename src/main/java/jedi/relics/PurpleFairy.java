package jedi.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.FusionHammer;
import jedi.util.Wiz;

public class PurpleFairy
    extends AbstractJediRelic
    implements BetterOnSmithRelic
{
    public static final String ID = "jedi:purplefairy";
    AbstractPlayer p = AbstractDungeon.player;

    public PurpleFairy()
    {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
        this.initializeTips();
    }

    public boolean canSpawn() {
        return !Wiz.adp().hasRelic(FusionHammer.ID) && f48();
    }

    @Override
    public void betterOnSmith(AbstractCard abstractCard)
    {
        switch (abstractCard.rarity)
        {
            case BASIC:
                p.heal(p.maxHealth);
                break;
            case UNCOMMON:
                p.heal(5);
                break;
            case RARE:
                p.heal(1);
                break;
            case CURSE:
                p.increaseMaxHp(5,true);
                p.heal(p.maxHealth);
                break;
            case SPECIAL:
            case COMMON:
            default:
                p.heal(10);
                break;
        }
    }
}
