package jedi.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class InstantObtainEffect
    extends AbstractGameEffect
{
    private AbstractRelic relic;

    public InstantObtainEffect(AbstractRelic relic)
    {
        this.relic = relic;
        duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update()
    {
        relic.instantObtain();
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {

    }

    @Override
    public void dispose()
    {

    }
}
