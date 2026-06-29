package com.pinangflow.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.pinangflow.app.domain.usecase.UpdateShrinkageUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ShrinkageWorker_Factory {
  private final Provider<UpdateShrinkageUseCase> updateShrinkageUseCaseProvider;

  public ShrinkageWorker_Factory(Provider<UpdateShrinkageUseCase> updateShrinkageUseCaseProvider) {
    this.updateShrinkageUseCaseProvider = updateShrinkageUseCaseProvider;
  }

  public ShrinkageWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, updateShrinkageUseCaseProvider.get());
  }

  public static ShrinkageWorker_Factory create(
      Provider<UpdateShrinkageUseCase> updateShrinkageUseCaseProvider) {
    return new ShrinkageWorker_Factory(updateShrinkageUseCaseProvider);
  }

  public static ShrinkageWorker newInstance(Context context, WorkerParameters params,
      UpdateShrinkageUseCase updateShrinkageUseCase) {
    return new ShrinkageWorker(context, params, updateShrinkageUseCase);
  }
}
