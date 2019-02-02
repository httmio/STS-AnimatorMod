package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OnTargetBlockLostAction extends AnimatorAction
{
    private final AbstractGameAction action;
    private final int initialBlock;

    public OnTargetBlockLostAction(AbstractCreature target, AbstractGameAction action)
    {
        this.target = target;
        this.initialBlock = target.currentBlock;
        this.action = action;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = action.actionType;
    }

    public void update()
    {
        if (initialBlock > 0 && target.currentBlock < initialBlock)
        {
            action.amount = initialBlock - target.currentBlock;
            AbstractDungeon.actionManager.addToBottom(action);
        }
        this.isDone = true;
    }
}
