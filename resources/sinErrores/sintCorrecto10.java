///[SinErrores]
// Prueba declaracion de un metodo dinamico con una expresión compleja

class Prueba1{
    int x;
    int metodo1() {
        x = ("hola"+"chau"-('b'*15));
    }
}
