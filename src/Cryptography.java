import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Cryptography {
    static ArrayList<Character> alphabet = new ArrayList<>(Arrays.asList('А', 'а', 'Б', 'б', 'В', 'в', 'Г', 'г', 'Д',
        'д', 'Е', 'е', 'Ё', 'ё', 'Ж', 'ж', 'З', 'з', 'И', 'и', 'Й', 'й', 'К', 'к', 'Л', 'л', 'М', 'м', 'Н', 'н', 'О',
        'о', 'П', 'п', 'Р', 'р', 'С', 'с', 'Т', 'т', 'У', 'у', 'Ф', 'ф', 'Х', 'х', 'Ц', 'ц', 'Ч', 'ч', 'Ш', 'ш', 'Щ',
        'щ', 'Ъ', 'ъ', 'Ы', 'ы', 'Ь', 'ь', 'Э', 'э', 'Ю', 'ю', 'Я', 'я', '.', ',', '\"', ':', '–', '!', '?','\u0020'));
    public static void main(String[] args) {
        System.out.println("Добрый день! Выберите вариант в скобках.");
        actionDefinition();
    }

    public static void actionDefinition() {
        System.out.println("(1) Шифруем или (2) дешифруем текст?");
        String gottenString = gettingString();
        String path = gettingPath();
        if (gottenString.equals("1")) {
            encryption(path);
        } if (gottenString.equals("2")) {
            System.out.println("Методом (1) Brute Force или (2) на основе статистических данных?");
            String result = gettingString();
            if (result.equals("1")) {
                decryptionBruteForce(path);
            } else {
                decryptionBasedOnStatistics(path);
            }
        } else {
            actionDefinition();
        }
    }
    public static String gettingString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static String gettingPath() {
        System.out.println("Введите абсолютный путь к файлу с текстом.");
        String inputString = gettingString();
        Path path = Path.of(inputString);
        if (!(path.isAbsolute())) {
            System.out.println("Вы ввели не абсолютный путь к файлу!");
            gettingPath();
        }
        return inputString;
    }
    public static ArrayList<Character> readingFile(String path) {
        ArrayList<Character> text = new ArrayList<>();
        System.out.println("Читаем Ваш текст.");
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            while (reader.ready()) {
                String readerString = reader.readLine();
                for (char c : readerString.toCharArray()) {
                        text.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    public static void actionAfterEncryption(ArrayList<Character> newText, String path) {
        System.out.println("Полученный текст (1) вывести в консоль, записать (2) в тот же файл или (3) в новый файл?");
        String result = gettingString();
        if (result.contains("1")) {
            for (Character character : newText) {
                System.out.print(character);
            }
            System.out.println();
            anotherAction();
        } else if (result.contains("2")) {
            entryToFile(path, newText);
            anotherAction();
        } else if (result.contains("3")) {
            System.out.println("Введите адрес файла, в который будем записывать.");
            entryToFile(gettingPath(), newText);
            anotherAction();
        } else {
            actionAfterEncryption(newText, path);
        }
    }
    public static void entryToFile(String path, ArrayList<Character> text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            for (Character o : text) {
                writer.write(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void anotherAction() {
        System.out.println("Будем еще работать? (Да) / (нет)");
        String answer = gettingString();
        if (answer.equalsIgnoreCase("да")) {
            actionDefinition();
        } else {
            System.exit(0);
        }
    }

    public static HashMap<Character, Integer> counterCharsInMap(ArrayList<Character> text) {
        HashMap<Character, Integer> mapOfCharacter = new HashMap<>();
        for (Character character : text) {
                mapOfCharacter.put(character, 1);
        }
        int counter = 1;
        for (Character character : text) {
            counter = mapOfCharacter.get(character);
            mapOfCharacter.put(character, counter + 1);
            counter = 1;
        }
        return mapOfCharacter;
    }
    public static Double switchToDouble(String str) {
        String newStr = str.replace(",", ".");
        return Double.parseDouble(newStr) * 100;
    }
    public static HashMap<Character, Double> switchToPercents(HashMap<Character, Integer> map, int textSize) {
        HashMap<Character, Double> mapWithPercent = new HashMap<>();
        String number;
        try {
            for (Map.Entry<Character, Integer> characterIntegerEntry : map.entrySet()) {
                    number = String.format("%.12f", (double) characterIntegerEntry.getValue() / textSize);
                mapWithPercent.put(characterIntegerEntry.getKey(), switchToDouble(number));
            }
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return mapWithPercent;
    }
    public static ArrayList<Character> clearList(ArrayList<Character> list) {
        ArrayList<Character> result = new ArrayList<>();
        for (Character character : list) {
            if (alphabet.contains(character)) {
                result.add(character);
            }
        }
        return result;
    }
    public static ArrayList<Double> sortList(HashMap<Character, Double> map) {
        ArrayList<Double> sortList = new ArrayList<>(map.values());
        Collections.sort(sortList);
        Collections.reverse(sortList);
        return sortList;
    }
    public static HashMap<Character, Character> mapping(HashMap<Character, Double> map1, ArrayList<Double> list1, HashMap<Character, Double> map2, ArrayList<Double> list2) {
        HashMap<Character, Character> mapping = new HashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            mapping.put(takeAChar(map1, list1.get(i)), takeAChar(map2, list2.get(i)));
            for (Character e : mapping.keySet()) {
                map1.remove(e);
            }
        }
        return mapping;
    }
    public static Character takeAChar(HashMap<Character, Double> map, Double n) {
        Character character = 0;
        for (Map.Entry<Character, Double> e : map.entrySet()) {
            if (e.getValue().equals(n)) {
                character = e.getKey();
                break;
            }
        }
        return character;
    }

    public static void isCorrectText(ArrayList<Character> text, String path) {
        String answer = gettingString();
        if (answer.equalsIgnoreCase("да")) {
            System.out.println("Отлично!");
            actionAfterEncryption(text, path);
        } else {
            System.out.println("Попробуйте найти слово, в котором 1 или 2 буквы неправильные, " +
                    "но оно само точно угадывается. Мы можем заменить один символ на другой." +
                    " Постарайтесь менять только на редко встречающиеся символы. Учитывайте регистр! ");
            switchSymbol(text, path);
        }
    }
    public static void switchSymbol(ArrayList<Character> text, String path) {
        System.out.println("Какой символ заменим?");
        Character wrongLetter = gettingString().toCharArray()[0];
        System.out.println("На какой символ поменяем?");
        Character rightLetter = gettingString().toCharArray()[0];
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).equals(wrongLetter)) {
                text.set(i, rightLetter);
            }
        }
        for (int i = 0; i < 1000; i++) {
            System.out.print(text.get(i));
        }
        System.out.println();
        System.out.println("Продолжаем менять? (Да) / (нет)");
        String answer = gettingString();
        if (answer.equalsIgnoreCase("нет")) {
            actionAfterEncryption(text, path);
        } else {
            switchSymbol(text, path);
        }
    }
    public static int searchKey(ArrayList<Character> text) {
        HashMap<String, Integer> mapOfCharacter = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        Integer count;
        for (int i = 0; i < text.size() - 1; i++) {
            stringBuilder.append(text.get(i));
            stringBuilder.append(text.get(i + 1));
            if (mapOfCharacter.containsKey(stringBuilder.toString())) {
                count = mapOfCharacter.get(stringBuilder.toString());
                mapOfCharacter.put(stringBuilder.toString(), count + 1);
            } else {
                mapOfCharacter.put(stringBuilder.toString(), 1);
            }
            stringBuilder = new StringBuilder();
        }
        count = 1;
        for (Integer value : mapOfCharacter.values()) {
            if (count < value) {
                count = value;
            }
        }
        for (Map.Entry<String, Integer> entry : mapOfCharacter.entrySet()) {
            if (entry.getValue().equals(count)) {
                stringBuilder.append(entry.getKey());
                break;
            }
        }
        Character space = stringBuilder.toString().toCharArray()[1];
        int spacePlace = 0;
        for (int i = 0; i < alphabet.size(); i++) {
            if(alphabet.get(i).equals(space)) {
                spacePlace = i;
                break;
            }
        }
        return spacePlace + 1;
    }

    public static void encryption(String path) {
        System.out.println("Можем сдвинуть все символы на 1 - 73 позиций. На сколько сдвинем?");
        int shift = Integer.parseInt(gettingString());
        if (shift > 73 || shift < 1){
            System.out.println("Некорректное значение.");
            encryption(path);
        }
        ArrayList<Character> text = readingFile(path);
        ArrayList<Character> newText = new ArrayList<>();
        System.out.println("Шифруем Ваш текст.");
        for (Character character : text) {
            if (alphabet.contains(character)) {
                int i = alphabet.indexOf(character);
                if (i + shift < alphabet.size()) {
                    newText.add(alphabet.get(i + shift));
                } else {
                    newText.add(alphabet.get((i + shift) % alphabet.size()));
                }
            } else {
                newText.add(character);
            }
        }
        actionAfterEncryption(newText, path);
    }
    public static void decryptionBruteForce(String path) {
        ArrayList<Character> text = readingFile(path);
        ArrayList<Character> newText = new ArrayList<>();
        System.out.println("У вас есть ключ к тексту? (Да) / (нет)");
        String haveKey = gettingString();
        int shift = 0;
        if (haveKey.equalsIgnoreCase("да")) {
            System.out.println("Введите символ зашифрованного алфавита и реального без пробела с учетом регистра.");
            char[] letter = gettingString().toCharArray();
            int wrongIndex = 0;
            int rightIndex = 0;
            for (int i = 0; i < alphabet.size(); i++) {
                if (letter[0] == alphabet.get(i)) {
                    wrongIndex = i;
                }
                if (letter[1] == alphabet.get(i)) {
                    rightIndex = i;
                }
            }
            shift = wrongIndex - rightIndex;
        } else {
            System.out.println("Подбираем ключ к шифру.");
            shift = searchKey(text);
        }

        System.out.println("Расшифровываем текст.");
        int shiftWithCircle;
        for (Character character : text) {
            for (int j = 0; j < alphabet.size(); j++) {
                if (alphabet.contains(character)) {
                    if (alphabet.get(j).equals(character)) {
                        if (j - shift < 0) {
                            shiftWithCircle = (j - shift) + alphabet.size();
                            newText.add(alphabet.get(shiftWithCircle));
                        } else {
                            newText.add(alphabet.get(j - shift));
                        }
                        break;
                    }
                } else {
                    newText.add(character);
                    break;
                }
            }
        }
        actionAfterEncryption(newText, path);
        anotherAction();
    }
    public static void decryptionBasedOnStatistics(String path) {
        ArrayList<Character> text = readingFile(path);
        ArrayList<Character> clearText = clearList(text);

        System.out.println("Нам нужен большой по объему тект того же автора или того же жанра. Лучше всего," +
                " если зашифрованный текст будет частью оригинального текста.");
        String pathToAnotherText = gettingPath();

        ArrayList<Character> anotherText = readingFile(pathToAnotherText);
        ArrayList<Character> anotherClearText = clearList(anotherText);

        System.out.println("Составляем соответствие символов.");
        HashMap<Character, Integer> textInMap = counterCharsInMap(clearText);
        HashMap<Character, Integer> anotherTextInMap = counterCharsInMap(anotherClearText);

        HashMap<Character, Double> mapWithPercent = switchToPercents(textInMap, clearText.size());
        HashMap<Character, Double> anotherMapWithPercent = switchToPercents(anotherTextInMap, anotherClearText.size());

        ArrayList<Double> sortList = sortList(mapWithPercent);
        ArrayList<Double> anotherSortList = sortList(anotherMapWithPercent);

        if (sortList.size() > anotherSortList.size()) {
            int counter = (sortList.size() - anotherSortList.size());
            for (int i = 0; i < counter; i++) {
                sortList.remove(sortList.size() - 1);
            }
        }
        HashMap<Character, Character> mapping = mapping(mapWithPercent, sortList, anotherMapWithPercent, anotherSortList);

        for (int i = 0; i < text.size(); i++) {
            for (Map.Entry<Character, Character> e : mapping.entrySet()) {
                if (text.get(i).equals(e.getKey())) {
                    text.set(i, e.getValue());
                    break;
                }
            }
        }
        System.out.println("Выводим часть текста в консоль.");
        for (int i = 0; i < 5000; i++) {
            System.out.print(text.get(i));
        }
        System.out.println();
        System.out.println("Текст читаемый? (Да) / (нет)");
        isCorrectText(text, path);
    }
}
