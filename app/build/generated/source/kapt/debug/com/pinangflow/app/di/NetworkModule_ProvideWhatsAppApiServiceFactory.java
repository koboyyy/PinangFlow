package com.pinangflow.app.di;

import com.pinangflow.app.data.remote.WhatsAppApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideWhatsAppApiServiceFactory implements Factory<WhatsAppApiService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvideWhatsAppApiServiceFactory(
      Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public WhatsAppApiService get() {
    return provideWhatsAppApiService(okHttpClientProvider.get());
  }

  public static NetworkModule_ProvideWhatsAppApiServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideWhatsAppApiServiceFactory(okHttpClientProvider);
  }

  public static WhatsAppApiService provideWhatsAppApiService(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWhatsAppApiService(okHttpClient));
  }
}
