///[SinErrores]
// Prueba declaracion de un metodo dinamico con una referencia encadenada a un método estático

class Prueba1{
    int x;
    int metodo1() {
        return Clase4.m1(x);
    }
}
