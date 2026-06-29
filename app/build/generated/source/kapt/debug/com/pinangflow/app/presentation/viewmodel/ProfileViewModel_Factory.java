package com.pinangflow.app.presentation.viewmodel;

import com.pinangflow.app.data.repository.WhatsAppHelper;
import com.pinangflow.app.domain.repository.ProfileRepository;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<WhatsAppHelper> whatsAppHelperProvider;

  public ProfileViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider,
      Provider<WhatsAppHelper> whatsAppHelperProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.whatsAppHelperProvider = whatsAppHelperProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(profileRepositoryProvider.get(), whatsAppHelperProvider.get());
  }

  public static ProfileViewModel_Factory create(
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<WhatsAppHelper> whatsAppHelperProvider) {
    return new ProfileViewModel_Factory(profileRepositoryProvider, whatsAppHelperProvider);
  }

  public static ProfileViewModel newInstance(ProfileRepository profileRepository,
      WhatsAppHelper whatsAppHelper) {
    return new ProfileViewModel(profileRepository, whatsAppHelper);
  }
}
