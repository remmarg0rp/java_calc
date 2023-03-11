import  java.util.Scanner ;
public class Calc {

    static final boolean _DEBUG = false ;
    static final String EXIT = "z" ;
    public static void main(String[] args) throws CalcException {

        final String PROMPT = "Exit: Type '0'\n> " ;
        String input ;      // входная строка
        String resOutput = new String();   // итоговоя строка
        Scanner inStr = new Scanner( System.in ) ;
        boolean exitCalc = false ;
        CalcException   calcException ;

        do {

            System.out.print( PROMPT ) ;
            input = inStr.nextLine() ;

            if ( !input.isBlank() )
                resOutput =  calc( input ) ;

            if ( resOutput.equals( EXIT ) ) return;

            if ( !resOutput.isBlank() ) PrintResul( resOutput );
            //System.out.println( resOutput );

        } while ( ! exitCalc ) ;

    }

    /** * Основной метод Calc */
    static String calc ( String input ) throws CalcException{

//        ParsingLine parsLine = new ParsingLine() ;
//        parsLine.ParsingLine( input );

        SimpleParse    simpleParse = new SimpleParse() ;
        String         resCalc  = new String();
        int            resInput = 0;

        /** (1)
         * Первый проход - разбираем входную строку на литералы,
         * составляем краткую лексемную последовательность для
         * второго прохода
         * (Разборщик написан без использования стандариной библиотнки работы
         * с регулярными выражениями
         */
        simpleParse.ResetParse();
        try {
            resInput = simpleParse.SimpleParser( input ) ;
            if ( resInput < 0 ) throw new CalcException( 0 ) ;
        } catch ( CalcException e) {

            e.getMsg( 0 ) ;
            resCalc = EXIT;
        }

        /** (2)
         * Второй проход
         * - c выделенными операциями и определителями (_pattern)
         * производим проверки и вычисления, сопоставляя их со значениями (_literal)
         */
        if ( resInput >= 0 ) {
            String     _pattern    = simpleParse.getResPatternSign() ;
            String[]   _literal    = simpleParse.getResLiteral() ;

/**
 * проверяем римские на корректность записи
 */
            try {
                if ( !simpleParse.CheckRim() ) throw new CalcException( 9 ) ;
            } catch ( CalcException e) {

                e.getMsg(  ) ;
                return "";
            }

            /**
             * Разбираем правильные лексемы (согласно ТЗ++)
             */
/**
 * Условия выхода
 */
            if ( _pattern.equals( "a" ) && _literal[0].equals( "0" ) ){ // выполняем обыкновенные вычисления

                return EXIT ;
            }

            if ( _pattern.equals( "aoa" ) ){ // выполняем обыкновенные вычисления
/**
 * условие 3
 */
                if ( simpleParse.CheckMaxVal( 0, 10) && simpleParse.CheckMaxVal( 2, 10) )
                    resCalc = simpleParse.execOperation (_literal, _pattern);
            }

            if ( _pattern.equals( "ror" ) ){ // выполняем вычисления c римскими
/**
 * условие 3
 */
                if ( simpleParse.CheckMaxValRim( 0, 10) && simpleParse.CheckMaxValRim( 2, 10) )
                    resCalc = simpleParse.execOperationRim (_literal, _pattern);

            }

/**
 * условие 5
 */
            try {
                if ( _pattern.equals( "roa" ) || _pattern.equals( "aor" )) throw new CalcException( 5 );

            } catch ( CalcException e ){

                e.getMsg( 5 );
                return "";
            }
/**
 * условие 7
 */
            try {
                if ( _pattern.contains( "ra" ) || _pattern.contains( "ar" )) throw new CalcException( 7 );

            } catch ( CalcException e ){

                e.getMsg( 7 );
                return EXIT ;
            }

/**
 * условие 1
 */
            try {
                if ( _pattern.contains( "aoaoa" ) || _pattern.contains( "roror" ) || _pattern.contains( "oo" )
                    ) throw new CalcException( 11 );

            } catch ( CalcException e ){

                e.getMsg(  );
                return EXIT ;
            }
/**
 * доп условия...
 */

/**
 * отрицательные римские
 */
            try {
                if (  _literal[ 0 ].equals("-") && _pattern.contains("or")
                ) throw new CalcException( 11 );

            } catch ( CalcException e ){

                e.getMsg(  );
                return EXIT ;
            }


/**
 * единичный операнд
 */
            try {
                if ( ( _pattern.equals( "a" ) || _pattern.equals( "r" ) || _pattern.equals( "o" )) )
                    throw new CalcException( 10 );

            } catch ( CalcException e ){

                e.getMsg(  );
                return "";
            }

        } // else throw new CalcException ("\n(!) не распознанный символ.");

        return resCalc ;
    }

    static void PrintResul ( String str ) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_WHITE = "\u001B[37m";

        int addSpace = 2 ;
        for ( int i = 0; i < str.length() + addSpace; i++ ) System.out.print("_");
        System.out.println();
        System.out.println( ANSI_YELLOW + str + ANSI_RESET + " |");
        for ( int i = 0; i < str.length() + addSpace; i++ ) System.out.print("-");
        System.out.println();

    }
} // Class Calc
