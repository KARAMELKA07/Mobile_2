package ru.mirea.zakirovakr.recyclerviewapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        EventRecyclerViewAdapter adapter = new EventRecyclerViewAdapter(getEvents());
        rv.setAdapter(adapter);

        rv.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
    }

    private List<Event> getEvents() {
        List<Event> list = new ArrayList<>();
        list.add(new Event("Подписание Великой хартии вольностей (1215)",
                "Король Иоанн подписал документ, ограничивший королевскую власть и закрепивший права знати.",
                "event_magnacarta"));

        list.add(new Event("Эпоха Возрождения (XIV–XVI вв.)",
                "Культурное возрождение Европы: искусство, наука, гуманизм, открытия Леонардо и Микеланджело.",
                "event_renaissance"));

        list.add(new Event("Открытие Америки Колумбом (1492)",
                "Путешествие Христофора Колумба, приведшее к началу эпохи Великих географических открытий.",
                "event_columbus"));

        list.add(new Event("Падение Константинополя (1453)",
                "Завершение Византийской империи; важный поворот в истории Европы и Османской империи.",
                "event_constantinople"));

        list.add(new Event("Печатный станок Гутенберга (ок. 1450)",
                "Революция в распространении знаний: книгопечатание стало массовым и доступным.",
                "event_printing_press"));

        list.add(new Event("Промышленная революция (XVIII–XIX вв.)",
                "Механизация производства, паровые машины, фабрики и урбанизация.",
                "event_industrial"));

        list.add(new Event("Французская революция (1789)",
                "Провозглашение прав человека, падение монархии и изменение политической карты Европы.",
                "event_french_revolution"));

        list.add(new Event("Первая мировая война (1914–1918)",
                "Глобальный конфликт, приведший к крушению империй и изменению международных отношений.",
                "event_wwi"));

        list.add(new Event("Вторая мировая война (1939–1945)",
                "Крупнейшая война в истории; Холокост; бомбардировки Хиросимы и Нагасаки.",
                "event_wwii"));

        list.add(new Event("Первый полет человека в космос (1961)",
                "Советский космонавт Юрий Гагарин совершил первый в истории человечества полёт в космос.",
                "event_gagarin"));


        list.add(new Event("Распад СССР (1991)",
                "Завершение существования Советского Союза, образование независимых государств.",
                "event_ussr_dissolution"));

        return list;
    }
}

