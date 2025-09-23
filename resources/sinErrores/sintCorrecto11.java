///[SinErrores]
// Prueba declaracion de un metodo dinamico con una referencia encadenada a un objeto recién creado

class Prueba1{
    int x;
    int metodo1() {
        return (new Objeto1()).m1(x);
    }
}
