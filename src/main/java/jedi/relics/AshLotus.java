package jedi.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AshLotus
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ashlotus";
    public int potency = 50;

    public AshLotus()
    {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public void onPlayerEndTurn()
    {
        addToBot(new ExhaustAction(1, false));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;
                if (p.hand.size() == 0 &&
                        p.drawPile.size() == 0 &&
                        p.discardPile.size() == 0)
                {
                    addToTop(new GainBlockAction(p, p, potency));
                    addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(potency), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                }
                isDone = true;
            }
        });
    }

    public String getUpdatedDescription()
    {
        return (this.DESCRIPTIONS[0] + potency + this.DESCRIPTIONS[1]);
    }
}
