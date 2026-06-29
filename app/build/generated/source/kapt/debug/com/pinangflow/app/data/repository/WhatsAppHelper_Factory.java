package com.pinangflow.app.data.repository;

import com.pinangflow.app.data.remote.WhatsAppApiService;
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
public final class WhatsAppHelper_Factory implements Factory<WhatsAppHelper> {
  private final Provider<WhatsAppApiService> apiServiceProvider;

  public WhatsAppHelper_Factory(Provider<WhatsAppApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public WhatsAppHelper get() {
    return newInstance(apiServiceProvider.get());
  }

  public static WhatsAppHelper_Factory create(Provider<WhatsAppApiService> apiServiceProvider) {
    return new WhatsAppHelper_Factory(apiServiceProvider);
  }

  public static WhatsAppHelper newInstance(WhatsAppApiService apiService) {
    return new WhatsAppHelper(apiService);
  }
}
