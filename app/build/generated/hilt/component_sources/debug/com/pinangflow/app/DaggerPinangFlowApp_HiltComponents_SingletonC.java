package com.pinangflow.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinangflow.app.data.remote.WhatsAppApiService;
import com.pinangflow.app.data.repository.AuthRepositoryImpl;
import com.pinangflow.app.data.repository.BatchRepositoryImpl;
import com.pinangflow.app.data.repository.ExportRepositoryImpl;
import com.pinangflow.app.data.repository.FarmerRepositoryImpl;
import com.pinangflow.app.data.repository.ProfileRepositoryImpl;
import com.pinangflow.app.data.repository.StockRepositoryImpl;
import com.pinangflow.app.data.repository.WhatsAppHelper;
import com.pinangflow.app.di.NetworkModule_ProvideFirebaseAuthFactory;
import com.pinangflow.app.di.NetworkModule_ProvideFirestoreFactory;
import com.pinangflow.app.di.NetworkModule_ProvideOkHttpClientFactory;
import com.pinangflow.app.di.NetworkModule_ProvideWhatsAppApiServiceFactory;
import com.pinangflow.app.domain.repository.AuthRepository;
import com.pinangflow.app.domain.repository.BatchRepository;
import com.pinangflow.app.domain.repository.ExportRepository;
import com.pinangflow.app.domain.repository.FarmerRepository;
import com.pinangflow.app.domain.repository.ProfileRepository;
import com.pinangflow.app.domain.repository.StockRepository;
import com.pinangflow.app.domain.usecase.CalculateShrinkageUseCase;
import com.pinangflow.app.domain.usecase.UpdateShrinkageUseCase;
import com.pinangflow.app.presentation.viewmodel.AuthViewModel;
import com.pinangflow.app.presentation.viewmodel.AuthViewModel_HiltModules;
import com.pinangflow.app.presentation.viewmodel.BatchViewModel;
import com.pinangflow.app.presentation.viewmodel.BatchViewModel_HiltModules;
import com.pinangflow.app.presentation.viewmodel.ExportViewModel;
import com.pinangflow.app.presentation.viewmodel.ExportViewModel_HiltModules;
import com.pinangflow.app.presentation.viewmodel.FarmerViewModel;
import com.pinangflow.app.presentation.viewmodel.FarmerViewModel_HiltModules;
import com.pinangflow.app.presentation.viewmodel.InventarisViewModel;
import com.pinangflow.app.presentation.viewmodel.InventarisViewModel_HiltModules;
import com.pinangflow.app.presentation.viewmodel.ProfileViewModel;
import com.pinangflow.app.presentation.viewmodel.ProfileViewModel_HiltModules;
import com.pinangflow.app.worker.ShrinkageWorker;
import com.pinangflow.app.worker.ShrinkageWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class DaggerPinangFlowApp_HiltComponents_SingletonC {
  private DaggerPinangFlowApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static PinangFlowApp_HiltComponents.SingletonC create() {
    return new Builder().build();
  }

  public static final class Builder {
    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public PinangFlowApp_HiltComponents.SingletonC build() {
      return new SingletonCImpl();
    }
  }

  private static final class ActivityRetainedCBuilder implements PinangFlowApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements PinangFlowApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements PinangFlowApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements PinangFlowApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements PinangFlowApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements PinangFlowApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements PinangFlowApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public PinangFlowApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends PinangFlowApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends PinangFlowApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends PinangFlowApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends PinangFlowApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(6).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_BatchViewModel, BatchViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_ExportViewModel, ExportViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_FarmerViewModel, FarmerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_InventarisViewModel, InventarisViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_ProfileViewModel, ProfileViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_pinangflow_app_presentation_viewmodel_BatchViewModel = "com.pinangflow.app.presentation.viewmodel.BatchViewModel";

      static String com_pinangflow_app_presentation_viewmodel_InventarisViewModel = "com.pinangflow.app.presentation.viewmodel.InventarisViewModel";

      static String com_pinangflow_app_presentation_viewmodel_AuthViewModel = "com.pinangflow.app.presentation.viewmodel.AuthViewModel";

      static String com_pinangflow_app_presentation_viewmodel_FarmerViewModel = "com.pinangflow.app.presentation.viewmodel.FarmerViewModel";

      static String com_pinangflow_app_presentation_viewmodel_ProfileViewModel = "com.pinangflow.app.presentation.viewmodel.ProfileViewModel";

      static String com_pinangflow_app_presentation_viewmodel_ExportViewModel = "com.pinangflow.app.presentation.viewmodel.ExportViewModel";

      @KeepFieldType
      BatchViewModel com_pinangflow_app_presentation_viewmodel_BatchViewModel2;

      @KeepFieldType
      InventarisViewModel com_pinangflow_app_presentation_viewmodel_InventarisViewModel2;

      @KeepFieldType
      AuthViewModel com_pinangflow_app_presentation_viewmodel_AuthViewModel2;

      @KeepFieldType
      FarmerViewModel com_pinangflow_app_presentation_viewmodel_FarmerViewModel2;

      @KeepFieldType
      ProfileViewModel com_pinangflow_app_presentation_viewmodel_ProfileViewModel2;

      @KeepFieldType
      ExportViewModel com_pinangflow_app_presentation_viewmodel_ExportViewModel2;
    }
  }

  private static final class ViewModelCImpl extends PinangFlowApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<BatchViewModel> batchViewModelProvider;

    private Provider<ExportViewModel> exportViewModelProvider;

    private Provider<FarmerViewModel> farmerViewModelProvider;

    private Provider<InventarisViewModel> inventarisViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.batchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.exportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.farmerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.inventarisViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(6).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_BatchViewModel, ((Provider) batchViewModelProvider)).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_ExportViewModel, ((Provider) exportViewModelProvider)).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_FarmerViewModel, ((Provider) farmerViewModelProvider)).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_InventarisViewModel, ((Provider) inventarisViewModelProvider)).put(LazyClassKeyProvider.com_pinangflow_app_presentation_viewmodel_ProfileViewModel, ((Provider) profileViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_pinangflow_app_presentation_viewmodel_ExportViewModel = "com.pinangflow.app.presentation.viewmodel.ExportViewModel";

      static String com_pinangflow_app_presentation_viewmodel_BatchViewModel = "com.pinangflow.app.presentation.viewmodel.BatchViewModel";

      static String com_pinangflow_app_presentation_viewmodel_InventarisViewModel = "com.pinangflow.app.presentation.viewmodel.InventarisViewModel";

      static String com_pinangflow_app_presentation_viewmodel_ProfileViewModel = "com.pinangflow.app.presentation.viewmodel.ProfileViewModel";

      static String com_pinangflow_app_presentation_viewmodel_FarmerViewModel = "com.pinangflow.app.presentation.viewmodel.FarmerViewModel";

      static String com_pinangflow_app_presentation_viewmodel_AuthViewModel = "com.pinangflow.app.presentation.viewmodel.AuthViewModel";

      @KeepFieldType
      ExportViewModel com_pinangflow_app_presentation_viewmodel_ExportViewModel2;

      @KeepFieldType
      BatchViewModel com_pinangflow_app_presentation_viewmodel_BatchViewModel2;

      @KeepFieldType
      InventarisViewModel com_pinangflow_app_presentation_viewmodel_InventarisViewModel2;

      @KeepFieldType
      ProfileViewModel com_pinangflow_app_presentation_viewmodel_ProfileViewModel2;

      @KeepFieldType
      FarmerViewModel com_pinangflow_app_presentation_viewmodel_FarmerViewModel2;

      @KeepFieldType
      AuthViewModel com_pinangflow_app_presentation_viewmodel_AuthViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pinangflow.app.presentation.viewmodel.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.bindAuthRepositoryProvider.get());

          case 1: // com.pinangflow.app.presentation.viewmodel.BatchViewModel 
          return (T) new BatchViewModel(singletonCImpl.bindBatchRepositoryProvider.get(), singletonCImpl.bindFarmerRepositoryProvider.get());

          case 2: // com.pinangflow.app.presentation.viewmodel.ExportViewModel 
          return (T) new ExportViewModel(singletonCImpl.bindExportRepositoryProvider.get());

          case 3: // com.pinangflow.app.presentation.viewmodel.FarmerViewModel 
          return (T) new FarmerViewModel(singletonCImpl.bindFarmerRepositoryProvider.get());

          case 4: // com.pinangflow.app.presentation.viewmodel.InventarisViewModel 
          return (T) new InventarisViewModel(singletonCImpl.bindStockRepositoryProvider.get());

          case 5: // com.pinangflow.app.presentation.viewmodel.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.whatsAppHelper());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends PinangFlowApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends PinangFlowApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends PinangFlowApp_HiltComponents.SingletonC {
    private final SingletonCImpl singletonCImpl = this;

    private Provider<FirebaseFirestore> provideFirestoreProvider;

    private Provider<StockRepositoryImpl> stockRepositoryImplProvider;

    private Provider<StockRepository> bindStockRepositoryProvider;

    private Provider<ShrinkageWorker_AssistedFactory> shrinkageWorker_AssistedFactoryProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<AuthRepository> bindAuthRepositoryProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<WhatsAppApiService> provideWhatsAppApiServiceProvider;

    private Provider<FarmerRepositoryImpl> farmerRepositoryImplProvider;

    private Provider<FarmerRepository> bindFarmerRepositoryProvider;

    private Provider<BatchRepositoryImpl> batchRepositoryImplProvider;

    private Provider<BatchRepository> bindBatchRepositoryProvider;

    private Provider<ExportRepositoryImpl> exportRepositoryImplProvider;

    private Provider<ExportRepository> bindExportRepositoryProvider;

    private Provider<ProfileRepositoryImpl> profileRepositoryImplProvider;

    private Provider<ProfileRepository> bindProfileRepositoryProvider;

    private SingletonCImpl() {

      initialize();

    }

    private UpdateShrinkageUseCase updateShrinkageUseCase() {
      return new UpdateShrinkageUseCase(bindStockRepositoryProvider.get(), new CalculateShrinkageUseCase());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return ImmutableMap.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>of("com.pinangflow.app.worker.ShrinkageWorker", ((Provider) shrinkageWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private WhatsAppHelper whatsAppHelper() {
      return new WhatsAppHelper(provideWhatsAppApiServiceProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 2));
      this.stockRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 1);
      this.bindStockRepositoryProvider = DoubleCheck.provider((Provider) stockRepositoryImplProvider);
      this.shrinkageWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<ShrinkageWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 4));
      this.authRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 3);
      this.bindAuthRepositoryProvider = DoubleCheck.provider((Provider) authRepositoryImplProvider);
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 7));
      this.provideWhatsAppApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<WhatsAppApiService>(singletonCImpl, 6));
      this.farmerRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 8);
      this.bindFarmerRepositoryProvider = DoubleCheck.provider((Provider) farmerRepositoryImplProvider);
      this.batchRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 5);
      this.bindBatchRepositoryProvider = DoubleCheck.provider((Provider) batchRepositoryImplProvider);
      this.exportRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 9);
      this.bindExportRepositoryProvider = DoubleCheck.provider((Provider) exportRepositoryImplProvider);
      this.profileRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 10);
      this.bindProfileRepositoryProvider = DoubleCheck.provider((Provider) profileRepositoryImplProvider);
    }

    @Override
    public void injectPinangFlowApp(PinangFlowApp pinangFlowApp) {
      injectPinangFlowApp2(pinangFlowApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private PinangFlowApp injectPinangFlowApp2(PinangFlowApp instance) {
      PinangFlowApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pinangflow.app.worker.ShrinkageWorker_AssistedFactory 
          return (T) new ShrinkageWorker_AssistedFactory() {
            @Override
            public ShrinkageWorker create(Context context, WorkerParameters params) {
              return new ShrinkageWorker(context, params, singletonCImpl.updateShrinkageUseCase());
            }
          };

          case 1: // com.pinangflow.app.data.repository.StockRepositoryImpl 
          return (T) new StockRepositoryImpl(singletonCImpl.provideFirestoreProvider.get());

          case 2: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) NetworkModule_ProvideFirestoreFactory.provideFirestore();

          case 3: // com.pinangflow.app.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirestoreProvider.get());

          case 4: // com.google.firebase.auth.FirebaseAuth 
          return (T) NetworkModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 5: // com.pinangflow.app.data.repository.BatchRepositoryImpl 
          return (T) new BatchRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.whatsAppHelper(), singletonCImpl.bindFarmerRepositoryProvider.get());

          case 6: // com.pinangflow.app.data.remote.WhatsAppApiService 
          return (T) NetworkModule_ProvideWhatsAppApiServiceFactory.provideWhatsAppApiService(singletonCImpl.provideOkHttpClientProvider.get());

          case 7: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 8: // com.pinangflow.app.data.repository.FarmerRepositoryImpl 
          return (T) new FarmerRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.whatsAppHelper());

          case 9: // com.pinangflow.app.data.repository.ExportRepositoryImpl 
          return (T) new ExportRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.whatsAppHelper());

          case 10: // com.pinangflow.app.data.repository.ProfileRepositoryImpl 
          return (T) new ProfileRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
