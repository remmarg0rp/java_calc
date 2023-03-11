class CalcException extends Exception {

    final String[] msg = {

            "(!) не распознанный символ(ы) во входной строке.",         // 0
            "(!) римские числа не могут быть 0 или отрицательные.",     // 1
            "(!) err(2) Не верно определена операци" ,                  // 2
            "(!) err(3) - один из аргументов больше 10.",               // 3
            "(!) err(4)",                                               // 4
            "(!) err(5) Одновременное использование разных систем " +
                    "исчесления недопустимо.",                          // 5
            "(!) err(6)",                                               // 6
            "(!) err(7) недопустимое сочетание символов",               // 7
            "(!) err(8)",                                               // 8
            "(!) err(9) Римские числа записаны не корректно",           // 9
            "(!) err(10) единичный операнд",                            // 10
            "(!) err(11) формат математичской операции не удовлетворяет условию", // 11
            "(!) err(12)",                                              // 12
            "(!) err(13) Деление на 0!",                                // 13


            ""

    };
    int indexMsg = 0 ;
     CalcException ( int indexMsg ){
//        super(msg);
        this.indexMsg = indexMsg ;
         System.out.println( msg [ indexMsg ] );

     }

     String getMsg ( int indexMsg ){

         return msg [ indexMsg ];
     }

    String getMsg (  ){

        return msg [ indexMsg ];
    }

}
