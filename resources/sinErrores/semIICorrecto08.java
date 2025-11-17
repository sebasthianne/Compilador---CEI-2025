// Chequea retorno con conformancia

class A {
    int a1;
    A b;
    
     A m1(){
         return new B();
     }
    

}


class B extends A{
    
}


class Init{
    static void main()
    { }
}


