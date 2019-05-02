package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.zafira.models.dto.AbstractType;

import java.util.function.Function;

public enum Builder {

    USER(o -> UserBuilder.buildUser()),
    TEST_RUN(o -> TestRunBuilder.buildTestRun((TestRunBuilder.Builder) o));

    private final Function<Object, ? extends AbstractType> builderSupplier;

    Builder(Function<Object, ? extends AbstractType> builderSupplier) {
        this.builderSupplier = builderSupplier;
    }

    public Function<Object, ? extends AbstractType> getBuilderSupplier() {
        return builderSupplier;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractType> T build(Object o) {
        return (T) builderSupplier.apply(o);
    }

}
