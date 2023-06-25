package jedi.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.options.SettingsScreen;
import jedi.buttons.CardLibraryMidgameButton;
import jedi.buttons.CompendiumMidgameButton;
import jedi.util.TextureLoader;

public class CompendiumMidgamePatches
{
    public static CompendiumMidgameButton CompButton;
    public static CardLibraryMidgameButton LibrButton;
    public static Texture relicImg;
    public static Texture cardImg;
    public static Texture paper;

//    @SpirePatch(clz = SettingsScreen.class, method = SpirePatch.CONSTRUCTOR)
    public static class Init
    {
        public static void Postfix(SettingsScreen __instance)
        {
            relicImg = TextureLoader.getTexture("resources/jedi/images/buttons/necronomicon.png");
            cardImg = TextureLoader.getTexture("resources/jedi/images/buttons/deck.png");
            paper = TextureLoader.getTexture("resources/jedi/images/buttons/OldPaper.jpg");
            CompButton = new CompendiumMidgameButton();
            LibrButton = new CardLibraryMidgameButton();
        }
    }

//    @SpirePatch(clz = SettingsScreen.class, method = "render")
    public static class RenderPatch
    {
        public static void Postfix(SettingsScreen __instance, SpriteBatch sb)
        {
            if (CardCrawlGame.isInARun())
            {
                if (!CompButton.renderRelics) LibrButton.render(sb);
                if (!LibrButton.render) CompButton.render(sb);
            }
        }
    }

//    @SpirePatch(clz = SettingsScreen.class, method = "update")
    public static class UpdatePatch
    {
        public static void Postfix(SettingsScreen __instance)
        {
            if (CardCrawlGame.isInARun())
            {
                if (!CompButton.renderRelics) LibrButton.update();
                if (!LibrButton.render) CompButton.update();
            }
        }
    }
}
