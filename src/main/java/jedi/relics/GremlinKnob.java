package jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.util.TextureLoader;

public class GremlinKnob
    extends CustomRelic
{
    public static final String ID = "jedi:gremlinknob";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);


    public GremlinKnob() {
        super(ID, IMG, OUTLINE, RelicTier.RARE,LandingSound.FLAT);
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m)
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (c.type == AbstractCard.CardType.SKILL)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public CustomRelic makeCopy()
    {
        return new GremlinKnob();
    }
}
