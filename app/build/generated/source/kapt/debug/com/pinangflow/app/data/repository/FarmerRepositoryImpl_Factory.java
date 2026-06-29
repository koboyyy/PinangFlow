package com.pinangflow.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
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
public final class FarmerRepositoryImpl_Factory implements Factory<FarmerRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<WhatsAppHelper> whatsappHelperProvider;

  public FarmerRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<WhatsAppHelper> whatsappHelperProvider) {
    this.firestoreProvider = firestoreProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.whatsappHelperProvider = whatsappHelperProvider;
  }

  @Override
  public FarmerRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), firebaseAuthProvider.get(), whatsappHelperProvider.get());
  }

  public static FarmerRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<WhatsAppHelper> whatsappHelperProvider) {
    return new FarmerRepositoryImpl_Factory(firestoreProvider, firebaseAuthProvider, whatsappHelperProvider);
  }

  public static FarmerRepositoryImpl newInstance(FirebaseFirestore firestore,
      FirebaseAuth firebaseAuth, WhatsAppHelper whatsappHelper) {
    return new FarmerRepositoryImpl(firestore, firebaseAuth, whatsappHelper);
  }
}
