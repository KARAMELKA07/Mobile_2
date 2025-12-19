# Практическая работа № 7

## 1. BottomNavigationApp

Был создан новый модуль **BottomNavigationApp**, в котором реализована навигация между тремя основными разделами через нижнее меню (BottomNavigationView) с использованием Navigation Component и View Binding.

**1) build.gradle.kts (модуль BottomNavigationApp)**
 Подключены зависимости Navigation Component 

```
implementation("androidx.navigation:navigation-fragment:2.8.4")
implementation("androidx.navigation:navigation-ui:2.8.4")
```

и включен View Binding для генерации binding-классов.

```
buildFeatures {
    viewBinding = true
}
```

**2) AndroidManifest.xml**
 Назначена стартовая Activity (`MainActivity`), подключена тема приложения.

**3) MainActivity.java**
 Получен `NavController` из `NavHostFragment`, настроены `AppBarConfiguration` и привязка `BottomNavigationView` к `NavController` через `NavigationUI`.

```java
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment == null) {
            return;
        }

        navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_info,
                R.id.navigation_profile
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
```

**4) res/layout/activity_main.xml**
 Размещены `NavHostFragment` (контейнер для фрагментов) и `BottomNavigationView` (нижнее меню).

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimary"
        android:theme="@style/ThemeOverlay.MaterialComponents.Dark.ActionBar"
        app:titleTextColor="@color/on_primary"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph"
        app:layout_constraintTop_toBottomOf="@id/toolbar"
        app:layout_constraintBottom_toTopOf="@id/nav_view"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/nav_view"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="@color/surface"
        app:menu="@menu/bottom_nav_menu"
        app:itemIconTint="@color/bottom_nav_item_colors"
        app:itemTextColor="@color/bottom_nav_item_colors"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

**5) res/navigation/nav_graph.xml**
 Описан граф навигации: три destination (Home/Info/Profile) и стартовый экран.

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="mirea.zakirovakr.bottomnavigationapp.HomeFragment"
        android:label="Home" />

    <fragment
        android:id="@+id/navigation_info"
        android:name="mirea.zakirovakr.bottomnavigationapp.InfoFragment"
        android:label="Info" />

    <fragment
        android:id="@+id/navigation_profile"
        android:name="mirea.zakirovakr.bottomnavigationapp.ProfileFragment"
        android:label="Profile" />

</navigation>
```

**6) res/menu/bottom_nav_menu.xml**
 Описаны пункты нижнего меню и их идентификаторы, совпадающие с destination в графе.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/navigation_home"
        android:icon="@drawable/ic_home_24"
        android:title="Home" />

    <item
        android:id="@+id/navigation_info"
        android:icon="@drawable/ic_info_24"
        android:title="Info" />

    <item
        android:id="@+id/navigation_profile"
        android:icon="@drawable/ic_profile_24"
        android:title="Profile" />

</menu>
```

**7) Фрагменты (Java + layout):**

- `HomeFragment.java` / `fragment_home.xml` — экран раздела Home, работа с UI через View Binding.

  ```java
  public class HomeFragment extends Fragment {
  
      private FragmentHomeBinding binding;
  
      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater,
                               @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
          binding = FragmentHomeBinding.inflate(inflater, container, false);
  
          binding.title.setText("Главная");
          binding.subtitle.setText("Здесь может быть список растений или быстрые действия.");
  
          return binding.getRoot();
      }
  
      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }
  ```

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <androidx.constraintlayout.widget.ConstraintLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:padding="16dp">
  
      <TextView
          android:id="@+id/title"
          android:layout_width="0dp"
          android:layout_height="wrap_content"
          android:textSize="24sp"
          android:textStyle="bold"
          android:textColor="@color/on_background"
          app:layout_constraintTop_toTopOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintEnd_toEndOf="parent"/>
  
      <TextView
          android:id="@+id/subtitle"
          android:layout_width="0dp"
          android:layout_height="wrap_content"
          android:layout_marginTop="8dp"
          android:textSize="16sp"
          android:textColor="@color/on_background"
          app:layout_constraintTop_toBottomOf="@id/title"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintEnd_toEndOf="parent"/>
  
  </androidx.constraintlayout.widget.ConstraintLayout>
  ```

- `InfoFragment.java` / `fragment_info.xml` — экран раздела Info, работа с UI через View Binding.

  ```java
  public class InfoFragment extends Fragment {
  
      private FragmentInfoBinding binding;
  
      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater,
                               @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
          binding = FragmentInfoBinding.inflate(inflater, container, false);
  
          binding.title.setText("Информация");
          binding.subtitle.setText("Советы по уходу, справка, FAQ и т. п.");
  
          return binding.getRoot();
      }
  
      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }
  ```

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <androidx.constraintlayout.widget.ConstraintLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:padding="16dp">
  
      <TextView
          android:id="@+id/title"
          android:layout_width="0dp"
          android:layout_height="wrap_content"
          android:textSize="24sp"
          android:textStyle="bold"
          android:textColor="@color/on_background"
          app:layout_constraintTop_toTopOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintEnd_toEndOf="parent"/>
  
      <TextView
          android:id="@+id/subtitle"
          android:layout_width="0dp"
          android:layout_height="wrap_content"
          android:layout_marginTop="8dp"
          android:textSize="16sp"
          android:textColor="@color/on_background"
          app:layout_constraintTop_toBottomOf="@id/title"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintEnd_toEndOf="parent"/>
  
  </androidx.constraintlayout.widget.ConstraintLayout>
  ```

- `ProfileFragment.java` / `fragment_profile.xml` — экран раздела Profile, работа с UI через View Binding.

  ```java
  public class ProfileFragment extends Fragment {
  
      private FragmentProfileBinding binding;
  
      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater,
                               @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
          binding = FragmentProfileBinding.inflate(inflater, container, false);
  
          binding.title.setText("Профиль");
          binding.subtitle.setText("Пользователь, настройки, предпочтения.");
  
          return binding.getRoot();
      }
  
      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }
  ```

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <TextView
        android:id="@+id/title"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="@color/on_background"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/subtitle"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:textSize="16sp"
        android:textColor="@color/on_background"
        app:layout_constraintTop_toBottomOf="@id/title"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

**8) Ресурсы оформления (иконки/цвета/темы)**

- `res/drawable/ic_home_24.xml`, `ic_info_24.xml`, `ic_profile_24.xml` — векторные иконки пунктов меню.
- `res/values/colors.xml`, `res/values/themes.xml`, `res/values-night/themes.xml` — обновленная цветовая гамма и темы.
- (опционально) `res/color/bottom_nav_item_colors.xml` — цвета активного/неактивного состояния пунктов меню.

### Демонстрация работы приложения (куда вставить картинки)

Вставьте скриншоты в отчет после этого блока:

1. Главный экран приложения с BottomNavigation 

   ![](./1)

   

2. Информация

   ![](./2)

   3. Профиль

      ![](./3)

## 2. NavigationDrawerApp

Был создан новый модуль **NavigationDrawerApp**, в котором реализована навигация через боковую шторку (Navigation Drawer) с использованием DrawerLayout, NavigationView и Navigation Component. Также реализовано закрытие шторки по кнопке «Back».

**1) build.gradle.kts (модуль NavigationDrawerApp)**
 Включен View Binding и добавлены зависимости Navigation Component.

**2) AndroidManifest.xml**
 Назначена стартовая Activity (`MainActivity`) и установлена тема модуля .

**3) res/values/themes.xml и res/values-night/themes.xml**
 Добавлены тема приложения и оверлеи для Toolbar, обновлена цветовая гамма.

**4) MainActivity.java**
 Настроены `DrawerLayout` и `NavigationView`, выполнена связка `NavigationUI` с `NavController` и `AppBarConfiguration`, реализовано закрытие шторки по кнопке Back (если открыта — закрываем, иначе выполняем стандартный back).

```java
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);

        if (navHostFragment == null) {
            return;
        }

        navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_info,
                R.id.nav_profile
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Закрытие шторки на кнопку Back
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return;
                }
                // если шторка закрыта — обычное поведение Back (NavController/back stack)
                setEnabled(false);
                getOnBackPressedDispatcher().onBackPressed();
                setEnabled(true);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
```

**5) Разметка Activity и контейнеров:**

- `res/layout/activity_main.xml` — корневой `DrawerLayout`, внутри include `app_bar_main` и `NavigationView`.

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <androidx.drawerlayout.widget.DrawerLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:id="@+id/drawer_layout"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:fitsSystemWindows="true"
      tools:openDrawer="start">
  
      <include
          android:id="@+id/app_bar_main"
          layout="@layout/app_bar_main"
          android:layout_width="match_parent"
          android:layout_height="match_parent" />
  
      <com.google.android.material.navigation.NavigationView
          android:id="@+id/nav_view"
          android:layout_width="wrap_content"
          android:layout_height="match_parent"
          android:layout_gravity="start"
          android:fitsSystemWindows="true"
          app:headerLayout="@layout/nav_header_main"
          app:menu="@menu/drawer_menu" />
  
  </androidx.drawerlayout.widget.DrawerLayout>
  ```

- `res/layout/app_bar_main.xml` — `AppBarLayout` + `Toolbar`, подключение `content_main`.

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <androidx.coordinatorlayout.widget.CoordinatorLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      tools:context=".MainActivity">
  
      <com.google.android.material.appbar.AppBarLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:theme="@style/ThemeOverlay.NavigationDrawerApp.AppBarOverlay">
  
          <androidx.appcompat.widget.Toolbar
              android:id="@+id/toolbar"
              android:layout_width="match_parent"
              android:layout_height="?attr/actionBarSize"
              android:background="?attr/colorPrimary"
              app:popupTheme="@style/ThemeOverlay.NavigationDrawerApp.PopupOverlay"
              app:titleTextColor="@color/on_primary" />
  
      </com.google.android.material.appbar.AppBarLayout>
  
      <include layout="@layout/content_main"/>
  
  </androidx.coordinatorlayout.widget.CoordinatorLayout>
  ```

- `res/layout/content_main.xml` — `NavHostFragment` (контейнер, где меняются фрагменты).

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <androidx.constraintlayout.widget.ConstraintLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:layout_behavior="@string/appbar_scrolling_view_behavior"
      tools:showIn="@layout/app_bar_main">
  
      <androidx.fragment.app.FragmentContainerView
          android:id="@+id/nav_host"
          android:name="androidx.navigation.fragment.NavHostFragment"
          android:layout_width="0dp"
          android:layout_height="0dp"
          app:defaultNavHost="true"
          app:navGraph="@navigation/nav_graph"
          app:layout_constraintTop_toTopOf="parent"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintEnd_toEndOf="parent" />
  
  </androidx.constraintlayout.widget.ConstraintLayout>
  ```

- `res/layout/nav_header_main.xml` — шапка шторки (логотип/заголовок/подзаголовок).

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="176dp"
      android:background="@color/primary"
      android:gravity="bottom"
      android:orientation="vertical"
      android:padding="16dp"
      android:theme="@style/ThemeOverlay.AppCompat.Dark">
  
      <ImageView
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:paddingTop="8dp"
          app:srcCompat="@mipmap/ic_launcher_round" />
  
      <TextView
          android:id="@+id/header_title"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:paddingTop="8dp"
          android:text="NavigationDrawerApp"
          android:textSize="18sp"
          android:textStyle="bold" />
  
      <TextView
          android:id="@+id/header_subtitle"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="Меню разделов" />
  
  </LinearLayout>
  ```

**6) res/navigation/nav_graph.xml**
 Описан граф навигации для пунктов шторки (Home/Info/Profile) и стартовый пункт.

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/navigation"
    app:startDestination="@id/nav_home">

    <fragment
        android:id="@+id/nav_home"
        android:name="mirea.zakirovakr.navigationdrawerapp.ui.home.HomeFragment"
        android:label="Home" />

    <fragment
        android:id="@+id/nav_info"
        android:name="mirea.zakirovakr.navigationdrawerapp.ui.info.InfoFragment"
        android:label="Info" />

    <fragment
        android:id="@+id/nav_profile"
        android:name="mirea.zakirovakr.navigationdrawerapp.ui.profile.ProfileFragment"
        android:label="Profile" />

</navigation>
```

**7) res/menu/drawer_menu.xml**
 Описаны пункты меню Navigation Drawer и иконки, id совпадают с destination графа.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/nav_home"
        android:icon="@drawable/ic_home_24"
        android:title="Home" />

    <item
        android:id="@+id/nav_info"
        android:icon="@drawable/ic_info_24"
        android:title="Info" />

    <item
        android:id="@+id/nav_profile"
        android:icon="@drawable/ic_profile_24"
        android:title="Profile" />

</menu>
```

**8) Фрагменты (Java + layout):**

- `HomeFragment.java` / `fragment_home.xml` — раздел Home, отображение данных через View Binding.

  ```java
  public class HomeFragment extends Fragment {
  
      private FragmentHomeBinding binding;
  
      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater,
                               @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
          binding = FragmentHomeBinding.inflate(inflater, container, false);
          binding.title.setText("Home");
          binding.subtitle.setText("Главный раздел приложения (пример).");
          return binding.getRoot();
      }
  
      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }
  ```

- `InfoFragment.java` / `fragment_info.xml` — раздел Info, отображение данных через View Binding.

  ```java
  public class InfoFragment extends Fragment {
  
      private FragmentInfoBinding binding;
  
      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater,
                               @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
          binding = FragmentInfoBinding.inflate(inflater, container, false);
          binding.title.setText("Info");
          binding.subtitle.setText("Справка/информация (пример).");
          return binding.getRoot();
      }
  
      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }
  ```

- `ProfileFragment.java` / `fragment_profile.xml` — раздел Profile, отображение данных через View Binding.

  ```java
  public class ProfileFragment extends Fragment {
  
      private FragmentProfileBinding binding;
  
      @Nullable
      @Override
      public View onCreateView(@NonNull LayoutInflater inflater,
                               @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
          binding = FragmentProfileBinding.inflate(inflater, container, false);
          binding.title.setText("Profile");
          binding.subtitle.setText("Профиль пользователя (пример).");
          return binding.getRoot();
      }
  
      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }
  ```

**9) Ресурсы иконок**

- `res/drawable/ic_home_24.xml`, `ic_info_24.xml`, `ic_profile_24.xml` — иконки пунктов меню шторки.

### Демонстрация работы приложения (куда вставить картинки)

Вставьте скриншоты в отчет после этого блока:

1. Меню

   ![](./4)

2. Главный экран приложения 

![](./5)

3. Информация

   ![](./6)

4. Профиль

![](./7)