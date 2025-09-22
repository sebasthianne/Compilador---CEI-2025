///[SinErrores]
// Prueba declaracion de un estático con un if-else y un return

class Prueba1{

    static int metodo1() {
        if(a+b>0){
	    ++a;
	} else{
	    --b;	
	}
	return a+b;
    }
}
