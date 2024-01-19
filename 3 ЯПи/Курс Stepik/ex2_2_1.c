//Определение библиотек ввода, новых типов и malloc (для работы с кучей)
#include<stdio.h> 
#include<stdlib.h>
#include<inttypes.h>

//Считывание эл. массива
int64_t read_int64(){
    int64_t num;
    scanf("%" SCNd64, &num);
    return num;
}
//Считывание размера массива
size_t read_size(){
    size_t size;
    scanf("%zu", &size);
    return size;
}

// заполнить уже выделенный массив array размера size числами
// числа нужно считывать из потока ввода
void array_int_fill( int64_t* array, size_t size ) {

    for(size_t i = 0; i < size; i++){

        array[i] = read_int64();
    }
}

// Считать размер массива в *size, выделить память под массив и заполнить его числами, вызвав array_int_fill
int64_t* array_int_read( size_t* size ) {
  
    *size = read_size();

    if(*size == 0) return 0;

    int64_t* arr = malloc(sizeof(int64_t) * (*size));

    array_int_fill(arr, *size);

    return arr;
}

int main(){
    size_t size;
    int64_t* arr = array_int_read(&size);

    for(size_t i = 0; i < size; i++){

        printf("%d ", arr[i]);

    }

    free(arr);

    return 0;
}