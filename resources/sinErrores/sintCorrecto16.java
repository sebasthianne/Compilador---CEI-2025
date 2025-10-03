///[SinErrores]
// Prueba declaracion de un estático con un if-else con expresión booleana muy compleja

class Prueba1{

    static int metodo1() {
        if((!(!(a+b>0)&&(c-d<=2))||(e*f<=1233124)&&!(++j/--r<++4))||(m%2==0)){
	    ++a;
	} else{
	    --b;	
	}
	return a+b;
    }
}
