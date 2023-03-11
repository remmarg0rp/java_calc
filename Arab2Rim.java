class Arab2Rim {
    String Parsing ( int inpArabNum) { //(!) проверить на ноль и выдать исключение

        /* СЕКЦИЯ ДЕКЛАРАЦИЙ */

        // Матрица основных римских цифр для последующего преобразования в алгоритме "по шаблону"
        // единицы пятёрки и дестки для каждого арабского разряда числа

        String rimONE   = "" ;
        String rimFIVE  = "" ;
        String rimTEN   = "" ;



        String[][] rimPattern = {
                {"I", "V", "X"},
                {"X", "L", "C"},
                {"C", "D", "M"},
                {"M", "_", "_"}
        };

        /* СЕКЦИЯ ИСПОЛНЕНИЯ */

        int[] matrixNum = new int[10];
        int countNum  = 0 ;
        String rimResult = "" ;

        while ( inpArabNum > 10 ) {

            matrixNum[ countNum++ ] = inpArabNum % 10 ;
            inpArabNum = inpArabNum / 10 ;

        }
        matrixNum[ countNum ] = inpArabNum % 10 ;

        int _ten = -1 ;
        for ( int i = 0; i <= countNum; i++ ) {

            rimONE  = rimPattern [ i ][ 0 ] ;
            rimFIVE = rimPattern [ i ][ 1 ] ;
            rimTEN  = rimPattern [ i ][ 2 ] ;

            switch ( matrixNum [ i ] ) {

                case 0: _ten++ ;
                    break;

                case 1:
                    rimResult = rimONE + rimResult ;
                    break;

                case 2:
                    rimResult = rimONE + rimONE + rimResult ;
                    break;

                case 3:
                    rimResult = rimONE + rimONE + rimONE + rimResult ;
                    break;

                case 4:
                    rimResult = rimONE + rimFIVE + rimResult ;
                    break;

                case 5:
                    rimResult = rimFIVE + rimResult ;
                    break;

                case 6:
                    rimResult = rimFIVE + rimONE + rimResult ;
                    break;

                case 7:
                    rimResult = rimFIVE + rimONE + rimONE + rimResult ;
                    break;

                case 8:
                    rimResult = rimFIVE + rimONE + rimONE + rimONE + rimResult ;
                    break;

                case 9:
                    rimResult = rimONE + rimTEN + rimResult ;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + matrixNum[i]);
            }

        } // for (i...)

        if ( rimResult.isEmpty() && _ten == countNum ) rimResult = rimTEN ;

    return rimResult ;
    } //Parsing

}
