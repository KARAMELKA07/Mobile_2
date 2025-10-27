package ru.mirea.zakirovakr.data.data.remote;

public class WeatherCodeMapper {

    public static String toRu(int code) {
        if (code == 0) return "Ясно";
        if (code == 1 || code == 2) return "Переменная облачность";
        if (code == 3) return "Пасмурно";
        if (code == 45 || code == 48) return "Туман";
        if (code >= 51 && code <= 57) return "Морось";
        if (code >= 61 && code <= 67) return "Дождь";
        if (code >= 71 && code <= 77) return "Снег";
        if (code >= 80 && code <= 82) return "Ливни";
        if (code >= 95 && code <= 99) return "Гроза";
        return "Неизвестно";
    }

    public static String toOwIconCode(int code) {
        if (code == 0) return "01d";
        if (code == 1 || code == 2) return "02d";
        if (code == 3) return "03d";
        if (code == 45 || code == 48) return "50d";
        if ((code >= 51 && code <= 57) || (code >= 61 && code <= 67))
            return "10d";
        if (code >= 71 && code <= 77) return "13d";
        if (code >= 80 && code <= 82) return "09d";
        if (code >= 95 && code <= 99) return "11d";
        return "03d";
    }

    public static String owIconUrl(String iconCode) {
        return "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
    }
}

