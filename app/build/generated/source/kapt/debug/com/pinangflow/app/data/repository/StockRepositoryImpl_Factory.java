package com.pinangflow.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class StockRepositoryImpl_Factory implements Factory<StockRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public StockRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public StockRepositoryImpl get() {
    return newInstance(firestoreProvider.get());
  }

  public static StockRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new StockRepositoryImpl_Factory(firestoreProvider);
  }

  public static StockRepositoryImpl newInstance(FirebaseFirestore firestore) {
    return new StockRepositoryImpl(firestore);
  }
}
