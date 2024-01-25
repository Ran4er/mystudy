#include<stdio.h>
#include<stdlib.h>
#include<inttypes.h>

int64_t* array_int_min( int64_t* array, size_t size ) {

    if(size == 0) return NULL;
    if(size == 1) return &array[0];

    int64_t min = 0;
    for(size_t i = 1; i < size; i++){

        if(array[min] > array[i]) min = i;

    }

    return &array[min];

}

int main(){

    int64_t* arr = malloc(sizeof(int64_t) * 5);
    //4 8 9 2 3
    arr[0] = 4;
    arr[1] = 8;
    arr[2] = 9;
    arr[3] = 2;
    arr[4] = 3;

    size_t size = 5;

    printf("%" PRId64, *array_int_min(arr, size));

}