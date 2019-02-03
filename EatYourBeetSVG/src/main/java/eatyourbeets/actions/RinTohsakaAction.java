package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class RinTohsakaAction extends AbstractGameAction
{
    private int block;

    public RinTohsakaAction(int block)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.block = block;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            AbstractPlayer player = AbstractDungeon.player;
            for (int i = 0; i < player.orbs.size(); ++i)
            {
                if (!(player.orbs.get(i) instanceof EmptyOrbSlot))
                {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(player, player, block));
                }
            }
        }

        this.tickDuration();
    }
}
