package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OnTargetBlockBreakAction extends AnimatorAction
{
    private AbstractGameAction action;
    private int initialBlock;

    public OnTargetBlockBreakAction(AbstractCreature target, AbstractGameAction action)
    {
        this.target = target;
        this.initialBlock = target.currentBlock;
        this.action = action;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = action.actionType;
    }

    public void update()
    {
        if (initialBlock > 0 && target.currentBlock <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(action);
        }
        this.isDone = true;
    }
}
