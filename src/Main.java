import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IncorrectDataException {
        Request("Введите пример: ");

    }

    public static void Request(String Req) throws IncorrectDataException {

        System.out.printf(Req);
        Scanner Prim = new Scanner(System.in);
        String StrIn = Prim.nextLine();
        System.out.println(Calc(StrIn));
        Question ("Продолжить работу? (ДА-Y / Нет-N) - ");

    }


    public static String Calc(String Str) throws IncorrectDataException {

        int Pos = 0;
        int ZnachA = 0;
        int ZnachB = 0;
        int Rezult = 0;
        int a = 9;
        boolean Rim = false;
        boolean Ara = false;
        String Z = "";
        String StrRez = "";


        //*****Удаление пробела********
        int indSpace = Str.indexOf((char) 32);
        if (indSpace != -1) {
            String[] Str2 = Str.split(" ");
            Str = "";
            for (int i = 0; i < Str2.length; i++) {
                Str = Str + Str2[i];
            }
        }
        //********************************

        int LenStr = Str.length();
        if (LenStr < 3 | LenStr > 9) throw new IncorrectDataException("Введено некорректное количества данных");

        int indPlus = Str.indexOf((char) 43); //Проверяем есть ли +
        int indMin = Str.indexOf((char) 45);   //Проверяем есть ли -
        int indMult = Str.indexOf((char) 42);  //Проверяем есть ли *
        int indDel = Str.indexOf((char) 47);   //Проверяем есть ли /

        //Проверяем есть ли знак
        if (indPlus == -1 & indMin == -1 & indMult == -1 & indDel == -1)
            throw new IncorrectDataException("Отсутствует знак выражения");
        if (indPlus != -1) {
            Pos = indPlus;
            Z = "+";
        }

        if (indMin != -1) {
            Pos = indMin;
            Z = "-";
        }
        if (indMult != -1) {
            Pos = indMult;
            Z = "*";
        }
        if (indDel != -1) {
            Pos = indDel;
            Z = "/";
        }

        String StNumA = Str.substring(0, Pos); //Значение А в строковом типе
        char[] SymbolsA = StNumA.toCharArray();
        StNumA="";
        for (int i = 0; i < SymbolsA.length; i++) {
            if(Character.isUpperCase(SymbolsA[i]) || Character.isLowerCase(SymbolsA[i])) {
                String SymbUp = String.valueOf(SymbolsA[i]).toUpperCase();
                StNumA = StNumA + SymbUp;
            }else StNumA = StNumA + SymbolsA[i];
        }

        String StNumB = Str.substring(Pos + 1, LenStr); //Значение В в строковом типе

        char[] SymbolsB = StNumB.toCharArray();
        StNumB="";
        for (int i = 0; i < SymbolsB.length; i++) {
            if(Character.isUpperCase(SymbolsB[i]) || Character.isLowerCase(SymbolsB[i])) {
                String SymbUp = String.valueOf(SymbolsB[i]).toUpperCase();
                StNumB = StNumB + SymbUp;
            }else StNumB = StNumB + SymbolsB[i];
        }

        if ( Z.equals("/") & StNumB.equals("0")) throw new IncorrectDataException("Деление на 0");

        String[] RimNumDes = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String[] RimNumEd = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] RimNumArr = new String[100];
        RimNumArr[99] = "C";
        String[] AraNum = new String[10];

        for (int i = 1; i <= 9; i++) {
            RimNumArr[i - 1] = RimNumEd[i];
        }

        for (int i = 1; i <= 10; i++) {
            AraNum[i - 1] = String.valueOf(i);
        }

        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                RimNumArr[a] = RimNumDes[i] + RimNumEd[j];
                a++;
            }
        }
        for (int i = 0; i <= 9; i++) {
            if (StNumA.equals(RimNumArr[i])) {
                ZnachA = i + 1;
                Rim = true;
            } else if (StNumA.equals(AraNum[i])) {
                ZnachA = i + 1;
                Ara = true;
            }
        }
        if (ZnachA == 0) throw new IncorrectDataException("Первое значение недопустимого типа или величины");

        for (int i = 0; i <= 9; i++) {

            if (StNumB.equals(RimNumArr[i])) {
                ZnachB = i + 1;
                Rim = true;
            } else if (StNumB.equals(AraNum[i])) {
                ZnachB = i + 1;
                Ara = true;
            }
        }

        if (ZnachB == 0) throw new IncorrectDataException("Второе значение недопустимого типа или величины");

        if (Ara & Rim) throw new IncorrectDataException("Одновременно ведены арабские и римские цифры");

        if (!Ara & !Rim) throw new IncorrectDataException("Введены недопустимые символы");

        switch (Z) {
            case "+":
                Rezult = ZnachB + ZnachA;
                if (Rim) {
                    StrRez="Ответ: " + RimNumArr[Rezult - 1]; //System.out.println("Ответ: " + RimNumArr[Rezult - 1]);
                } else StrRez = "Ответ: " + Rezult; //System.out.println("Ответ: " + Rezult);
                break;
            case "-":
                Rezult = ZnachA - ZnachB;
                if (Rim) {
                    if (Rezult < 1)
                        throw new IncorrectDataException("Результат для римских чисел меньше или равен нулю");
                    StrRez="Ответ: " + RimNumArr[Rezult - 1];
                    //System.out.println("Ответ: " + RimNumArr[Rezult - 1]);
                } else StrRez = "Ответ: " + Rezult; // System.out.println("Ответ: " + Rezult);
                break;
            case "*":
                Rezult = ZnachB * ZnachA;
                if (Rim) {
                    StrRez="Ответ: " + RimNumArr[Rezult - 1];
                    //System.out.println("Ответ: " + RimNumArr[Rezult - 1]);
                } else  StrRez = "Ответ: " + Rezult; //System.out.println("Ответ: " + Rezult);
                break;
            case "/":
                Rezult = ZnachA / ZnachB;
                if (Rim) {
                    if (Rezult < 1)
                        throw new IncorrectDataException("Результат для римских чисел меньше или равен нулю");
                    StrRez="Ответ: " + RimNumArr[Rezult - 1]; //System.out.println("Ответ: " + RimNumArr[Rezult - 1]);
                } else StrRez = "Ответ: " + Rezult; //System.out.println("Ответ: " + Rezult);
                break;
        }
        return StrRez;

    }
    public static void Question (String Ques) throws IncorrectDataException {
        System.out.printf(Ques);
        Scanner Prim = new Scanner(System.in);
        String Quest = Prim.nextLine();

        switch (Quest) {
            case "Y":
                Request("Введите пример: ");
                break;
            case "y":
                Request("Введите пример: ");
                break;
            case "N":
                System.out.printf("Работа завершена.");
                return;

            case "n":
                System.out.printf("Работа завершена.");
                return;
            default:
                System.out.printf("Неправильный ввод"+"\n");
                Question ("Продолжить работу? (ДА-Y Нет-N) - ");

        }
}
}
