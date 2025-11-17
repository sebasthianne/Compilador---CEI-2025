// Acceso simple a una variable de instancia
// Chequea asignación con conformancia

class A {
    int a1;
    A b;
    
     void m1(){
         b = new B();
    }
    

}


class B extends A{
    
}


class Init{
    static void main()
    { }
}


