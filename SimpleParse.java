class SimpleParse {
    static final boolean _DEBUG = false ;
    Rim2Arab   rim2Arab = new Rim2Arab() ;
    Arab2Rim   arab2Rim = new Arab2Rim() ;
    static final String  arabPattern = "-0123456789" ;
    static final String  rimPattern  = "IVXLCDM" ;
    static final String  operation  = "+-*/" ;
    static final int  pOPERATION = 2 ;
    static final int  pARAB = 0 ;


    static final String[] patternArray    = { arabPattern, rimPattern, operation };
    static String[] patternSign     = { "a", "r", "o" };
    static int[]      _indPattern   =  { 0, 0, 0 };
    static String resPatternSign  = "" ;
    static String[] resLiteral  = new  String[80] ; // максимальное кол-во операндов в строке
    static int indLiteral = 0 ;

    /** SimpleParser
     * Простой разборщик входной строки. (функционал требует расширения)
     * выполняет поиск о обработки с последующим выкладыванием результата в итоговую таблицу
     * (пс Выходные таблицы следует объединить в класс )
     * @param inp - входная строка
     * @return - возвращает код результата ( отрицательный - выявленная ошибка не соответствия шаблонам разбора)
     */
    int SimpleParser (String inp) {

        // инициализация переменных класса
        for ( int i = 0; i< _indPattern.length; i++ ) _indPattern[ i ] =  0 ;
        resPatternSign  = "" ;
        for ( int i = 0; i< resLiteral.length; i++ ) resLiteral[i]  = "" ;
        indLiteral = 0 ;
        boolean found = false ;

        // Цикл по входной строке
        String sym ; // символ из выборки
        int _prevP = 0; // временная переменная
        String _literal = "" ;
/**
 * Основной цикл разюбора строки по шаблонам
 * */
        for ( int i = 0 ; i < inp.length(); i++ ){
            // проверяем на символы пропуска (!) по хорошему нужен отдкльный паттерн
            sym = inp.substring( i, i+1 ).toUpperCase() ;

            if ( sym.equals( " " ) || sym.equals( "\t" )) continue;
            // Перебор паттернов для сравнения
            for (int p = 0; p < patternArray.length; p++ ){

                // Перебор символов текущего паттерна
                for (int j = 0; j < patternArray[p].length(); j++ ){

                    if ( sym.equals( patternArray[p].substring(j, j +1) ) ) {


                        // обработка "-" минуса (может встречаться как в операциях так и в арабских
                        if (    sym.equals("-") && p == pARAB) {
                                if ( i+2 <= inp.length() &&
                                        ( inp.substring( i+1, i+2 ).equals(" ") ||
                                        inp.substring( i+1, i+2 ).equals("\t")||
                                        _indPattern[ p ] != 0) ) {

                                found = false;
                                break;
                            }
                        }

                        //записываем ранее опреленную последовательность и переходим к следующему
                        if (_prevP != p /* && _indPattern[_prevP] != 0*/) {

                            if ( _indPattern[ _prevP ] != 0 ) {

                                resLiteral[indLiteral] = _literal;

                                // !!! костыль для минуса
                                if ( patternSign[_prevP].equals("a") && _literal.equals("-"))
                                    resPatternSign += "o";
                                else
                                    resPatternSign += patternSign[_prevP];

                                _indPattern[_prevP] = 0;
                                _prevP = p;
                                _indPattern[p]++;
                                indLiteral++;
                                _literal = sym ;
                            } else {
                                _indPattern[p]++;
                                _literal += sym;
                                _prevP = p;
                            }
                            found = true;
                            break;
                        }

//                       if (_indPattern[p] == 0) {
//
//                           _prevP = p;
//                           _indPattern[p]++;
//                           _literal += sym;
//                           found = true;
//                           break;
//                       }

                        // если данный патерн продолжается (число/ строка и т.д.)
                        if (p == _prevP || ( sym.equals("-") && p == pARAB && _indPattern[p] ==0 ) ) {
                            _indPattern[p]++;
                            _literal += sym;

                            found = true;
                            break;
                        } else {

                            found = false ;
                            break;
                        }

                    }


                } /// for (j...)

                if ( found ) {
//                       found =false ;
                    break;
                }

            } /// for (p...)

            // если после всех поисков не найдено ни одного соответствия (!) ???
            // генерим синтаксическую ошибку и вываливаемся
            if ( !found ) { // не распознанный символ!

                return -1;
            }
            found = false ;
//               if ( found ) break;

        } // for i...
// сохраняем последнее вхождение
        resLiteral[indLiteral] = _literal;
        resPatternSign += patternSign[_prevP];

//DEBUG
        if ( _DEBUG ) {
            System.out.println("___");
            System.out.println(resPatternSign);
        }

        for ( int i = 0 ; i <= indLiteral; i++){

        }

    return 1 ;
    }

/** execOperation - выполнение операций с арабсими числами
 *
 * @param resLiteral
 * @param patternSign
 * @return
 */
String  execOperation ( String[] resLiteral, String patternSign ){

    int _res = 0 ;
    switch ( resLiteral[1].charAt( 0 ) ) {

        case '+':
            _res = Integer.parseInt( resLiteral[ 0 ] ) + Integer.parseInt( resLiteral[ 2 ] );
            break;

        case '-':
            _res = Integer.parseInt( resLiteral[ 0 ] ) - Integer.parseInt( resLiteral[ 2 ] );
            break;

        case '*':
            _res = Integer.parseInt( resLiteral[ 0 ] ) * Integer.parseInt( resLiteral[ 2 ] );
            break;

        case '/':
            try {

                if ( Integer.parseInt( resLiteral[ 2 ] ) == 0 ) throw new CalcException(13) ;

            } catch ( CalcException e ){

                e.getMsg( );
                return "";
            }
            _res = Integer.parseInt( resLiteral[ 0 ] ) / Integer.parseInt( resLiteral[ 2 ] );
            break;

    } // switch ...

return Integer.toString( _res ) ;
}

/** execOperationRim - выполнение операций с римскими числами
 *
 * @param resLiteral
 * @param patternSign
 * @return
 */
String  execOperationRim ( String[] resLiteral, String patternSign ) throws CalcException {

//    Rim2Arab   rim2Arab = new Rim2Arab() ;
//    Arab2Rim   arab2Rim = new Arab2Rim() ;

    int _res = 0 ;
    switch ( resLiteral[1].charAt( 0 ) ) {

        case '+':
            _res = rim2Arab.Parsing( resLiteral [ 0 ] ) + rim2Arab.Parsing( resLiteral [ 2 ] ) ;
            break;

        case '-':
            try {
                _res = rim2Arab.Parsing(resLiteral[0]) - rim2Arab.Parsing(resLiteral[2]);
                if ( _res <=0 ) throw new CalcException( 1 ) ;//("(!) римские числа не могут быть 0 или отрицательные");

            } catch ( CalcException e) {

                e.getMsg( 1 );
                System.out.println( "(Результат :<" + _res + ">)" + e.indexMsg );
            }
            break;

        case '*':
            _res = rim2Arab.Parsing( resLiteral [ 0 ] ) * rim2Arab.Parsing( resLiteral [ 2 ] ) ;
            break;

        case '/':
            try {
                _res = rim2Arab.Parsing(resLiteral[0]) / rim2Arab.Parsing(resLiteral[2]);
                if ( _res <=0 ) throw new CalcException( 1 ) ;//("(!) римские числа не могут быть 0 или отрицательные");

            } catch ( CalcException e) {

                e.getMsg( 1 );
                System.out.println( "(Результат :<" + _res + ">)" + e.indexMsg );

            }
            break;

    } // switch ...

return ( _res > 0) ? arab2Rim.Parsing ( _res ) : "" ;
}

    /**
     * ResetParser - сброс параметров класса для повторного поиска.
     */
    void ResetParse () {

        resPatternSign  = "" ;
        indLiteral = 0 ;
        for ( int i = 0; i < resLiteral.length; i++ )
            resLiteral[ i ] = "" ;

    }

    /** CheckMaxVal - проверяет максимальное значение уазанного параметра
     *
     * @param- номерпроверяемого парматера
     * @value максимально допустимое значение (включительно)
     * @return true - параметр в допуске
     */
    boolean CheckMaxVal ( int param, int value) {


        try {
            if ( Integer.parseInt(resLiteral[param] ) > value || Integer.parseInt( resLiteral[param] ) > value )
                throw new CalcException(3);

        } catch (CalcException e) {
            e.getMsg(3);
            return false ;
        }

    return true;
    }

    /**
     * проверка входной строки на корректность римских чисел
     * @return true все римские цифры корректны
     */
    boolean CheckRim ( ) {

        for ( int i = 0; i < resPatternSign.length(); i++)

            if ( resPatternSign.substring( i, i+1).equals("r") ){

                if ( rim2Arab.Parsing( resLiteral [ i ] ) == 0 ) return false;
            }

    return true;
    }

    /** CheckMaxValRim - проверяет максимальное значение уазанного параметра в римских числах
     *
     * @param - номер проверяемого парматера
     * @value максимально допустимое значение (включительно)
     * @return true - параметр в допуске
     */
    boolean CheckMaxValRim ( int param, int value) {


        try {
            if ( rim2Arab.Parsing( resLiteral [ 0 ] ) > value || rim2Arab.Parsing( resLiteral [ 2 ] ) > value)
                throw new CalcException(3);

        } catch (CalcException e) {
            e.getMsg(3);
            return false ;
        }

    return true;
    }


    /** getResLiteral - получение значений лексем по разобранной строке для дальнейших операций
     *
     * @return - массив лексем
     */
    String[] getResLiteral() {
        return resLiteral;
    }

    /** getResPatternSign - получение определителей лексем по разобранной строке
     *
     * @return - строка определителей
     */
    String getResPatternSign() {
        return resPatternSign;
    }
} // class SimpleParse
