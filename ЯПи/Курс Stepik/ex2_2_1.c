//Определение библиотек ввода, новых типов и malloc (для работы с кучей)
#include<stdio.h> 
#include<malloc.h>
#include<inttypes.h>

//Считывание эл. массива
int64_t read_int64(){
    
    return 
}
//Считывание размера массива
size_t read_size(){
    char num[] = "";
    scanf("%s", num);
    return (size_t) num[0];
}

// заполнить уже выделенный массив array размера size числами
// числа нужно считывать из потока ввода
void array_int_fill( int64_t* array, size_t size ) {

    
    
}

// Считать размер массива в *size, выделить память под массив и заполнить его числами, вызвав array_int_fill
int64_t* array_int_read( size_t* size ) {
  
    int64_t arr = malloc(sizeof(int64_t) * (*size));
    
    array_int_fill(arr, size);
}

int main(){
    array_int_read(read_size);
}