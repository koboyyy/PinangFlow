package com.pinangflow.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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
public final class BatchRepositoryImpl_Factory implements Factory<BatchRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<WhatsAppHelper> whatsappHelperProvider;

  private final Provider<FarmerRepository> farmerRepositoryProvider;

  public BatchRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<WhatsAppHelper> whatsappHelperProvider,
      Provider<FarmerRepository> farmerRepositoryProvider) {
    this.firestoreProvider = firestoreProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.whatsappHelperProvider = whatsappHelperProvider;
    this.farmerRepositoryProvider = farmerRepositoryProvider;
  }

  @Override
  public BatchRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), firebaseAuthProvider.get(), whatsappHelperProvider.get(), farmerRepositoryProvider.get());
  }

  public static BatchRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<WhatsAppHelper> whatsappHelperProvider,
      Provider<FarmerRepository> farmerRepositoryProvider) {
    return new BatchRepositoryImpl_Factory(firestoreProvider, firebaseAuthProvider, whatsappHelperProvider, farmerRepositoryProvider);
  }

  public static BatchRepositoryImpl newInstance(FirebaseFirestore firestore,
      FirebaseAuth firebaseAuth, WhatsAppHelper whatsappHelper, FarmerRepository farmerRepository) {
    return new BatchRepositoryImpl(firestore, firebaseAuth, whatsappHelper, farmerRepository);
  }
}
