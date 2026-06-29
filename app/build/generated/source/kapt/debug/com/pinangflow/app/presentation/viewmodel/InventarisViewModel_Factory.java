package com.pinangflow.app.presentation.viewmodel;

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
public final class InventarisViewModel_Factory implements Factory<InventarisViewModel> {
  private final Provider<StockRepository> stockRepositoryProvider;

  public InventarisViewModel_Factory(Provider<StockRepository> stockRepositoryProvider) {
    this.stockRepositoryProvider = stockRepositoryProvider;
  }

  @Override
  public InventarisViewModel get() {
    return newInstance(stockRepositoryProvider.get());
  }

  public static InventarisViewModel_Factory create(
      Provider<StockRepository> stockRepositoryProvider) {
    return new InventarisViewModel_Factory(stockRepositoryProvider);
  }

  public static InventarisViewModel newInstance(StockRepository stockRepository) {
    return new InventarisViewModel(stockRepository);
  }
}
