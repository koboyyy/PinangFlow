package com.pinangflow.app.domain.usecase;

import com.pinangflow.app.domain.repository.StockRepository;
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
public final class UpdateShrinkageUseCase_Factory implements Factory<UpdateShrinkageUseCase> {
  private final Provider<StockRepository> stockRepositoryProvider;

  private final Provider<CalculateShrinkageUseCase> calculateShrinkageUseCaseProvider;

  public UpdateShrinkageUseCase_Factory(Provider<StockRepository> stockRepositoryProvider,
      Provider<CalculateShrinkageUseCase> calculateShrinkageUseCaseProvider) {
    this.stockRepositoryProvider = stockRepositoryProvider;
    this.calculateShrinkageUseCaseProvider = calculateShrinkageUseCaseProvider;
  }

  @Override
  public UpdateShrinkageUseCase get() {
    return newInstance(stockRepositoryProvider.get(), calculateShrinkageUseCaseProvider.get());
  }

  public static UpdateShrinkageUseCase_Factory create(
      Provider<StockRepository> stockRepositoryProvider,
      Provider<CalculateShrinkageUseCase> calculateShrinkageUseCaseProvider) {
    return new UpdateShrinkageUseCase_Factory(stockRepositoryProvider, calculateShrinkageUseCaseProvider);
  }

  public static UpdateShrinkageUseCase newInstance(StockRepository stockRepository,
      CalculateShrinkageUseCase calculateShrinkageUseCase) {
    return new UpdateShrinkageUseCase(stockRepository, calculateShrinkageUseCase);
  }
}
