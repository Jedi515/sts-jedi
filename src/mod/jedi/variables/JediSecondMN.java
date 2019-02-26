package mod.jedi.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mod.jedi.cards.CustomJediCard;

public class JediSecondMN
    extends DynamicVariable
{
    @Override
    public String key() {
        return "jedi:SecondMN";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return ((CustomJediCard)abstractCard).isSecondMNModified;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return ((CustomJediCard)abstractCard).secondMN;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return ((CustomJediCard)abstractCard).baseSecondMN;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return ((CustomJediCard)abstractCard).isSecondMNUpgraded;
    }
}
