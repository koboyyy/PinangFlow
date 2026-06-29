package com.pinangflow.app.presentation.viewmodel;

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
public final class FarmerViewModel_Factory implements Factory<FarmerViewModel> {
  private final Provider<FarmerRepository> farmerRepositoryProvider;

  public FarmerViewModel_Factory(Provider<FarmerRepository> farmerRepositoryProvider) {
    this.farmerRepositoryProvider = farmerRepositoryProvider;
  }

  @Override
  public FarmerViewModel get() {
    return newInstance(farmerRepositoryProvider.get());
  }

  public static FarmerViewModel_Factory create(
      Provider<FarmerRepository> farmerRepositoryProvider) {
    return new FarmerViewModel_Factory(farmerRepositoryProvider);
  }

  public static FarmerViewModel newInstance(FarmerRepository farmerRepository) {
    return new FarmerViewModel(farmerRepository);
  }
}
