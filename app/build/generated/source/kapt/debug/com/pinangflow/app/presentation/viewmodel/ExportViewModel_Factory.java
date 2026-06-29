package com.pinangflow.app.presentation.viewmodel;

import com.pinangflow.app.domain.repository.ExportRepository;
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
public final class ExportViewModel_Factory implements Factory<ExportViewModel> {
  private final Provider<ExportRepository> exportRepositoryProvider;

  public ExportViewModel_Factory(Provider<ExportRepository> exportRepositoryProvider) {
    this.exportRepositoryProvider = exportRepositoryProvider;
  }

  @Override
  public ExportViewModel get() {
    return newInstance(exportRepositoryProvider.get());
  }

  public static ExportViewModel_Factory create(
      Provider<ExportRepository> exportRepositoryProvider) {
    return new ExportViewModel_Factory(exportRepositoryProvider);
  }

  public static ExportViewModel newInstance(ExportRepository exportRepository) {
    return new ExportViewModel(exportRepository);
  }
}
