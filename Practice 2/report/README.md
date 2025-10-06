# Практическая работа №2

## 1 ПОСТРОЕНИЕ МОДУЛЬНОГО ПРОЕКТА

​	MovieD.java - Доменная модель данных, содержащая идентификатор и название фильма

```java
public class MovieD {
    private final int id;
    private final String name;

    public MovieD(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

​	MovieRepository.java \- Интерфейс репозитория для абстракции операций с данными фильмов

```java
public interface MovieRepository {
    boolean saveMovie(MovieD movie);
    MovieD getMovie();
}
```

​	GetFavoriteFilmUseCase.java \- Use case для получения избранного фильма из репозитория

```java
public class GetFavoriteFilmUseCase {
    private final MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieD execute() {
        return movieRepository.getMovie();
    }
}
```

​	SaveMovieToFavoriteUseCase.java \- Use case для сохранения фильма в избранное через репозиторий

```java
public class SaveMovieToFavoriteUseCase {
    private final MovieRepository movieRepository;

    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(MovieD movie) {
        return movieRepository.saveMovie(movie);
    }
}
```

​	Movie.java \- Модель данных слоя хранения с дополнительным полем даты сохранения

```java
public class Movie {
    private final int id;
    private final String name;
    private final String localDate;

    public Movie(int id, String name, String localDate) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLocalDate() {
        return localDate;
    }
}
```

​	MovieStorage.java \- Интерфейс хранилища для абстракции механизма сохранения данных

```java
public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
}
```

​	SharedPrefMovieStorage.java \- Реализация хранения данных в SharedPreferences с сохранением даты

```java
public class SharedPrefMovieStorage implements MovieStorage {
    private static final String SHARED_PREFS_NAME = "shared_prefs_name";
    private static final String KEY = "movie_name";
    private static final String DATE_KEY = "movie_date";
    private static final String ID_KEY = "movie_id";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Movie get() {
        String movieName = sharedPreferences.getString(KEY, "unknown");
        String movieDate = sharedPreferences.getString(DATE_KEY, String.valueOf(LocalDate.now()));
        int movieId = sharedPreferences.getInt(ID_KEY, -1);
        return new Movie(movieId, movieName, movieDate);
    }

    @Override
    public boolean save(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, movie.getName());
        editor.putString(DATE_KEY, String.valueOf(LocalDate.now()));
        editor.putInt(ID_KEY, 1);
        return editor.commit();
    }
}
```

​	MovieRepositoryImpl.java \- Реализация репозитория с мапперами для преобразования между доменной и data моделями

```java
public class MovieRepositoryImpl implements MovieRepository {
    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(MovieD movie) {
        Movie storageMovie = mapToStorage(movie);
        return movieStorage.save(storageMovie);
    }

    @Override
    public MovieD getMovie() {
        Movie movie = movieStorage.get();
        return mapToDomain(movie);
    }

    private Movie mapToStorage(MovieD movie) {
        String name = movie.getName();
        return new Movie(2, name, LocalDate.now().toString());
    }

    private MovieD mapToDomain(Movie movie) {
        return new MovieD(movie.getId(), movie.getName());
    }
}
```

​	MainActivity.java \- Главная активность с UI для ввода, сохранения и отображения избранных фильмов

```java
public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private SaveMovieToFavoriteUseCase saveMovieUseCase;
    private GetFavoriteFilmUseCase getMovieUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация зависимостей
        SharedPrefMovieStorage storage = new SharedPrefMovieStorage(this);
        MovieRepository repository = new MovieRepositoryImpl(storage);
        saveMovieUseCase = new SaveMovieToFavoriteUseCase(repository);
        getMovieUseCase = new GetFavoriteFilmUseCase(repository);

        // Инициализация UI
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        Button saveButton = findViewById(R.id.saveButton);
        Button getButton = findViewById(R.id.getButton);

        saveButton.setOnClickListener(v -> saveMovie());
        getButton.setOnClickListener(v -> getMovie());
    }

    private void saveMovie() {
        String movieName = editText.getText().toString();
        if (!movieName.isEmpty()) {
            MovieD movie = new MovieD(1, movieName);
            saveMovieUseCase.execute(movie);
            editText.setText("");
        }
    }

    private void getMovie() {
        MovieD movie = getMovieUseCase.execute();
        textView.setText("Любимый фильм: " + movie.getName());
    }
}
```

​	activity_main.xml \- Layout с интерфейсом для ввода названия фильма и отображения результата

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/editText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Введите название фильма"
        android:layout_marginBottom="16dp" />

    <Button
        android:id="@+id/saveButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Сохранить фильм"
        android:layout_marginBottom="16dp" />

    <Button
        android:id="@+id/getButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Получить фильм"
        android:layout_marginBottom="16dp" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Любимый фильм будет отображен здесь"
        android:textSize="18sp" />

</LinearLayout>
```

​	Логика была разделена на три модуля: domain, data, app.

```kts
rootProject.name = "filmapp"
include(":app")
include(":data")
include(":domain")
```

​	Была реализована чистая архитектура в Android-приложении, где изучено и применено модульное разделение на три независимых слоя: domain с бизнес-логикой и use cases, data с репозиториями и хранилищами, и presentation с UI компонентами. Код использует принципы инверсии зависимостей через интерфейсы Repository и Storage, обеспечивая тестируемость и заменяемость компонентов, а также реализует преобразование моделей между слоями с помощью мапперов, что исключает прямые зависимости между domain и data модулями. В работе изучены механизмы внедрения зависимостей через конструкторы, инкапсуляция работы с SharedPreferences в отдельном классе хранилища, и организация коммуникации между слоями через use cases, где Activity взаимодействует только с бизнес-логикой, не зная о деталях реализации хранения данных.

![](.\1.png)

## 2 КОНТРОЛЬНОЕ ЗАДАНИЕ

​	Был нарисован прототип приложения в figma. 

![](.\2.png)

1. Требуется создать новые модули data и domain. Перенести соответствующий код приложения в данные модули. 
2. Создать новую активити и реализовать в ней страницу авторизации с использованием firebase auth. Логику работы с FB распределить между тремя модулями. (работа с firebase рассматривалась в прошлом семестре). 
3. В репозитории реализовать три способа обработки данных: 
   - SharedPreferences, информация о клиенте
   - Room и класс NetworkApi для работы с сетью с замоканными данными.