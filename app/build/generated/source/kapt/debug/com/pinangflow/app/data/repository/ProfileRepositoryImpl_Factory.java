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
public final class ProfileRepositoryImpl_Factory implements Factory<ProfileRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public ProfileRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firestoreProvider = firestoreProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public ProfileRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), firebaseAuthProvider.get());
  }

  public static ProfileRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new ProfileRepositoryImpl_Factory(firestoreProvider, firebaseAuthProvider);
  }

  public static ProfileRepositoryImpl newInstance(FirebaseFirestore firestore,
      FirebaseAuth firebaseAuth) {
    return new ProfileRepositoryImpl(firestore, firebaseAuth);
  }
}
