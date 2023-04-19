package com.emily.apicraft.genetics.mutations.condition;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.mutations.IMutationCondition;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;

public class ConditionTimeLimited implements IMutationCondition {
    private final int timeStart;
    private final int timeEnd;

    public ConditionTimeLimited(int timeStart, int timeEnd){
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
    }
    @Override
    public Function<Float, Float> getModifier(IBeeHousing beeHousing) {
        int time = (int) (beeHousing.getBeeHousingLevel().getDayTime() % 24000);
        boolean inRange = time <= timeEnd && time >= timeStart;
        return (x) -> inRange ? x : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
