

class A {
    B a1;
    int a2;
   
    void a()
    {
	a1.m1(a1);
    }
       
} 

class B extends A{
    A a3;
    
     void m1(A p1)     
    {
        a1.a3.a2 = 4;
        
    }
}


class Init{
    static void main()
    { }
}


