///[SinErrores]
// Prueba múltiples clases con modificadores

abstract class Prueba1{
    int m1(){return 1;}
}

static class Prueba2 extends Prueba1{
    int m1(){return 2;}
}

final class Prueba3 extends Prueba2{
    int m1(){return 3;}
}
