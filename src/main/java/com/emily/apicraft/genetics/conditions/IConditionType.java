package com.emily.apicraft.genetics.conditions;

import java.util.function.Supplier;

public interface IConditionType<T extends IBeeCondition> {
    Supplier<IConditionSerializer<T>> getSerializer();
}
