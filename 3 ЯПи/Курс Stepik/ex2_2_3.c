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

    int64_t mini = array[0];

    for(size_t i = 1; i < size; i++){
        if(array[i] < mini) mini = array[i];
    }

    printf("%" PRId64, mini);
}

// Считать размер массива в *size, выделить память под массив и заполнить его числами, вызвав array_int_fill
int64_t* array_int_read( size_t* size ) {
  
    *size = read_size();

    if(*size == 0) intptr_print(x);

    int64_t* arr = malloc(sizeof(int64_t) * (*size));

    array_int_fill(arr, *size);

    return arr;
}

int64_t* array_int_min( int64_t* array, size_t size ) {

    if(size == 0) return NULL;
    if(size == 1) return &array[0];

    int64_t min = 0;
    for(size_t i = 1; i < size; i++){

        if(array[min] > array[i]) min = i;

    }

    return &array[min];

}

// Выводит None если x == NULL, иначе число, на которое указывает x.
void intptr_print( int64_t* x ) {
    if(x == NULL) printf("%s", "None");
    else printf("%d", x);
}

void perform() {

    size_t size;
    scanf("%zu", &size);
    intptr_print(array_int_min(array_int_read(&size), size));
    
}

int main(){

    perform();

}