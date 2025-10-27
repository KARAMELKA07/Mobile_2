package ru.mirea.zakirovakr.listviewapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ListView listViewBooks;

    private String[] books = new String[] {
            "Достоевский — Идиот", "Булгаков — Мастер и Маргарита", "Тургенев — Отцы и дети",
            "Толстой — Война и мир", "Пушкин — Евгений Онегин", "Гоголь — Мёртвые души",
            "Чехов — Вишнёвый сад", "Лермонтов — Герой нашего времени", "Набоков — Защита Лужина",
            "Солженицын — Архипелаг ГУЛАГ", "Кавка — Процесс", "Оруэлл — 1984",
            "Хаксли — О дивный новый мир", "Керуак — В дороге", "Кинг — Противостояние",
            "Хемингуэй — Старик и море", "Ремарк — Три товарища", "Стейнбек — Гроздья гнева",
            "Кальдерон — Жизнь есть сон", "Камю — Чума", "Флобер — Мадам Бовари",
            "Пруст — В сторону Свана", "Верн — Двадцать тысяч лье", "Дюма — Граф Монте-Кристо",
            "Сэлинджер — Над пропастью", "Брэдбери — 451° по Фаренгейту", "Азимов — Основание",
            "Кларк — 2001", "Толкин — Властелин колец", "Роулинг — Гарри Поттер",
            "Мартин — Игра престолов", "Рот — Дивергент", "Харуки Мураками — Норвежский лес",
            "Гришэм — Клиент"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewBooks = findViewById(R.id.listViewBooks);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                books
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text2.setText(getItem(position).toString());
                text1.setText(String.valueOf(position+1));
                return view;
            }
        };

        listViewBooks.setAdapter(adapter);
    }
}
