package eatyourbeets.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;

public class HideCardEffect extends AbstractGameEffect
{
    private AbstractCard c;
    private static final float DUR = 1.0F;

    public HideCardEffect(AbstractCard c)
    {
        this.duration = 1.0F;
        this.c = c;
    }

    public void update()
    {
        this.c.target_y = -10000;
        this.c.isFlipped = true;
    }

    public void render(SpriteBatch sb)
    {

    }

    public void dispose()
    {

    }
}