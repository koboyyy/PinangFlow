package com.pinangflow.app.di;

import com.pinangflow.app.data.local.PinangDatabase;
import com.pinangflow.app.data.local.StockDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideStockDaoFactory implements Factory<StockDao> {
  private final Provider<PinangDatabase> databaseProvider;

  public DatabaseModule_ProvideStockDaoFactory(Provider<PinangDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StockDao get() {
    return provideStockDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStockDaoFactory create(
      Provider<PinangDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStockDaoFactory(databaseProvider);
  }

  public static StockDao provideStockDao(PinangDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStockDao(database));
  }
}
