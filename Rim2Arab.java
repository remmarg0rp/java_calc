class Rim2Arab {

    static int resR2A = 0;
    static final int rimLEVEL = 3 ; // максимальное кол-вл уровней
    static String[][] rimNum = {
            {"I", "V", "X"}, //level 0
            {"X", "L", "C"}, //level 1
            {"C", "D", "M"}, //level 2
            {"M", "_", "_"}  //level 3
    };

    static String[] rimPattern = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    static String rONE = "";
    static String rFIVE = "";
    static String rTEN = "";
    static String wString = new String(" ") ;
    static int lastIndex = 0 ;

    int Parsing ( String inpRimNum) {
/**
 *  Инициализация переменных класса перед каждым вызовом
 */
        resR2A = 0;
        rONE = "";
        rFIVE = "";
        rTEN = "";
        wString = new String(" ") ;
        lastIndex = 0 ;

//        String inpRimNum = new String("XLIX");
        int level = 0;

        level = rimLevel(inpRimNum);
        SetRimNum(level);
        /**
         * выбираем из строки римских только последовательность из чисел из указанного уровня
         */
        wString = SelWorkRimStr (inpRimNum, lastIndex );

        /**
         * Алгоритм сопоставления выборки с шаблоном записи римских цифр
         */
        String addStr = new String();
        boolean _cmp = false;
        boolean lastParsing = false;

        do {
            if ( level == 0 ) lastParsing = true ;
            for (int k = 0; k < 4; k++) { //4е варианта периобора (поиска и сравнения) заданного числа и шаблона

                for (int l = 0; l < rimPattern.length; l++) {

                    String _cmpStr = addStr + RealRimPattern ( rimPattern[l] );
                    _cmp = wString.equals( _cmpStr );
                    if (_cmp) {
                        resR2A += (l + 1) * Math.pow(10, level);
                        break;
                    }
                    // не получилось - возможно десяток больше?
                } // for (l...)

                if (!_cmp) {
                    resR2A += 10;
                    addStr = rTEN + addStr;
                    continue;
                }

                if (level == 0 || _cmp ) break;
            }   // for (k)

            if ( level == 0 && ! _cmp ) break; // ничего не нашли - выходим с ошибкой;
            if ( level > 0  && ! _cmp ) { // не нашли - ищем на уровне ниже
                level -- ;
                SetRimNum( level ) ;
                resR2A = 0;
                addStr = "";
                continue;
            }
            // нашли, но есть ещё уровень
            if ( _cmp && level >0 && lastIndex == inpRimNum.length() ) break ;
            if ( _cmp && level >0 && lastIndex != inpRimNum.length() ) {

                level--;
                _cmp  = false ;
                SetRimNum(level );
                wString = SelWorkRimStr(inpRimNum, lastIndex);

            } else level--;

        } while (  ! lastParsing );

        if ( !_cmp ) {
//            System.out.println("(!) Римское число не верно записано.");
            resR2A = 0;
        }

    return resR2A ;
    }

    /**
     * RimLevel - вычисление уровня вхождения первой римской цифры
     * @param str - обработаваемая строка с римскими цифрами
     * @return - максимальный уровень римского ряда для начала вычислений
     */
    public static int rimLevel ( String str){
        String sym1 = str.substring( 0, 1 ).toUpperCase() ;
//        String sym2 = str.substring( 1, 2 ).toUpperCase() ;
        int l = 0 ;
        for ( int i= 0; i < rimNum.length; i++)

            for ( int j = 0; j< rimNum[i].length; j++) {

                if ( sym1.equals(rimNum[ rimLEVEL - i ][j]) ) return (rimLEVEL - i) ;
            }
        return l ;
    }

    /** SetRimNum
     * Присвоения шаблоеа чисел римского ряда указанного уровня
     * @param level - максимальный уровень вхождения
     */
     static void SetRimNum ( int level ) {

        rONE  = rimNum[ level ] [0];
        rFIVE = rimNum[ level ] [1];
        rTEN  = rimNum[ level ] [2];
    }

    /**
     * Выбираем строку, согласно текущего уровня римских цифр
     * @param inp
     * @param lastI
     * @return
     */
     static String SelWorkRimStr ( String inp, int lastI ) {
        int j;
        for (j = lastI; j < inp.length(); j++) {

            if (    inp.substring(j, j+1 ).compareTo(rONE) == 0 ||
                    inp.substring(j, j+1 ).compareTo(rFIVE) == 0 ||
                    inp.substring(j, j+1 ).compareTo(rTEN) == 0
            ) continue;
            else break;
        }

        String _str = inp.substring(lastIndex, j);
        lastIndex = j; // индекс для следующей выборки.

        return  _str ;
    }

    /**
     * Подстановка/изменения римских цифр в шаблоне в зависимости от уровня
     * @param str - исходный шаблон
     * @return - реальный шаблон для поиска
     */
     static String RealRimPattern ( String str ){
        String retStr = "" ;
        for ( int i = 0; i < str.length(); i++){
            if ( str.substring(i,i+1).equals( "I" ) ) retStr += rONE ;
            if ( str.substring(i,i+1).equals( "V" ) ) retStr += rFIVE ;
            if ( str.substring(i,i+1).equals( "X" ) ) retStr += rTEN ;

        }

        return retStr ;
    }
}
