package com.pinangflow.app.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class CalculateShrinkageUseCase_Factory implements Factory<CalculateShrinkageUseCase> {
  @Override
  public CalculateShrinkageUseCase get() {
    return newInstance();
  }

  public static CalculateShrinkageUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CalculateShrinkageUseCase newInstance() {
    return new CalculateShrinkageUseCase();
  }

  private static final class InstanceHolder {
    private static final CalculateShrinkageUseCase_Factory INSTANCE = new CalculateShrinkageUseCase_Factory();
  }
}
