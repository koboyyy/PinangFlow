package com.pinangflow.app.presentation.viewmodel;

import com.pinangflow.app.domain.repository.BatchRepository;
import com.pinangflow.app.domain.repository.FarmerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class BatchViewModel_Factory implements Factory<BatchViewModel> {
  private final Provider<BatchRepository> batchRepositoryProvider;

  private final Provider<FarmerRepository> farmerRepositoryProvider;

  public BatchViewModel_Factory(Provider<BatchRepository> batchRepositoryProvider,
      Provider<FarmerRepository> farmerRepositoryProvider) {
    this.batchRepositoryProvider = batchRepositoryProvider;
    this.farmerRepositoryProvider = farmerRepositoryProvider;
  }

  @Override
  public BatchViewModel get() {
    return newInstance(batchRepositoryProvider.get(), farmerRepositoryProvider.get());
  }

  public static BatchViewModel_Factory create(Provider<BatchRepository> batchRepositoryProvider,
      Provider<FarmerRepository> farmerRepositoryProvider) {
    return new BatchViewModel_Factory(batchRepositoryProvider, farmerRepositoryProvider);
  }

  public static BatchViewModel newInstance(BatchRepository batchRepository,
      FarmerRepository farmerRepository) {
    return new BatchViewModel(batchRepository, farmerRepository);
  }
}
