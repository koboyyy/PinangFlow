package com.pinangflow.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ShrinkageWorker_AssistedFactory_Impl implements ShrinkageWorker_AssistedFactory {
  private final ShrinkageWorker_Factory delegateFactory;

  ShrinkageWorker_AssistedFactory_Impl(ShrinkageWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public ShrinkageWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<ShrinkageWorker_AssistedFactory> create(
      ShrinkageWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ShrinkageWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<ShrinkageWorker_AssistedFactory> createFactoryProvider(
      ShrinkageWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ShrinkageWorker_AssistedFactory_Impl(delegateFactory));
  }
}
