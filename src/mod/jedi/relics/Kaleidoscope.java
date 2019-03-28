package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.jedi.util.TextureLoader;

import java.util.ArrayList;

public class Kaleidoscope
    extends CustomRelic
{
    public static final String ID = "jedi:kaleidoscope";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private static ArrayList<AbstractCard.CardColor> cardColors = new ArrayList<>();

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public Kaleidoscope()
    {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip()
    {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            if (!cardColors.contains(c.color))
            {
                cardColors.add(c.color);
            }
        }
        this.counter = cardColors.size();
    }

    @Override
    public void onMasterDeckChange()
    {
        cardColors.clear();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            if (!cardColors.contains(c.color))
            {
                cardColors.add(c.color);
            }
        }
        this.counter = cardColors.size();
    }

    @Override
    public void atBattleStart()
    {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, cardColors.size()), cardColors.size()));
    }

}
