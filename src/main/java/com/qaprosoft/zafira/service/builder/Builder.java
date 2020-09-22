package com.qaprosoft.zafira.service.builder;

import java.util.function.Function;

public enum Builder {

    USER(o -> UserBuilder.buildUser()),
    TEST_RUN(o -> TestRunBuilder.buildTestRun((TestRunBuilder.Builder) o));

    private final Function<Object, ?> builderSupplier;

    Builder(Function<Object, ?> builderSupplier) {
        this.builderSupplier = builderSupplier;
    }

    public Function<Object, ?> getBuilderSupplier() {
        return builderSupplier;
    }

    @SuppressWarnings("unchecked")
    public <T> T build(Object o) {
        return (T) builderSupplier.apply(o);
    }

    public <T> T build() {
        return build(null);
    }

}
